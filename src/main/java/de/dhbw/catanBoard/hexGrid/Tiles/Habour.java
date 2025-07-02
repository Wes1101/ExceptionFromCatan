package de.dhbw.catanBoard.hexGrid.Tiles;

import de.dhbw.catanBoard.hexGrid.Node;
import de.dhbw.catanBoard.hexGrid.Tile;
import de.dhbw.resources.Resources;
import lombok.Getter;

@Getter
public class Habour extends Tile {
    Resources resourceType;

    public Habour(Resources resourceType) {
        this.resourceType = resourceType;
        setType("habour");
    }

    public void trigger() {

    }
}
