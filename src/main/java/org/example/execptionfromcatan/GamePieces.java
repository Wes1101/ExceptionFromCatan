package org.example.execptionfromcatan;

import lombok.Getter;
import lombok.Setter;
import org.example.execptionfromcatan.tiles.HexTile;

/**
 * This Class is administrating all the game pieces.
 *
 * @Atussa Mehrawari
 * @Version??
 *
 */


@Getter
@Setter
public abstract class GamePieces {
    protected HexTile location;

    public GamePieces(HexTile location) {
        this.location = location;
    }

    public abstract String getType();
}




