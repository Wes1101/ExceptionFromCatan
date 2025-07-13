package de.dhbw.gamePieces;

import de.dhbw.player.Player;
import de.dhbw.resources.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Settlement in the game, which is a basic type of building.
 * <p>
 * Settlements are the initial type of player construction. They generate resources
 * when adjacent tiles are activated. This class defines the resource cost
 * for building a settlement and sets its type appropriately.
 * </p>
 */
public class Settlement extends Building {

    /**
     * Constructs a new Settlement for the specified player.
     *
     * @param owner the player who owns the settlement
     */
    public Settlement(Player owner) {
        super(owner);
        buildingType = BuildingTypes.SETTLEMENT;
    }

    /**
     * Returns the resource costs required to build a Settlement.
     * <ul>
     *     <li>1 Wood</li>
     *     <li>1 Brick</li>
     *     <li>1 Sheep</li>
     *     <li>1 Stone</li>
     * </ul>
     *
     * @return a map containing the required resources and their amounts
     */
    public static Map<Resources, Integer> getBuildCost() {
        Map<Resources, Integer> cost = new HashMap<>();
        cost.put(Resources.WOOD, 1);
        cost.put(Resources.BRICK, 1);
        cost.put(Resources.SHEEP, 1);
        cost.put(Resources.STONE, 1); // Consider changing to WHEAT if aligning with standard rules
        return cost;
    }
}
