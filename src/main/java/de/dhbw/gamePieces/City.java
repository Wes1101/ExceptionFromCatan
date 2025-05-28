package de.dhbw.gamePieces;

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
    public Map<ResourceType, Integer> getBuildCost() {
        Map<ResourceType, Integer> cost = new HashMap<>();
        cost.put(ResourceType.STONE, 3);
        cost.put(ResourceType.WHEAT, 2);
        return cost;
    }

    @Override
    public String getType() {
        return "City";
    }
}
