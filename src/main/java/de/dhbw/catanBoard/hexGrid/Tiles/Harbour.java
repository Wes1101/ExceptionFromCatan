
package de.dhbw.catanBoard.hexGrid.Tiles;

import de.dhbw.catanBoard.hexGrid.Node;
import de.dhbw.catanBoard.hexGrid.Tile;
import de.dhbw.player.Bank;
import de.dhbw.player.Player;
import de.dhbw.resources.Resources;
import lombok.Getter;

import java.util.ArrayList;

/**
 * Represents a harbour tile on the Catan board.
 * <p>
 * Harbours allow players to trade resources with the bank at improved exchange rates.
 * Some harbours are specific (2:1), while others are generic (3:1).
 * </p>
 */
@Getter
public class Harbour extends Tile {

    /**
     * Constructs a harbour tile with a given trade resource type and adjacent nodes.
     * <p>
     * If the resource type is {@code Resources.NONE}, it is a 3:1 generic harbour.
     * Otherwise, it's a 2:1 specific harbour.
     * </p>
     *
     * @param resourceType the resource this harbour specializes in, or NONE for generic
     * @param nodes        the nodes surrounding the harbour tile
     */
    public Harbour(Resources resourceType, Node[] nodes) {
        super(resourceType, nodes);
    }

    /**
     * Allows the given player to perform a trade via this harbour if adjacent.
     * <p>
     * - 3:1 harbours allow any 3 resources for 1 of any other.<br>
     * - 2:1 harbours require 2 of the specified resource for 1 of any other.
     * </p>
     *
     * @param player the player attempting to trade
     * @param bank   the bank facilitating the trade
     */
    public void trade(Player player, Bank bank) {
        for (Node node : this.getHexTileNodes()) {
            if (node.getBuilding() != null && node.getBuilding().getOwner().equals(player)) {

                if (this.getResourceType() == Resources.NONE) {
                    // Generic 3:1 trade
                    ArrayList<Resources> tradable = new ArrayList<>();
                    for (Resources res : player.getResources().keySet()) {
                        if (player.getResources(res) >= 3) {
                            tradable.add(res);
                        }
                    }

                    // Simulated trade (should be replaced with GUI input)
                    Resources[] chosenRes = {Resources.STONE, Resources.WOOD};

                    if (chosenRes != null && player.getResources(chosenRes[1]) >= 3) {
                        bank.removeResources(chosenRes[0], 1, player);
                        player.removeResources(chosenRes[1], 3, bank);
                    }
                } else {
                    // Specific 2:1 trade
                    if (player.getResources(this.getResourceType()) >= 2) {
                        Resources[] chosenRes = {Resources.STONE, this.getResourceType()};

                        if (chosenRes != null) {
                            bank.removeResources(chosenRes[0], 1, player);
                            player.removeResources(chosenRes[1], 2, bank);
                        }
                    }
                }
                break;
            }
        }
    }
}
