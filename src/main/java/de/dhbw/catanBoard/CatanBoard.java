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

//wasser tiles, Häfen - check

@Getter
public class CatanBoard {
    IntTupel[] hex_coords;
    Map<IntTupel, Tile> board = new HashMap<>();
    Map<Integer, List<Ressource>> diceBoard = new HashMap<>();

    Graph graph;

    /**
     * Konstruktor: Initialisiert das CatanBoard mit dem gegebenen Radius.
     * Ruft dabei die Methoden initNodes, initGraph, initHexCoords und createGraph auf.
     *
     * @param radius Radius des Spielfelds in Hex-Ringen
     */
    public CatanBoard(int radius) {
        graph = new Graph(calcNumNodes(radius));
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


            Node[] HexNodes = getExistingNodes(coords);

            for (int i = 0; i < HexNodes.length; i++) {
                if (HexNodes[i] == null) {
                    HexNodes[i] = graph.getNodes()[index];
                    int nextIndex = (i + 1 + HexNodes.length) % HexNodes.length;
                    if (HexNodes[nextIndex] != null) {
                        graph.createEdge(index, HexNodes[nextIndex].id);
                    }
                    int prevIndex = (i - 1 + HexNodes.length) % HexNodes.length;
                    if (HexNodes[prevIndex] != null) {
                        graph.createEdge(index, HexNodes[prevIndex].id);
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

                board.put(habour[i], new Habour(habourTypes.getFirst(), getExistingNodes(habour[i])));
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

    private Node[] getExistingNodes(IntTupel coords) {
        AxialDirection[] DIR = {
                AxialDirection.NORTH_WEST,
                AxialDirection.NORTH_EAST,
                AxialDirection.WEST
        };

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

        return HexNodes;
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

    public Graph getGraph() {
        return this.graph;
    }

    public void triggerBoard(int diceNumber, Bank bank) {

        List<Ressource> tiles = diceBoard.get(diceNumber);
        System.out.println(tiles);

        for (Ressource tile : tiles) {
            tile.trigger(bank);
        }
    }

    public void buildSettlement(int node, Building building) {
        graph.getNodes()[node].setBuilding(building);
    }

    public void buildCity(int node, Building building) {
        graph.getNodes()[node].setBuilding(building);
    }

//    public void buildStreet(Player player, int node1, int node2) {
//        updateGraph(node1, node2, 1, player.getId());
//    }

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

}