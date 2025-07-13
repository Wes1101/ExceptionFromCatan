package de.dhbw.gamePieces;

import de.dhbw.player.Player;

/**
 * Abstract base class for all building types in the game (e.g., Settlement, City, Street).
 * <p>
 * Contains shared attributes and logic relevant to all buildings, such as the owning player
 * and the building type. Specific building types should extend this class.
 * </p>
 */
@lombok.Getter
public abstract class Building {

    /** The player who owns this building. */
    protected Player owner;

    /** The type of the building (e.g., CITY, STREET, SETTLEMENT). */
    BuildingTypes buildingType;

    /**
     * Constructs a building with the specified owner.
     *
     * @param owner the player who owns the building
     */
    public Building(Player owner) {
        this.owner = owner;
    }
}
