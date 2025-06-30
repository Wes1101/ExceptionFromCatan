package de.dhbw.dto;

public class MessageTypeDto implements NetworkPayload{
    public NetMsgType type;

    public MessageTypeDto(NetMsgType type) {
        this.type = type;
    }
}
