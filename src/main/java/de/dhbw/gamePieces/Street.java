package de.dhbw.gamePieces;

import de.dhbw.frontEnd.board.HexTile;
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
    public Street(Player owner, HexTile location) {
        super(owner, location);
    }
    /**
     * Returns the resource costs required to build a street.
     *
     * @return a map containing the number of each resource needed for road construction
     */
    @Override
    public Map<Resources, Integer> getBuildCost() {
        Map<Resources, Integer> cost = new HashMap<>();
        cost.put(Resources.WOOD, 1);
        cost.put(Resources.BRICK, 1);
        return cost;
    }
    /**
     * Returns the type of this buildable object.
     *
     * @return the string "Street"
     */
    @Override
    public String getType() {
        return "Street";
    }

    /**
     * Returns a string representation of the street, including the owner and location. For testing.
     *
     * @return a string describing who owns the street and where it is located
     */
    @Override
    public String toString() {
        return "Street owned by " + owner.getName() + " at " + location;
    }

}
