package de.dhbw.frontEnd.board;

import de.dhbw.catanBoard.hexGrid.Tiles.Ressource;
import de.dhbw.player.Player;
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
import de.dhbw.catanBoard.hexGrid.Tile;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import java.util.function.Consumer;

import de.dhbw.catanBoard.hexGrid.Tiles.Water;

@Slf4j
public class SceneBoardController implements Initializable, GameUI {

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
  @Setter
  private CatanBoard catanBoard;

  private Player activePlayer;

  private Consumer<String> settlementClickCallback;


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

    //initBoard();

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

  @FXML
  private void onNodeClicked(ActionEvent event) {
    Button btn = (Button) event.getSource();
    String fxId = btn.getId();
    System.out.println("Knoten geklickt: fx:id=" + fxId);

    if (settlementClickCallback != null) {
      settlementClickCallback.accept(fxId);
    }
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

  public void initBoard() {
    Map<IntTupel, Tile> hexes = catanBoard.getHexTiles();


    log.debug("Anzahl HexTiles: " + hexes.size());
    for (IntTupel tupel : hexes.keySet()) {
      log.debug("Hex bei q=" + tupel.q() + ", r=" + tupel.r());
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


      // Wasser vom Backend
      if (hexes.get(coords) instanceof de.dhbw.catanBoard.hexGrid.Tiles.Water) {
        de.dhbw.frontEnd.board.HexTile frontendHex =
                new de.dhbw.frontEnd.board.HexTile(q, r, x, y, size, "Water");
        tile_layer.getChildren().add(frontendHex);
        frontendHex.toBack();
      }


      // Hafen vom Backend
      else if (hexes.get(coords) instanceof de.dhbw.catanBoard.hexGrid.Tiles.Habour) {
        de.dhbw.catanBoard.hexGrid.Tiles.Habour h =
                (de.dhbw.catanBoard.hexGrid.Tiles.Habour) hexes.get(coords);
        String resourceName = h.getResourceType().name() + "_Harbour";

        de.dhbw.frontEnd.board.HexTile frontendHex =
                new de.dhbw.frontEnd.board.HexTile(q, r, x, y, size, resourceName);

        tile_layer.getChildren().add(frontendHex);
        frontendHex.toBack();
      }



      // Ressource vom Backend
      else if (hexes.get(coords) instanceof Ressource) {
        Ressource resTile = (Ressource) hexes.get(coords);
        String resourceName = resTile.getResourceType().name();


        de.dhbw.frontEnd.board.HexTile frontendHex =
                new de.dhbw.frontEnd.board.HexTile(q, r, x, y, size, resourceName);

        tile_layer.getChildren().add(frontendHex);
        frontendHex.toBack();
      }

    }

  }
  public void setSettlementClickCallback(Consumer<String> callback) {
    this.settlementClickCallback = callback;
  }

  /**
   * Method to pass active player to UI. Needed to show in the UI whose currently  playing via player id or name
   *
   * @param player Currently active player
   */
  @Override
  public void setactivePlayer(Player player) {
  activePlayer = player;

  }

  /**
   * Starts the dice roll animation.
   * This is optional and can be implemented for visual effect.
   */
  @Override
  public void startRollDiceAnimation() {

  }

  /**
   * Displays the result of a die roll.
   *
   * @param dice1 the value of the first dice
   * @param dice2 the value of the second dice
   */
  @Override
  public void showDice(int dice1, int dice2) {

  }

  /**
   * Prompts the player to select a location to build a settlement.
   * The UI should display an instruction like "Please select a node to build a settlement."
   *
   * @return the node ID where the settlement should be built
   */
  @Override
  public int buildSettlement() {
    int NodeID = 0;
    Player internalActivePlayer = this.activePlayer;
    return 0;
  }

  /**
   * Prompts the player to select a location to build a street.
   * The UI should display an instruction like "Please select a location to build a street."
   *
   * @return the two adjacent nodes of where the street should be built
   */
  @Override
  public IntTupel buildStreet() {
    return null;
  }

  /**
   * Prompts the player to select a location to build a city.
   * The UI should display an instruction like "Please select a node to build a city."
   *
   * @return the node ID where the city should be built
   */
  @Override
  public int buildCity() {
    return 0;
  }

  /**
   * Updates the UI to reflect the latest resource values and states of all players.
   *
   * @param players an array of all players with their updated states
   */
  @Override
  public void updatePlayerResources(Player[] players) {

  }

  /**
   * Activates the bandit placement phase in the UI.
   * Should return the new location for the bandit.
   *
   * @return the coordinates (as an IntTupel) of the new bandit location
   */
  @Override
  public IntTupel activateBandit() {
    return null;
  }

  /**
   * Prompts the user to select which player to rob.
   * The UI should allow choosing from a list of players.
   *
   * @param players an array of all possible players to be robbed
   * @return the player who was selected to be robbed
   */
  @Override
  public Player getRobbedPlayer(Player[] players) {
    return null;
  }
}
