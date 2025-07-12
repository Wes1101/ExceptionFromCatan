package de.dhbw.frontEnd.board;

import de.dhbw.catanBoard.hexGrid.IntTupel;
import de.dhbw.catanBoard.hexGrid.Node;
import de.dhbw.catanBoard.hexGrid.Tile;
import de.dhbw.catanBoard.hexGrid.Tiles.Resource;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class HexTile extends Group {
  private final int q, r;
  private final String resourceName;
  private final SceneBoardController boardController;

  public HexTile(Tile hexTile, double centerX, double centerY, double size, String resourceName, SceneBoardController boardController) {
    this.q = hexTile.getCoordinates().q();
    this.r = hexTile.getCoordinates().r();
    this.resourceName = resourceName;
    this.boardController = boardController;

    // Hexagon erzeugen
    Polygon hex = new Polygon();
    List<double[]> cornerPoints = new ArrayList<>();

    for (int i = 0; i < 6; i++) {
      int shiftedIndex = (i + 5) % 6; // entspricht (i - 1 + 6) % 6
      double angle = Math.toRadians(60 * shiftedIndex - 30);
      double x = centerX + size * Math.cos(angle);
      double y = centerY + size * Math.sin(angle);
      hex.getPoints().addAll(x, y);
      cornerPoints.add(new double[]{x, y});
    }

    // Hintergrundtextur setzen
    Image img = null;
    try {
      img = new Image(getClass().getResourceAsStream(resourceName + ".png"));
    } catch (Exception e) {
      log.warn("⚠️ Bild konnte nicht geladen werden für Ressource: " + resourceName, e);
    }

    if (img != null) {
      hex.setFill(new ImagePattern(img));
    } else {
      hex.setFill(Color.WHITE);
    }

    hex.setStroke(Color.GRAY);
    hex.setStrokeWidth(1);
    hex.setUserData(new int[]{q, r});

    // Hexagon hinzufügen (liegt unter den Buttons)
    getChildren().add(hex);

    // Wenn Resource-Typ, dann Buttons an Eckpunkten über Controller einfügen
    if (hexTile instanceof Resource) {
      boardController.addCornerButtons(hexTile, cornerPoints);
      boardController.addEdgeButtons(hexTile, cornerPoints);
    }

    // Mausklick-Callback
    this.setOnMouseClicked(evt -> {
      System.out.println("HexTile: q=" + q + ", r=" + r + ", Res=" + resourceName);

      System.out.println(
              "Node IDs: " +
                      Arrays.stream(hexTile.getHexTileNodes())
                              .filter(Objects::nonNull)       // Nur echte Nodes
                              .map(Node::getId)               // Hole Integer-ID
                              .map(String::valueOf)           // Konvertiere zu String
                              .collect(Collectors.joining(", "))
      );

      if (boardController.getNewBanditLocationClickCallback() != null) {
        boardController.getNewBanditLocationClickCallback().accept(new IntTupel(q, r));
      } else {
        log.debug("⚠️ No bandit location callback set yet.");
      }
    });
  }
}
