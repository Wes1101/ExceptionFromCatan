package de.dhbw.player;

import de.dhbw.catanBoard.hexGrid.IntTupel;
import de.dhbw.catanBoard.hexGrid.Tile;
import de.dhbw.gamePieces.City;
import de.dhbw.gamePieces.Settlement;
import de.dhbw.gamePieces.Street;
import de.dhbw.player.Player;
import lombok.Getter;
import de.dhbw.resources.*;

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

}
