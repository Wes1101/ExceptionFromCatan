package de.dhbw.frontEnd.board;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HexTile extends Group {
  private final int q, r;

  public HexTile(int q, int r, double centerX, double centerY, double size) {
    this.q = q;
    this.r = r;

    // Polygon
    Polygon hex = new Polygon();
    for (int i = 0; i < 6; i++) {
      double angle = Math.toRadians(60 * i - 30);
      double x = centerX + size * Math.cos(angle);
      double y = centerY + size * Math.sin(angle);
      hex.getPoints().addAll(x, y);
    }
    hex.setFill(Color.WHITE);
    hex.setStroke(Color.GRAY);
    hex.setStrokeWidth(1);

    // q/r als UserData ans Polygon hängen (oder alternativ: an diese Group)
    hex.setUserData(new int[] { q, r });

    getChildren().add(hex);

    // Klick Handler hextile
    this.setOnMouseClicked(
        evt -> {
          System.out.println("HexTile: q=" + q + ", r=" + r);
        }
      );
  }
}
