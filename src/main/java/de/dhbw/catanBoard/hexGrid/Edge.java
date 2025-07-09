
package de.dhbw.catanBoard.hexGrid;

import de.dhbw.gamePieces.Street;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an edge between two nodes in the Catan board graph.
 * <p>
 * An edge can hold a street (road) built by a player. The edge is initially buildable.
 * </p>
 */
@Getter
@Setter
public class Edge {

    /**
     * Indicates whether the edge is currently buildable.
     * <p>
     * This flag may be used to prevent or allow construction depending on game state.
     * </p>
     */
    private boolean build;

    /**
     * The street (road) currently built on this edge.
     * <p>
     * Initially null until a player builds a road.
     * </p>
     */
    private Street street;

    /**
     * Constructs a new buildable edge with no street.
     */
    public Edge() {
        this.build = true;
        this.street = null;
    }
}
