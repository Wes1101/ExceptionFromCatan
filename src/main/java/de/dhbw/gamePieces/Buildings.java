package de.dhbw.gamePieces;

import lombok.Getter;
import lombok.Setter;

import de.dhbw.player.Player;

/**
 * This Class is administrating all the game pieces.
 *
 * @author Atussa Mehrawari
 * @version 0.1
 *
 */


@Getter
@Setter
public abstract class Buildings {
    protected Player owner;

    public Buildings(Player owner) {
        this.owner = owner;
    }

    //costBuilding

    public abstract String getType();
}




