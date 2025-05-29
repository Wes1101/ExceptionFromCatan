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
  private Type type;
  private Object data;

  public NetworkMessage(Type type, Object data) {
    this.type = type;
    this.data = data;
  }
}
