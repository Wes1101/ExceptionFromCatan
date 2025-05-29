package de.dhbw.network.client;

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
    private final Socket socket;
    private BufferedReader in;

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

    @Override
    public void run() {
        String message;
        try {
            while (!socket.isClosed() && (message = in.readLine()) != null) {
                // Process the received message
                handleServerMessage(message);
            }
        } catch (IOException e) {
            log.error("Connection to server lost: {}", e.getMessage());
        }
    }

    private void handleServerMessage(String message) {
        // TODO: Implement message handling logic
        log.info("Server: {}", message);
    }
}
