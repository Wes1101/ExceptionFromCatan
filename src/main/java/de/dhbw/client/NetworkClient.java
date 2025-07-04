package de.dhbw.client;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;

/**
 * This class represents a simple socket client for connecting to a server,
 * sending and receiving messages, and managing the connection lifecycle.
 *
 * @author David Willig
 * @version 1.0
 * @since 2024-06-09
 */
@Slf4j
public class NetworkClient {
  /**
   * The socket used for client-server communication.
   */
  private Socket clientSocket = null;

  /**
   * Writer for sending messages to the server.
   */
  private PrintWriter out = null;

  public static final int DISCOVERY_PORT = 54321;

  /**
   * Constructs a new Client instance.
   */
  public NetworkClient() {
  }

  /**
   * Connects to a server at the specified host and port.
   *
   * @param host the server hostname or IP address
   * @param port the server port number
   * @throws IOException if an I/O error occurs when creating the socket
   */
  public void connect(final String host, final int port) throws IOException {
    clientSocket = new Socket();
    try {
      clientSocket.connect(new InetSocketAddress(host, port));
      log.info("Connected to {}:{}", host, port);
    } catch (IOException e) {
      log.error("Could not connect to {}:{} - {}", host, port, e.getMessage());
      if (clientSocket != null && !clientSocket.isClosed()) {
        try {
          clientSocket.close();
        } catch (IOException closeEx) {
          log.warn("Error closing socket after failed connect: {}", closeEx.getMessage());
        }
      }
      throw e;
    }

    try {
      this.out =
        new PrintWriter(
          new OutputStreamWriter(
            clientSocket.getOutputStream(),
            StandardCharsets.UTF_8
          ),
          true
        );
    } catch (IOException e) {
      log.error("Error initializing streams: {}", e.getMessage());
    }

    new Thread(
            new ServerHandler(clientSocket),
            String.format("ServerHandler:%s", clientSocket.getInetAddress())
    ).start();
    log.info("Server Handler started.");
  }

  /**
   * Closes the client connection and associated streams.
   *
   * @throws IOException if an I/O error occurs when closing the connection
   */
  public void close() throws IOException {
    if (out != null) out.close();
    if (clientSocket != null && !clientSocket.isClosed()) {
      clientSocket.close();
      log.info(
        "Disconnected from {}:{}",
        clientSocket.getInetAddress(),
        clientSocket.getPort()
      );
    }
  }

  public static void main(String[] args) {
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      System.out.print("Enter server IP address: ");
      String ip = reader.readLine();
      System.out.print("Enter server port: ");
      int port = Integer.parseInt(reader.readLine());

      NetworkClient client = new NetworkClient();
      client.connect(ip, port);
    } catch (IOException e) {
      log.error("Error connecting to server: {}", e.getMessage());
    }
  }
}
