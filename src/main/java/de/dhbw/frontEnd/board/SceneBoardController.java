package de.dhbw.frontEnd.board;

import de.dhbw.catanBoard.hexGrid.Tiles.Resource;
import de.dhbw.gameController.GameController;
import de.dhbw.gameController.PlayerTupelVar;
import de.dhbw.player.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;


import javafx.scene.shape.Rectangle;  //+++


import java.net.URL;
import java.util.*;

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
import javafx.util.Duration;


import de.dhbw.catanBoard.CatanBoard;
import de.dhbw.catanBoard.hexGrid.IntTupel;
import de.dhbw.catanBoard.hexGrid.Tile;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;


@Slf4j
public class SceneBoardController implements Initializable, GameUI {

  @FXML
  private Label player_1_label;
  @FXML
  private Label player_2_label;
  @FXML
  private Label player_3_label;
  @FXML
  private Label player_4_label;
  @FXML
  private Label player_5_label;
  @FXML
  private Label player_6_label;

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

  @FXML
  private Button diceButton1;

  @FXML
  private Button diceButton2;

  @FXML
  private ImageView finish_turn_button;

  @FXML
  private Button build_development_card;

  @FXML
  private Button build_settlement;

  @FXML
  private Button build_city;

  @FXML
  private Button build_road;

  @FXML
  private Label victory_points_background_number;

  private final Image[] diceImages = new Image[6];
  private Image diceEmptyImage;
  private final Random random = new Random();

  // Duration of fade animation
  private static final Duration FADE_DURATION = Duration.millis(300);
  private static final Duration HOVER_DURATION = Duration.millis(200);
  @Setter
  private CatanBoard catanBoard;

  private Player activePlayer;

  private Consumer<String> settlementClickCallback;

  private CompletableFuture<Integer> settlenemtNodeSelectionFuture;

  private Consumer<String> streetClickCallback;

  private CompletableFuture<IntTupel> streetSelectionFuture;

  private Consumer<String> finishTurnClickCallback;

  private CompletableFuture<String> finishTurnSelectionFuture;

  protected Consumer<IntTupel> newBanditLocationClickCallback;

  protected CompletableFuture<PlayerTupelVar> triggerBanditFuture;

  private Runnable onUIReady;

  private int realDice1 = 1;
  private int realDice2 = 1;

  @Setter
  private GameController gameController;

  private boolean waitingForSettlementClick;
  private Consumer<Button> settlementClickHandler;

  private boolean waitingForCityClick;
  private Consumer<Button> cityClickHandler;

  private boolean waitingForStreetClick;
  private Consumer<Button> streetClickHandler;

  private final Map<IntTupel, ImageView> banditOverlays = new HashMap<>();



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

    Platform.runLater(() -> {
      if (onUIReady != null) onUIReady.run();
    });

    loadDiceImages();


// Würfelbutton-Klicks triggern die Animation
    diceButton1.setOnAction(e -> startRollDiceAnimation());
    diceButton2.setOnAction(e -> startRollDiceAnimation());


    tile_layer.getChildren().stream()
            .filter(n -> n instanceof Rectangle && n.getId() != null && n.getId().startsWith("road_"))
            .forEach(n -> n.setOnMouseClicked(evt -> {
                      System.out.println("Straße geklickt: fx:id=" + n.getId());

                      log.debug("\uD83D\uDD35 Button clicked:  fx:id=" + n.getId());
                      log.debug("🔴 Callback is: " + streetClickCallback);

                      if (streetClickCallback != null) {
                        streetClickCallback.accept(n.getId());
                      }

                      if (waitingForStreetClick && streetClickHandler != null) {
                        log.debug("🟢 Street click handler invoked for: " + n.getId());
                        streetClickHandler.accept((Button) n);
                      }
                    }
            ));

    finish_turn_button.setMouseTransparent(true);
    trade_card.setMouseTransparent(true);
    build_settlement.setDisable(true);
    build_city.setDisable(true);
    build_road.setDisable(true);
    build_development_card.setDisable(true);
  }

  private Background makeDiceBackground(Image image) {
    return new Background(new BackgroundImage(
            image,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, false, false, false, false)
    ));
  }

  private void loadDiceImages() {
    for (int i = 0; i < 6; i++) {
      diceImages[i] = new Image(getClass().getResource("/de/dhbw/frontEnd/board/dice" + (i + 1) + ".png").toExternalForm());
    }
    diceEmptyImage = new Image(getClass().getResource("/de/dhbw/frontEnd/board/diceempty.png").toExternalForm());
  }



  @FXML
  private void onNodeClicked(ActionEvent event) {
    Button btn = (Button) event.getSource();
    String fxId = btn.getId();
    log.debug("\uD83D\uDD35 Button clicked:  fx:id=" + fxId);
    log.debug("🔴 Callback is: " + settlementClickCallback);

    if (settlementClickCallback != null) {
      settlementClickCallback.accept(fxId);
    }

    if (waitingForSettlementClick && settlementClickHandler != null) {
      log.debug("🟢 Settlement click handler invoked for: " + fxId);
      settlementClickHandler.accept(btn);
    }

    if (waitingForCityClick && cityClickHandler != null) {
      log.debug("🟢 City click handler invoked for: " + fxId);
      cityClickHandler.accept(btn);
    }
  }

  @FXML
  private void onFinishTurnClicked(MouseEvent event) {
    log.debug("🔵 Finish Turn button clicked");
    finish_turn_button.setMouseTransparent(true);
    trade_card.setMouseTransparent(true);
    build_settlement.setDisable(true);
    build_city.setDisable(true);
    build_road.setDisable(true);

    if (finishTurnClickCallback != null) {
      finishTurnClickCallback.accept("finish_turn_button");
    }
  }

  @FXML
  private void onBuildSettlement(MouseEvent event) {
    waitingForSettlementClick = true;
    settlementClickHandler = (Button btn) -> {
      log.debug("🟢 Settlement button clicked: " + btn.getId());

      int nodeId = Integer.parseInt(btn.getId().replace("node_", ""));
      gameController.buildSettlement(nodeId, activePlayer);

      waitingForSettlementClick = false;
      settlementClickHandler = null;
    };
  }

  @FXML
  private void onBuildCity(MouseEvent event) {
    waitingForCityClick = true;
    cityClickHandler = (Button btn) -> {
      log.debug("🟢 City button clicked: " + btn.getId());

      int nodeId = Integer.parseInt(btn.getId().replace("node_", ""));
      gameController.buildCity(nodeId, activePlayer);

      waitingForCityClick = false;
      cityClickHandler = null;
    };
  }

  @FXML
  private void onBuildRoad(MouseEvent event) {
    waitingForStreetClick = true;
    streetClickHandler = (Button btn) -> {
      log.debug("🟢 Street button clicked: " + btn.getId());

      String idPart = btn.getId().replace("road_", "");
      String[] parts = idPart.split("_");
      IntTupel streetId = new IntTupel(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
      gameController.buildStreet(streetId, activePlayer);

      waitingForStreetClick = false;
      streetClickHandler = null;
    };
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

  public void setDiceResult(int dice1, int dice2) {
    this.realDice1 = dice1;
    this.realDice2 = dice2;
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
    Map<IntTupel, Tile> hexes = catanBoard.getBoard();


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
                new de.dhbw.frontEnd.board.HexTile(q, r, x, y, size, "Water", this);
        tile_layer.getChildren().add(frontendHex);
        frontendHex.toBack();
      }


      // Hafen vom Backend
      else if (hexes.get(coords) instanceof de.dhbw.catanBoard.hexGrid.Tiles.Harbour) {
        de.dhbw.catanBoard.hexGrid.Tiles.Harbour h =
                (de.dhbw.catanBoard.hexGrid.Tiles.Harbour) hexes.get(coords);
        String resourceName = h.getResourceType().name() + "_Harbour";

        de.dhbw.frontEnd.board.HexTile frontendHex =
                new de.dhbw.frontEnd.board.HexTile(q, r, x, y, size, resourceName, this);

        tile_layer.getChildren().add(frontendHex);
        frontendHex.toBack();
      }



      // Ressource vom Backend
      else if (hexes.get(coords) instanceof Resource) {
        Resource resTile = (Resource) hexes.get(coords);
        String resourceName = resTile.getResourceType().name();


        de.dhbw.frontEnd.board.HexTile frontendHex =
                new de.dhbw.frontEnd.board.HexTile(q, r, x, y, size, resourceName, this);

        tile_layer.getChildren().add(frontendHex);
        frontendHex.toBack();
      }

    }

  }

  public void updateBoard(CatanBoard catanBoard) {
    Map<IntTupel, Tile> hexes = catanBoard.getBoard();

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

      Tile tile = hexes.get(coords);

      // Wasser
      if (tile instanceof de.dhbw.catanBoard.hexGrid.Tiles.Water) {
        HexTile frontendHex = new HexTile(q, r, x, y, size, "Water", this);
        tile_layer.getChildren().add(frontendHex);
        frontendHex.toBack();
      }

      // Hafen
      else if (tile instanceof de.dhbw.catanBoard.hexGrid.Tiles.Harbour h) {
        String resourceName = h.getResourceType().name() + "_Harbour";
        HexTile frontendHex = new HexTile(q, r, x, y, size, resourceName, this);
        tile_layer.getChildren().add(frontendHex);
        frontendHex.toBack();
      }

      // Ressourcen
      else if (tile instanceof Resource resTile) {
        String resourceName = resTile.getResourceType().name();
        HexTile frontendHex = new HexTile(q, r, x, y, size, resourceName, this);
        tile_layer.getChildren().add(frontendHex);
        frontendHex.toBack();
      }

      // Show bandit overlay if needed
      if (tile instanceof Resource resTile) {
        showBanditIfBlocked(coords, resTile, x, y, size);
      }

        //ToDo: add roads and buildings
    }
  }


  private void showBanditIfBlocked(IntTupel coords, Resource tile, double x, double y, double size) {
    log.debug("Tile at {} isBlocked: {}", coords, tile.isBlocked());
    if (tile.isBlocked()) {
      // Only add if not already added
      if (!banditOverlays.containsKey(coords)) {
        Image banditImage = new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/de/dhbw/frontEnd/board/bandit.png")));
        ImageView banditView = new ImageView(banditImage);

        banditView.setFitWidth(size);
        banditView.setFitHeight(size);
        banditView.setX(x - size / 2);
        banditView.setY(y - size / 2);

        tile_layer.getChildren().add(banditView);
        banditOverlays.put(coords, banditView);
      }
    } else {
      // Remove existing bandit image if present
      ImageView existing = banditOverlays.remove(coords);
      if (existing != null) {
        tile_layer.getChildren().remove(existing);
      }
    }
  }


  public void setPlayerAmount(int playerCount) {
    // Set the visibility of player labels based on the number of players
    player_1_label.setVisible(playerCount >= 1);
    player_2_label.setVisible(playerCount >= 2);
    player_3_label.setVisible(playerCount >= 3);
    player_4_label.setVisible(playerCount >= 4);
    player_5_label.setVisible(playerCount >= 5);
    player_6_label.setVisible(playerCount >= 6);

    // Log-Ausgabe zur Überprüfung
    log.info("Anzahl der Spieler in SceneBoardController gesetzt: {}", playerCount);
  }

  /**
   * Method to pass active player to UI. Needed to show in the UI whose currently  playing via player id or name
   *
   * @param player Currently active player
   */
  @Override
  public void setactivePlayer(Player player) {
    this.activePlayer = player; // Den übergebenen Spieler in der Instanzvariablen speichern
    int ID = player.getId();

    victory_points_background_number.setText(Integer.toString(player.getVictoryPoints()));

    this.setActivePlayerLabel(ID);

    // Log-Ausgabe zur Überprüfung
    log.info("Aktiver Spieler in SceneBoardController gesetzt: ID = {}", player.getId());

  }

  private void setActivePlayerLabel(int id) {

    switch (id) {
      case 0:
        player_2_label.setStyle("-fx-text-fill: white");
        player_3_label.setStyle("-fx-text-fill: white");
        player_4_label.setStyle("-fx-text-fill: white");
        player_5_label.setStyle("-fx-text-fill: white");
        player_6_label.setStyle("-fx-text-fill: white");


        player_1_label.setStyle("-fx-text-fill: red");
        log.info("Spieler 1 ist aktiv coloured");


        break;
      case 1:
        player_1_label.setStyle("-fx-text-fill: white");
        player_3_label.setStyle("-fx-text-fill: white");
        player_4_label.setStyle("-fx-text-fill: white");
        player_5_label.setStyle("-fx-text-fill: white");
        player_6_label.setStyle("-fx-text-fill: white");


        player_2_label.setStyle("-fx-text-fill: red");
        log.info("Spieler 2 ist aktiv coloured");

        break;
      case 2:
        player_1_label.setStyle("-fx-text-fill: white");
        player_2_label.setStyle("-fx-text-fill: white");
        player_4_label.setStyle("-fx-text-fill: white");
        player_5_label.setStyle("-fx-text-fill: white");
        player_6_label.setStyle("-fx-text-fill: white");


        player_3_label.setStyle("-fx-text-fill: red");
        log.info("Spieler 3 ist aktiv coloured");

        break;
      case 3:
        player_1_label.setStyle("-fx-text-fill: white");
        player_2_label.setStyle("-fx-text-fill: white");
        player_3_label.setStyle("-fx-text-fill: white");
        player_5_label.setStyle("-fx-text-fill: white");
        player_6_label.setStyle("-fx-text-fill: white");


        player_4_label.setStyle("-fx-text-fill: red");
        log.info("Spieler 4 ist aktiv coloured");

        break;
      case 4:
        player_1_label.setStyle("-fx-text-fill: white");
        player_2_label.setStyle("-fx-text-fill: white");
        player_3_label.setStyle("-fx-text-fill: white");
        player_4_label.setStyle("-fx-text-fill: white");
        player_6_label.setStyle("-fx-text-fill: white");


        player_5_label.setStyle("-fx-text-fill: red");
        log.info("Spieler 5 ist aktiv coloured");

        break;
      case 5:

        player_1_label.setStyle("-fx-text-fill: white");
        player_2_label.setStyle("-fx-text-fill: white");
        player_3_label.setStyle("-fx-text-fill: white");
        player_4_label.setStyle("-fx-text-fill: white");
        player_5_label.setStyle("-fx-text-fill: white");

        player_6_label.setStyle("-fx-text-fill: red");
        log.info("Spieler 6 ist aktiv coloured");
        break;
    }



  }

  /**
   * Starts the dice roll animation.
   * This is optional and can be implemented for visual effect.
   */
  @Override
  public void startRollDiceAnimation() {

    diceButton1.getStyleClass().remove("rolled");
    diceButton2.getStyleClass().remove("rolled");

    Timeline timeline = new Timeline();
    int rollSteps = 10;

    for (int i = 0; i < rollSteps; i++) {
      timeline.getKeyFrames().add(new KeyFrame(Duration.millis(i * 100), e -> {
        int temp1 = random.nextInt(6);
        int temp2 = random.nextInt(6);
        diceButton1.setBackground(makeDiceBackground(diceImages[temp1]));
        diceButton2.setBackground(makeDiceBackground(diceImages[temp2]));
      }));
    }

    // Letzter Frame: Anzeige des echten Ergebnisses
    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(rollSteps * 100), e -> {
      showDice(realDice1, realDice2); // hier kommt das „echte“ Ergebnis
    }));

    timeline.play();
  }



  /**
   * Displays the result of a die roll.
   *
   * @param dice1 the value of the first dice
   * @param dice2 the value of the second dice
   */
  public void showDice(int dice1, int dice2) {
    System.out.println("🎲 Ergebnis anzeigen: " + dice1 + ", " + dice2);

    Image[] diceImages = {
            null,
            new Image(getClass().getResource("/de/dhbw/frontEnd/board/dice1.png").toExternalForm()),
            new Image(getClass().getResource("/de/dhbw/frontEnd/board/dice2.png").toExternalForm()),
            new Image(getClass().getResource("/de/dhbw/frontEnd/board/dice3.png").toExternalForm()),
            new Image(getClass().getResource("/de/dhbw/frontEnd/board/dice4.png").toExternalForm()),
            new Image(getClass().getResource("/de/dhbw/frontEnd/board/dice5.png").toExternalForm()),
            new Image(getClass().getResource("/de/dhbw/frontEnd/board/dice6.png").toExternalForm())
    };

    BackgroundImage bg1 = new BackgroundImage(
            diceImages[dice1],
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, true, true)
    );

    BackgroundImage bg2 = new BackgroundImage(
            diceImages[dice2],
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, true, true)
    );

    diceButton1.setBackground(new Background(bg1));
    diceButton2.setBackground(new Background(bg2));


    // Optional: rolled-Klasse für CSS-Fix
    if (!diceButton1.getStyleClass().contains("rolled")) diceButton1.getStyleClass().add("rolled");
    if (!diceButton2.getStyleClass().contains("rolled")) diceButton2.getStyleClass().add("rolled");
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

  public CompletableFuture<Integer> waitForSettlementClick() {
    log.debug("\uD83D\uDFE2 waitForSettlementClick CALLED");

    this.showUserMessage("Click settlement node", "Please click on a settlement node",
            Alert.AlertType.INFORMATION);

    settlenemtNodeSelectionFuture = new CompletableFuture<>();

    // Set a one-time callback to complete the future when a button is clicked
    this.settlementClickCallback = (String fxId) -> {
      try {
        String nodeIdStr = fxId.replace("node_", "");  // ✅ sanitize
        int nodeId = Integer.parseInt(nodeIdStr);
        System.out.println("🟡 settlementClickCallback INVOKED with: " + nodeId);
        if (!settlenemtNodeSelectionFuture.isDone()) {
          settlenemtNodeSelectionFuture.complete(nodeId);
        }
      } catch (NumberFormatException e) {
        settlenemtNodeSelectionFuture.completeExceptionally(e);
      }
      // ✅ Clear the callback so future clicks do nothing
      this.settlementClickCallback = null;
    };

    return settlenemtNodeSelectionFuture;
  }

  public CompletableFuture<IntTupel> waitForStreetClick() {
    log.debug("\uD83D\uDFE2 waitForStreetClick CALLED");

    this.showUserMessage("Click street", "Please click on a street node",
            Alert.AlertType.INFORMATION);

    streetSelectionFuture = new CompletableFuture<IntTupel>();

    // Set a one-time callback to complete the future when a button is clicked
    this.streetClickCallback = (String fxId) -> {
      try {
        // ✅ sanitize
        String idPart = fxId.replace("road_", "");
        String[] parts = idPart.split("_");
        IntTupel streetId = new IntTupel(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));

        System.out.println("🟡 settlementClickCallback INVOKED with: " + streetId);
        if (!streetSelectionFuture.isDone()) {
          streetSelectionFuture.complete(streetId);
        }
      } catch (NumberFormatException e) {
        streetSelectionFuture.completeExceptionally(e);
      }
      // ✅ Clear the callback so future clicks do nothing
      this.settlementClickCallback = null;
    };

    return streetSelectionFuture;
  }

  public CompletableFuture<String> waitForFinishTurnClick() {
    log.debug("\uD83D\uDFE2 waitForFinishTurnClick CALLED");

    finish_turn_button.setMouseTransparent(false);
    trade_card.setMouseTransparent(false);
    build_settlement.setDisable(false);
    build_city.setDisable(false);
    build_road.setDisable(false);

    finishTurnSelectionFuture = new CompletableFuture<String>();

    // Set a one-time callback to complete the future when a button is clicked
    this.finishTurnClickCallback = (String name) -> {
      try {
        log.debug("🟡 waitForFinishTurnClicked INVOKED with: " + name);
        if (!finishTurnSelectionFuture.isDone()) {
          finishTurnSelectionFuture.complete(name);
        }
      } catch (NumberFormatException e) {
        finishTurnSelectionFuture.completeExceptionally(e);
      }
      // ✅ Clear the callback so future clicks do nothing
      this.finishTurnClickCallback = null;
    };

    return finishTurnSelectionFuture;
  }

  public CompletableFuture<PlayerTupelVar> waitForBanditLoctionAndPlayer(Player[] players) {
    log.debug("\uD83D\uDFE2 waitForBanditLoactionAndPlayer CALLED");

    this.showUserMessage("Click on new Bandit location", "Please click on a new Bandit location",
            Alert.AlertType.INFORMATION);

    triggerBanditFuture = new CompletableFuture<PlayerTupelVar>();

    // Set a one-time callback to complete the future when a button is clicked
    this.newBanditLocationClickCallback = (IntTupel banditLocation) -> {
      try {

        System.out.println("🟡 waitForBanditLoactionAndPlayer INVOKED with: " + banditLocation);
        if (!triggerBanditFuture.isDone()) {

          Player robbedPlayer;
          do {
            robbedPlayer = promptPlayerToRob(players);
          } while (robbedPlayer == null);

          triggerBanditFuture.complete(new PlayerTupelVar(banditLocation, robbedPlayer));
        }
      } catch (NumberFormatException e) {
        triggerBanditFuture.completeExceptionally(e);
      }
      // ✅ Clear the callback so future clicks do nothing
      this.newBanditLocationClickCallback = null;
    };

    return triggerBanditFuture;
  }

  public Player promptPlayerToRob(Player[] players) {
    // Create a dialog
    Dialog<Player> dialog = new Dialog<>();
    dialog.setTitle("Rob a Player");
    dialog.setHeaderText("Please select the player you would like to steal a random resource from");

    // Add buttons
    ButtonType stealButtonType = new ButtonType("Steal", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(stealButtonType, ButtonType.CANCEL);

    // ComboBox with player IDs
    ComboBox<Player> comboBox = new ComboBox<>();
    comboBox.getItems().addAll(players);
    comboBox.setPromptText("Select a player");

    // Display player ID in dropdown (instead of toString)
    comboBox.setCellFactory(listView -> new ListCell<>() {
      @Override
      protected void updateItem(Player player, boolean empty) {
        super.updateItem(player, empty);
        setText(empty || player == null ? null : "Player " + player.getId());
      }
    });
    comboBox.setButtonCell(new ListCell<>() {
      @Override
      protected void updateItem(Player player, boolean empty) {
        super.updateItem(player, empty);
        setText(empty || player == null ? null : "Player " + player.getId());
      }
    });

    // Layout
    VBox vbox = new VBox(10);
    vbox.setPadding(new Insets(15));
    vbox.getChildren().add(comboBox);
    dialog.getDialogPane().setContent(vbox);

    // Result converter
    dialog.setResultConverter(dialogButton -> {
      if (dialogButton == stealButtonType) {
        return comboBox.getValue();
      }
      return null;
    });

    // Show dialog and return selected player
    Optional<Player> result = dialog.showAndWait();
    return result.orElse(null);
  }

  public Consumer<IntTupel> getNewBanditLocationClickCallback() {
    return newBanditLocationClickCallback;
  }

  public void setOnUIReady(Runnable r) {
    this.onUIReady = r;
  }

  public void showUserMessage(String title, String message, Alert.AlertType alertType) {
    Platform.runLater(() -> {
      Alert alert = new Alert(alertType);
      alert.setTitle(title);
      alert.setHeaderText(title);
      alert.setContentText(message);
      alert.show();
    });
  }

}
