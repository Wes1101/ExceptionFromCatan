package de.dhbw.resources;

/**
 * Enum representing all possible resource types in the game.
 * <p>
 * These resources are used for building, trading, and managing player inventory.
 * </p>
 * <ul>
 *     <li>{@code WOOD} – Used for roads and settlements</li>
 *     <li>{@code STONE} – Used primarily for cities and development cards</li>
 *     <li>{@code BRICK} – Used for roads and settlements</li>
 *     <li>{@code SHEEP} – Used for settlements and development cards</li>
 *     <li>{@code WHEAT} – Used for settlements, cities, and development cards</li>
 *     <li>{@code NONE} – Placeholder or sentinel value indicating no resource</li>
 * </ul>
 */
public enum Resources {
    WOOD,
    STONE,
    BRICK,
    SHEEP,
    WHEAT,
    NONE
}
