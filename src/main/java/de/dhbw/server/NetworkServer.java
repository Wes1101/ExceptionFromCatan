package de.dhbw.server;

import com.google.gson.Gson;
import de.dhbw.dto.INetServerAddressPayload;
import de.dhbw.gameController.GameController;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

import de.dhbw.dto.NetMsgType;
import de.dhbw.gameController.GameControllerTypes;
import de.dhbw.mapping.INetServerAddressMapper;
import de.dhbw.network.MessageFactory;
import de.dhbw.network.NetworkMessage;
import de.dhbw.player.Player;
import lombok.extern.slf4j.Slf4j;

/**
 * A basic multithreaded server that accepts client connections,
 * manages communication, and handles server lifecycle events.
 *
 * @author David Willig
 * @version 1.0
 * @since 2024-06-09
 */
@Slf4j
public class NetworkServer {
  /**
   * The server socket used to accept client connections.
   */
  private ServerSocket serverSocket = null;

  /**
   * The port number the server listens on.
   */
  public static final int DISCOVERY_PORT = 54321;

  /**
   * The port number the server listens on.
   */
  private static int TCP_PORT = 0;

  /**
   * Map of client IDs to writers for all connected clients for broadcasting messages.
   */
  private final ConcurrentHashMap<Integer, PrintWriter> clientWriterMap = new ConcurrentHashMap<>(); // clientId -> PrintWriter

  private final GameController gameController;

  private static final CountDownLatch startLatch = new CountDownLatch(1);

  /**
   * Constructs a new Server and starts it on the specified port.
   * Initializes the ServerSocket and logs the server start.
   */
  public NetworkServer(GameController gameController) {
    this.gameController = gameController;

    this.initServer();
    //TODO: @FrontEnd this.startDiscoveryThread();
  }

  /**
   * Returns the IP address the server is bound to.
   *
   * @return the server's IP address as a String, or null if unavailable
   */
  public String getIp() { //TODO: @David richtige Interface auswählen
    try {
      for (NetworkInterface ni : java.util.Collections.list(NetworkInterface.getNetworkInterfaces())) {
        for (InetAddress addr : java.util.Collections.list(ni.getInetAddresses())) {
          if (!addr.isLoopbackAddress() && addr instanceof Inet4Address) {
            return addr.getHostAddress();
          }
        }
      }
    } catch (SocketException e) {
      log.error("Error getting server IP: {}", e.getMessage());
    }
    return null;
  }

  /**
   * Returns the TCP port the server is listening on.
   *
   * @return the server's TCP port
   */
  public int getPort() {
    if (serverSocket != null && serverSocket.isBound()) {
      return serverSocket.getLocalPort();
    }
    return -1;
  }


  private void initServer() {
    try {
      serverSocket = new ServerSocket(0);
      TCP_PORT = serverSocket.getLocalPort();
      log.info("Server started on port ({}) and host ({})", TCP_PORT, getIp());
    } catch (IOException e) {
      log.error("Error starting server on port ({}): {}", TCP_PORT, e.getMessage());
    }
  }

  private void startDiscoveryThread() {
    new Thread(() -> {
      try (DatagramSocket udpDiscSocket = new DatagramSocket(DISCOVERY_PORT)) {
        log.info("Discovery UDP Socket started on UDP-Port {}", DISCOVERY_PORT);

        byte[] buf = new byte[256];
        while (startLatch.getCount() > 0) {
          DatagramPacket packet = new DatagramPacket(buf, buf.length);
          udpDiscSocket.receive(packet);
          log.info("Received UDP packet from {}", packet.getAddress().getHostAddress());
          String json = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);
          NetworkMessage received = new Gson().fromJson(json, NetworkMessage.class);
          if (received.getType() == NetMsgType.DISCOVER_SERVER) {
            INetServerAddressPayload payload = new INetServerAddressMapper().toPayload(new InetSocketAddress(getIp(), TCP_PORT));
            NetworkMessage<INetServerAddressPayload> serverAddress = new NetworkMessage<>(NetMsgType.IS_SERVER, payload);
            String response = MessageFactory.toJson(serverAddress);
            log.info("NetworkMessage prepared: {}", response);
            byte[] responseBytes = response.getBytes();
            DatagramPacket responsePacket = new DatagramPacket(
                    responseBytes, responseBytes.length, packet.getAddress(), packet.getPort()
            );
            udpDiscSocket.send(responsePacket);
            log.info("Discovery-Antwort an {}:{}", packet.getAddress(), packet.getPort());
          }
        }
      } catch (IOException e) {
        log.error("UDP Discovery Thread error: {}", e.getMessage());
      }
    }, "DiscoverySocket").start();
    log.info("Starting Discovery Thread...");
  }

  /**
   * Accepts incoming client connections and starts a handler for each client.
   *
   * @throws IOException if an I/O error occurs when accepting connections
   */
  public void initConnections() throws IOException, InterruptedException {

    Player[] players = gameController.getPlayers();

    int currentConnections = 0;
    while (currentConnections < gameController.getPlayerAmount()) {
      try {
        Socket clientSocket = serverSocket.accept();
        log.info("Client connected: {}", clientSocket.getInetAddress());
        currentConnections++;

        try {
          PrintWriter writer = new PrintWriter(
            new OutputStreamWriter(
              clientSocket.getOutputStream(),
              java.nio.charset.StandardCharsets.UTF_8
            ),
            true
          );
          clientWriterMap.put(players[currentConnections-1].getId(), writer);
        } catch (IOException e) {
          log.error("Error initializing streams: {}", e.getMessage());
        }

        if (clientSocket.isConnected()) {
          new Thread(
                  new ClientHandler(clientSocket, startLatch),
                  String.format("ClientHandler:%s", clientSocket.getInetAddress())
          ).start();
        }
      } catch (IOException e) {
        log.error(e.getMessage());
        break;
      }
    }

    log.info("All clients connected. Starting game...");
    startLatch.countDown();
    log.info("Game started with {} players.", gameController.getPlayerAmount());
    //TODO: @David Game controller Game Start
  }

  /**
   * Broadcasts a message to all connected clients.
   *
   * @param message the message to broadcast
   */
  public void broadcast(String message) { //TODO: @David Payload interface
    for (PrintWriter writer : clientWriterMap.values()) {
      writer.println(message);
    }
    log.info("Broadcasted message to all clients: {}", message);
  }

  /**
   * Sends a message to a specific client identified by their player ID.
   *
   * @param playerId the unique ID of the player
   * @param message  the message to send
   */
  public void sendToClient(int playerId, String message) {//TODO: @David Payload interface
    PrintWriter writer = clientWriterMap.get(playerId);
    if (writer != null) {
      writer.println(message);
      log.info("Sent message to client {}: {}", playerId, message);
    } else {
      log.warn("No client found with ID {} to send message.", playerId);
    }
  }

  /**
   * Closes the server socket if it is open.
   * Logs a message indicating the socket has been closed.
   * If an error occurs during closure, it logs the error message.
   */
  private void close() {
    try {
      for (PrintWriter writer : clientWriterMap.values()) {
        writer.close();
      }
      clientWriterMap.clear();
      if (serverSocket != null && !serverSocket.isClosed()) {
        serverSocket.close();
        log.info("Server socket closed.");
      }
    } catch (IOException e) {
      log.error("Error closing server socket: {}", e.getMessage());
    }
  }

  //TODO: @David Rausmachen
  public static void main(String[] args) throws IOException, InterruptedException {
    NetworkServer server = new NetworkServer(new GameController(3, 0, GameControllerTypes.SERVER, false));
    server.initConnections();
  }
}
