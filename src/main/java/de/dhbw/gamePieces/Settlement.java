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
    public Settlement(Player owner, HexTile location) {
        super(owner, location);
    }

    @Override
    public Map<Resources, Integer> getBuildCost() {
        Map<Resources, Integer> cost = new HashMap<>();
        cost.put(Resources.WOOD, 1);
        cost.put(Resources.BRICK, 1);
        cost.put(Resources.SHEEP, 1);
        cost.put(Resources.WHEAT, 1);
        return cost;
    }

    @Override
    public String getType() {
        return "Settlement";
    }

    @Override
    public String toString() {
        return "Settlement by " + owner.getName() + " at " + location;
    }

}
