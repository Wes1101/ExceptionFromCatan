package de.dhbw.frontEnd.board;

import javafx.scene.control.Button;

public class NodeFX extends Button {

    public NodeFX() {
        this.setStyle(
                "-fx-background-color: red; " +
                        "-fx-background-radius: 50%; " +
                        "-fx-min-width: 10px; " +
                        "-fx-min-height: 10px; " +
                        "-fx-max-width: 10px; " +
                        "-fx-max-height: 10px;"
        );
    }
}
