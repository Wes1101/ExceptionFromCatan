package de.dhbw.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;

/**
 * A runnable thread responsible for continuously reading and processing messages from the server.
 * <p>
 * Used by {@link NetworkClient} to asynchronously handle incoming data.
 * </p>
 */
@Slf4j
public class ServerHandler implements Runnable {

  /** Socket connected to the server. */
  private final Socket socket;

  /** Reader used to read incoming messages from the server. */
  private BufferedReader in;

  /**
   * Constructs a new {@code ServerHandler} bound to the specified socket.
   *
   * @param socket the active connection to the server
   */
  public ServerHandler(Socket socket) {
    this.socket = socket;
    try {
      this.in = new BufferedReader(
              new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
      );
    } catch (IOException e) {
      log.error("Error initializing input stream: {}", e.getMessage());
    }
  }

  /**
   * Continuously reads and logs messages from the server.
   * Terminates when the connection is closed or an error occurs.
   */
  @Override
  public void run() {
    String message;
    try {
      while (!socket.isClosed() && (message = in.readLine()) != null) {
        log.info("Received from server: {}", message);
        handleServerMessage(message);
      }
    } catch (IOException e) {
      log.error("Connection to server lost: {}", e.getMessage());
    }
  }

  /**
   * Processes a message received from the server.
   * <p>
   * This method should be extended to perform actions based on the content of the message.
   * </p>
   *
   * @param message the message received from the server
   */
  private void handleServerMessage(String message) {
    // TODO: Implement custom handling logic (e.g., JSON parsing, commands)
    log.info("Server: {}", message);
  }
}
