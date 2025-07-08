package de.dhbw.player;

import de.dhbw.gamePieces.*;
import de.dhbw.resources.Resources;
import lombok.Getter;

import java.util.*;

@Getter
public class Bank implements ResourceReceiver {
    @Override
    public void addResources(Resources type, int amount) {
        resources.put(type, resources.get(type) + amount);
    }

    List<Building> buildings;
    EnumMap<Resources, Integer> resources;

    public Bank(int amountResources, Player[] players) {
        resources = new EnumMap<>(Resources.class);
        for (Resources res : Resources.values()) {
            this.resources.put(res, amountResources);
        }

        buildings = new ArrayList<>();
        //cards = new ArrayList<>();

        for (Player player : players) {
            initBuildings(player, 5, 5, 6);
        }
    }

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

    public Building getBuilding(BuildingTypes getBuilding, Player player) {
        for (Building building : buildings) {
            if (building.getBuildingType() == getBuilding && building.getOwner() == player) {
                removeBuilding(building);
                return building;
            }
        }
        return null;
    }

    public void removeBuilding(Building building) {
        buildings.remove(building);
    }

    public void addBuilding(Building building) {
        buildings.add(building);
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

}
