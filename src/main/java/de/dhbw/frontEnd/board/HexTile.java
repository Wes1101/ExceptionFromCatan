package de.dhbw.frontEnd.board;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import java.util.function.Consumer;
import javafx.scene.input.MouseEvent;


public class HexTile extends Group {
  private final int q, r;
  private final String resourceName;

  public HexTile(int q, int r, double centerX, double centerY, double size, String resourceName) {
    this.q = q;
    this.r = r;
    this.resourceName = resourceName;

    Polygon hex = new Polygon();
    for (int i = 0; i < 6; i++) {
      double angle = Math.toRadians(60 * i - 30);
      double x = centerX + size * Math.cos(angle);
      double y = centerY + size * Math.sin(angle);
      hex.getPoints().addAll(x, y);
    }

    // Hex Bilder
    Image img = null;
    try {
      img = new Image(getClass().getResourceAsStream( resourceName + ".png"));
    } catch (Exception e) {
    }
    if (img != null) {
      hex.setFill(new ImagePattern(img));
    } else {
      hex.setFill(Color.WHITE);
    }

    hex.setStroke(Color.GRAY);
    hex.setStrokeWidth(1);
    hex.setUserData(new int[]{ q, r });
    getChildren().add(hex);

    this.setOnMouseClicked(evt ->
            System.out.println("HexTile: q=" + q + ", r=" + r + ", Res=" + resourceName)
    );

    this.setOnMouseClicked((MouseEvent event) -> {
      if (onClickedCallback != null) {
        onClickedCallback.accept(this);
      }
    });


  }

  private Consumer<HexTile> onClickedCallback;

  public void setOnClickedCallback(Consumer<HexTile> callback) {
    this.onClickedCallback = callback;
  }

}
