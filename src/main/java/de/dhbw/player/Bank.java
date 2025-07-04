package de.dhbw.player;

import de.dhbw.gamePieces.*;
import lombok.Getter;

import java.util.*;

@Getter
public class Bank  extends Player {

    List<Building> buildings;

    public Bank(int amountResources, Player[] players) {
        super(amountResources);

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

}
