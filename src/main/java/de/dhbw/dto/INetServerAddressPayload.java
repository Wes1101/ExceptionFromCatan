package de.dhbw.dto;

import lombok.Getter;
/**
 * DTO class for an inet server address.
 *
 * @author David Willig
 * @version 1.0
 * @since 2024-06-09
 */


@Getter
public class INetServerAddressPayload implements NetworkPayload {
    public int PORT = -1;

    public String IP = "0.0.0.0";

    public INetServerAddressPayload() {} // for gson
    public INetServerAddressPayload(int port, String ip) {
        this.PORT = port;
        this.IP = ip;

    }
}
