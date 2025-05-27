package de.dhbw.network.client;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.*;

/**
 * A simple socket client for connecting, sending, receiving, and closing a connection to a server.
 */
@Slf4j
public class Client {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

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
        socket = new Socket(host, port);
        socket.connect(new InetSocketAddress(host, port));
        log.info("Connected to {}:{}", host, port);

        out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    /**
     * Closes the client connection and associated streams.
     *
     * @throws IOException if an I/O error occurs when closing the connection
     */
    public void close() throws IOException {
        if (out != null) out.close();
        if (in != null) in.close();
        if (socket != null && !socket.isClosed()) {
            socket.close();
            log.info("Disconnected from {}:{}", socket.getInetAddress(), socket.getPort());
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
