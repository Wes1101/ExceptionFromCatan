package de.dhbw.catanBoard.hexGrid;

import de.dhbw.gamePieces.Street;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Edge {
    boolean build;
    Street street;

    public Edge() {
        this.build = true;
        this.street = null;
    }
}
