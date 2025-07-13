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
 * This abstract class represents a Player in the game.
 * A player can own, gain, and lose resources during gameplay.
 */
@Getter
@Setter
@Slf4j
public class Player implements ResourceReceiver {
    @Override
    public void addResources(Resources type, int amount) {
        resources.put(type, resources.get(type) + amount);
    }

    int id;
    int victoryPoints;
    List<Street> cards;

    /**
     * Stores the player's resources using an EnumMap for efficiency.
     */
    EnumMap<Resources, Integer> resources;

    /**
     * Constructor initializes each resource type with a given starting amount.
     *
     *
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
     * Transfers a specific amount of a resource from this player to another player.
     *
     * @param type   the type of resource
     * @param amount the amount to transfer
     * @param target the player receiving the resource
     */
    public void removeResources(Resources type, int amount, ResourceReceiver target) {
        for (Resources res : resources.keySet()) {
            if (res == type) {
                resources.put(res, resources.get(res) - amount);
                target.addResources(type, amount);
                return;
            }
        }
    }


    public int getResources(Resources type) {
        return resources.get(type);
    }

    /**
     * Calculates the total number of resource cards the player currently has.
     *
     * @return the total resource count
     */
  
    public int getTotalResources() {
        int sum = 0;
        for (int count : resources.values()) {
            sum += count;
        }
        return sum;
    }

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
                log.info("❌not enough resources {}", resource);
                log.info("costs: " + costs.get(resource) + "/ have:" + this.getResources(resource));
                return false;
            }
        }
        log.info("✅enough resources");
        return true;
    }

    private void buyBuilding(Map<Resources, Integer> costs, Bank bank) {
        for (Resources resource : costs.keySet()) {
            this.removeResources(resource, costs.get(resource), bank);
        }
    }

    public void trade(Map<Resources, Integer> resources, Player player) {
        for (Resources resource : resources.keySet()) {
            this.removeResources(resource, resources.get(resource), player);
        }
    }

    /**
     * Randomly steals one resource from another player and transfers it to a second player.
     * Currently commented out and may be used later for game mechanics
     *
     * @param from the player to steal from
     * @param to   the player to receive the stolen resource
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

    /**
     * If a player has more resources than the threshold, this method forces them
     * to discard half of their resources (rounded down). The discarded resources
     * are returned to the bank.
     *
     * @param threshold the maximum allowed resources before discarding is required
     * @param bank      the bank to return discarded resources to
     */
    public void banditRemovesResources(int threshold, Bank bank) {
            int total = this.getTotalResources();
            if (total > threshold) {
                int toDiscard = total / 2;

                List<Resources> toRemove = new ArrayList<>();
                Random r = new Random();

                for (int i = 0; i < toDiscard; i++) {
                    Resources randRes = Resources.values()[r.nextInt(Resources.values().length)];
                    if (randRes != Resources.NONE && this.getResources().get(randRes) > 0) {
                        this.removeResources(randRes, 1, bank);
                        bank.addResources(randRes, 1);
                    }
                    else {
                        i--;
                    }
                }
            }


            // Gui Platzhalter
//            Map<Resources, Integer> selected = gui.askPlayerWhichCardsToDiscard(player, toDiscard);
//            for (Map.Entry<Resources, Integer> entry : selected.entrySet()) {
//                player.removeResources(entry.getKey(), entry.getValue());
            }

}

