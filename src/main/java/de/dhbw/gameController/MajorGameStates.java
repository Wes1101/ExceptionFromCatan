package de.dhbw.gameController;

/**
 * Represents the high-level phase of the game.
 * <ul>
 *     <li>{@code BEGINNING} – The setup phase where players place initial settlements and roads.</li>
 *     <li>{@code MAIN} – The main game loop where players take turns, roll dice, collect resources, and perform actions.</li>
 * </ul>
 * Used by {@link GameController} to control overall game flow.
 */
public enum MajorGameStates {
    BEGINNING,
    MAIN
}
