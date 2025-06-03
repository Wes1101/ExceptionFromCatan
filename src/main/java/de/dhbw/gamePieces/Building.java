package de.dhbw.gamePieces;
import de.dhbw.frontEnd.board.HexTile;
import de.dhbw.player.Player;
import de.dhbw.resources.Resources;

import java.util.Map;


/**
 This abstract class manages all core functions of buildings in the game.
 * Subclasses include specific building types like City and Street.
 *
 * @author Atussa Mehrawari
 * @version 1.0
 *
 */

@lombok.Getter
public abstract class Building extends GamePieces {
    protected Player owner;

    public Building(Player owner, HexTile location) {
        super(location);
        this.owner = owner;
    }

    /**
     * Returns the resource cost required to build this building.
     * @return Map of ResourceType to quantity
     */
    public abstract Map<Resources, Integer> getBuildCost();

}
