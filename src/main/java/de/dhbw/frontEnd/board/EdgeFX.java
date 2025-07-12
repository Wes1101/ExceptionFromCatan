package de.dhbw.frontEnd.board;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EdgeFX extends Rectangle {

    public EdgeFX(double lenght, int v1) {
        super(lenght, v1);
        this.setFill(Color.YELLOW);
    }
}
