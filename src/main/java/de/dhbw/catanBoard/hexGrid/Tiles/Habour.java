package de.dhbw.catanBoard.hexGrid.Tiles;

import de.dhbw.catanBoard.hexGrid.Node;
import de.dhbw.catanBoard.hexGrid.Tile;
import de.dhbw.player.Bank;
import de.dhbw.player.Player;
import de.dhbw.resources.Resource;
import de.dhbw.resources.Resources;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Habour extends Tile {

    public Habour(Resources resourceType, Node[] nodes) {
        super(resourceType, nodes);
    }

    public void trade(Player player, Bank bank) {
        for (Node node : this.HexTileNodes) {
            if (node.getBuilding().getOwner().equals(player)) { //TODO: equals
                if (this.resourceType == Resources.NONE) {
                    ArrayList<Resources> resources = new ArrayList<>();
                    for (Resources resource : player.getResources().keySet()) {
                        if (player.getResources(resource) >= 3) {
                            resources.add(resource);
                        }
                    }
                    //Resources[] chosenRes = gui.trade(resources);
                    Resources[] chosenRes = {Resources.WOOD, Resources.STONE};
                    if (chosenRes != null) {
                        bank.removeResources(chosenRes[0], 1, player);
                        player.removeResources(chosenRes[1], 3, bank);
                    }
                }
                else {
                    Resources resource;
                    if (player.getResources(this.resourceType) >= 2) {
                        resource = this.resourceType;
                    }
                    else {
                        resource = null;
                    }
                    //Resources[] chosenRes = gui.trade(respurce);
                    Resources[] chosenRes = {resource, Resources.STONE};
                    if (chosenRes != null) {
                        bank.removeResources(chosenRes[0], 1, player);
                        player.removeResources(chosenRes[1], 2, bank);
                    }
                }

                break;
            }
        }
    }
}
