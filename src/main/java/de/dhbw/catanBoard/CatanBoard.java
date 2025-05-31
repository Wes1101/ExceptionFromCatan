package de.dhbw.catanBoard;

import de.dhbw.catanBoard.hexGrid.Directions;
import de.dhbw.catanBoard.hexGrid.HexTile;
import de.dhbw.catanBoard.hexGrid.IntTupel;
import de.dhbw.catanBoard.hexGrid.Node;
import de.dhbw.resources.Resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


//resourcen zuordnen - check
//zahlen chips

//trigger board funktion um erwürfeltes hexfeld auszuführen

//alg für längste handelsstraße

//methoden um zu bauen (siedlung, stadt, straße)

//attribut für hexagins blocked - boolean - check


public class CatanBoard {
    IntTupel[] hex_coords;
    Map<IntTupel, HexTile> board = new HashMap<>();
    static Node[] nodes;
    int[][][] graph;

    private static final int STREET = 0;
    private static final int PLAYER = 1;

    public CatanBoard(int radius) {
        initNodes(radius);
        initGraph();
        initHexCoords(radius);
        createGraph(radius);
    }

    public static void initNodes(int n) {
        int numNodes = calcNumNodes(n);
        nodes = new Node[numNodes];
        for (int i = 0; i < numNodes; i++) {
            nodes[i] = new Node(i);
            System.out.println("Node " + i + " created");
        }
    }

    private void initGraph() {
        graph = new int[nodes.length][nodes.length][2];
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes.length; j++) {
                graph[i][j][STREET] = 0; // keine Straße
                graph[i][j][PLAYER] = -1; // kein Besitzer
            }
        }
    }

    public void updateGraph(int i, int j, int existenz, int spieler) {
        graph[i][j][STREET] = existenz;
        graph[i][j][PLAYER] = spieler;

        graph[j][i][STREET] = existenz;
        graph[j][i][PLAYER] = spieler;
    }

    private void initHexCoords(int radius) {
        hex_coords = new IntTupel[calcNumHexTiles(radius)];
        int index = 0;
        for (int i = -radius+1; i < radius; i++) {
            for (int j = -radius+1; j < radius; j++) {
                if (Math.abs(i+j) < radius) {
                    hex_coords[index] = new IntTupel(j, i);
                    index++;
                }
            }
        }
    }

    private void createGraph(int radius) {
        int index = 0;
        Directions[] DIR = {
                Directions.NORTH_WEST,
                Directions.NORTH_EAST,
                Directions.WEST
        };

        ArrayList<Resources> allResources = generateResourceTypes(hex_coords.length-1);
        allResources.add(Resources.NONE);
        Random rand = new Random();
        System.out.println("Alle Ressourcen: " + allResources);

        for (IntTupel coords : hex_coords) {
            Node[] HexNodes = new Node[6];

            for (Directions dir : DIR) {
                IntTupel neighbor = new IntTupel(coords.q() + dir.getDq(), coords.r() + dir.getDr());

                if (board.containsKey(neighbor)) {
                    System.out.println("Knoten " + coords.q() + "," + coords.r() + " verbunden mit Knoten " + neighbor.q() + "," + neighbor.r());
                    switch (dir) {
                        case NORTH_WEST:
                            HexNodes[5] = board.get(neighbor).getHexTileNodes()[3];
                            HexNodes[0] = board.get(neighbor).getHexTileNodes()[2];
                            break;
                        case NORTH_EAST:
                            HexNodes[0] = board.get(neighbor).getHexTileNodes()[4];
                            HexNodes[1] = board.get(neighbor).getHexTileNodes()[3];
                            break;
                        case WEST:
                            HexNodes[4] = board.get(neighbor).getHexTileNodes()[2];
                            HexNodes[5] = board.get(neighbor).getHexTileNodes()[1];
                            break;
                    }
                }
            }

            for (int i = 0; i < HexNodes.length; i++) {
                System.out.println(i);
                if (HexNodes[i] == null) {
                    HexNodes[i] = nodes[index];
                    int nextIndex = (i + 1 + HexNodes.length) % HexNodes.length;
                    if (HexNodes[nextIndex] != null) {
                        updateGraph(index, HexNodes[nextIndex].id, 1, -1);
                    }
                    int prevIndex = (i - 1 + HexNodes.length) % HexNodes.length;
                    if (HexNodes[prevIndex] != null) {
                        updateGraph(index, HexNodes[prevIndex].id, 1, -1);
                    }
                    index++;
                }
            }


            int randomIndex = rand.nextInt(allResources.size());
            board.put(coords, new HexTile(0, allResources.get(randomIndex), HexNodes));
            allResources.remove(randomIndex);
        }

        System.out.println("Graph created");
        System.out.println("Anzahl Knoten: " + index);
        for (IntTupel coords : hex_coords) {
            System.out.println("Hexagon " + coords.q() + "," + coords.r() + ": " + board.get(coords).getResourceType());
        }
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                // Drucke das Element mit einem Leerzeichen als Trennzeichen
                System.out.print(graph[i][j][0] + ", ");
            }
            // Nach jedem inneren Durchlauf Zeilenumbruch
            System.out.println();
        }
    }

    public static ArrayList<Resources> generateResourceTypes(int numTiles) {
        ArrayList<Resources> allResources = new ArrayList<>();
        Resources[] values = Resources.values();
        for (int i = 0; i < numTiles; i++) {
            if (values[i % values.length] != Resources.NONE) {
                allResources.add(values[i % values.length]);
            }
            else {
                numTiles++;
            }
        }
        return allResources;
    }

    public static int calcNumNodes(int n) {
        if (n <= 0) {
            return 0;
        }
        return calcNumNodes(n - 1) + (2 * n - 1) * 6;
    }

    public static int calcNumHexTiles(int n) {
        if (n == 1) {
            return 1;
        }
        return calcNumHexTiles(n - 1) + 6 * (n - 1);
    }
}