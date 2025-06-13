package de.dhbw.catanBoard.hexGrid;
//import de.dhbw.bank.Bank;
//import de.dhbw.player.Player;
import de.dhbw.player.Bank;
import de.dhbw.resources.Resources;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

/**
     *Sechseck Kachel auf dem Spielfeld.
     *Jede davon bekommt eine zugeordnete Würfelzahl zugeordnet.
     *Sie kennt auch anliegende Felder und Kanten.
     * */

    @Getter
    @Setter
public class Tile {
    private Resources resourceType;
    private Node[] HexTileNodes;
    private boolean blocked;

    public Tile(Resources resourceType, Node[] nodes)
    {
        this.resourceType = resourceType;
        this.HexTileNodes = nodes;
        this.blocked = false;
    }

    public String getAllHexTileNodes() {
        int[] nodeIDs = new int[HexTileNodes.length];
        for (int i = 0; i < HexTileNodes.length; i++) {
            nodeIDs[i] = HexTileNodes[i].id;
        }
        return Arrays.toString(nodeIDs);
    }

    public void trigger(Bank bank) {
        for (Node node : HexTileNodes) {
            if (node.getPlayer() != null) {
                if (node.getBuilding().getBuildingType() == node.getBuilding().getBuildingType()) {
                    bank.removeResources(resourceType, 1, node.getPlayer());
                } else if (node.getBuilding().getBuildingType() == node.getBuilding().getBuildingType()) {
                    bank.removeResources(resourceType, 2, node.getPlayer());
                }
            }
        }
    }

}