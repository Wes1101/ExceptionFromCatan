package de.dhbw.network;


import com.google.gson.Gson;
import de.dhbw.dto.INetServerAddressPayload;
import de.dhbw.dto.MessageTypeDto;
import de.dhbw.dto.NetMsgType;
import de.dhbw.dto.NetworkPayload;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * Factory class for serializing and deserializing network messages to and from JSON.
 * Uses Gson for JSON processing and supports type-safe deserialization based on message type.
 */
@Slf4j
public class MessageFactory {
    private static final Gson gson = new Gson();
    private static final Map<NetMsgType, Type> typeMap = Map.of(
            NetMsgType.IS_SERVER, new TypeToken<NetworkMessage<INetServerAddressPayload>>(){}.getType()
    );

    /**
     * Serializes a NetworkMessage to its JSON representation.
     *
     * @param msg the message to serialize
     * @return JSON string representation of the message
     */
    public static String toJson(NetworkMessage<? extends NetworkPayload> msg) {
        log.debug("Serializing message of type: {}", msg.type);
        return gson.toJson(msg);
    }

    /**
     * Deserializes a JSON string into a NetworkMessage of the appropriate type.
     *
     * @param json the JSON string to deserialize
     * @return the deserialized NetworkMessage
     * @throws IllegalArgumentException if the message type is unknown
     */
    public static NetworkMessage<? extends NetworkPayload> fromJson(String json) {
        log.debug("Deserializing message from JSON: {}", json);
        NetworkMessage<MessageTypeDto> meta = gson.fromJson(json, new TypeToken<NetworkMessage<INetServerAddressPayload>>(){}.getType());
        Type t = typeMap.get(meta.type);
        if (t == null) {
            log.error("Unknown message type: {}", meta.type);
            throw new IllegalArgumentException("Unknown Type: " + meta.type);
        }
        return gson.fromJson(json, t);
    }
}
