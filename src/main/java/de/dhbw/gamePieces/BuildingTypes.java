package de.dhbw.gamePieces;

/**
 * Enum representing the different types of buildings in the game.
 * <ul>
 *     <li>{@code SETTLEMENT} - A basic building that generates resources.</li>
 *     <li>{@code CITY} - An upgraded settlement that generates more resources.</li>
 *     <li>{@code STREET} - A road used for expanding across the board.</li>
 * </ul>
 * Used to classify instances of {@link Building} and its subclasses.
 */
public enum BuildingTypes {
    SETTLEMENT,
    CITY,
    STREET
}
