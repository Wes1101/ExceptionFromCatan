package de.dhbw.gamePieces;
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
public abstract class Building {
    protected Player owner;
    BuildingTypes buildingType;

    public Building(Player owner) {
        this.owner = owner;
    }
}
