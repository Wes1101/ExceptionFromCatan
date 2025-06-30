package de.dhbw.network;

import de.dhbw.dto.NetMsgType;
import de.dhbw.dto.NetworkPayload;
import lombok.Getter;

import java.io.Serializable;

/**
 * Represents a message exchanged between client and server,
 * containing a message type and associated data.
 *
 * @author David Willig
 * @version 1.0
 * @since 2024-06-09
 */

@Getter
public class NetworkMessage<T extends NetworkPayload> {
  /**
   * The type of the network message.
   */
  public NetMsgType type = NetMsgType.NO_TYPE;

  /**
   * The data payload of the message.
   */
  public T data;

  /**
   * Constructs a new NetworkMessage with the given type and data.
   *
   * @param type the type of the message
   * @param data the data payload
   */
  public NetworkMessage(NetMsgType type, T data ) {
    this.type = type;
    this.data = data;
  }
}
