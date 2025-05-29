package de.dhbw.catanBoard;

import de.dhbw.resources.Wood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatanBoard {
    private final int[][][] graph;
    private final Vertex[] vertices;
    List<int[]> hexCoords = new ArrayList<>();
    private final int numCorners;
    private final int numPlayers;

    // Statische Map mit Richtungen
    static Map<String, int[]> directionMap = new HashMap<>();

    // Statischer Initialisierungsblock, um die Map zu füllen
    static {
        directionMap.put("E",  new int[]{1, 0});
        directionMap.put("NE", new int[]{1, -1});
        directionMap.put("NW", new int[]{0, -1});
        directionMap.put("W",  new int[]{-1, 0});
        directionMap.put("SW", new int[]{-1, 1});
        directionMap.put("SE", new int[]{0, 1});
    }

    private static final int EXISTENZ = 0;
    private static final int SPIELER = 1;

    public CatanBoard(int numCorners, int numPlayers) {
        this.numCorners = numCorners;
        this.numPlayers = numPlayers;

        this.graph = new int[numCorners][numCorners][2];
        this.vertices = new Vertex[numCorners];

        generateHexSpiral(numCorners);
        initializeVertices();
        initializeGraph();
    }

    public static void generateHexSpiral(int n) {
        HexTile[] hexTiles = new HexTile[n];
        String[] directions = {"E", "NE", "NW", "W", "SW", "SE"};

        // Zentrum
        hexTiles[0] = new HexTile(0, 0, 0, new Wood(1));
        hexTiles[1] = new HexTile(directionMap.get("SW")[0], directionMap.get("SW")[1], 0, new Wood(1));
        hexTiles[2] = new HexTile(directionMap.get("SE")[0], directionMap.get("SE")[1], 0, new Wood(1));
        hexTiles[3] = new HexTile(directionMap.get("E")[0], directionMap.get("E")[1], 0, new Wood(1));
        hexTiles[4] = new HexTile(directionMap.get("NE")[0], directionMap.get("NE")[1], 0, new Wood(1));
        hexTiles[5] = new HexTile(directionMap.get("NW")[0], directionMap.get("NW")[1], 0, new Wood(1));
        hexTiles[6] = new HexTile(directionMap.get("W")[0], directionMap.get("W")[1], 0, new Wood(1));

        int index = 7;
        int layer = 1;

        while (index < n) {
            // Richtiger Startpunkt:
            int q = -1;
            int r = 1;
            for (int i = 0; i < layer; i++) {
                q += directionMap.get("SW")[0];  // -1
                r += directionMap.get("SW")[1];  // +1
            }

            // Jetzt spiralförmig im Uhrzeigersinn die 6 Seiten ablaufen
            for (String dir : directions) {
                for (int step = 0; step <= layer; step++) {
                    if (index >= n) break;

                    hexTiles[index++] = new HexTile(q, r, 0, new Wood(1));

                    q += directionMap.get(dir)[0];
                    r += directionMap.get(dir)[1];
                }
            }

            layer++;
        }

        // Ausgabe
        for (int i = 0; i < hexTiles.length; i++) {
            HexTile h = hexTiles[i];
            System.out.println("Hex " + i + ": (" + h.q + ", " + h.r + ")");
        }
    }

    private void initializeVertices() {
        for (int i = 0; i < numCorners; i++) {
            this.vertices[i] = new Vertex(i, i, i);
        }
    }

    private void initializeGraph() {
        for (int i = 0; i < numCorners; i++) {
            for (int j = 0; j < numCorners; j++) {
                graph[i][j][EXISTENZ] = 0; // keine Straße
                graph[i][j][SPIELER] = -1; // kein Besitzer
            }
        }
    }

    public void addRoad(int from, int to, int playerId) {
        graph[from][to][EXISTENZ] = 1;
        graph[to][from][EXISTENZ] = 1;
        graph[from][to][SPIELER] = playerId;
        graph[to][from][SPIELER] = playerId;
    }

    public void printGraph() {
        for (int i = 0; i < numCorners; i++) {
            for (int j = 0; j < numCorners; j++) {
                if (graph[i][j][EXISTENZ] == 1) {
                    System.out.println("Straße zwischen " + i + " und " + j +
                            " gehört Spieler " + graph[i][j][SPIELER]);
                }
            }
        }
    }

}
