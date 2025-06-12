package de.dhbw.player;

import de.dhbw.gamePieces.City;
import de.dhbw.gamePieces.Settlement;
import de.dhbw.gamePieces.Street;
import de.dhbw.resources.Resources;
import javafx.scene.AmbientLight;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class Storage {
    List<Settlement> settlements;
    List<City> cities;
    List<Street> streets;
    List<Street> cards;
    EnumMap<Resources, Integer> resources;

    public Storage() {
        settlements = new ArrayList<>();
        cities = new ArrayList<>();
        streets = new ArrayList<>();
        cards = new ArrayList<>();
        resources = new EnumMap<>(Resources.class);
    }


    public void fillStorage(int amountSettlements, int amountCities, int amountStreets) {


            for (int i = 0; i < amountSettlements; i++) {
                this.settlements.add(new Settlement(player));
            }
            for (int i = 0; i < amountSettlements; i++) {
                this.settlements.add(new Settlement(player));
            }
            for (int i = 0; i < amountSettlements; i++) {
                this.settlements.add(new Settlement(player));
            }
        }
    }
}
