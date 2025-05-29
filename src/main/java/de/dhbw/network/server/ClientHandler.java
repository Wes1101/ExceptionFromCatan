package de.dhbw.network.server;

import java.io.*;
import java.net.Socket;

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
    private Socket clientSocket = null;
    private BufferedReader in = null;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            this.in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream(), java.nio.charset.StandardCharsets.UTF_8)
            );
        } catch (IOException e) {
            log.error("Error initializing streams", e);
        }
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                log.info("Received from client: {}", message);
            }
        } catch (IOException e) {
            log.warn("Client disconnected.", e);
        } finally {
            try {
                clientSocket.close();
                log.info("Client socket closed in finally block.");
            } catch (IOException e) {
                log.error("Error closing client socket in finally block.", e);
            }
        }
    }

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
