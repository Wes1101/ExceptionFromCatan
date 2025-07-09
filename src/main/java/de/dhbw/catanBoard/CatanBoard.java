
package de.dhbw.catanBoard;

import de.dhbw.catanBoard.hexGrid.*;
import de.dhbw.catanBoard.hexGrid.Tiles.Harbour;
import de.dhbw.catanBoard.hexGrid.Tiles.Resource;
import de.dhbw.catanBoard.hexGrid.Tiles.Water;
import de.dhbw.gamePieces.Building;
import de.dhbw.gamePieces.Street;
import de.dhbw.player.Bank;
import de.dhbw.resources.Resources;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Represents the game board for Catan.
 * <p>
 * Handles the creation and initialization of resource tiles, number tokens, harbours, water tiles,
 * and the graph structure used for settlements and roads.
 * </p>
 */
@Getter
public class CatanBoard {

    private static final Logger log = LoggerFactory.getLogger(CatanBoard.class);

    private IntTupel[] hex_coords;
    private final Map<IntTupel, Tile> board = new HashMap<>();
    private final Map<Integer, List<Resource>> diceBoard = new HashMap<>();
    private final Graph graph;

    /**
     * Creates a new CatanBoard instance.
     * <p>
     * Initializes the graph structure for settlements and roads, generates all hex tile coordinates based on the board radius,
     * and builds the complete board including resource tiles, number tokens (chips), and harbour/water tiles.
     * </p>
     *
     * @param radius the number of hex rings around the center tile (including the center)
     */
    public CatanBoard(int radius) {
        log.info("Initializing CatanBoard with radius: {}", radius);
        this.graph = new Graph(calcNumNodes(radius));
        initHexCoords(radius);
        createGraph(radius);
    }

    /**
     * Recursively calculates the total number of graph nodes (settlement positions) required for a board
     * of given radius.
     *
     * @param n the number of hex rings around the center
     * @return the total number of nodes needed for the given radius
     */
    private int calcNumNodes(int n) {
        return (n <= 0) ? 0 : calcNumNodes(n - 1) + (2 * n - 1) * 6;
    }

    /**
     * Initializes all axial coordinates (q, r) for hex tiles on the board for a given radius.
     *
     * @param radius the board radius (number of hex rings)
     */
    private void initHexCoords(int radius) {
        hex_coords = new IntTupel[calcNumHexTiles(radius)];
        int index = 0;
        for (int i = -radius + 1; i < radius; i++) {
            for (int j = -radius + 1; j < radius; j++) {
                if (Math.abs(i + j) < radius) {
                    hex_coords[index++] = new IntTupel(j, i);
                }
            }
        }
        log.info("Hex coordinates initialized with {} tiles", hex_coords.length);
    }

    /**
     * Recursively calculates the number of hex tiles required for a board of given radius.
     *
     * @param n the radius of the board (number of rings)
     * @return the total number of hex tiles
     */
    private int calcNumHexTiles(int n) {
        return (n == 1) ? 1 : calcNumHexTiles(n - 1) + 6 * (n - 1);
    }

    /**
     * Generates and assembles the entire game board.
     * <p>
     * This includes:
     * <ul>
     *     <li>Creating and assigning resource tiles with number tokens</li>
     *     <li>Creating graph nodes and connecting them</li>
     *     <li>Placing harbours and water tiles around the edge</li>
     * </ul>
     *
     * @param radius the board radius used to determine tile layout and harbour placement
     */
    private void createGraph(int radius) {
        int index = 0;
        ArrayList<Integer> numChips = generateChipNumbers();
        ArrayList<Resources> allResources = generateResourceTypes(hex_coords.length - 1);

        allResources.add(Resources.NONE);
        Collections.shuffle(numChips);
        Collections.shuffle(allResources);

        for (int i = 1; i < allResources.size(); i++) {
            if (allResources.get(i) == Resources.NONE) {
                numChips.add(i, 0);
                break;
            }
        }

        for (IntTupel coords : hex_coords) {
            Node[] HexNodes = getExistingNodes(coords);

            for (int i = 0; i < HexNodes.length; i++) {
                if (HexNodes[i] == null) {
                    HexNodes[i] = graph.getNodes()[index];
                    int nextIndex = (i + 1 + HexNodes.length) % HexNodes.length;
                    int prevIndex = (i - 1 + HexNodes.length) % HexNodes.length;

                    if (HexNodes[nextIndex] != null) graph.createEdge(index, HexNodes[nextIndex].getId());
                    if (HexNodes[prevIndex] != null) graph.createEdge(index, HexNodes[prevIndex].getId());
                    index++;
                }
            }

            Resource tile = new Resource(allResources.removeFirst(), HexNodes);
            board.put(coords, tile);

            for (Node node : HexNodes) {
                node.addHexTile(tile);
            }

            int chip = numChips.removeFirst();
            diceBoard.computeIfAbsent(chip, k -> new ArrayList<>()).add(tile);
        }

        createHarbourTiles(radius);
    }

    /**
     * Creates harbour and water tiles around the edge of the board.
     *
     * @param radius the board radius used to determine outer harbour ring
     */
    private void createHarbourTiles(int radius) {
        IntTupel[] harbour = new IntTupel[calcNumHexTiles(radius + 1) - calcNumHexTiles(radius)];
        int q = AxialDirection.SOUTH_WEST.getDq() * radius;
        int r = AxialDirection.SOUTH_WEST.getDr() * radius;
        int i = 0;

        for (AxialDirection dir : AxialDirection.values()) {
            for (int j = 0; j < radius; j++) {
                q += dir.getDq();
                r += dir.getDr();
                harbour[i++] = new IntTupel(q, r);
            }
        }

        List<Resources> harbourTypes = new ArrayList<>(Arrays.asList(Resources.values()));
        for (i = harbourTypes.size(); i < harbour.length / 2; i++) {
            harbourTypes.add(Resources.NONE);
        }
        Collections.shuffle(harbourTypes);

        for (i = 0; i < harbour.length; i++) {
            if (i % 2 == 1) {
                board.put(harbour[i], new Harbour(harbourTypes.removeFirst(), getExistingNodes(harbour[i])));
            } else {
                board.put(harbour[i], new Water());
            }
        }
        log.info("Harbours and water tiles created around the board.");
    }

    /**
     * Returns already existing nodes from neighboring hex tiles.
     *
     * @param coords the coordinate of the current tile
     * @return an array of 6 nodes (some may be null) representing the corners of the hex tile
     */
    private Node[] getExistingNodes(IntTupel coords) {
        Node[] HexNodes = new Node[6];
        AxialDirection[] DIR = {
                AxialDirection.NORTH_WEST,
                AxialDirection.NORTH_EAST,
                AxialDirection.WEST
        };

        for (AxialDirection dir : DIR) {
            IntTupel neighbor = new IntTupel(coords.q() + dir.getDq(), coords.r() + dir.getDr());
            if (board.containsKey(neighbor)) {
                switch (dir) {
                    case NORTH_WEST -> {
                        HexNodes[5] = board.get(neighbor).getHexTileNodes()[3];
                        HexNodes[0] = board.get(neighbor).getHexTileNodes()[2];
                    }
                    case NORTH_EAST -> {
                        HexNodes[0] = board.get(neighbor).getHexTileNodes()[4];
                        HexNodes[1] = board.get(neighbor).getHexTileNodes()[3];
                    }
                    case WEST -> {
                        HexNodes[4] = board.get(neighbor).getHexTileNodes()[2];
                        HexNodes[5] = board.get(neighbor).getHexTileNodes()[1];
                    }
                }
            }
        }

        return HexNodes;
    }

    /**
     * Generates a list of number tokens (dice values) with weighted frequency.
     *
     * @return list of dice numbers to assign to resource tiles
     */
    public ArrayList<Integer> generateChipNumbers() {
        Map<Integer, Integer> weights = Map.of(2, 1, 3, 2, 4, 3, 5, 4, 6, 5, 8, 5, 9, 4, 10, 3, 11, 2, 12, 1);
        ArrayList<Integer> chips = new ArrayList<>();
        int tileCount = hex_coords.length - 1;

        weights.forEach((number, weight) -> {
            int count = (int) Math.round(weight * tileCount / 30.0);
            chips.addAll(Collections.nCopies(count, number));
        });

        return chips;
    }

    /**
     * Creates a balanced list of resource types for all resource tiles.
     *
     * @param count number of resource tiles to generate (excluding desert)
     * @return a list of resource types in random order
     */
    private ArrayList<Resources> generateResourceTypes(int count) {
        ArrayList<Resources> types = new ArrayList<>();
        Resources[] values = Resources.values();
        for (int i = 0; i < count; i++) {
            if (values[i % values.length] != Resources.NONE) {
                types.add(values[i % values.length]);
            } else {
                count++;
            }
        }
        return types;
    }

    /**
     * Triggers all resource tiles associated with the rolled dice number.
     *
     * @param diceNumber the rolled dice number (2–12)
     * @param bank       the bank handling resource distribution
     */
    public void triggerBoard(int diceNumber, Bank bank) {
        log.info("Triggering board for dice number: {}", diceNumber);
        List<Resource> tiles = diceBoard.get(diceNumber);
        if (tiles != null) {
            tiles.forEach(tile -> tile.trigger(bank));
        }
    }

    /**
     * Places a settlement on the specified node.
     *
     * @param node     node index
     * @param building the building to place
     */
    public void buildSettlement(int node, Building building) {
        graph.getNodes()[node].setBuilding(building);
        log.info("Settlement built at node {}", node);
    }

    /**
     * Upgrades a settlement to a city at the specified node.
     *
     * @param node     node index
     * @param building new city building
     * @param bank     bank to return the original building
     */
    public void buildCity(int node, Building building, Bank bank) {
        bank.addBuilding(graph.getNodes()[node].getBuilding());
        graph.getNodes()[node].setBuilding(building);
        log.info("City built at node {}", node);
    }

    /**
     * Builds a street between two nodes.
     *
     * @param node1  first node index
     * @param node2  second node index
     * @param street street object
     */
    public void buildStreet(int node1, int node2, Building street) {
        graph.updateEdge(node1, node2, (Street) street);
        log.info("Street built between node {} and {}", node1, node2);
    }

    /**
     * Toggles blocking state of a hex tile (e.g. due to a robber).
     *
     * @param coords coordinates of the tile
     */
    public void blockHex(IntTupel coords) {
        Tile tile = board.get(coords);
        if (tile instanceof Resource resTile) {
            resTile.setBlocked(!resTile.isBlocked());
            log.info("Hex at {} is now {}", coords, resTile.isBlocked() ? "BLOCKED" : "UNBLOCKED");
        }
    }

    /**
     * Returns coordinates of the desert tile.
     *
     * @return coordinates of the desert tile, or null if not found
     */
    public IntTupel getDesertCoords() {
        return board.entrySet().stream()
                .filter(entry -> entry.getValue() instanceof Resource res && res.getResourceType() == Resources.NONE)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }
}
