package de.dhbw.player;

import de.dhbw.gamePieces.BuildingTypes;
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
    EnumMap<Resources, Integer> resourceStorage;

    public Storage(Player player, EnumMap<Resources, Integer> resources, EnumMap<BuildingTypes, Integer> buildings) {
        settlements = new ArrayList<>();
        cities = new ArrayList<>();
        streets = new ArrayList<>();
        cards = new ArrayList<>();
        resourceStorage = new EnumMap<>(Resources.class);

        initResources(resources);
        initBuildings(player, buildings);
    }


    private void initBuildings(Player player, EnumMap<BuildingTypes, Integer> buildings) {

            for (int i = 0; i < buildings.get(BuildingTypes.SETTLEMENT); i++) {
                this.settlements.add(new Settlement(player));
            }
            for (int i = 0; i < buildings.get(BuildingTypes.CITY); i++) {
                this.settlements.add(new City(player));
            }
            for (int i = 0; i < buildings.get(BuildingTypes.STREET); i++) {
                this.settlements.add(new Street(player));
            }
    }

    private void initResources(EnumMap<Resources, Integer> resources) {

        for (int i = 0; i < resources.get(BuildingTypes.SETTLEMENT); i++) {
            this.settlements.add(new Settlement(player));
        }
        for (int i = 0; i < resources.get(BuildingTypes.CITY); i++) {
            this.settlements.add(new Settlement(player));
        }
        for (int i = 0; i < resources.get(BuildingTypes.STREET); i++) {
            this.settlements.add(new Settlement(player));
        }
    }
}
