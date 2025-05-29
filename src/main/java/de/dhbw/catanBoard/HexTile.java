package de.dhbw.catanBoard;
import de.dhbw.bank.Bank;
import de.dhbw.player.Player;
import de.dhbw.resources.Resource;
import de.dhbw.resources.Wheat;
import lombok.Getter;

import java.util.List;
import java.util.ArrayList;

    /**
     *Sechseck Kachel auf dem Spielfeld.
     *Jede davon bekommt eine zugeordnete Würfelzahl zugeordnet.
     *Sie kennt auch anliegende Felder und Kanten.
     * */

    @Getter
public class HexTile {
    private int diceNumber;
    private String resourceType;
    private Node[] HexTileNodes = new Node[6];

    final int q;
    final int r;

    public HexTile(int q, int r, int diceNumber, String resourceType, Node[] nodes)
    {
        this.q = q;
        this.r = r;
        this.diceNumber = diceNumber;
        this.resourceType = resourceType;
        this.HexTileNodes = nodes;
    }

    public void triggerHexTile(int amount, Bank bank, Player player) {
        //bank.removeResources(resourceType.getName(), amount, player);
    }

}