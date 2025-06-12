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
// map: würfelzahl -> arrayList<hexagon> shoutout an LL

//alg für längste handelsstraße

//methoden um zu bauen (siedlung, stadt, straße)

//attribut für hexagons blocked - boolean - check



public class CatanBoard {
    IntTupel[] hex_coords;
    Map<IntTupel, HexTile> board = new HashMap<>();
    static Node[] nodes;
    int[][][] graph;

    private static final int STREET = 0;
    private static final int PLAYER = 1;

    /**
     * Konstruktor: Initialisiert das CatanBoard mit dem gegebenen Radius.
     * Ruft dabei die Methoden initNodes, initGraph, initHexCoords und createGraph auf.
     *
     * @param radius Radius des Spielfelds in Hex-Ringen
     */
    public CatanBoard(int radius) {
        initNodes(radius);
        initGraph();
        initHexCoords(radius);
        createGraph(radius);
    }

    /**
     * Rekursive Berechnung der Gesamtzahl der benötigten Knoten (Nodes)
     * für einen gegebenen Radius n.
     *
     * @param n Radius des Spielfelds (Anzahl der Hex-Ringe inklusive Zentrum)
     * @return Gesamtzahl der Nodes für diesen Radius
     */
    private static int calcNumNodes(int n) {
        if (n <= 0) {
            return 0;
        }
        return calcNumNodes(n - 1) + (2 * n - 1) * 6;
    }

    /**
     * Initialisiert das statische Node-Array mit der Anzahl aller Nodes für den
     * angegebenen Radius. Jeder Node erhält eine eindeutige ID.
     *
     * @param n Radius des Spielfelds (Anzahl der Hex-Ringe inklusive Zentrum)
     */
    public static void initNodes(int n) {
        int numNodes = calcNumNodes(n);
        nodes = new Node[numNodes];
        for (int i = 0; i < numNodes; i++) {
            nodes[i] = new Node(i);
            System.out.println("Node " + i + " created");
        }
    }

    /**
     * Initialisiert den Straßen-Graphen ohne vorhandene Straßen.
     * Setzt für jedes Paar (i,j):
     *   graph[i][j][STREET]  = 0 (keine Straße)
     *   graph[i][j][PLAYER]  = -1 (kein Besitzer)
     */
    private void initGraph() {
        graph = new int[nodes.length][nodes.length][2];
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes.length; j++) {
                graph[i][j][STREET] = 0;
                graph[i][j][PLAYER] = -1;
            }
        }
    }

    /**
     * Aktualisiert den Graphen, um das Vorhandensein einer Straße und die Spielerzugehörigkeit
     * zwischen zwei Knoten (i und j) zu setzen oder zu entfernen. Die Operation wird in beide
     * Richtungen durchgeführt, da der Graph ungerichtet ist.
     *
     * @param i        ID des ersten Knotens
     * @param j        ID des zweiten Knotens
     * @param existenz 1 = Straße vorhanden, 0 = keine Straße
     * @param spieler  Index des Spielers, dem die Straße gehört, oder -1, wenn frei
     */
    public void updateGraph(int i, int j, int existenz, int spieler) {
        graph[i][j][STREET] = existenz;
        graph[i][j][PLAYER] = spieler;
        graph[j][i][STREET] = existenz;
        graph[j][i][PLAYER] = spieler;
    }

    /**
     * Berechnet alle axialen Koordinaten (q,r) für HexTiles innerhalb des gegebenen Radius
     * und füllt das Array hex_coords. Gültige Koordinaten erfüllen |q + r| < radius.
     *
     * @param radius Radius des Spielfelds in Hex-Ringen
     */
    private void initHexCoords(int radius) {
        hex_coords = new IntTupel[calcNumHexTiles(radius)];
        int index = 0;
        for (int i = -radius + 1; i < radius; i++) {
            for (int j = -radius + 1; j < radius; j++) {
                if (Math.abs(i + j) < radius) {
                    hex_coords[index] = new IntTupel(j, i);
                    index++;
                }
            }
        }
    }

    /**
     * Rekursive Berechnung der Gesamtzahl der HexTiles für einen gegebenen Radius.
     *
     * @param n Radius des Spielfelds (Anzahl der Hex-Ringe)
     * @return Anzahl aller HexTiles
     */
    private static int calcNumHexTiles(int n) {
        if (n == 1) {
            return 1;
        }
        return calcNumHexTiles(n - 1) + 6 * (n - 1);
    }

    /**
     * Erzeugt alle HexTiles auf dem Spielfeld und verteilt dabei zufällig die Ressourcentypen.
     * Verbindet außerdem die zugehörigen Knoten (Nodes) im Straßen-Graphen.
     *
     * Vorgehen:
     * 1. Erzeuge eine Liste aller benötigten Ressourcen (ohne Wüste) und füge dann eine Wüste hinzu.
     * 2. Für jede Koordinate in hex_coords:
     *    a) Lege vorhandene Eckknoten anhand von Nachbar-HexTiles fest.
     *    b) Weise allen noch leeren Eckknoten einen freien Node zu und verbinde benachbarte Knoten im Graphen.
     *    c) Wähle zufällig eine Ressource aus und erstelle das HexTile.
     *
     * @param radius Radius des Spielfelds (wird nicht aktiv in dieser Methode verwendet)
     */
    private void createGraph(int radius) {
        int index = 0;
        Directions[] DIR = {
                Directions.NORTH_WEST,
                Directions.NORTH_EAST,
                Directions.WEST
        };

        ArrayList<Resources> allResources = generateResourceTypes(hex_coords.length - 1);
        allResources.add(Resources.NONE);
        Random rand = new Random();
        System.out.println("Alle Ressourcen: " + allResources);

        for (IntTupel coords : hex_coords) {
            Node[] HexNodes = new Node[6];

            for (Directions dir : DIR) {
                IntTupel neighbor = new IntTupel(coords.q() + dir.getDq(), coords.r() + dir.getDr());
                if (board.containsKey(neighbor)) {
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
            System.out.println("Hexagon " + coords.q() + "," + coords.r() + ": " + board.get(coords).getAllHexTileNodes());
        }
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                System.out.print(graph[i][j][STREET] + ", ");
            }
            System.out.println();
        }
    }

    /**
     * Generiert eine Liste aller benötigten Ressourcen (ohne Wüste) für numTiles HexTiles.
     * Überspringt dabei den Wert Resources.NONE und sorgt für eine gleichmäßige Verteilung.
     *
     * @param numTiles Anzahl der HexTiles (ohne Wüste), die eine Ressource benötigen
     * @return ArrayList mit Ressourcen-Typen
     */
    private static ArrayList<Resources> generateResourceTypes(int numTiles) {
        ArrayList<Resources> allResources = new ArrayList<>();
        Resources[] values = Resources.values();

        for (int i = 0; i < numTiles; i++) {
            if (values[i % values.length] != Resources.NONE) {
                allResources.add(values[i % values.length]);
            } else {
                numTiles++;
            }
        }
        return allResources;
    }

    public void triggerBoard(int i) {
    }
}
