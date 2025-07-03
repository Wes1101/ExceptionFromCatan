package de.dhbw.catanBoard.hexGrid;

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
    public Resources resourceType;
    public Node[] HexTileNodes;

    public Tile(Resources resourceType, Node[] nodes)
    {
        this.resourceType = resourceType;
        this.HexTileNodes = nodes;
    }

    public String getAllHexTileNodes() {
        int[] nodeIDs = new int[HexTileNodes.length];
        for (int i = 0; i < HexTileNodes.length; i++) {
            nodeIDs[i] = HexTileNodes[i].id;
        }
        return Arrays.toString(nodeIDs);
    }

}
