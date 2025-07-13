package de.dhbw.client;

import com.google.gson.Gson;
import de.dhbw.dto.INetServerAddressPayload;
import de.dhbw.dto.NetMsgType;
import de.dhbw.dto.NetworkPayload;
import de.dhbw.mapping.INetServerAddressMapper;
import de.dhbw.network.MessageFactory;
import de.dhbw.network.NetworkMessage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * Handles the discovery of servers in the local network using UDP broadcast.
 * <p>
 * This class implements {@link Runnable} and can be used in a separate thread.
 * It sends a broadcast discovery request over UDP and listens for server responses.
 * </p>
 */
@Slf4j
public class DiscoveryClient implements Runnable {

    /** Port used for discovery broadcast communication. */
    public static final int DISCOVERY_PORT = 54321;

    public DiscoveryClient() {}

    /**
     * Starts the discovery process by:
     * <ol>
     *     <li>Sending a {@code DISCOVER_SERVER} request to the broadcast address.</li>
     *     <li>Waiting for responses from servers responding with {@code IS_SERVER} messages.</li>
     *     <li>Logging each found server address.</li>
     * </ol>
     */
    @Override
    public void run() {
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket();
            socket.setBroadcast(true);

            // Create discovery message
            NetworkMessage request = new NetworkMessage(NetMsgType.DISCOVER_SERVER, null);
            String jsonSend = MessageFactory.toJson(request);
            log.info("NetworkMessage prepared: {}", jsonSend);

            byte[] sendData = jsonSend.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sendPacket = new DatagramPacket(
                    sendData,
                    sendData.length,
                    InetAddress.getByName("255.255.255.255"),
                    DISCOVERY_PORT
            );

            // Send broadcast
            socket.send(sendPacket);
            log.info("Broadcast sent to 255.255.255.255");

            // Set timeout for responses
            socket.setSoTimeout(2000);

            // Listen for incoming responses
            while (true) {
                try {
                    byte[] recvBuf = new byte[256];
                    DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
                    socket.receive(receivePacket);

                    String jsonReceive = new String(
                            receivePacket.getData(),
                            0,
                            receivePacket.getLength(),
                            StandardCharsets.UTF_8
                    );

                    NetworkMessage<? extends NetworkPayload> response = MessageFactory.fromJson(jsonReceive);

                    if (response.getType() == NetMsgType.IS_SERVER &&
                            response.data instanceof INetServerAddressPayload inetServerAddress) {

                        InetSocketAddress serverAddress = new INetServerAddressMapper().fromPayload(inetServerAddress);
                        log.info("Found game server at {}:{}", serverAddress.getHostString(), serverAddress.getPort());

                        // TODO: Pass address to Client class or connection handler
                    }
                } catch (SocketTimeoutException e) {
                    log.info("No more servers found.");
                    break;
                } catch (IOException e) {
                    log.error("Error receiving packet: {}", e.getMessage());
                    break;
                }
            }

        } catch (SocketException e) {
            log.error("Could not create socket: {}", e.getMessage());
        } catch (IOException e) {
            log.error("Error sending broadcast: {}", e.getMessage());
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
