
package de.dhbw.catanBoard.hexGrid;

/**
 * Represents a coordinate pair (q, r) in an axial hex grid system.
 * <p>
 * This record is used to uniquely identify the position of hex tiles on the Catan board.
 * It provides a concise and immutable structure with built-in methods like equals and hashCode.
 * </p>
 *
 * @param q the axial q-coordinate
 * @param r the axial r-coordinate
 */
public record IntTupel(int q, int r) {
    // This record automatically provides:
    // - private final fields 'q' and 'r'
    // - a canonical constructor
    // - public accessor methods q() and r()
    // - equals(), hashCode(), and toString()
}
