package de.dhbw.network.client;

import com.google.gson.Gson;
import de.dhbw.network.server.NetMsgType;
import de.dhbw.network.server.NetworkMessage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

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

            NetworkMessage request = new NetworkMessage(NetMsgType.DISCOVER_SERVER, null);
            String jsonSend = new Gson().toJson(request);
            log.info("NetworkMessage prepared:{} ", jsonSend);
            byte[] sendData = jsonSend.getBytes();
            DatagramPacket sendPacket;

            try {
                sendPacket = new DatagramPacket(sendData, sendData.length,
                        InetAddress.getByName("255.255.255.255"), DISCOVERY_PORT);
                socket.send(sendPacket);
                log.info("Broadcast sent to 255.255.255.255");
            } catch (IOException e) {
                log.error("Error sending broadcast: {}", e.getMessage());
                return;
            }

            socket.setSoTimeout(2000);
            try {
                while (true) {
                    byte[] recvBuf = new byte[256];
                    DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
                    socket.receive(receivePacket);
                    String jsonReceive = new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);
                    NetworkMessage response = new Gson().fromJson(jsonReceive, NetworkMessage.class);
                    if (response.getType() == NetMsgType.IS_SERVER) {
                        InetSocketAddress serverAddress = (InetSocketAddress) response.getData();
                        // TODO: Übergabe an Client class
                        log.info("Found game server at {}:{}", serverAddress.getHostString(), serverAddress.getPort());
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
