package de.dhbw.client;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;

/**
 * Represents a simple TCP client for connecting to a server,
 * sending messages, and handling server responses.
 * <p>
 * Handles socket creation, connection, and cleanup.
 * A {@link ServerHandler} thread is launched upon successful connection.
 * </p>
 */
@Slf4j
public class NetworkClient {

  /** The socket used for communication with the server. */
  private Socket clientSocket = null;

  /** Writer used to send messages to the server. */
  private PrintWriter out = null;

  /** Default port used for discovery (not directly used here but can be reused). */
  public static final int DISCOVERY_PORT = 54321;

  /** Constructs a new network client. */
  public NetworkClient() {}

  /**
   * Connects to a remote server using the provided host and port.
   * <p>
   * Also initializes the output stream and starts a {@link ServerHandler} thread.
   * </p>
   *
   * @param host the server IP or hostname
   * @param port the port number to connect to
   * @throws IOException if connection or stream setup fails
   */
  public void connect(final String host, final int port) throws IOException {
    clientSocket = new Socket();
    try {
      clientSocket.connect(new InetSocketAddress(host, port));
      log.info("Connected to {}:{}", host, port);
    } catch (IOException e) {
      log.error("Could not connect to {}:{} - {}", host, port, e.getMessage());
      if (!clientSocket.isClosed()) {
        try {
          clientSocket.close();
        } catch (IOException closeEx) {
          log.warn("Error closing socket after failed connect: {}", closeEx.getMessage());
        }
      }
      throw e;
    }

    try {
      this.out = new PrintWriter(
              new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8),
              true
      );
    } catch (IOException e) {
      log.error("Error initializing output stream: {}", e.getMessage());
    }

    new Thread(
            new ServerHandler(clientSocket),
            String.format("ServerHandler:%s", clientSocket.getInetAddress())
    ).start();

    log.info("Server handler started.");
  }

  /**
   * Closes the client connection and output stream.
   *
   * @throws IOException if an error occurs while closing the socket
   */
  public void close() throws IOException {
    if (out != null) {
      out.close();
    }

    if (clientSocket != null && !clientSocket.isClosed()) {
      log.info("Disconnected from {}:{}", clientSocket.getInetAddress(), clientSocket.getPort());
      clientSocket.close();
    }
  }

  /**
   * Main method for manually connecting to a server via command line input.
   * Prompts the user for IP and port, then attempts to connect.
   */
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
