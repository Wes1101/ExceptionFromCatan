package de.dhbw.network.client;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;

/**
 * This class represents a simple socket client for connecting to a server,
 * sending and receiving messages, and managing the connection lifecycle.
 *
 * @author David Willig
 * @version 1.0
 * @since 2024-06-09
 */
@Slf4j
public class Client {
    private Socket clientSocket = null;
    private PrintWriter out = null;

    public Client() {
    }

    /**
     * Connects to a server at the specified host and port.
     *
     * @param host the server hostname or IP address
     * @param port the server port number
     * @throws IOException if an I/O error occurs when creating the socket
     */
    public void connect(final String host, final int port) throws IOException {
        clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress(host, port));
        log.info("Connected to {}:{}", host, port);

        try {
            this.out = new PrintWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8), true)
            ;
        } catch (IOException e) {
            log.error("Error initializing streams: {}", e.getMessage());
        }

        new Thread(
                new ServerHandler(clientSocket)
        ).start();
    }

    /**
     * Closes the client connection and associated streams.
     *
     * @throws IOException if an I/O error occurs when closing the connection
     */
    public void close() throws IOException {
        if (out != null) out.close();
        if (clientSocket != null && !clientSocket.isClosed()) {
            clientSocket.close();
            log.info(
                    "Disconnected from {}:{}",
                    clientSocket.getInetAddress(),
                    clientSocket.getPort()
            );
        }
    }

    public static void main(String[] args) {
        try {
            new Client().connect("localhost", 8080);
        } catch (IOException e) {
            log.error("Error connecting to server: {}", e.getMessage());
        }
    }
}
