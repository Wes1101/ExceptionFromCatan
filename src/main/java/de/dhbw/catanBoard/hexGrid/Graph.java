package de.dhbw.catanBoard.hexGrid;

import java.util.logging.Logger;
import de.dhbw.gamePieces.Street;
import de.dhbw.gamePieces.Player;

public class Graph {
    private static final Logger LOGGER = Logger.getLogger(Graph.class.getName());
    private final Edge[][] graph;
    private final int size;

    public Graph(int numberOfVertices) {
        this.size = numberOfVertices;
        this.graph = new Edge[numberOfVertices][numberOfVertices];
    }

    /**
     * Erstellt eine neue Kante zwischen den Knoten i und j, falls noch nicht vorhanden.
     */
    public void createEdge(int i, int j) {
        if (graph[i][j] != null) {
            LOGGER.fine("Edge between " + i + " and " + j + " already exists, no need to create.");
            return;
        }

        if (i < 0 || j < 0 || i >= size || j >= size) {
            LOGGER.warning("createEdge: indices out of bounds (" + i + ", " + j + ").");
            return;
        }

        if (i == j) {
            LOGGER.warning("createEdge: cannot create edge from a vertex to itself (" + i + ").");
            return;
        }

        Edge edge = new Edge();
        graph[i][j] = edge;
        graph[j][i] = edge;
        LOGGER.info("Created new edge between " + i + " and " + j + ".");
    }

    /**
     * Aktualisiert die Kante zwischen i und j mit dem gegebenen Street-Objekt.
     * Falls die Kante noch nicht existiert, wird sie zuerst angelegt, um NullPointerException zu vermeiden.
     */
    public void updateEdge(int i, int j, Street street) {
        if (graph[i][j] == null) {
            LOGGER.info("Edge between " + i + " and " + j + " does not exist. Creating new edge to avoid NullPointerException.");
            createEdge(i, j);
        }

        if (graph[i][j] == null) {
            LOGGER.severe("Failed to create edge between " + i + " and " + j + ". Update aborted to prevent errors.");
            return;
        }

        graph[i][j].setStreet(street);
        LOGGER.info("Updated edge between " + i + " and " + j + " with Street: " + street + ".");
    }

    /**
     * Liefert das Edge-Objekt zwischen i und j zurück (oder null, falls keine Kante existiert).
     */
    public Edge getEdge(int i, int j) {
        if (i < 0 || j < 0 || i >= size || j >= size) {
            return null;
        }
        return graph[i][j];
    }

    /**
     * Berechnet die Länge der längsten zusammenhängenden Straßenkette des angegebenen Spielers.
     * Dabei wird eine Tiefensuche (DFS) auf dem Graphen durchgeführt.
     */
    public int getLongestRoad(Player player) {
        boolean[][] visited = new boolean[size][size];
        int longest = 0;
        for (int v = 0; v < size; v++) {
            int length = dfs(v, visited, player);
            if (length > longest) {
                longest = length;
            }
        }
        return longest;
    }

    /**
     * DFS-Hilfsmethode zur Berechnung der längsten Straße ab dem aktuellen Knoten.
     * Durchquert rekursiv alle Nachbarkanten, die vom selben Spieler belegt sind,
     * ohne Kanten doppelt zu verwenden.
     */
    private int dfs(int current, boolean[][] visited, Player player) {
        int longestPath = 0;
        for (int next = 0; next < size; next++) {
            if (graph[current][next] != null && !visited[current][next]) {
                Street road = graph[current][next].getStreet();

                if (road != null && road.getOwner() == player) {
                    visited[current][next] = true;
                    visited[next][current] = true;
                    int pathLen = 1 + dfs(next, visited, player);
                    if (pathLen > longestPath) {
                        longestPath = pathLen;
                    }
                    visited[current][next] = false;
                    visited[next][current] = false;
                }
            }
        }
        return longestPath;
    }
}