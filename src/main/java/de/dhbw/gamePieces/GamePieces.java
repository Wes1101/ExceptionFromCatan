package de.dhbw.gamePieces;

import lombok.Getter;
import lombok.Setter;
import org.example.execptionfromcatan.tiles.HexTile;

/**
 * This Class is administrating all the game pieces.
 *
 * @author Atussa Mehrawari
 * @version 0.1
 *
 */


@Getter
@Setter
public abstract class GamePieces {
    protected HexTile location;

    public GamePieces(HexTile location) {
        this.location = location;
    }

    public HexTile getLocation() {
        return location;
    }

    public abstract String getType();
}




