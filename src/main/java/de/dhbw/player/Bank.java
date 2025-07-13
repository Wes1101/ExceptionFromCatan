package de.dhbw.player;

import de.dhbw.gamePieces.*;
import de.dhbw.resources.Resources;
import lombok.Getter;

import java.util.*;

/**
 * Represents the central Bank in the game.
 * <p>
 * The Bank manages:
 * <ul>
 *     <li>All shared resource cards (stone, brick, wood, etc.)</li>
 *     <li>A pool of available buildings (settlements, cities, streets) for each player</li>
 * </ul>
 * It implements {@link ResourceReceiver}, allowing it to receive resources from players or the board.
 */
@Getter
public class Bank implements ResourceReceiver {

    /** List of all buildings currently available in the bank. */
    List<Building> buildings;

    /** Map of available resources, indexed by type (e.g., WOOD, STONE). */
    EnumMap<Resources, Integer> resources;

    /**
     * Creates a new bank with the specified amount of each resource.
     * Also pre-generates a pool of buildings for each player.
     *
     * @param amountResources the amount of each resource type to initialize
     * @param players         array of players to initialize building stock for
     */
    public Bank(int amountResources, Player[] players) {
        resources = new EnumMap<>(Resources.class);
        for (Resources res : Resources.values()) {
            this.resources.put(res, amountResources);
        }

        buildings = new ArrayList<>();

        for (Player player : players) {
            initBuildings(player, 5, 5, 10); // 5 settlements, 5 cities, 10 streets
        }
    }

    /**
     * Adds a specific amount of a resource to the bank.
     *
     * @param type   the type of resource
     * @param amount the amount to add
     */
    @Override
    public void addResources(Resources type, int amount) {
        resources.put(type, resources.get(type) + amount);
    }

    /**
     * Initializes a given number of each building type for a player.
     *
     * @param player     the player for whom buildings are created
     * @param settlement number of settlements
     * @param city       number of cities
     * @param street     number of streets
     */
    private void initBuildings(Player player, int settlement, int city, int street) {
        for (int i = 0; i < settlement; i++) {
            this.buildings.add(new Settlement(player));
        }
        for (int i = 0; i < city; i++) {
            this.buildings.add(new City(player));
        }
        for (int i = 0; i < street; i++) {
            this.buildings.add(new Street(player));
        }
    }

    /**
     * Retrieves a building of a specific type for the given player from the bank.
     * The building is removed from the bank's pool upon retrieval.
     *
     * @param getBuilding the type of building to retrieve (SETTLEMENT, CITY, STREET)
     * @param player      the owner of the building
     * @return the building instance, or null if none is available
     */
    public Building getBuilding(BuildingTypes getBuilding, Player player) {
        for (Building building : buildings) {
            if (building.getBuildingType() == getBuilding && building.getOwner() == player) {
                removeBuilding(building);
                return building;
            }
        }
        return null;
    }

    /**
     * Removes a building from the bank's pool.
     *
     * @param building the building to remove
     */
    public void removeBuilding(Building building) {
        buildings.remove(building);
    }

    /**
     * Adds a building back to the bank's pool.
     *
     * @param building the building to add
     */
    public void addBuilding(Building building) {
        buildings.add(building);
    }

    /**
     * Transfers a specific amount of a resource from the bank to another player or entity.
     *
     * @param type   the resource type
     * @param amount the amount to transfer
     * @param target the recipient implementing {@link ResourceReceiver}
     */
    public void removeResources(Resources type, int amount, ResourceReceiver target) {
        if (resources.containsKey(type)) {
            resources.put(type, resources.get(type) - amount);
            target.addResources(type, amount);
        }
    }
}
