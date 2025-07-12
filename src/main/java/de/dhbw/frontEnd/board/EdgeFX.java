package de.dhbw.frontEnd.board;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

class EdgeFX extends Button {

    private static final String BASE_STYLE =
            "-fx-background-color: rgba(255,255,255,0.3);" +
                    "-fx-background-radius: 0;" +
                    "-fx-border-color: grey;" +
                    "-fx-border-width: 1px;" +
                    "-fx-padding: 0;";

    private static final String HOVER_STYLE =
            "-fx-background-color: white;" +
                    "-fx-background-radius: 0;" +
                    "-fx-border-color: gold;" +
                    "-fx-border-width: 2px;" +
                    "-fx-padding: 0;";

    public EdgeFX(double width, double height) {
        setPrefWidth(width);
        setPrefHeight(height);
        setMinWidth(width);
        setMinHeight(height);
        setMaxWidth(width);
        setMaxHeight(height);
        setStyle(BASE_STYLE);

        DropShadow glow = new DropShadow();
        glow.setColor(Color.GOLD);
        glow.setRadius(10);
        glow.setSpread(0.6);

        // Hover: mehr Kontrast, Rahmen + Glow-Effekt
        this.setOnMouseEntered(e -> {
            setStyle(HOVER_STYLE);
            setEffect(glow);
        });

        // Reset bei MouseExit
        this.setOnMouseExited(e -> {
            setStyle(BASE_STYLE);
            setEffect(null);
        });
    }
}
