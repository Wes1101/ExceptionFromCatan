package de.dhbw.network.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import lombok.extern.slf4j.Slf4j;

/**
 * Handels the client connection to the Server
 *
 */

@Slf4j
public class ClientHandler {
  private Socket clientSocket = null;
  private DataInputStream dataInputStream = null;

  public ClientHandler(Socket clientSocket) {
    this.clientSocket = clientSocket;
    try {
      this.dataInputStream =
        new DataInputStream(
          new BufferedInputStream(clientSocket.getInputStream())
        );
    } catch (IOException e) {
      log.error("Error initializing DataInputStream", e);
    }
  }

  public String readMessage() {
    String message = null;
    try {
      message = dataInputStream.readUTF();
      log.info("Received message: {}", message);
    } catch (IOException e) {
      log.error("Error reading message from client", e);
    }
    return message;
  }

  public void close() {
    try {
      if (dataInputStream != null) {
        dataInputStream.close();
      }
      if (clientSocket != null && !clientSocket.isClosed()) {
        clientSocket.close();
        log.info("Client socket closed successfully.");
      }
    } catch (IOException e) {
      log.error("Error closing client handler", e);
    }
  }
}
