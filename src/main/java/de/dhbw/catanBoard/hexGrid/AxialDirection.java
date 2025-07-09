
package de.dhbw.catanBoard.hexGrid;

/**
 * Represents the six neighbor directions in the axial hex grid coordinate system.
 * <p>
 * Each direction corresponds to a specific offset in axial coordinates (q, r),
 * allowing movement or adjacency detection within a hexagonal grid.
 * </p>
 */
public enum AxialDirection {

    /** East direction (q+1, r+0). */
    EAST       ( 1,  0 ),

    /** Northeast direction (q+1, r-1). */
    NORTH_EAST ( 1, -1 ),

    /** Northwest direction (q+0, r-1). */
    NORTH_WEST ( 0, -1 ),

    /** West direction (q-1, r+0). */
    WEST       (-1,  0 ),

    /** Southwest direction (q-1, r+1). */
    SOUTH_WEST (-1,  1 ),

    /** Southeast direction (q+0, r+1). */
    SOUTH_EAST ( 0,  1 );

    /** Offset on the q-axis for this direction. */
    private final int dq;

    /** Offset on the r-axis for this direction. */
    private final int dr;

    /**
     * Constructs an axial direction with specific coordinate offsets.
     *
     * @param dq the q-axis offset
     * @param dr the r-axis offset
     */
    private AxialDirection(int dq, int dr) {
        this.dq = dq;
        this.dr = dr;
    }

    /**
     * Gets the q-axis offset of the direction.
     *
     * @return the dq offset
     */
    public int getDq() {
        return dq;
    }

    /**
     * Gets the r-axis offset of the direction.
     *
     * @return the dr offset
     */
    public int getDr() {
        return dr;
    }
}
