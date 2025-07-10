
package de.dhbw.catanBoard.hexGrid;

import de.dhbw.gamePieces.Street;
import de.dhbw.player.Player;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the underlying graph for the Catan board used to track roads and settlements.
 * <p>
 * Each node represents a build location (corner of hex tiles), and edges represent possible streets between them.
 * </p>
 */
@Setter
@Getter
public class Graph {

    private static final Logger log = LoggerFactory.getLogger(Graph.class);
    private Edge[][] graph;
    private Node[] nodes;

    /**
     * Constructs a new Graph with a specified number of nodes.
     *
     * @param nodes total number of nodes to initialize in the graph
     */

    public Graph(int nodes) {
        initNodes(nodes);
        graph = new Edge[nodes][nodes];
    }

    /**
     * Initializes an array of {@link Node} objects with the given number of entries.
     *
     * @param numNodes number of nodes to create
     */

    private void initNodes(int numNodes) {
        nodes = new Node[numNodes];
        for (int i = 0; i < numNodes; i++) {
            nodes[i] = new Node(i);
        }
        log.info("Initialized {} graph nodes", numNodes);
    }

    /**
     * Creates a bidirectional edge between two nodes.
     *
     * @param i index of the first node
     * @param j index of the second node
     */

    public void createEdge(int i, int j) {
        graph[i][j] = new Edge();
        graph[j][i] = new Edge();
        log.info("Created edge between node {} and node {}", i, j);
    }

    /**
     * Updates an edge between two nodes with a specific street object.
     * <p>
     * This represents a player building a road between two locations.
     * </p>
     *
     * @param i      first node index
     * @param j      second node index
     * @param street the street to assign to the edge
     */

    public void updateEdge(int i, int j, Street street) {
    if(graph[i][j]==null)
        {
        log.info("Keine kante für("+i+","+j+"), gefunden -> somit uss eine neue erstellt werden.");
        createEdge(i,j);
        }
        graph[i][j].setStreet(street);
        graph[j][i].setStreet(street); //zum testen einmal drinnen lassen und dann einmal entfernen bitte.
        log.info("Street built between node {} and node {} by PlayerID {}", i, j, street.getOwner().getId());
    }

    /**
     * Calculates the longest continuous trade route (road) owned by the specified player.
     * <p>
     * Uses depth-first search (DFS) to explore paths and determine the maximum path length.
     * </p>
     *
     * @param player the player whose roads are being analyzed
     * @return the length of the longest connected road owned by the player (minimum 3 to count)
     */

    public int findLongestTradeRoutes(Player player) {
        int maxLength = 0;
        for (int startNode = 0; startNode < graph.length; startNode++) {
            boolean[] searched = new boolean[graph.length];
            maxLength = Math.max(maxLength, dfsLength(startNode, -1, searched, player));
        }
        log.info("Longest road length for player {}: {}", player, maxLength);
        return maxLength >= 3 ? maxLength : 0;
    }

    /**
     * Recursive helper function that performs DFS to find the longest road segment from a given node.
     *
     * @param current  the current node being explored
     * @param from     the previous node to avoid looping back
     * @param searched boolean array marking visited nodes
     * @param player   the player owning the roads
     * @return length of the longest path found from this node
     */

    private int dfsLength(int current, int from, boolean[] searched, Player player) {
        searched[current] = true;
        int maxDepth = 1;

        for (int neighbor = 0; neighbor < graph.length; neighbor++) {
            if (graph[current][neighbor] != null &&
                    graph[current][neighbor].getStreet() != null &&
                    graph[current][neighbor].getStreet().getOwner() == player) {

                if (neighbor != from && !searched[neighbor]) {
                    maxDepth = Math.max(maxDepth, 1 + dfsLength(neighbor, current, searched, player));
                }
            }
        }

        searched[current] = false;
        log.debug("DFS from node {} results in depth {}", current, maxDepth);
        return maxDepth;
    }

    /**
     * Determines which player currently has the longest road.
     *
     * @param players array of all players
     * @return the player with the longest road, or {@code null} if no one qualifies
     */

    public Player findPlayerLongestStreet(Player[] players) {
        int maxLength = 0;
        Player winner = null;

        for (Player player : players) {
            int length = findLongestTradeRoutes(player);
            if (length > maxLength) {
                maxLength = length;
                winner = player;
            }
            logRouteDetails(player, length, winner, maxLength);
        }
        return winner;
    }

    /**
     * Helper method for logging current road details per player during evaluation.
     *
     * @param player    the player currently being evaluated
     * @param length    the length of the player's longest road
     * @param winner    the current leading player
     * @param maxLength the length of the longest road so far
     */

    private void logRouteDetails(Player player, int length, Player winner, int maxLength) {
        log.info("Player {} has a road length of {}", player, length);
        log.info("Current longest road is by Player {} with length {}", winner, maxLength);
    }
}
