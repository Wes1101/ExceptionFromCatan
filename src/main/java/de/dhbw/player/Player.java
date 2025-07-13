package de.dhbw.player;

import de.dhbw.catanBoard.CatanBoard;
import de.dhbw.catanBoard.hexGrid.IntTupel;
import de.dhbw.catanBoard.hexGrid.Tile;
import de.dhbw.catanBoard.hexGrid.Tiles.Resource;
import de.dhbw.gamePieces.*;
import de.dhbw.resources.Resources;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Represents a player in the game.
 * <p>
 * Each player has an ID, tracks victory points, manages resources, and can perform
 * actions like building settlements, roads, and cities. A player also participates
 * in trading and can interact with the bank.
 * </p>
 */
@Getter
@Setter
@Slf4j
public class Player implements ResourceReceiver {

    int id;
    int victoryPoints;
    List<Street> cards;
    EnumMap<Resources, Integer> resources;

    /**
     * Initializes a player with the given ID and sets all resource counts to 0.
     *
     * @param id the player's unique identifier
     */
    public Player(int id) {
        this.id = id;
        this.victoryPoints = 0;
        resources = new EnumMap<>(Resources.class);
        for (Resources res : Resources.values()) {
            this.resources.put(res, 0);
        }
    }

    /**
     * Adds the specified resource to the player's inventory.
     */
    @Override
    public void addResources(Resources type, int amount) {
        resources.put(type, resources.get(type) + amount);
    }

    /**
     * Transfers a resource from this player to another receiver.
     */
    public void removeResources(Resources type, int amount, ResourceReceiver target) {
        if (resources.containsKey(type)) {
            resources.put(type, resources.get(type) - amount);
            target.addResources(type, amount);
        }
    }

    public int getResources(Resources type) {
        return resources.get(type);
    }

    /**
     * @return total number of resources the player currently holds
     */
    public int getTotalResources() {
        return resources.values().stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Awards resources to the player based on surrounding tiles.
     */
    public void getNodeResources(int node, Bank bank, CatanBoard board) {
        for (Tile tile : board.getGraph().getNodes()[node].getHexNeighbors()) {
            if (tile.getResourceType() != Resources.NONE) {
                bank.removeResources(tile.getResourceType(), 1, this);
            }
        }
    }

    public boolean buyFirstSettlement(int node, Bank bank, CatanBoard board) {
        Building building = bank.getBuilding(BuildingTypes.SETTLEMENT, this);
        if (building != null) {
            board.buildSettlement(node, building);
            this.victoryPoints++;
            return true;
        }
        return false;
    }

    public boolean buildSettlement(int node, Bank bank, Player player, CatanBoard board) {
        Building building = bank.getBuilding(BuildingTypes.SETTLEMENT, player);
        if (building != null) {
            buyBuilding(Settlement.getBuildCost(), bank);
            board.buildSettlement(node, building);
            this.victoryPoints++;
            return true;
        }
        return false;
    }

    public boolean buildCity(int node, Bank bank, Player player, CatanBoard board) {
        Building building = bank.getBuilding(BuildingTypes.CITY, player);
        if (building != null) {
            buyBuilding(City.getBuildCost(), bank);
            board.buildCity(node, building, bank);
            this.victoryPoints++;
            return true;
        }
        return false;
    }

    public boolean buyFirstStreet(int node1, int node2, Bank bank, CatanBoard board) {
        Building building = bank.getBuilding(BuildingTypes.STREET, this);
        if (building != null) {
            board.buildStreet(node1, node2, building);
            return true;
        }
        return false;
    }

    public boolean buildStreet(int node1, int node2, Bank bank, Player player, CatanBoard board) {
        Building building = bank.getBuilding(BuildingTypes.STREET, player);
        if (building != null) {
            buyBuilding(Street.getBuildCost(), bank);
            board.buildStreet(node1, node2, building);
            return true;
        }
        return false;
    }

    public boolean enoughResources(Map<Resources, Integer> costs) {
        log.info("has the player enough resources?");
        for (Resources resource : costs.keySet()) {
            if (costs.get(resource) > this.getResources(resource)) {
                log.info("\u274C not enough resources {}", resource);
                return false;
            }
        }
        log.info("\u2705 enough resources");
        return true;
    }

    private void buyBuilding(Map<Resources, Integer> costs, Bank bank) {
        for (Resources resource : costs.keySet()) {
            this.removeResources(resource, costs.get(resource), bank);
        }
    }

    /**
     * Transfers multiple resource types to another player.
     */
    public void trade(Map<Resources, Integer> resources, Player player) {
        for (Resources resource : resources.keySet()) {
            this.removeResources(resource, resources.get(resource), player);
        }
    }

    /**
     * Discards half of the player's resources if they exceed the threshold.
     * This is usually triggered when a 7 is rolled.
     */
    public void banditRemovesResources(int threshold, Bank bank) {
        int total = this.getTotalResources();
        if (total > threshold) {
            int toDiscard = total / 2;
            Random r = new Random();
            for (int i = 0; i < toDiscard; i++) {
                Resources randRes = Resources.values()[r.nextInt(Resources.values().length)];
                if (randRes != Resources.NONE && this.getResources().get(randRes) > 0) {
                    this.removeResources(randRes, 1, bank);
                    bank.addResources(randRes, 1);
                } else {
                    i--; // retry
                }
            }
        }
    }

    /**
     * Randomly steals one resource from another player and gives it to a second player.
     * Currently commented out for potential future use.
     */
    public void stealRandomResources(Player from, Player to) {
        //        List<Resources> resources = new ArrayList<>();
//
//        // checkt die Ressoucen des Spielers und schaut, ob der Spieler Res besitzt.
//        // Wenn nicht gibt es 0 zurück
//        for (Resources res : Resources.values()) {
//            Integer count = from.getResources().getOrDefault(res, 0);
//
//            // es sollen nur ressourcen geklaut werden, die existieren.
//            // Wenn Res >0 dann wird diese zu der liste available hinzugefügt.
//            if (count > 0) {
//                resources.add(res);
//            }
//        }
//
//        // Falls Res leer - abbrechen
//        if (resources.isEmpty()) {
//            return;
//        }
//
//        // Zufällige Ressource wählen
//        Random r = new Random();
//        Resources chosen = resources.get(r.nextInt(resources.size()));
//
//        // Ressoucen Transfer
//        from.removeResources(chosen, 1, to);
    }
}
