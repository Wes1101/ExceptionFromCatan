package de.dhbw.player;

import de.dhbw.catanBoard.hexGrid.IntTupel;
import de.dhbw.catanBoard.hexGrid.Tile;
import de.dhbw.player.Player;
import lombok.Getter;
import de.dhbw.resources.*;

import java.util.*;

@Getter
public class Bank  extends Player {

    Map<Player, Storage> storeBuildings = new HashMap<>();

    public Bank(int amountResources, Player[] players) {
        super(amountResources);
        initializeBuildings(players);
    }

    private void initializeBuildings(Player[] players) {
        int numSettlement = 5;
        int numCity = 5;
        int numStreet = 6;

//        for (Player player : players) {
//            storeBuildings.put(player, new Storage());
//            storeBuildings.get(player).fillStorage(player);
//        }
    }




}
