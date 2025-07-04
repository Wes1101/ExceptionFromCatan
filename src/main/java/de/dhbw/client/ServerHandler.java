package de.dhbw.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;

/**
 * Thread that continuously listens for messages from the server
 * and processes them.
 *
 * @author David Willig
 * @version 1.0
 * @since 2024-06-09
 */
@Slf4j
public class ServerHandler implements Runnable {
  /**
   * The socket connected to the server.
   */
  private final Socket socket;

  /**
   * Reader for incoming messages from the server.
   */
  private BufferedReader in;

  /**
   * Constructs a new ServerHandler for the given socket.
   *
   * @param socket the socket connected to the server
   */
  public ServerHandler(Socket socket) {
    this.socket = socket;
    try {
      this.in =
        new BufferedReader(
          new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
        );
    } catch (IOException e) {
      log.error("Error initializing input stream: {}", e.getMessage());
    }
  }

  /**
   * Listens for messages from the server and processes them.
   * Cleans up resources when the thread ends.
   */
  @Override
  public void run() {
    String message;
    try {
      while (!socket.isClosed() && (message = in.readLine()) != null) {
        log.info("Received from client: {}", message);
        handleServerMessage(message);
      }
    } catch (IOException e) {
      log.error("Connection to server lost: {}", e.getMessage());
    }
  }

  /**
   * Handles a message received from the server.
   *
   * @param message the message received
   */
  private void handleServerMessage(String message) {
    // TODO: @David Implement message handling logic
    log.info("Server: {}", message);
  }
}
