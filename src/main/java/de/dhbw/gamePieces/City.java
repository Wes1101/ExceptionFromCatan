package de.dhbw.gamePieces;

import de.dhbw.player.Player;
import de.dhbw.resources.Resources;

import java.util.Map;
import java.util.HashMap;

/**
 * Represents a City in the game, which is an upgraded form of a Settlement.
 * <p>
 * Cities produce more resources than settlements and have a higher building cost.
 * This class defines the specific cost required to build a city and sets the appropriate
 * building type.
 * </p>
 */
public class City extends Building {

    /**
     * Creates a new City owned by the given player.
     *
     * @param owner the player who owns the city
     */
    public City(Player owner) {
        super(owner);
        buildingType = BuildingTypes.CITY;
    }

    /**
     * Returns the resource cost required to build a City.
     * <ul>
     *     <li>3 Stone</li>
     *     <li>2 Wheat</li>
     * </ul>
     *
     * @return a map representing the required resources and their amounts
     */
    public static Map<Resources, Integer> getBuildCost() {
        Map<Resources, Integer> cost = new HashMap<>();
        cost.put(Resources.STONE, 3);
        cost.put(Resources.WHEAT, 2);
        return cost;
    }
}
