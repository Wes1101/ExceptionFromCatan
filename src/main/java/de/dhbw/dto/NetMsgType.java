package de.dhbw.dto;

/**
 * Enumerates the possible types of network messages exchanged
 * between client and server.
 *
 * @author David Willig
 * @version 1.0
 * @since 2024-06-09
 */
public enum NetMsgType {
  NO_TYPE, // empty type (not possible/valid)
  DISCOVER_SERVER, // Indicates a request to discover a server
  IS_SERVER, // Respond to discover server with IP and Port
  GAME_START // Indicates the start of a game
}
