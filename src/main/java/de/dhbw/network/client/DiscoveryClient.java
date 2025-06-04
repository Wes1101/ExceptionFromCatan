package de.dhbw.network.client;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;

/**
 * Handles the discovery of servers in the local network using UDP broadcast.
 * This class implements Runnable and can be executed in a separate thread.
 *
 * The DiscoveryClient sends a discovery request and waits for server responses.
 *
 * @author David Willig
 * @version 1.0
 * @since 2024-06-09
 */
@Slf4j
public class DiscoveryClient implements Runnable {

    public static final int DISCOVERY_PORT = 54321;

    public DiscoveryClient() {}

    /**
     * Starts the discovery process for finding available servers in the local network.
     */
    @Override
    public void run() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            socket.setBroadcast(true);

            String request = "DISCOVER_SERVER";
            byte[] sendData = request.getBytes();

            try {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                        InetAddress.getByName("255.255.255.255"), DISCOVERY_PORT);
                socket.send(sendPacket);
                log.info("Broadcast sent.");
            } catch (IOException e) {
                log.error("Error sending broadcast: {}", e.getMessage());
                return;
            }

//            socket.setSoTimeout(2000);
            try {
                while (true) {
                    byte[] recvBuf = new byte[256];
                    DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
                    socket.receive(receivePacket);
                    String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    if (message.startsWith("GAME_SERVER:")) {
//                        String[] parts = message.split(":");
//                        String serverIp = parts[1];
//                        String serverPort = parts[2];
                        log.info("Found game server at {}:{}");
                    }
                }
            } catch (SocketTimeoutException e) {
                log.info("No more servers found.");
            } catch (IOException e) {
                log.error("Error receiving packet: {}", e.getMessage());
            }
        } catch (SocketException e) {
            log.error("Could not create socket: {}", e.getMessage());
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
