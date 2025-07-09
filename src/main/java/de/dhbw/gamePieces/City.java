package de.dhbw.gamePieces;

import de.dhbw.player.Player;
import de.dhbw.resources.Resources;

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
    public City(Player owner) {
        super(owner);
        buildingType = BuildingTypes.CITY;
    }

    public static Map<Resources, Integer> getBuildCost() {
        Map<Resources, Integer> cost = new HashMap<>();
        cost.put(Resources.STONE, 3);
        cost.put(Resources.WHEAT, 2);
        return cost;
    }
}
