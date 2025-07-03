package de.dhbw.player;

import de.dhbw.gamePieces.*;
import lombok.Getter;

import java.util.*;

@Getter
public class Bank  extends Player {

    List<Settlement> settlements;
    List<City> cities;
    List<Street> streets;

    public Bank(int amountResources, Player[] players) {
        super(amountResources);

        settlements = new ArrayList<>();
        cities = new ArrayList<>();
        streets = new ArrayList<>();
        //cards = new ArrayList<>();

        for (Player player : players) {
            initBuildings(player, 5, 5, 6);
        }
    }

    private void initBuildings(Player player, int settlement, int city, int street) {

        for (int i = 0; i < settlement; i++) {
            this.settlements.add(new Settlement(player));
        }
        for (int i = 0; i < city; i++) {
            this.cities.add(new City(player));
        }
        for (int i = 0; i < street; i++) {
            this.streets.add(new Street(player));
        }
    }

    public boolean containsBuilding(BuildingTypes building, Player player) {
        if (building == BuildingTypes.SETTLEMENT) {
            for (Settlement settlement : settlements) {
                if (settlement.getOwner() == player) {
                    return true;
                }
            }
        }
        else if (building == BuildingTypes.CITY) {
            for (City city : cities) {
                if (city.getOwner() == player) {
                    return true;
                }
            }
        }
        else if (building == BuildingTypes.STREET) {
            for (Street street : streets) {
                if (street.getOwner() == player) {
                    return true;
                }
            }
        }
        return false;
    }

}
