package de.dhbw.gameRules;


import de.dhbw.catanBoard.CatanBoard;
import de.dhbw.catanBoard.hexGrid.Edge;
import de.dhbw.gamePieces.Building;
import de.dhbw.gamePieces.BuildingTypes;
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
        boolean noEnemyBuildingsNext = areEnemyBuildingsNext(board, node, player);
        boolean hasNoBuilding = !isBuilt(board, node);

        if (noEnemyBuildingsNext && hasNoBuilding) {
            return true;
        }
        return false;
    }

    public boolean buildsettlement(CatanBoard board, int node, Player player) {
        boolean connectedStreet = nextToOwnStreet(board, node, player);
        boolean hasNoBuilding = !isBuilt(board, node);
        boolean noEnemyBuildingsNext = areEnemyBuildingsNext(board, node, player);

        if (connectedStreet && hasNoBuilding && noEnemyBuildingsNext) {
            return true;
        }
        return false;
    }

    public boolean buildCity(CatanBoard board, int node, Player player) {
        boolean hasOwnSettlement = hasOwnSettlement(board, node, player);

        if (hasOwnSettlement) {
            return true;
        }
        return false;
    }

    public boolean buildStreet(CatanBoard board, int node1, int node2, Player player) {
        boolean builtNode1 = isBuilt(board, node1);
        boolean builtNode2 = isBuilt(board, node2);
        boolean streetFromNode1 = nextToOwnStreet(board, node1, player);
        boolean streetFromNode2 = nextToOwnStreet(board, node2, player);

        if (builtNode1 || builtNode2 || streetFromNode1 || streetFromNode2) {
            return true;
        }
        return false;
    }












    private boolean areEnemyBuildingsNext(CatanBoard board, int node, Player player) {
        int allNodes = board.getGraph().getNodes().length;
        for (int i = 0; i < allNodes; i++) {

            boolean built = board.getGraph().getGraph()[node][i].isBuild();
            if (built == true) {

                Player owner = board.getGraph().getNodes()[i].getBuilding().getOwner();
                if (owner != player) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isBuilt(CatanBoard board, int node) {
        Building building = board.getGraph().getNodes()[node].getBuilding();
        if (building == null) {
            return true;
        }
        return false;
    }

    private boolean nextToOwnStreet(CatanBoard board, int node, Player player) {
        for (Edge edge : board.getGraph().getGraph()[node]) {
            if (edge.getStreet() != null) {
                if (edge.getStreet().getOwner() == player) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasOwnSettlement(CatanBoard board, int node, Player player) {
        Building building = board.getGraph().getNodes()[node].getBuilding();
        if (building.getBuildingType() == BuildingTypes.SETTLEMENT && building.getOwner() == player) {
            return true;
        }
        return false;
    }
}
