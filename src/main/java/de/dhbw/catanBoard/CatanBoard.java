package de.dhbw.catanBoard;

import de.dhbw.catanBoard.hexGrid.*;
import de.dhbw.catanBoard.hexGrid.Tiles.Habour;
import de.dhbw.catanBoard.hexGrid.Tiles.Ressource;
import de.dhbw.catanBoard.hexGrid.Tiles.Water;
import de.dhbw.gamePieces.Building;
import de.dhbw.player.Bank;
import de.dhbw.player.Player;
import de.dhbw.resources.Resources;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

import java.util.*;


//resourcen zuordnen - check
//zahlen chips - check

//trigger board funktion um erwürfeltes hexfeld auszuführen - check
// map: würfelzahl -> arrayList<hexagon> shoutout an LL - check

//alg für längste handelsstraße

//methoden um zu bauen (siedlung, stadt, straße) - check

//attribut für hexagons blocked - boolean - check

//wasser tiles, Häfen

@Getter
public class CatanBoard {
    IntTupel[] hex_coords;
    Map<IntTupel, Tile> board = new HashMap<>();
    Map<Integer, List<Ressource>> diceBoard = new HashMap<>();
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
    private int calcNumNodes(int n) {
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
    private void initNodes(int n) {
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
    private void updateGraph(int i, int j, int existenz, int spieler) {
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
    private int calcNumHexTiles(int n) {
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
        AxialDirection[] DIR = {
                AxialDirection.NORTH_WEST,
                AxialDirection.NORTH_EAST,
                AxialDirection.WEST
        };

        ArrayList<Integer> numChips = generateChipNumbers();
        Collections.shuffle(numChips);

        ArrayList<Resources> allResources = generateResourceTypes(hex_coords.length - 1);
        allResources.add(Resources.NONE);
        Collections.shuffle(allResources);

        for (int i = 1; i < allResources.size(); i++) {
            if (allResources.get(i) == Resources.NONE) {
                numChips.add(i, 0);
            }
        }

        for (IntTupel coords : hex_coords) {
            Node[] HexNodes = new Node[6];

            for (AxialDirection dir : DIR) {
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

            board.put(coords, new Ressource(allResources.getFirst(), HexNodes));
            allResources.removeFirst();

            for (Node HexNode : HexNodes) {
                HexNode.addHexTile(board.get(coords));
            }

            if (!diceBoard.containsKey(numChips.getFirst())) {
                diceBoard.put(numChips.getFirst(), new ArrayList<>());
            }
            diceBoard.get(numChips.getFirst()).add((Ressource) board.get(coords));
            numChips.removeFirst();
        }

        //create habour coords
        IntTupel[] habour = new IntTupel[calcNumHexTiles(radius + 1)-calcNumHexTiles(radius)];
        int q = AxialDirection.SOUTH_WEST.getDq() * (radius);
        int r = AxialDirection.SOUTH_WEST.getDr() * (radius);
        System.out.println("q = " + q + ", r = " + r);
        int i = 0;

        for (AxialDirection dir : AxialDirection.values()) {
            System.out.println(dir.toString());
            for (int j = 0; j < radius; j++) {
                q = q + dir.getDq();
                r = r + dir.getDr();

                habour[i] = new IntTupel(q, r);
                System.out.println(habour[i]);
                i++;
            }
        }

        //create habour tiles
        /**
         * 2:1 resources
         * 3:1 any
         */
        List<Resources> habourTypes = new ArrayList<>();

        for (Resources resources : Resources.values()) {
            habourTypes.add(resources);
        }
        for (i = habourTypes.size(); i < habour.length / 2; i++) {
            habourTypes.add(Resources.NONE);
        }

        Collections.shuffle(habourTypes);
        System.out.println("habourTypes = " + habourTypes);

        for (i = 0; i < habour.length; i++) {
            if (i%2 == 1) {
                board.put(habour[i], new Habour(habourTypes.getFirst()));
                habourTypes.remove(habourTypes.getFirst());
            }
            else {
                board.put(habour[i], new Water());
            }
        }

        for (IntTupel coord : board.keySet()) {
            if (board.get(coord) instanceof Habour) {
                Habour habourTile = (Habour) board.get(coord);
                System.out.println("coords = " + coord + " = " + habourTile.getResourceType());
            }

        }
    }

    /**
     * Generiert eine Liste aller benötigten Ressourcen (ohne Wüste) für numTiles HexTiles.
     * Überspringt dabei den Wert Resources.NONE und sorgt für eine gleichmäßige Verteilung.
     *
     * @param numTiles Anzahl der HexTiles (ohne Wüste), die eine Ressource benötigen
     * @return ArrayList mit Ressourcen-Typen
     */
    private ArrayList<Resources> generateResourceTypes(int numTiles) {
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

    public ArrayList<Integer> generateChipNumbers() {
      ArrayList<Integer> chips = new ArrayList<Integer>();

      Map<Integer, Integer> weights = new HashMap<>();
      weights.put(2, 1);
      weights.put(3, 2);
      weights.put(4, 3);
      weights.put(5, 4);
      weights.put(6, 5);
      weights.put(8, 5);
      weights.put(9, 4);
      weights.put(10, 3);
      weights.put(11, 2);
      weights.put(12, 1);

      for (int key : weights.keySet()) {
        int numChips = Math.toIntExact(Math.round(weights.get(key) * (hex_coords.length - 1) / 30.0));
        for (int j = 0; j < numChips; j++) {
          chips.add(key);
        }
      }

      return chips;
    }

    public Map<IntTupel, Tile> getHexTiles() {
        return this.board;
    }

    public int[][][] getGraph() {
        return this.graph;
    }

    public void triggerBoard(int diceNumber, Bank bank) {

        List<Ressource> tiles = diceBoard.get(diceNumber);
        System.out.println(tiles);

        for (Ressource tile : tiles) {
            tile.trigger(bank);
        }
    }

    public void buildSettlement(Player player, int node, Building building) {
        nodes[node].setBuilding(building);
        nodes[node].setPlayer(player);
    }

    public void buildCity(Player player, int node, Building building) {
        nodes[node].setBuilding(building);
        nodes[node].setPlayer(player);
    }

    public void buildStreet(Player player, int node1, int node2) {
        updateGraph(node1, node2, 1, player.getId());
    }

    public void blockHex(IntTupel coords) {
        Tile tile = board.get(coords);

        if (tile instanceof Ressource) {
            Ressource resTile = (Ressource) tile;

            if (resTile.isBlocked()) {
                resTile.setBlocked(false);
            } else {
                resTile.setBlocked(true);
            }
        }
    }

    public IntTupel getDesertCoords() {
        for (IntTupel coords : board.keySet()) {
            if (board.get(coords) instanceof Ressource) {
                Ressource resTile = (Ressource) board.get(coords);
                if (resTile.getResourceType() == Resources.NONE) {
                    return coords;
                }
            }
        }
        return null;
    }

    /**
     *Dies ist der Algorithmus, welcher die längste Handel-Strecke heraussucht.
     *Es wird als Algorithms Depth-First Search genutzt,
     *es wird erst geschaut, sobald ein Spieler 3 Gebäude aneinander hat.
     * @param playerId dies ist die ID des spielers
     * @return Gibt die Länge des shits raus.
     */

    public int findLongestTradeRoutes(int playerId) {
        int maxLength = 0;
        for (int startNode = 0; startNode < hex_coords.length; startNode++) {
            boolean[] searched = new boolean[nodes.length];
            maxLength =
                    Math.max(maxLength, dfsLength(startNode, -1, searched, playerId));
        }
        System.out.println("Winner: Player" + maxLength);
        return maxLength >= 3 ? maxLength : 0;
    }

    /**
     *Führt die Suche durch (DFS) zur Ermittlung der Längsten durchgehenden Straße eines Spielers.
     * @param current Aktueller Knoten
     * @param from Vorheriger Knoten (Vermeidung von Rücklaufen)
     * @param playerId ID des Spielers, dessen Straße aktuell geprüft wird.
     * @param searched Merkt sich, welche Knoten schon durchsucht wurden.
     * @return Länge des längsten Pfades.
     */

    private int dfsLength(int current, int from, boolean[] searched, int playerId)
    {
        searched[current] = true;
        int maxTiefe = 1;

        for (int nachbar = 0; nachbar < nodes.length; nachbar++)
        {
            if (graph[current][nachbar][STREET] == 1 && graph[current][nachbar][PLAYER] == playerId)
            {
                if (nachbar != from && !searched[nachbar])
                {
                    maxTiefe = Math.max(maxTiefe, 1 + dfsLength(nachbar, current, searched, playerId));
                }
            }
        }
        searched[current] = false;
        System.out.println("DFS von node:" + current + "ergibt die Tiefe:" + maxTiefe);
        return maxTiefe;
    }

    /**
     *Dies hier findet den Spieler mit der längsten Handelsstraße.
     * @param playerCount Anzahl aller Spieler.
     * @return Spieler ID mit der längsten Straße oder gibt halt -1 aus, falls keiner derzeit die längste Straße hat.
     */

    public int findPlayerLongestStreet(int playerCount)
    {
        int maxLength = 0;
        int winner = -1;

        for (int playerId = 0; playerId < playerCount; playerId++)
        {
            int length = findLongestTradeRoutes(playerId);
            if (length > maxLength)
            {
                maxLength = length;
                winner = playerId;
            }
            logRouteDetails(playerId,length,winner,maxLength);
        }
        return winner;
    }

    /**
     *Helfer Methode zur Protokollierung von den Routeninformationen
     * @param playerId spieler id
     * @param length Länge des in Führung liegenden Spielers
     * @param winner Spieler Id mi der bislang längsten Straße
     * @param maxLength Länge der bislang längsten Straße
     */

    private void logRouteDetails(int playerId,int length, int winner,int maxLength)
    {
        System.out.println("Spieler" + playerId + "Hat eine Straßen länge" + length);
        System.out.println("Aktueller Sieger ist Spieler" + winner + "mit der Straßenlänge:" + maxLength);
    }
}