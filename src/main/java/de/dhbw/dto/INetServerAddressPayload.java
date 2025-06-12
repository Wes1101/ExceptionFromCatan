package de.dhbw.dto;

import lombok.Getter;

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
