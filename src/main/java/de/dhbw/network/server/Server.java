package de.dhbw.network.server;

import java.io.*;
import java.net.*;
import lombok.extern.slf4j.Slf4j;

/**
 * A basic multithreaded server that accepts up to four clients.
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
  public static final int PORT = 8080;

  /**
   * Constructs a new Server and starts it on the specified port.
   * Initializes the ServerSocket and logs the server start.
   */
  public Server() {
    try {
      serverSocket = new ServerSocket(PORT);
      log.info("Server started on port {}", PORT);

      while (true) {
        try {
          initConnections();
        } catch (IOException e) {
          log.error(e.getMessage());
          break;
        }
      }
    } catch (IOException e) {
      log.error("Error starting server on port {}: {}", PORT, e.getMessage());
    }
  }

  private void initConnections() throws IOException {
    Socket clientSocket = serverSocket.accept();
    log.info("Client connected: {}", clientSocket.getInetAddress());

    if (clientSocket.isConnected()) {
      new Thread(
        () -> {
          ClientHandler clientHandler = new ClientHandler(clientSocket);
          clientHandler.readMessage();
          clientHandler.close();
        }
      )
      .start();
    }
  }

  /**
   * Closes the server socket if it is open.
   * Logs a message indicating the socket has been closed.
   * If an error occurs during closure, it logs the error message.
   */
  private void close() {
    try {
      if (serverSocket != null && !serverSocket.isClosed()) {
        serverSocket.close();
        log.info("Server socket closed.");
      }
    } catch (IOException e) {
      log.error("Error closing server socket: {}", e.getMessage());
    }
  }

  /**
   * Creates the Server Instanze.
   *
   * @param args Argument String for the main method
   * @throws IOException if an I/O error occurs when creating the server socket
   */
  public static void main(String[] args) throws IOException {
    new Server();
  }
}
