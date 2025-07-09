
package de.dhbw.catanBoard.hexGrid;

import de.dhbw.gamePieces.Building;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node on the Catan board.
 * <p>
 * Nodes correspond to intersection points where players can place settlements or cities.
 * Each node may be adjacent to up to three hex tiles.
 * </p>
 */
@Getter
@Setter
public class Node {

    private final int id;
    private List<Tile> hexNeighbors;
    private Building building;

    /**
     * Constructs a new Node with a unique identifier.
     *
     * @param id the unique index of the node on the board
     */
    public Node(int id) {
        this.id = id;
        this.hexNeighbors = new ArrayList<>();
        this.building = null;
    }

    /**
     * Assigns a building (e.g., settlement or city) to this node.
     *
     * @param building the building to place on this node
     */
    public void setBuilding(Building building) {
        this.building = building;
    }

    /**
     * Associates a hex tile with this node as a neighboring tile.
     * <p>
     * Each node can be shared by up to three hex tiles in a standard Catan board layout.
     * </p>
     *
     * @param tile the hex tile to associate with this node
     */
    public void addHexTile(Tile tile) {
        hexNeighbors.add(tile);
    }
}
