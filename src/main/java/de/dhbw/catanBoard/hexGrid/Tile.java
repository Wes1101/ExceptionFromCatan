
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
     * Axial coordinates (q, r) of the tile on the hex grid.
     */
    private IntTupel coordinates;

    /**
     * Dice number assigned to this tile. Determines which dice roll triggers resource production.
     */
    private int diceNumber;

    /**
     * Constructs a new Tile with a specific resource type and surrounding nodes.
     * <p>
     * If no nodes are provided, an empty array of six nodes is initialized.
     * </p>
     *
     * @param resourceType the resource type associated with this tile
     * @param diceNumber   the number token on the tile (used in dice rolls)
     * @param nodes        the six corner nodes (can be null)
     * @param coordinates  axial coordinates of the tile on the board
     */
    public Tile(Resources resourceType, int diceNumber, Node[] nodes, IntTupel coordinates) {
        this.resourceType = resourceType;
        this.coordinates = coordinates;
        this.diceNumber = diceNumber;
        if (nodes != null) {
            this.hexTileNodes = nodes;
        } else {
            this.hexTileNodes = new Node[6];
        }
    }
}
