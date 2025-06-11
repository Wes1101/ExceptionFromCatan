package de.dhbw.catanBoard.hexGrid;
//import de.dhbw.bank.Bank;
//import de.dhbw.player.Player;
import de.dhbw.resources.Resources;
import lombok.Getter;

import java.util.Arrays;

/**
 *Sechseck Kachel auf dem Spielfeld.
 *Jede davon bekommt eine zugeordnete Würfelzahl zugeordnet.
 *Sie kennt auch anliegende Felder und Kanten.
 * */

@Getter
public class HexTile {
    private int diceNumber;
    private Resources resourceType;
    private Node[] HexTileNodes = new Node[6];
    private boolean blocked;

    //final int q;
    //final int r;

    public HexTile(int diceNumber, Resources resourceType, Node[] nodes)
    {
        //this.q = q;
        //this.r = r;
        this.diceNumber = diceNumber;
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

//    public void triggerHexTile(int amount, Bank bank, Player player) {
//        //bank.removeResources(resourceType.getName(), amount, player);
//    }

}