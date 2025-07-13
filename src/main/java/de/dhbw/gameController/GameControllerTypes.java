package de.dhbw.gameController;

/**
 * Defines the execution mode for the {@link GameController}.
 * <ul>
 *     <li>{@code LOCAL} – Game is played on a single machine with direct GUI access.</li>
 *     <li>{@code SERVER} – GameController runs as a server and manages remote clients.</li>
 *     <li>{@code CLIENT} – GameController runs as a client and receives instructions from the server.</li>
 * </ul>
 * This enum is used to route game logic and user interactions appropriately depending on the game's setup.
 */
public enum GameControllerTypes {
    LOCAL,
    SERVER,
    CLIENT
}
