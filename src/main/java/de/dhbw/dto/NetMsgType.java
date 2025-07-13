package de.dhbw.dto;

/**
 * Enumerates the possible types of network messages exchanged
 * between a client and a server during setup and gameplay.
 */
public enum NetMsgType {

  /** Represents an undefined or uninitialized message type (should not be used). */
  NO_TYPE,

  /** Message sent by a client to discover available servers on the network. */
  DISCOVER_SERVER,

  /** Server's response to a discovery request, including its IP and port. */
  IS_SERVER,

  /** Indicates that the game has officially started. */
  GAME_START
}
