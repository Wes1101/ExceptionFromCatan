package de.dhbw.gameRules;
import de.dhbw.catanBoard.CatanBoard;
import de.dhbw.catanBoard.hexGrid.Edge;
import de.dhbw.gamePieces.*;
import de.dhbw.player.Player;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
/**
 * Represents the complete rule set for building structures and determining game progress in Catan.
 * <p>
 * Includes logic for checking build permissions (settlements, cities, roads),
 * assigning the longest road bonus, and checking victory conditions.
 * </p>
 */
@Getter
@Slf4j
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
        log.info("Rules initialized with victory points: {}", victoryPoints);
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
        boolean canBuild = !areEnemyBuildingsNext(board, node, player) && !isBuilt(board, node);
        log.info("Player {} attempting to build first settlement at node {}: {}", player.getId(), node, canBuild);
        return canBuild;
    }
    public boolean buildFirstStreet(CatanBoard board, int node1, int node2, Player player) {
        boolean builtNode1 = hasOwnSettlement(board, node1, player);
        boolean builtNode2 = hasOwnSettlement(board, node2, player);
        boolean streetFromNode1 = nextToOwnStreet(board, node1, player);
        boolean streetFromNode2 = nextToOwnStreet(board, node2, player);
        boolean buildable = isStreetBuildable(board, node1, node2);
        boolean canBuild = (builtNode1 || builtNode2 || streetFromNode1 || streetFromNode2) && buildable;
        log.info("Player {} attempting to build street between nodes {} and {}: {}", player.getId(), node1, node2, canBuild);
        return canBuild;
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
        boolean canBuild = nextToOwnStreet(board, node, player)
                && !isBuilt(board, node)
                && areEnemyBuildingsNext(board, node, player)
                && player.enoughResources(Settlement.getBuildCost());
        log.info("Player {} attempting to build settlement at node {}: {}", player.getId(), node, canBuild);
        return canBuild;
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
        boolean canBuild = hasOwnSettlement(board, node, player)
                && player.enoughResources(City.getBuildCost());
        log.info("Player {} attempting to build city at node {}: {}", player.getId(), node, canBuild);
        return canBuild;
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
        boolean builtNode1 = hasOwnSettlement(board, node1, player);
        boolean builtNode2 = hasOwnSettlement(board, node2, player);
        boolean streetFromNode1 = nextToOwnStreet(board, node1, player);
        boolean streetFromNode2 = nextToOwnStreet(board, node2, player);
        boolean enoughResources = player.enoughResources(Street.getBuildCost());
        boolean canBuild = (builtNode1 || builtNode2 || streetFromNode1 || streetFromNode2) && enoughResources;
        log.info("Player {} attempting to build street between nodes {} and {}: {}", player.getId(), node1, node2, canBuild);
        return canBuild;
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
            log.info("Removed longest road bonus from player {}", this.playerWithLongestRoad.getId());
        }
        Player longestRoadOwner = board.getGraph().findPlayerLongestStreet(players);
        this.playerWithLongestRoad = longestRoadOwner;
        if (this.playerWithLongestRoad != null) {
            this.playerWithLongestRoad.setVictoryPoints(playerWithLongestRoad.getVictoryPoints() + 2);
            log.info("Assigned longest road bonus to player {}", this.playerWithLongestRoad.getId());
        }
    }
    /**
     * Checks if the player has reached the victory point goal.
     *
     * @param player         the player to check
     * @return the player if they have won, otherwise null
     */
    public Boolean checkWin(Player player) {
        boolean hasWon = player.getVictoryPoints() >= this.victoryPoints;
        log.info("Checking win condition for player {}: {}", player.getId(), hasWon);
        return hasWon;
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
            if (edge != null) {
                Building building = board.getGraph().getNodes()[i].getBuilding();
                if (building != null && building.getOwner() != player) {
                    log.info("Enemy building found near node {} from player {}", node, player.getId());
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Checks if the given node is unoccupied.
     *
     * @param board the game board
     * @param node  the node to evaluate
     * @return true if unoccupied, false otherwise
     */
    private boolean isBuilt(CatanBoard board, int node) {
        boolean result = board.getGraph().getNodes()[node].getBuilding() != null;
        log.info("Node {} is built: {}", node, result);
        return result;
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
                log.info("Node {} is next to a street owned by player {}", node, player.getId());
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
        boolean ownsSettlement = building != null
                && building.getBuildingType() == BuildingTypes.SETTLEMENT
                && building.getOwner() == player;
        log.info("Player {} owns settlement at node {}: {}", player.getId(), node, ownsSettlement);
        return ownsSettlement;
    }
    private boolean isStreetBuildable(CatanBoard board, int node1, int node2) {
        if (board.getGraph().getGraph()[node1][node2] != null) {
            return true;
        }
        return false;
    }
}