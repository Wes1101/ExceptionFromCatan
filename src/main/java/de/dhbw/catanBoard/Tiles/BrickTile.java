package de.dhbw.catanBoard.Tiles;

import de.dhbw.catanBoard.hexGrid.Node;
import de.dhbw.catanBoard.hexGrid.Tile;
import de.dhbw.resources.Resources;

public class BrickTile extends Tile {
    public BrickTile(Node[] nodes) {
        super(Resources.BRICK, nodes);
    }
}
