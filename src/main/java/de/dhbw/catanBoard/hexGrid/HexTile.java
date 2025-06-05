package de.dhbw.catanBoard.hexGrid;

//import de.dhbw.bank.Bank;
//import de.dhbw.player.Player;
import de.dhbw.resources.Resources;
import java.util.Arrays;
import lombok.Getter;

/**
 *Sechseck Kachel auf dem Spielfeld.
 *Jede davon bekommt eine zugeordnete Würfelzahl zugeordnet.
 *Sie kennt auch anliegende Felder und Kanten.
 * */

@Getter
public class HexTile {
  private int diceNumber;
  private Resources resourceType;
  private Node[] HexTileNodes;
  private boolean blocked;

  public HexTile(int diceNumber, Resources resourceType, Node[] nodes) {
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
