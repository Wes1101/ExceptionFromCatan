package de.dhbw.gamePieces;

import java.util.Map;

/**
 This abstract class manages all core functions of buildings in the game.
 * Subclasses include specific building types like City and Street.
 *
 * @author Atussa Mehrawari
 * @version 1.0
 *
 */

public abstract class Building extends GamePieces {
    protected Player owner;

    public Building(Player owner, HexTile location) {
        super(location);
        this.owner = owner;
    }
    public Player getOwner() {
        return owner;
    }

    /**
     * Returns the resource cost required to build this building.
     * @return Map of ResourceType to quantity
     */
    public abstract Map<ResourceType, Integer> getBuildCost();

    /**
     * Returns the type of the building (e.g., "City", "Street").
     * @return the building type as a String
     */
    public abstract String getType();
}
