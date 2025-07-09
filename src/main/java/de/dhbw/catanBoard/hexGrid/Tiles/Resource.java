
package de.dhbw.catanBoard.hexGrid.Tiles;

import de.dhbw.catanBoard.hexGrid.Node;
import de.dhbw.catanBoard.hexGrid.Tile;
import de.dhbw.player.Bank;
import de.dhbw.resources.Resources;
import de.dhbw.gamePieces.Building;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a resource-producing hex tile on the Catan board.
 * <p>
 * Each resource tile has a specific resource type and can be blocked by the robber.
 * When triggered by a dice roll, adjacent players may receive resources unless the tile is blocked.
 * </p>
 */
@Getter
@Setter
public class Resource extends Tile {

    /**
     * Indicates whether this tile is currently blocked (e.g. by the robber).
     */
    private boolean blocked;

    /**
     * Constructs a resource tile with the given resource type and corner nodes.
     *
     * @param resourceType the type of resource this tile produces
     * @param nodes        the corner nodes surrounding this tile
     */
    public Resource(Resources resourceType, Node[] nodes) {
        super(resourceType, nodes);
        this.blocked = false;
    }

    /**
     * Triggers the resource production for this tile.
     * <p>
     * If the tile is not blocked, all adjacent buildings owned by players will generate resources.
     * Settlements produce 1 unit, cities produce 2 units.
     * </p>
     *
     * @param bank the central bank that manages and distributes resources
     */
    public void trigger(Bank bank) {
        if (blocked) return;

        for (Node node : this.getHexTileNodes()) {
            Building building = node.getBuilding();
            if (building != null && building.getOwner() != null) {
                int amount = switch (building.getBuildingType()) {
                    case SETTLEMENT -> 1;
                    case CITY -> 2;
                    default -> 0;
                };
                bank.removeResources(this.getResourceType(), amount, building.getOwner());
            }
        }
    }
}
