package de.dhbw.frontEnd.board;

import de.dhbw.catanBoard.hexGrid.IntTupel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import java.util.function.Consumer;
import javafx.scene.input.MouseEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HexTile extends Group {
  private final int q, r;
  private final String resourceName;
  private final SceneBoardController boardController;

  public HexTile(int q, int r, double centerX, double centerY, double size, String resourceName, SceneBoardController boardController) {
    this.q = q;
    this.r = r;
    this.resourceName = resourceName;
    this.boardController = boardController;

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

    this.setOnMouseClicked(evt -> {

      System.out.println("HexTile: q=" + q + ", r=" + r + ", Res=" + resourceName);

      if (boardController.getNewBanditLocationClickCallback() != null) {
        boardController.getNewBanditLocationClickCallback().accept(new IntTupel(q, r));
      } else {
        log.debug("⚠️ No bandit location callback set yet.");
      }
    });
  }
}
