package de.dhbw.gamePieces;

import de.dhbw.player.Player;
import de.dhbw.resources.Resources;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Street (road) in the game, which is a basic structure used to expand territory.
 * <p>
 * Streets are essential for connecting settlements and cities, and for gaining the "Longest Road" bonus.
 * This class defines the construction cost and sets the building type accordingly.
 * </p>
 */
public class Street extends Building {

    /**
     * Constructs a new Street owned by the specified player.
     *
     * @param owner the player who owns the street
     */
    public Street(Player owner) {
        super(owner);
        buildingType = BuildingTypes.STREET;
    }

    /**
     * Returns the resource costs required to build a Street.
     * <ul>
     *     <li>1 Wood</li>
     *     <li>1 Brick</li>
     * </ul>
     *
     * @return a map containing the required resources and their amounts
     */
    public static Map<Resources, Integer> getBuildCost() {
        Map<Resources, Integer> cost = new HashMap<>();
        cost.put(Resources.WOOD, 1);
        cost.put(Resources.BRICK, 1);

        // Remove SHEEP if it's not part of official or intended custom rules
        // cost.put(Resources.SHEEP, 1);

        return cost;
    }
}
