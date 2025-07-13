package de.dhbw.server;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;

/**
 * Handles the communication and connection management for a single client
 * connected to the server.
 *
 * @author David Willig
 * @version 1.0
 * @since 2024-06-09
 */
@Slf4j
public class ClientHandler implements Runnable {
  /**
   * The socket for the connected client.
   */
  private Socket clientSocket = null;

  /**
   * Reader for incoming messages from the client.
   */
  private BufferedReader in = null;

  private CountDownLatch startLatch = null;

  /**
   * Constructs a new ClientHandler for the given client socket.
   *
   * @param clientSocket the socket connected to the client
   */
  public ClientHandler(Socket clientSocket, CountDownLatch startLatch) {
    this.clientSocket = clientSocket;
    this.startLatch = startLatch;
    try {
      this.in =
        new BufferedReader(
          new InputStreamReader(
            clientSocket.getInputStream(),
            java.nio.charset.StandardCharsets.UTF_8
          )
        );
    } catch (IOException e) {
      log.error("Error initializing streams", e);
    }
  }

  /**
   * Listens for messages from the client and logs them.
   * Closes resources when the client disconnects.
   */
  @Override
  public void run() {
    log.info("Client waits for start signal...");
    try {
      startLatch.await();
    } catch (InterruptedException e) {
      log.error("Client handler interrupted while waiting for start signal", e);
    }
    log.info("Client handler started.");

    String message;
    try {
      while (!clientSocket.isClosed() && (message = in.readLine()) != null) {
        log.info("Received from client: {}", message);
        handleClientMessage(message);
      }
    } catch (IOException e) {
      log.warn("Client disconnected.", e);
    } finally {
      close();
    }
  }

  /**
   * Handles a message received from a Client.
   *
   * @param message the message received
   */
  private void handleClientMessage(String message) {
    // TODO: @David Implement message handling logic
    log.info("Server: {}", message);
  }

  /**
   * Closes the client handler's resources.
   */
  public void close() {
    try {
      if (in != null) in.close();
      if (clientSocket != null && !clientSocket.isClosed()) {
        clientSocket.close();
        log.info("Client socket closed successfully.");
      }
    } catch (IOException e) {
      log.error("Error closing client handler", e);
    }
  }
}
