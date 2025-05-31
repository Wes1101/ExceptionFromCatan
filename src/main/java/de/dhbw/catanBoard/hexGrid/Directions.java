package de.dhbw.catanBoard.hexGrid;

public enum Directions {
    NORTH_EAST ( 1, -1 ),
    EAST       ( 1, 0 ),
    SOUTH_EAST ( 0,  1 ),
    SOUTH_WEST (-1,  1 ),
    WEST       ( -1,  0 ),
    NORTH_WEST (0,  -1 );

    private final int dq;
    private final int dr;

    private Directions(int dq, int dr) {
        this.dq = dq;
        this.dr = dr;
    }

    public int getDq() {
        return dq;
    }

    public int getDr() {
        return dr;
    }
}
