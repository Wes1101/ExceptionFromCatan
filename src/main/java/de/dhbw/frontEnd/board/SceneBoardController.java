package de.dhbw.frontEnd.board;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;


import de.dhbw.catanBoard.CatanBoard;
import de.dhbw.catanBoard.hexGrid.IntTupel;
import de.dhbw.catanBoard.hexGrid.HexTile;
import java.util.Map;

public class SceneBoardController implements Initializable {

  @FXML
  private HBox root;

  @FXML
  private StackPane grain_group;

  @FXML
  private StackPane brick_group;

  @FXML
  private StackPane ore_group;

  @FXML
  private StackPane sheep_group;

  @FXML
  private StackPane lumber_group;

  @FXML
  private StackPane development_card_group;

  @FXML
  private ScrollPane scroll_pane;

  @FXML
  private StackPane board_stack;

  @FXML
  private Pane tile_layer;

  @FXML
  private Pane road_layer;

  @FXML
  private Pane building_layer;

  @FXML
  private ImageView trade_card;

  @FXML
  private ImageView setting_button;

  @FXML
  private StackPane trade_menue;

  @FXML
  private StackPane setting_menue;

  @FXML
  private Button close;

  @FXML
  private Button close_option_menue;

  // Duration of fade animation
  private static final Duration FADE_DURATION = Duration.millis(300);
  private static final Duration HOVER_DURATION = Duration.millis(200);

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    // Hintergrundbild laden und setzen:
    Image img = new Image(getClass().getResource("scene_background.png").toExternalForm());
    BackgroundImage bgImg = new BackgroundImage(
            img,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
    );
    root.setBackground(new Background(bgImg));

    // Map scroll effect
    board_stack.setOnScroll(
            e -> {
              double zoomFactor = 1.1;
              double delta = e.getDeltaY() > 0 ? zoomFactor : 1 / zoomFactor;
              board_stack.setScaleX(board_stack.getScaleX() * delta);
              board_stack.setScaleY(board_stack.getScaleY() * delta);
              e.consume();
            }
    );

    initBoard();

    // Card hover effect
    addHover(grain_group);
    addHover(brick_group);
    addHover(ore_group);
    addHover(sheep_group);
    addHover(lumber_group);
    addHover(development_card_group);

    // Menue setup
    trade_menue.setVisible(false);
    trade_menue.setOpacity(0);

    trade_card.setOnMouseClicked(this::onOpenTrade);
    close.setOnAction(this::onCloseTrade);

    setting_menue.setVisible(false);
    setting_menue.setOpacity(0);

    setting_button.setOnMouseClicked(this::onOpenSetting);
    close_option_menue.setOnAction(this::onCloseSetting);

    root.getChildrenUnmodifiable().stream()
            .filter(n -> n instanceof Button)
            .forEach(n -> n.toFront());


  }

  private void addHover(StackPane pane) {
    // Card Hover up
    pane.setOnMouseEntered(
            e -> {
              TranslateTransition tt = new TranslateTransition(HOVER_DURATION, pane);
              tt.setToY(-10);
              tt.play();
            }
    );
    // Card Hover down
    pane.setOnMouseExited(
            e -> {
              TranslateTransition tt = new TranslateTransition(HOVER_DURATION, pane);
              tt.setToY(0);
              tt.play();
            }
    );
  }

  private void onOpenTrade(MouseEvent event) {
    // Trade menue animation (open)
    trade_menue.setVisible(true);
    FadeTransition fadeIn = new FadeTransition(FADE_DURATION, trade_menue);
    fadeIn.setFromValue(0);
    fadeIn.setToValue(1);
    fadeIn.play();
  }

  private void onCloseTrade(ActionEvent event) {
    // Trade menue animation (close)
    FadeTransition fadeOut = new FadeTransition(FADE_DURATION, trade_menue);
    fadeOut.setFromValue(1);
    fadeOut.setToValue(0);
    fadeOut.setOnFinished(e -> trade_menue.setVisible(false));
    fadeOut.play();
  }

  private void onOpenSetting(MouseEvent event) {
    // Setting menue animation (open)
    setting_menue.setVisible(true);
    FadeTransition fadeIn = new FadeTransition(FADE_DURATION, setting_menue);
    fadeIn.setFromValue(0);
    fadeIn.setToValue(1);
    fadeIn.play();
  }

  private void onCloseSetting(ActionEvent event) {
    // Setting menue animation (close)
    FadeTransition fadeOut = new FadeTransition(FADE_DURATION, setting_menue);
    fadeOut.setFromValue(1);
    fadeOut.setToValue(0);
    fadeOut.setOnFinished(e -> setting_menue.setVisible(false));
    fadeOut.play();
  }

  private void initBoard() {
    int radius = 3;
    CatanBoard catanBoard = new CatanBoard(radius);

    Map<IntTupel, de.dhbw.catanBoard.hexGrid.HexTile> hexes = catanBoard.getHexTiles();


    System.out.println("Anzahl HexTiles: " + hexes.size());
    for (IntTupel tupel : hexes.keySet()) {
      System.out.println("Hex bei q=" + tupel.q() + ", r=" + tupel.r());
    }

    double size = 50;
    double width = Math.sqrt(3) * size;
    double height = 1.5 * size;
    double offsetX = 400;
    double offsetY = 300;

    for (IntTupel coords : hexes.keySet()) {
      int q = coords.q();
      int r = coords.r();

      double x = offsetX + (q * width) + (r * (width / 2));
      double y = offsetY - (r * height);

      // Ressource vom Backend
      String resourceName = hexes.get(coords).getResourceType().name();


      de.dhbw.frontEnd.board.HexTile frontendHex =
              new de.dhbw.frontEnd.board.HexTile(q, r, x, y, size, resourceName);

      tile_layer.getChildren().add(frontendHex);
      frontendHex.toBack();
    }


    int[][] nodeGrid = {
            {52, 53, 24, 25, 26, 27, 28},   // Zeile 1
            {50, 51, 22, 23, 6, 7, 8, 29, 30},  // Zeile 2
            {48, 49, 20, 21, 5, 0, 1, 9, 10, 31, 32}, // Zeile 3
            {47, 46, 19, 18, 4, 3, 2, 12, 11, 34, 33},  // Zeile 4
            {45, 44, 17, 16, 15, 14, 13, 36, 35}, //Zeile 5
            {43, 42, 41, 40, 39, 38, 37}, //Zeile 6
    };

  }
}
