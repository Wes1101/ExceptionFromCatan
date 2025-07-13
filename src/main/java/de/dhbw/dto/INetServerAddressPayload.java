package de.dhbw.dto;

import lombok.Getter;

/**
 * Data Transfer Object (DTO) representing a server's network address.
 * <p>
 * Used to serialize or transmit IP and port information, e.g. for client-server connection setup.
 * This class is compatible with JSON serialization libraries like Gson.
 * </p>
 */
@Getter
public class INetServerAddressPayload implements NetworkPayload {

    /** The server port number. Defaults to -1 if uninitialized. */
    public int PORT = -1;

    /** The server IP address. Defaults to "0.0.0.0" if uninitialized. */
    public String IP = "0.0.0.0";

    /**
     * Default constructor required for serialization frameworks like Gson.
     */
    public INetServerAddressPayload() {}

    /**
     * Constructs a new address payload with the specified port and IP.
     *
     * @param port the server's port
     * @param ip   the server's IP address as a string
     */
    public INetServerAddressPayload(int port, String ip) {
        this.PORT = port;
        this.IP = ip;
    }
}
