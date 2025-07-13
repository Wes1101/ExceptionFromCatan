
package de.dhbw.catanBoard.hexGrid.Tiles;

import de.dhbw.catanBoard.hexGrid.IntTupel;
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
     * Constructs a water tile.
     * <p>
     * Initializes a tile with no resource type, no dice number, and no adjacent nodes.
     * It is placed at the specified board coordinates.
     * </p>
     *
     * @param coords axial coordinates of this water tile on the hex grid
     */
    public Water(IntTupel coords) {
        super(null, 0, null, coords);
    }
}
