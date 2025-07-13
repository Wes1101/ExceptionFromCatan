
package de.dhbw.frontEnd.board;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

/**
 * Represents a visual edge element on the game board.
 * <p>
 * This class extends a JavaFX Button and is styled to represent a clickable edge (road)
 * in the Catan GUI. It visually responds to mouse hover with style and glow effects.
 * </p>
 */
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

    /**
     * Constructs an EdgeFX object with specified dimensions.
     * <p>
     * Applies visual styling and hover effects using drop shadow and CSS styles.
     * </p>
     *
     * @param width  the preferred width of the edge
     * @param height the preferred height of the edge
     */
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

        // On hover: highlight the edge with a glowing effect and golden border
        this.setOnMouseEntered(e -> {
            setStyle(HOVER_STYLE);
            setEffect(glow);
        });

        // On mouse exit: revert to default appearance
        this.setOnMouseExited(e -> {
            setStyle(BASE_STYLE);
            setEffect(null);
        });
    }
}
