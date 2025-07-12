package de.dhbw.frontEnd.board;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class NodeFX extends Button {

    private static final int SIZE = 14; // Durchmesser in px

    private static final String BASE_STYLE =
            "-fx-background-color: rgba(255,255,255,0.3); " +
                    "-fx-background-radius: 50%; " +
                    "-fx-border-radius: 50%; " +
                    "-fx-min-width: " + SIZE + "px; " +
                    "-fx-min-height: " + SIZE + "px; " +
                    "-fx-max-width: " + SIZE + "px; " +
                    "-fx-max-height: " + SIZE + "px; " +
                    "-fx-border-color: grey;" +
                    "-fx-padding: 0;";

    private static final String HOVER_STYLE =
            "-fx-background-color: white; " +
                    "-fx-background-radius: 50%; " +
                    "-fx-border-radius: 50%; " +
                    "-fx-min-width: " + SIZE + "px; " +
                    "-fx-min-height: " + SIZE + "px; " +
                    "-fx-max-width: " + SIZE + "px; " +
                    "-fx-max-height: " + SIZE + "px; " +
                    "-fx-border-color: gold; " +
                    "-fx-border-width: 1px; " +
                    "-fx-padding: 0;";

    public NodeFX() {
        this.setStyle(BASE_STYLE);

        DropShadow glow = new DropShadow();
        glow.setColor(Color.GOLD);
        glow.setRadius(10);
        glow.setSpread(0.6);

        this.setOnMouseEntered(event -> {
            this.setStyle(HOVER_STYLE);
            this.setEffect(glow);
        });

        this.setOnMouseExited(event -> {
            this.setStyle(BASE_STYLE);
            this.setEffect(null);
        });
    }
}
