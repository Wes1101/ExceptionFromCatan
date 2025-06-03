package de.dhbw.network.server;

/**
 * Represents a message exchanged between client and server,
 * containing a message type and associated data.
 *
 * @author David Willig
 * @version 1.0
 * @since 2024-06-09
 */
public class NetworkMessage {
  /**
   * The type of the network message.
   */
  private Type type;

  /**
   * The data payload of the message.
   */
  private Object data;

  /**
   * Constructs a new NetworkMessage with the given type and data.
   *
   * @param type the type of the message
   * @param data the data payload
   */
  public NetworkMessage(Type type, Object data) {
    this.type = type;
    this.data = data;
  }
}
