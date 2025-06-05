package de.dhbw.catanBoard.hexGrid;

/**
 * Enum für die sechs Nachbarrichtungen im axialen Hex-Koordinatensystem.
 * Jeder Eintrag enthält die Verschiebung in den Koordinaten (dq, dr).
 */
public enum Directions {
  /** Nordost-Richtung: Verschiebung (1, -1). */
  NORTH_EAST(1, -1),

  /** Ost-Richtung: Verschiebung (1, 0). */
  EAST(1, 0),

  /** Südost-Richtung: Verschiebung (0, 1). */
  SOUTH_EAST(0, 1),

  /** Südwest-Richtung: Verschiebung (-1, 1). */
  SOUTH_WEST(-1, 1),

  /** West-Richtung: Verschiebung (-1, 0). */
  WEST(-1, 0),

  /** Nordwest-Richtung: Verschiebung (0, -1). */
  NORTH_WEST(0, -1);

  /** Änderung der q-Achse (axial). */
  private final int dq;

  /** Änderung der r-Achse (axial). */
  private final int dr;

  /**
   * Konstruktor für eine Richtung mit den gegebenen Verschiebungen.
   *
   * @param dq Veränderung auf der q-Achse
   * @param dr Veränderung auf der r-Achse
   */
  private Directions(int dq, int dr) {
    this.dq = dq;
    this.dr = dr;
  }

  /**
   * Liefert die Verschiebung auf der q-Achse für diese Richtung.
   *
   * @return Wert der dq-Koordinate
   */
  public int getDq() {
    return dq;
  }

  /**
   * Liefert die Verschiebung auf der r-Achse für diese Richtung.
   *
   * @return Wert der dr-Koordinate
   */
  public int getDr() {
    return dr;
  }
}
