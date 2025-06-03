package de.dhbw.gamePieces;

import de.dhbw.frontEnd.board.HexTile;
import de.dhbw.player.Player;
import de.dhbw.resources.Brick;
import de.dhbw.resources.Resource;
import de.dhbw.resources.Wood;
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

  @Override
  public Map<Class<? extends Resource>, Integer> getBuildCost() {
    Map<Class<? extends Resource>, Integer> cost = new HashMap<>();
    cost.put(Wood.class, 1);
    cost.put(Brick.class, 1);
    return cost;
  }

  @Override
  public String getType() {
    return "Street";
  }

  @Override
  public String toString() {
    return "Street owned by " + owner.getName() + " at " + location;
  }
}
