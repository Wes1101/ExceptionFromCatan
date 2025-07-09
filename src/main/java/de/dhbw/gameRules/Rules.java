
package de.dhbw.gameRules;

import de.dhbw.catanBoard.CatanBoard;
import de.dhbw.catanBoard.hexGrid.Edge;
import de.dhbw.gamePieces.*;
import de.dhbw.player.Player;
import lombok.Getter;

/**
 * Represents the complete rule set for building structures and determining game progress in Catan.
 * <p>
 * Includes logic for checking build permissions (settlements, cities, roads),
 * assigning the longest road bonus, and checking victory conditions.
 * </p>
 */
@Getter
public class Rules {

    /**
     * Keeps track of the current player with the longest road bonus.
     * Used to update victory points accordingly.
     */
    private Player playerWithLongestRoad;
    private final int victoryPoints;

    /**
     * Constructs a new Rules object with no longest road owner initially set.
     */
    public Rules(int victoryPoints) {
        this.playerWithLongestRoad = null;
        this.victoryPoints = victoryPoints;
    }

    /**
     * Checks if a player can build their first settlement.
     * <p>
     * Requires the target node to be unoccupied and not adjacent to any enemy settlements.
     * </p>
     *
     * @param board  the game board
     * @param node   the node index where the settlement is to be placed
     * @param player the player attempting the build
     * @return true if the player can build the first settlement, false otherwise
     */
    public boolean buildFirstSettlement(CatanBoard board, int node, Player player) {
        return areEnemyBuildingsNext(board, node, player) && !isBuilt(board, node);
    }

    /**
     * Checks if a player can build a regular settlement.
     * <p>
     * Requires an adjacent owned street, no enemy buildings nearby, and sufficient resources.
     * </p>
     *
     * @param board  the game board
     * @param node   the node to place the settlement
     * @param player the player attempting the build
     * @return true if valid, otherwise false
     */
    public boolean buildSettlement(CatanBoard board, int node, Player player) {
        return nextToOwnStreet(board, node, player)
                && !isBuilt(board, node)
                && areEnemyBuildingsNext(board, node, player)
                && player.enoughResources(Settlement.getBuildCost());
    }

    /**
     * Checks if a city can be built on top of an existing settlement.
     * <p>
     * The player must own a settlement at the target node and have the required resources.
     * </p>
     *
     * @param board  the game board
     * @param node   the node to upgrade
     * @param player the player attempting the upgrade
     * @return true if the city can be built, false otherwise
     */
    public boolean buildCity(CatanBoard board, int node, Player player) {
        return hasOwnSettlement(board, node, player)
                && player.enoughResources(City.getBuildCost());
    }

    /**
     * Checks if a player can build a road between two given nodes.
     * <p>
     * Requires adjacency to a player's own structure or street, unoccupied edge, and enough resources.
     * </p>
     *
     * @param board  the game board
     * @param node1  first node index
     * @param node2  second node index
     * @param player the player attempting the build
     * @return true if the road can be built, false otherwise
     */
    public boolean buildStreet(CatanBoard board, int node1, int node2, Player player) {
        boolean builtNode1 = isBuilt(board, node1);
        boolean builtNode2 = isBuilt(board, node2);
        boolean streetFromNode1 = nextToOwnStreet(board, node1, player);
        boolean streetFromNode2 = nextToOwnStreet(board, node2, player);
        boolean enoughResources = player.enoughResources(Street.getBuildCost());

        return (builtNode1 || builtNode2 || streetFromNode1 || streetFromNode2) && enoughResources;
    }

    /**
     * Assigns the "Longest Road" bonus to the player who qualifies for it.
     * <p>
     * Deducts 2 victory points from the previous holder and awards 2 to the new one.
     * </p>
     *
     * @param board   the game board
     * @param players all players in the game
     */
    public void assignLongestRoadToPlayer(CatanBoard board, Player[] players) {
        if (this.playerWithLongestRoad != null) {
            this.playerWithLongestRoad.setVictoryPoints(playerWithLongestRoad.getVictoryPoints() - 2);
        }

        Player longestRoadOwner = board.getGraph().findPlayerLongestStreet(players);
        this.playerWithLongestRoad = longestRoadOwner;

        if (this.playerWithLongestRoad != null) {
            this.playerWithLongestRoad.setVictoryPoints(playerWithLongestRoad.getVictoryPoints() + 2);
        }
    }

    /**
     * Checks if the player has reached the victory point goal.
     *
     * @param player         the player to check
     * @return the player if they have won, otherwise null
     */
    public Boolean checkWin(Player player) {
        return player.getVictoryPoints() >= this.victoryPoints ? true : false;
    }

    // === Private Helper Methods ===

    /**
     * Verifies that no enemy buildings are adjacent to the given node.
     *
     * @param board  the game board
     * @param node   the node to evaluate
     * @param player the current player
     * @return true if no enemy buildings are adjacent, false otherwise
     */
    private boolean areEnemyBuildingsNext(CatanBoard board, int node, Player player) {
        int allNodes = board.getGraph().getNodes().length;
        for (int i = 0; i < allNodes; i++) {
            Edge edge = board.getGraph().getGraph()[node][i];
            if (edge != null && edge.isBuild()) {
                Player owner = board.getGraph().getNodes()[i].getBuilding().getOwner();
                if (owner != player) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the given node is unoccupied.
     *
     * @param board the game board
     * @param node  the node to evaluate
     * @return true if unoccupied, false otherwise
     */
    private boolean isBuilt(CatanBoard board, int node) {
        return board.getGraph().getNodes()[node].getBuilding() == null;
    }

    /**
     * Checks if the node is adjacent to a road owned by the player.
     *
     * @param board  the game board
     * @param node   the node to check
     * @param player the player in question
     * @return true if adjacent to player's road, false otherwise
     */
    private boolean nextToOwnStreet(CatanBoard board, int node, Player player) {
        for (Edge edge : board.getGraph().getGraph()[node]) {
            if (edge != null && edge.getStreet() != null && edge.getStreet().getOwner() == player) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the player owns a settlement on the given node.
     *
     * @param board  the game board
     * @param node   the node to check
     * @param player the player in question
     * @return true if the player owns a settlement here, false otherwise
     */
    private boolean hasOwnSettlement(CatanBoard board, int node, Player player) {
        Building building = board.getGraph().getNodes()[node].getBuilding();
        return building != null
                && building.getBuildingType() == BuildingTypes.SETTLEMENT
                && building.getOwner() == player;
    }
}
