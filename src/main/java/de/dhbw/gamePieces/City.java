package de.dhbw.gamePieces;

import de.dhbw.player.Player;
import de.dhbw.frontEnd.board.HexTile;
import de.dhbw.resources.Resource;
import de.dhbw.resources.Stone;
import de.dhbw.resources.Wheat;

import java.util.Map;
import java.util.HashMap;

/**
 * TThis class manages all functionality related to Streets, which are a subclass of Building.
 *
 * @author Atussa Mehrawari
 * @version 1.0
 *
 */

public class City extends Building {
    public City(Player owner, HexTile location) {
        super(owner, location);
    }

    @Override
    public Map<Class<? extends Resource>, Integer> getBuildCost() {
        Map<Class<? extends Resource>, Integer> cost = new HashMap<>();
        cost.put(Stone.class, 3);
        cost.put(Wheat.class, 2);
        return cost;
    }

    @Override
    public String getType() {
        return "City";
    }
}
