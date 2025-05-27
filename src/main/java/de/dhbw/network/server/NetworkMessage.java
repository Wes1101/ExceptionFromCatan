package de.dhbw.network.server;

public class NetworkMessage {
    private Type type;
    private Object data;

    public NetworkMessage(Type type, Object data) {
        this.type = type;
        this.data = data;
    }

}
