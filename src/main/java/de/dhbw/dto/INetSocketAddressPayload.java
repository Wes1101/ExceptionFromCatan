package de.dhbw.dto;

import lombok.Getter;

@Getter
public class INetSocketAddressPayload implements NetworkPayload {
    public int PORT = -1;

    public String IP = "0.0.0.0";

    public INetSocketAddressPayload() {} // for gson
    public INetSocketAddressPayload(int port, String ip) {
        this.PORT = port;
        this.IP = ip;

    }
}
