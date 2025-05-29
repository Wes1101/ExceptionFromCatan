package de.dhbw.network.server;

/**
 * Enumerates the possible types of network messages exchanged
 * between client and server.
 *
 * @author David Willig
 * @version 1.0
 * @since 2024-06-09
 */
public enum Type {
  CONNECT,
  DISCONNECT,
  MESSAGE,
  ERROR
}
