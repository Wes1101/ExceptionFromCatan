package de.dhbw.gameController;

import de.dhbw.catanBoard.hexGrid.IntTupel;

/**
 * Represents a pair of {@link IntTupel} coordinates.
 * <p>
 * Used to pass or store two related positions on the game board, such as endpoints of a road,
 * or selections made by the user involving two different nodes or tiles.
 * </p>
 *
 * @param t1 The first coordinate tuple
 * @param t2 The second coordinate tuple
 */
public record TwoTuples(IntTupel t1, IntTupel t2) {
}
