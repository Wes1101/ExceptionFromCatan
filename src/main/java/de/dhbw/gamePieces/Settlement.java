package de.dhbw.gamePieces;

import de.dhbw.frontEnd.board.HexTile;
import de.dhbw.player.Player;
import de.dhbw.resources.*;

import java.util.HashMap;
import java.util.Map;

/**
 * This class administers all functions related to Settlements, which are a subclass of Building.
 *
 * @author Atussa Mehrawari
 * @version 1.0
 *
 */

public class Settlement extends Building {

    public Settlement(Player owner) {
        super(owner);
        buildingType = Buildings.SETTLEMENT;
    }

//    @Override
//    public Map<Class<? extends Resource>, Integer> getBuildCost() {
//        Map<Class<? extends Resource>, Integer> cost = new HashMap<>();
//        cost.put(Wood.class, 1);
//        cost.put(Brick.class, 1);
//        cost.put(Sheep.class, 1);
//        cost.put(Wheat.class, 1);
//        return cost;
//    }

    public String getType() {
        return "Settlement";
    }

    @Override
    public Map<Class<? extends Resources>, Integer> getBuildCost() {
        return Map.of();
    }

    //    @Override
//    public String toString() {
//        return "Settlement by " + owner.getName() + " at " + location;
//    }

}
