package de.dhbw.server;

import com.google.gson.Gson;
import de.dhbw.dto.INetServerAddressPayload;
import de.dhbw.gameController.GameController;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

import de.dhbw.dto.NetMsgType;
import de.dhbw.gameController.GameControllerTypes;
import de.dhbw.mapping.INetServerAddressMapper;
import de.dhbw.network.MessageFactory;
import de.dhbw.network.NetworkMessage;
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
   * List of writers for all connected clients for broadcasting messages.
   */
  private final List<PrintWriter> clientWriters = new CopyOnWriteArrayList<>(); //TODO: @David Map<PlayerID, PrintWriters>

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

  private static String getHostIP() throws UnknownHostException {
    return InetAddress.getLocalHost().getHostAddress();
  }

  private void initServer() {
    try {
      serverSocket = new ServerSocket(0);
      TCP_PORT = serverSocket.getLocalPort();
      log.info("Server started on port ({}) and host ({})", TCP_PORT, getHostIP());
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
            INetServerAddressPayload payload = new INetServerAddressMapper().toPayload(new InetSocketAddress(getHostIP(), TCP_PORT));
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
  private void initConnections() throws IOException, InterruptedException {
    int currentConnections = 0;
    while (currentConnections < gameController.getPlayerAmount()) {
      try {
        Socket clientSocket = serverSocket.accept();
        log.info("Client connected: {}", clientSocket.getInetAddress());
        currentConnections++;

        try {
          clientWriters.add(
            new PrintWriter(
              new OutputStreamWriter(
                clientSocket.getOutputStream(),
                java.nio.charset.StandardCharsets.UTF_8
              ),
              true
            )
          );
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
  }

  /**
   * Broadcasts a message to all connected clients.
   *
   * @param message the message to broadcast
   */
  public void broadcast(String message) {
    for (PrintWriter writer : clientWriters) {
      writer.println(message);
    }
    log.info("Broadcasted message to all clients: {}", message);
  }

  //TODO: @David Funktion zum versenden der Message an einen spezifischen Client

  /**
   * Closes the server socket if it is open.
   * Logs a message indicating the socket has been closed.
   * If an error occurs during closure, it logs the error message.
   */
  private void close() {
    try {
      for (PrintWriter writer : clientWriters) {
        writer.close();
      }
      clientWriters.clear();
      if (serverSocket != null && !serverSocket.isClosed()) {
        serverSocket.close();
        log.info("Server socket closed.");
      }
    } catch (IOException e) {
      log.error("Error closing server socket: {}", e.getMessage());
    }
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    NetworkServer server = new NetworkServer(new GameController(3, 0, GameControllerTypes.SERVER, false)); //TODO @David
    server.initConnections();
  }
}
