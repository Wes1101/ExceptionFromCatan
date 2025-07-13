
package de.dhbw.frontEnd.board;

import de.dhbw.catanBoard.hexGrid.IntTupel;
import de.dhbw.catanBoard.hexGrid.Node;
import de.dhbw.catanBoard.hexGrid.Tile;
import de.dhbw.catanBoard.hexGrid.Tiles.Resource;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a visual hexagonal tile on the game board.
 * <p>
 * This class is responsible for rendering the tile, its texture, dice number,
 * and attaching corner and edge interaction buttons through the SceneBoardController.
 * It also handles user interactions such as selecting a tile for bandit placement.
 * </p>
 */
@Slf4j
public class HexTile extends Group {
  private final int q, r;
  private final String resourceName;
  private final SceneBoardController boardController;
  int diceNumber;

  /**
   * Constructs a new HexTile and initializes its visuals and interactions.
   *
   * @param hexTile         the associated game logic tile
   * @param centerX         x-coordinate of the tile center
   * @param centerY         y-coordinate of the tile center
   * @param size            radius/size of the hexagon
   * @param resourceName    name of the resource to load texture
   * @param boardController reference to the scene controller for interaction callbacks
   * @param diceNumber      number assigned to this tile (used in dice rolls)
   */
  public HexTile(Tile hexTile, double centerX, double centerY, double size, String resourceName, SceneBoardController boardController, int diceNumber) {
    this.q = hexTile.getCoordinates().q();
    this.r = hexTile.getCoordinates().r();
    this.resourceName = resourceName;
    this.diceNumber = diceNumber;
    this.boardController = boardController;

    // Create the hexagon shape
    Polygon hex = new Polygon();
    List<double[]> cornerPoints = new ArrayList<>();

    for (int i = 0; i < 6; i++) {
      int shiftedIndex = (i + 5) % 6;
      double angle = Math.toRadians(60 * shiftedIndex - 30);
      double x = centerX + size * Math.cos(angle);
      double y = centerY + size * Math.sin(angle);
      hex.getPoints().addAll(x, y);
      cornerPoints.add(new double[]{x, y});
    }

    // Apply texture or fallback color
    Image img = null;
    try {
      img = new Image(getClass().getResourceAsStream(resourceName + ".png"));
    } catch (Exception e) {
      log.warn("⚠️ Could not load image for resource: " + resourceName, e);
    }

    if (img != null) {
      hex.setFill(new ImagePattern(img));
    } else {
      hex.setFill(Color.WHITE);
    }

    hex.setStroke(Color.GRAY);
    hex.setStrokeWidth(1);
    hex.setUserData(new int[]{q, r});
    getChildren().add(hex);

    // If tile is a resource tile, add UI elements for interaction
    if (hexTile instanceof Resource) {
      boardController.addCornerButtons(hexTile, cornerPoints);
      boardController.addEdgeButtons(hexTile, cornerPoints);

      if (this.diceNumber > 0) {
        double radius = size * 0.30;
        Circle badge = new Circle(centerX, centerY, radius, Color.web("#E2DBC7"));
        badge.setStroke(Color.web("#412515"));

        Text txt = new Text(centerX - 4, centerY + 4, String.valueOf(this.diceNumber));
        txt.setFont(Font.font("Serif", size * 0.20));
        txt.setFill(Color.web("#412515"));
        txt.setMouseTransparent(true);

        getChildren().addAll(badge, txt);
      }
    }

    // Mouse click handler for bandit interaction
    this.setOnMouseClicked(evt -> {
      System.out.println("HexTile: q=" + q + ", r=" + r + ", Res=" + resourceName);
      System.out.println("Node IDs: " +
              Arrays.stream(hexTile.getHexTileNodes())
                      .filter(Objects::nonNull)
                      .map(Node::getId)
                      .map(String::valueOf)
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
