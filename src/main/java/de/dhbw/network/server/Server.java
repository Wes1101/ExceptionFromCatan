package de.dhbw.network.server;

import java.io.*;
import java.net.*;

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
    private static final String HOST = "localhost";

    // PrintWriter for server-side communication
    private PrintWriter out = null;

    /**
     * Constructs a new Server and starts it on the specified port.
     * Initializes the ServerSocket and logs the server start.
     */
    public Server() {
        try {
            serverSocket = new ServerSocket(PORT, 15, InetAddress.getByName(HOST));
            log.info("Server started on port {} and host {}", PORT, HOST);
        } catch (IOException e) {
            log.error("Error starting server on port {}: {}", PORT, e.getMessage());
        }
    }

    private void initConnections() throws IOException {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                log.info("Client connected: {}", clientSocket.getInetAddress());

                // Initialize BufferedReader and PrintWriter for this connection
                try {
                    out = new PrintWriter(
                            new OutputStreamWriter(clientSocket.getOutputStream(), java.nio.charset.StandardCharsets.UTF_8), true
                    );
                } catch (IOException e) {
                    log.error("Error initializing streams: {}", e.getMessage());
                }

                if (clientSocket.isConnected()) {
                    new Thread(
                            new ClientHandler(clientSocket)
                    ).start();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
                break;
            }
        }
    }

    /**
     * Closes the server socket if it is open.
     * Logs a message indicating the socket has been closed.
     * If an error occurs during closure, it logs the error message.
     */
    private void close() {
        try {
            if (out != null) out.close();
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
        new Server().initConnections();
    }
}
