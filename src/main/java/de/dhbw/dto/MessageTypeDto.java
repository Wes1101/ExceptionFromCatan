package de.dhbw.dto;
/**
 * DTO class for the message type.
 *
 * @author David Willig
 * @version 1.0
 * @since 2024-06-09
 */
public class MessageTypeDto implements NetworkPayload{
    public NetMsgType type;

    public MessageTypeDto(NetMsgType type) {
        this.type = type;
    }
}
