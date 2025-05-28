package com.version.game_interface;

import javafx.scene.Group;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;


public class HexTile extends Group {
    private final int numberToken;

    public HexTile(double centerX, double centerY, double size, int numberToken) {
        this.numberToken = numberToken;

        // Creating polygon
        Polygon hex = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i - 30);
            double x = centerX + size * Math.cos(angle);
            double y = centerY + size * Math.sin(angle);
            hex.getPoints().addAll(x, y);
        }

        hex.setFill(Color.WHITE);        // weißer Hintergrund
        hex.setStroke(Color.GRAY);       // grauer Rand
        hex.setStrokeWidth(1);


        Text label = new Text(String.valueOf(numberToken));
        label.setFont(Font.font(18));
        label.setX(centerX - label.getLayoutBounds().getWidth() / 2);
        label.setY(centerY + label.getLayoutBounds().getHeight() / 4);

        // fill group
        getChildren().addAll(hex, label);
    }

    public int getNumberToken() {
        return numberToken;
    }
}