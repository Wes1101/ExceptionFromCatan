
package de.dhbw.catanBoard.hexGrid;

import de.dhbw.resources.Resources;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a single hexagonal tile on the Catan board.
 * <p>
 * Each tile has a specific resource type and maintains references to its surrounding corner nodes.
 * These nodes are used to determine which players receive resources and where buildings can be placed.
 * </p>
 */
@Getter
@Setter
public class Tile {

    /**
     * The type of resource this tile produces.
     * Can be one of the {@link Resources} types, or NONE (e.g., for a desert).
     */
    private Resources resourceType;

    /**
     * The six corner nodes surrounding this tile.
     * Used to determine which players are adjacent to the tile.
     */
    private Node[] hexTileNodes;

    /**
     * Constructs a new Tile with a specific resource type and surrounding nodes.
     * <p>
     * If no nodes are provided, an empty array of six nodes is initialized.
     * </p>
     *
     * @param resourceType the resource type associated with this tile
     * @param nodes        the six corner nodes (can be null)
     */
    public Tile(Resources resourceType, Node[] nodes) {
        this.resourceType = resourceType;
        if (nodes != null) {
            this.hexTileNodes = nodes;
        } else {
            this.hexTileNodes = new Node[6];
        }
    }
}
