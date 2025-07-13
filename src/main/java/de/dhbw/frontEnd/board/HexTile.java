package de.dhbw.frontEnd.board;

import de.dhbw.catanBoard.hexGrid.IntTupel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import java.util.function.Consumer;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;



@Slf4j
public class HexTile extends Group {
  private final int q, r;
  private final String resourceName;
  private final int numberToken;
  private final SceneBoardController boardController;

  public HexTile(int q, int r, double centerX, double centerY, double size, String resourceName, int numberToken, SceneBoardController boardController) {
    this.q = q;
    this.r = r;
    this.numberToken = numberToken;
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



    // ❶ Sechseck zeichnen (unverändert)

    if (numberToken > 0) {
      double radius = size * 0.30;

      Circle badge = new Circle(centerX, centerY, radius, Color.web("#E2DBC7"));
      badge.setStroke(Color.web("#412515"));

      Text txt = new Text(centerX - 4, centerY + 4, String.valueOf(numberToken));
      txt.setFont(Font.font("Serif", size * 0.20));       // ← Serif-Schrift
      txt.setFill(Color.web("#412515"));                  // ← brauner Farbton
      txt.setMouseTransparent(true);                      // Klick geht ans HexTile

      getChildren().addAll(badge, txt);
    }


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
