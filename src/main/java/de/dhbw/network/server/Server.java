package de.dhbw.network.server;

import de.dhbw.gameController.GameController;
import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
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
public class Server {
  /**
   * The server socket used to accept client connections.
   */
  private ServerSocket serverSocket = null;

  /**
   * The port number the server listens on.
   */
  private static final int PORT = 8080;

  /**
   * The host address the server binds to.
   */
  private static final String HOST = "localhost";

  /**
   * List of writers for all connected clients for broadcasting messages.
   */
  private final List<PrintWriter> clientWriters = new CopyOnWriteArrayList<>();

  private final GameController gameController;

  private static final CountDownLatch startLatch = new CountDownLatch(1);

  /**
   * Constructs a new Server and starts it on the specified port.
   * Initializes the ServerSocket and logs the server start.
   */
  public Server(GameController gameController) {
    this.gameController = gameController;

    try {
      serverSocket = new ServerSocket(PORT, 15, InetAddress.getByName(HOST));
      log.info("Server started on port {} and host {}", PORT, HOST);
    } catch (IOException e) {
      log.error("Error starting server on port {}: {}", PORT, e.getMessage());
    }
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
          new Thread(new ClientHandler(clientSocket, startLatch)).start();
        }
      } catch (IOException e) {
        log.error(e.getMessage());
        break;
      }
    }

    log.info("All clients connected. Starting game...");
    startLatch.countDown();
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

  public static void main(String[] args)
    throws IOException, InterruptedException {
    Server server = new Server(new GameController(3, 0));
    server.initConnections();
  }
}
