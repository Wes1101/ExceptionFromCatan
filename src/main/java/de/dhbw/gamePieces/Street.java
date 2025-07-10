package de.dhbw.gamePieces;

import de.dhbw.player.Player;
import de.dhbw.resources.Resources;


import java.util.HashMap;
import java.util.Map;

/**
 * This class manages all functionality related to Streets, which are a subclass of Building.
 *
 * @author Atussa Mehrawari
 * @version 1.0
 *
 */


public class Street extends Building {
    public Street(Player owner) {
        super(owner);
        buildingType = BuildingTypes.STREET;
    }
    /**
     * Returns the resource costs required to build a street.
     *
     * @return a map containing the number of each resource needed for road construction
     */
    public static Map<Resources, Integer> getBuildCost() {
        Map<Resources, Integer> cost = new HashMap<>();
        cost.put(Resources.WOOD, 1);
        cost.put(Resources.BRICK, 1);
        return cost;
    }
}
