package de.dhbw.gameRules;


import de.dhbw.catanBoard.CatanBoard;
import de.dhbw.catanBoard.hexGrid.Edge;
import de.dhbw.gamePieces.Building;
import de.dhbw.player.Player;

/*
erste siedlung bauen:
    -keine feindlichen siedlungen benachbart
    -knoten noch nicht bebaut

siedlung bauen:
    -keine feindlichen siedlungen benachbart
    -knoten noch nicht bebaut
    -grenzt an eigene straße an

stadt bauen:
    -knoten muss eine eigene siedlung haben

straße bauen:
    -muss an eigene siedlung oder straße anknüpfen
    -kante darf noch nicht bebaut sein
*/
public class Rules {

    public boolean buildFirstSettlement(CatanBoard board, int node, Player player) {

        for (Edge edge :  board.getGraph().getGraph()[node]) {
            if (edge.getStreet() != null) {
                if (edge.getStreet().getOwner() != player) {
                    return false;
                }
            }
        }

        Building building = board.getGraph().getNodes()[node].getBuilding();

        if (building == null) {
            return true;
        }

        return false;
    }
}
