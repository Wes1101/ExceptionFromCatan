package de.dhbw.dto;

/**
 * Data Transfer Object (DTO) for transmitting message types over the network.
 * <p>
 * Wraps a {@link NetMsgType} enum value to represent the type of a message being sent.
 * Implements {@link NetworkPayload} to support serialization and mapping.
 * </p>
 */
public class MessageTypeDto implements NetworkPayload {

    /** The type of the network message (e.g., CONNECT, DISCONNECT, GAME_STATE, etc.). */
    public NetMsgType type;

    /**
     * Constructs a new message type DTO with the given type.
     *
     * @param type the type of message being wrapped
     */
    public MessageTypeDto(NetMsgType type) {
        this.type = type;
    }
}
