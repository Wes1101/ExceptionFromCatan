package de.dhbw.catanBoard;

import de.dhbw.resources.Wood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatanBoard {
    private final int[][][] graph;
    private final Node[] nodes;
    private final HexTile[] hexTiles;

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

    public CatanBoard(int dimension) {
        int numNodes = (int) (6 * dimension + 6 * (Math.pow(2, dimension) - 2));
        System.out.println("Ecken: " + numNodes);
        this.nodes = new Node[numNodes];
        initializeNodes(numNodes);

        this.graph = new int[numNodes][numNodes][2];
        initializeGraph();

        int numHexTiles = 1 + 3 * dimension * (dimension + 1);
        System.out.println("Hexecken: " + numHexTiles);
        this.hexTiles = new HexTile[numHexTiles];
        generateHexSpiral(numHexTiles, this.nodes);
    }

    public void generateHexSpiral(int n, Node[] nodes) {
        String[] directions = {"E", "NE", "NW", "W", "SW", "SE"};

        // Zentrum
        this.hexTiles[0] = new HexTile(0, 0, 0, "wood", getNodesForHex(0, 0, nodes));
        this.hexTiles[1] = new HexTile(directionMap.get("SW")[0], directionMap.get("SW")[1], 0, "wood",
                getNodesForHex(directionMap.get("SW")[0], directionMap.get("SW")[1], nodes));
        this.hexTiles[2] = new HexTile(directionMap.get("SE")[0], directionMap.get("SE")[1], 0, "wood",
                getNodesForHex(directionMap.get("SE")[0], directionMap.get("SE")[1], nodes));
        this.hexTiles[3] = new HexTile(directionMap.get("E")[0], directionMap.get("E")[1], 0, "wood",
                getNodesForHex(directionMap.get("E")[0], directionMap.get("E")[1], nodes));
        this.hexTiles[4] = new HexTile(directionMap.get("NE")[0], directionMap.get("NE")[1], 0, "wood",
                getNodesForHex(directionMap.get("NE")[0], directionMap.get("NE")[1], nodes));
        this.hexTiles[5] = new HexTile(directionMap.get("NW")[0], directionMap.get("NW")[1], 0, "wood",
                getNodesForHex(directionMap.get("NW")[0], directionMap.get("NW")[1], nodes));
        this.hexTiles[6] = new HexTile(directionMap.get("W")[0], directionMap.get("W")[1], 0, "wood",
                getNodesForHex(directionMap.get("W")[0], directionMap.get("W")[1], nodes));

        int index = 7;
        int layer = 1;

        while (index < n) {
            int q = -1;
            int r = 1;
            for (int i = 0; i < layer; i++) {
                q += directionMap.get("SW")[0];
                r += directionMap.get("SW")[1];
            }

            for (String dir : directions) {
                for (int step = 0; step <= layer; step++) {
                    if (index >= n) break;

                    this.hexTiles[index] = new HexTile(q, r, 0, "wood", getNodesForHex(q, r, nodes));
                    index++;

                    q += directionMap.get(dir)[0];
                    r += directionMap.get(dir)[1];
                }
            }

            layer++;
        }

        for (int i = 0; i < this.hexTiles.length; i++) {
            HexTile h = this.hexTiles[i];
            System.out.println("Hex " + i + ": (" + h.q + ", " + h.r + ")");
        }
    }

    public static Node[] getNodesForHex(int q, int r, Node[] nodes) {
        Node[] hexNodes = new Node[6];

        for (int i = 0; i < 6; i++) {
            int nodeId = calculateNodeId(q, r, i);  // Du musst dies passend zur ID-Logik implementieren
            hexNodes[i] = nodes[nodeId];
        }

        return hexNodes;
    }

    public static int calculateNodeId(int q, int r, int i) {
        // 1) Ring-Index d
        int z = -q - r;
        int d = (Math.abs(q) + Math.abs(r) + Math.abs(z)) / 2;

        // 2) Position im Spiral-Durchlauf
        int firstInRing = d == 0 ? 0 : 1 + 3 * (d - 1) * d;
        int offsetInRing;
        if      (r ==  d)          offsetInRing =  d - q;
        else if (q + r ==  d)      offsetInRing = 2 * d + q;
        else if (q ==  d)          offsetInRing = 4 * d - r;
        else if (r == -d)          offsetInRing = 5 * d - q;
        else                       offsetInRing = 3 * d - r;
        int tileIndex = firstInRing + offsetInRing;

        // 3a) Zentrum
        if (tileIndex == 0) {
            return i;  // (0,0): 0..5
        }

        // 3b) Geteilte Ecken i=0,1 → Nachbar-Rekursion
        int[][] neigh = {{ 1,-1},{ 0,-1},{-1, 0},{-1, 1},{ 0, 1},{ 1, 0}};
        if (i < 2) {
            int nq = q + neigh[i][0], nr = r + neigh[i][1];
            int ni = (i == 0 ? 3 : 4);  // teilt Ecke 3 oder 4 im Nachbar
            return calculateNodeId(nq, nr, ni);
        }

        // 3c) Neue Ecken i=2..5
        int baseNode = 6 + 4 * (tileIndex - 1);
        return baseNode + (i - 2);
    }




    private void initializeNodes(int numNodes) {
        for (int i = 0; i < numNodes; i++) {
            this.nodes[i] = new Node(i);
        }
    }

    private void initializeGraph() {
        for (int i = 0; i < this.nodes.length; i++) {
            for (int j = 0; j < this.nodes.length; j++) {
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
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes.length; j++) {
                if (graph[i][j][EXISTENZ] == 1) {
                    System.out.println("Straße zwischen " + i + " und " + j +
                            " gehört Spieler " + graph[i][j][SPIELER]);
                }
            }
        }
    }

}
