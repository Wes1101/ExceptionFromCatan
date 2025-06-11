package de.dhbw.gamePieces;

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
    /**
     * Returns the resource costs required to build a settlement.
     *
     * @return a map containing the number of each resource needed for settlement construction
     */
    @Override
    public Map<Resources, Integer> getBuildCost() {
        Map<Resources, Integer> cost = new HashMap<>();
        cost.put(Resources.WOOD, 1);
        cost.put(Resources.BRICK, 1);
        cost.put(Resources.SHEEP, 1);
        cost.put(Resources.WHEAT, 1);
        return cost;
    }


    /**
     * Returns a string representation of the settlement, including the owner and location. For testing.
     *
     * @return a string describing who owns the settlement and where it is located
     */
    @Override
    public String toString() {
        return "Settlement by " + owner.getName();
    }

}
