
package de.dhbw.catanBoard.hexGrid.Tiles;

import de.dhbw.catanBoard.hexGrid.Tile;

/**
 * Represents a water tile on the Catan board.
 * <p>
 * Water tiles are non-interactive and do not produce resources or allow building.
 * They are placed around the edge of the map to simulate the ocean.
 * </p>
 */
public class Water extends Tile {

    /**
     * Constructs a water tile with no resource type and no adjacent nodes.
     */
    public Water() {
        super(null, null);
    }
}
