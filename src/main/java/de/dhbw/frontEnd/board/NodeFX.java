
/**
 * NodeFX represents a circular UI button element for nodes on the Catan board.
 * <p>
 * This class provides basic styling and interactive hover effects to enhance visual feedback.
 * It uses JavaFX DropShadow effects to highlight nodes when hovered over.
 * </p>
 */

package de.dhbw.frontEnd.board;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class NodeFX extends Button {

    private static final int SIZE = 14; // Diameter in pixels

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

    /**
     * Constructs a NodeFX button with default style and hover effect.
     */
    public NodeFX() {
        this.setStyle(BASE_STYLE);

        DropShadow glow = new DropShadow();
        glow.setColor(Color.GOLD);
        glow.setRadius(10);
        glow.setSpread(0.6);

        // Apply hover effect
        this.setOnMouseEntered(event -> {
            this.setStyle(HOVER_STYLE);
            this.setEffect(glow);
        });

        // Reset on mouse exit
        this.setOnMouseExited(event -> {
            this.setStyle(BASE_STYLE);
            this.setEffect(null);
        });
    }
}
