package de.dhbw.frontEnd.board;

import de.dhbw.catanBoard.hexGrid.*;
import de.dhbw.catanBoard.hexGrid.Tiles.Harbour;
import de.dhbw.catanBoard.hexGrid.Tiles.Resource;
import de.dhbw.gameController.GameController;
import de.dhbw.gameController.PlayerTupelVar;
import de.dhbw.gamePieces.Building;
import de.dhbw.gamePieces.Street;
import de.dhbw.player.Player;
import de.dhbw.resources.Resources;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;


import javafx.scene.paint.Color;
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
  private Label grain_card_number;
  @FXML
  private Label brick_card_number;
  @FXML
  private Label ore_card_number;
  @FXML
  private Label wool_card_number;
  @FXML
  private Label lumber_card_number;
  @FXML
  private StackPane victory_points_container_1;
  @FXML
  private StackPane victory_points_container_2;
  @FXML
  private StackPane victory_points_container_3;
  @FXML
  private StackPane victory_points_container_4;
  @FXML
  private StackPane victory_points_container_5;
  @FXML
  private StackPane victory_points_container_6;

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
  private Pane button_layer;

  @FXML
  private Pane road_layer;

  @FXML
  private Pane building_layer;

  @FXML
  private ImageView trade_card;

  @FXML
  private ImageView setting_button;


  @FXML
  private StackPane setting_menue;


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
  private ImageView victory_points_background_1;

  @FXML
  private ImageView victory_points_background_2;

  @FXML
  private ImageView victory_points_background_3;

  @FXML
  private ImageView victory_points_background_4;

  @FXML
  private ImageView victory_points_background_5;

  @FXML
  private ImageView victory_points_background_6;

  @FXML
  private Label victory_points_player_1;

  @FXML
  private Label victory_points_player_2;

  @FXML
  private Label victory_points_player_3;

  @FXML
  private Label victory_points_player_4;

  @FXML
  private Label victory_points_player_5;

  @FXML
  private Label victory_points_player_6;

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

  private final Map<Integer, Point2D> nodeScreenPositions = new HashMap<>();



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
            .filter(n -> n instanceof EdgeFX && n.getId() != null && n.getId().startsWith("road_"))
            .forEach(n -> n.setOnMouseClicked(evt -> {

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
  private void onNodeClicked(ActionEvent event) { //johann
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
  private void onEdgeClicked(ActionEvent event) { //johann
    Button btn = (Button) event.getSource();
    String fxId = btn.getId();

    log.debug("\uD83D\uDD35 Button clicked:  fx:id=" + fxId);
    log.debug("🔴 Callback is: " + streetClickCallback);

    if (streetClickCallback != null) {
      streetClickCallback.accept(fxId);
    }

    if (waitingForStreetClick && streetClickHandler != null) {
      log.debug("🟢 Street click handler invoked for: " + fxId);
      streetClickHandler.accept((Button) btn);
    }

  }

  @FXML
  private void onFinishTurnClicked(MouseEvent event) {
    log.debug("🔵 Finish Turn button clicked");
    finish_turn_button.setMouseTransparent(true);
    build_settlement.setDisable(true);
    build_city.setDisable(true);
    build_road.setDisable(true);

    if (finishTurnClickCallback != null) {
      finishTurnClickCallback.accept("finish_turn_button");
    }
  }

  @FXML
  private void onBuildSettlement(ActionEvent event) {
    System.out.println("<UNK> Build Settlement button clicked!?!?!?!?!?!?!?!?!?!??!?!?!??!?!?!?!?!");
    waitingForSettlementClick = true;
    settlementClickHandler = (Button btn) -> {
      log.debug("🟢 Settlement button clicked: " + btn.getId());

      int nodeId = Integer.parseInt(btn.getId().replace("node_", ""));
      gameController.buildSettlement(nodeId, activePlayer);
      updatePlayerResources(gameController.getPlayers());
      this.updateBoard(catanBoard);

      waitingForSettlementClick = false;
      settlementClickHandler = null;
    };
  }

  @FXML
  private void onBuildCity(ActionEvent event) {
    System.out.println("<UNK> Build Settlement button clicked!?!?!?!?!?!?!?!?!?!??!?!?!??!?!?!?!?!jkwfwrjk fwr ");
    waitingForCityClick = true;
    cityClickHandler = (Button btn) -> {
      log.debug("🟢 City button clicked: " + btn.getId());

      int nodeId = Integer.parseInt(btn.getId().replace("node_", ""));
      gameController.buildCity(nodeId, activePlayer);
      updatePlayerResources(gameController.getPlayers());
      this.updateBoard(catanBoard);
      waitingForCityClick = false;
      cityClickHandler = null;
    };
  }

  //johann
  @FXML
  private void onBuildRoad(ActionEvent event) {
    System.out.println("wfhawdfvs avfsaefvfsnvkf");
    waitingForStreetClick = true;
    streetClickHandler = (Button btn) -> {
      log.debug("🟢 Street button clicked: " + btn.getId());

      String idPart = btn.getId().replace("road_", "");
      String[] parts = idPart.split("_");
      IntTupel streetId = new IntTupel(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
      gameController.buildStreet(streetId, activePlayer);
      updatePlayerResources(gameController.getPlayers());
      this.updateBoard(catanBoard);
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
      double y = offsetY + (r * height);


      // Wasser vom Backend
      if (hexes.get(coords) instanceof de.dhbw.catanBoard.hexGrid.Tiles.Water) {
        de.dhbw.frontEnd.board.HexTile frontendHex =
                new de.dhbw.frontEnd.board.HexTile(hexes.get(coords), x, y, size, "Water", this, 0);
        tile_layer.getChildren().add(frontendHex);
        frontendHex.toBack();
      }


      // Hafen vom Backend
      else if (hexes.get(coords) instanceof de.dhbw.catanBoard.hexGrid.Tiles.Harbour) {
        de.dhbw.catanBoard.hexGrid.Tiles.Harbour h =
                (de.dhbw.catanBoard.hexGrid.Tiles.Harbour) hexes.get(coords);
        String resourceName = h.getResourceType().name() + "_Harbour";

        de.dhbw.frontEnd.board.HexTile frontendHex =
                new de.dhbw.frontEnd.board.HexTile(hexes.get(coords), x, y, size, resourceName, this, 0);

        tile_layer.getChildren().add(frontendHex);
        frontendHex.toBack();
      }

//      // Ressource vom Backend
//      else if (hexes.get(coords) instanceof Resource) {
//        Resource resTile = (Resource) hexes.get(coords);
//        String resourceName = resTile.getResourceType().name();
//        int num = resTile.getNumberToken();
//
//        de.dhbw.frontEnd.board.HexTile frontendHex =
//                new de.dhbw.frontEnd.board.HexTile(q, r, x, y, size, resourceName, num,this);
//
//        tile_layer.getChildren().add(frontendHex);
//        frontendHex.toBack();
//      }

      // Ressource vom Backend
      else if (hexes.get(coords) instanceof Resource) {
        Resource resTile = (Resource) hexes.get(coords);
        String resourceName = resTile.getResourceType().name();
        int num = resTile.getDiceNumber();


        de.dhbw.frontEnd.board.HexTile frontendHex =
                new de.dhbw.frontEnd.board.HexTile(hexes.get(coords), x, y, size, resourceName, this, num);

        tile_layer.getChildren().add(frontendHex);
        frontendHex.toBack();
      }

    }
    calculateNodeScreenPositions(catanBoard);

  }

  public void addCornerButtons(Tile tile, List<double[]> corners) {
    Node[] hexTileNodes = tile.getHexTileNodes();

    for (int i = 0; i < corners.size(); i++) {
      double[] point = corners.get(i);
      double x = point[0];
      double y = point[1];

      Node node = hexTileNodes[i];
      if (node == null) continue;
      boolean exists = tile_layer.getChildren().stream()
              .anyMatch(n -> ("node_" + node.getId()).equals(n.getId()));
      if (exists) {
        continue;
      }

      Button redButton = new NodeFX();
      redButton.setLayoutX(x - 7);
      redButton.setLayoutY(y - 7);
      //redButton.setMouseTransparent(true);
      redButton.setId("node_" + node.getId());

      // Optional: EventHandler für später
      //redButton.setOnAction(this::onNodeClicked);
      redButton.setOnAction(this::onNodeClicked);

      tile_layer.getChildren().add(redButton);
    }
  }

  // Methode zum Hinzufügen gelber Rechtecke zwischen den Ecken
  public void addEdgeButtons(Tile tile, List<double[]> corners) {
    if (!(tile instanceof Resource resTile)) return;

    Node[] hexTileNodes = tile.getHexTileNodes();

    for (int i = 0; i < corners.size(); i++) {
      double[] start = corners.get(i);
      double[] end = corners.get((i + 1) % 6);

      Node nodeA = hexTileNodes[i];
      Node nodeB = hexTileNodes[(i + 1) % 6];
      if (nodeA == null || nodeB == null) continue;

      String id = "road_" + nodeA.getId() + "_" + nodeB.getId();

      boolean exists = tile_layer.getChildren().stream()
              .anyMatch(n -> id.equals(n.getId()));
      if (exists) {
        continue;
      }

      double startX = start[0];
      double startY = start[1];
      double endX = end[0];
      double endY = end[1];

      double dx = endX - startX;
      double dy = endY - startY;
      double length = Math.sqrt(dx * dx + dy * dy);
      double angle = Math.toDegrees(Math.atan2(dy, dx));

      double shorteningFactor = 0.6;
      double shortenedLength = length * shorteningFactor;

      double centerX = (startX + endX) / 2;
      double centerY = (startY + endY) / 2;

      EdgeFX rect = new EdgeFX(shortenedLength, 8);
      rect.setLayoutX(centerX - shortenedLength / 2);
      rect.setLayoutY(centerY - 4); // halbe Höhe
      rect.setRotate(angle);
      rect.setId(id);
      rect.setOnAction(this::onEdgeClicked);

      tile_layer.getChildren().add(rect);
    }
  }



  private void calculateNodeScreenPositions(CatanBoard catanBoard) {
    double size = 50;
    double width = Math.sqrt(3) * size;
    double height = 1.5 * size;
    double offsetX = 400;
    double offsetY = 300;

    Map<IntTupel, Tile> board = catanBoard.getBoard();

    for (Map.Entry<IntTupel, Tile> entry : board.entrySet()) {
      IntTupel coords = entry.getKey();
      Tile tile = entry.getValue();
      Node[] hexNodes = tile.getHexTileNodes();

      int q = coords.q();
      int r = coords.r();
      double centerX = offsetX + (q * width) + (r * (width / 2));
      double centerY = offsetY - (r * height);

      for (int i = 0; i < 6; i++) {
        Node node = hexNodes[i];
        if (node == null || nodeScreenPositions.containsKey(node.getId())) continue;

        double angle = Math.toRadians(60 * i - 30);
        double x = centerX + size * Math.cos(angle);
        double y = centerY + size * Math.sin(angle);
        nodeScreenPositions.put(node.getId(), new Point2D(x, y));
      }
    }
  }

  public void updateBoard(CatanBoard catanBoard) { //TODO: new tiles generated???
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
      double y = offsetY + (r * height);

      Tile tile = hexes.get(coords);

//      // Wasser
//      if (tile instanceof de.dhbw.catanBoard.hexGrid.Tiles.Water) {
//        HexTile frontendHex = new HexTile(hexes.get(coords), x, y, size, "Water", this);
//        tile_layer.getChildren().add(frontendHex);
//        frontendHex.toBack();
//      }
//
//      // Hafen
//      else if (tile instanceof de.dhbw.catanBoard.hexGrid.Tiles.Harbour h) {
//        String resourceName = h.getResourceType().name() + "_Harbour";
//        HexTile frontendHex = new HexTile(hexes.get(coords), x, y, size, resourceName, this);
//        tile_layer.getChildren().add(frontendHex);
//        frontendHex.toBack();
//      }
//
//      // Ressourcen
//      else if (tile instanceof Resource resTile) {
//        String resourceName = resTile.getResourceType().name();
//        HexTile frontendHex = new HexTile(hexes.get(coords), x, y, size, resourceName, this);
//        tile_layer.getChildren().add(frontendHex);
//        frontendHex.toBack();
//      }

      // Show bandit overlay if needed
      if (tile instanceof Resource resTile) {
        showBanditIfBlocked(coords, resTile, x, y, size);
      }
    }
    this.drawAllBuildings(catanBoard);
    this.drawAllStreets(catanBoard);
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

  //johann
  private void drawAllBuildings(CatanBoard catanBoard) {
    double size = 50;

    Map<IntTupel, Tile> board = catanBoard.getBoard();
    Set<Integer> drawnNodes = new HashSet<>(); // Avoid duplicates

    for (Map.Entry<IntTupel, Tile> entry : board.entrySet()) {
      Tile tile = entry.getValue();

      if (!(tile instanceof Resource || tile instanceof Harbour)) continue;

      Node[] hexNodes = tile.getHexTileNodes();

      for (int i = 0; i < 6; i++) {
        Node node = hexNodes[i];
        if (node == null || drawnNodes.contains(node.getId())) continue;

        Building building = node.getBuilding();
        if (building == null) continue;
        drawnNodes.add(node.getId());

          log.debug("Drawing building at node: {}", node.getId());
          int ownerPlayerId = node.getBuilding().getOwner().getId();

        // Calculate node screen position
        Point2D pos = nodeScreenPositions.get(node.getId());
        if (pos == null) continue;


        String imgPath;
        if (building instanceof de.dhbw.gamePieces.City) {
          imgPath = "/de/dhbw/frontEnd/board/city-"+this.playerIdToColor(ownerPlayerId)+".png";
        } else {
          imgPath = "/de/dhbw/frontEnd/board/settlement-"+this.playerIdToColor(ownerPlayerId)+".png";
        }

        Image img = new Image(Objects.requireNonNull(
                getClass().getResourceAsStream(imgPath)));
        ImageView buildingView = new ImageView(img);

        double imgSize = size * 0.6;
        buildingView.setFitWidth(imgSize);
        buildingView.setFitHeight(imgSize);

        String nodeID = "node_" + node.getId();
        tile_layer.getChildren().stream()
                .filter(node1 -> node1 instanceof NodeFX && node1.getId().equals(nodeID))
                .forEach(node1 -> {
                  ((NodeFX) node1).setGraphic(buildingView);
                  node1.setStyle("-fx-padding: 0; -fx-background-insets: 0;");;
                });

      }
    }
  }

  private void drawAllStreets(CatanBoard catanBoard) {
    double size = 50;

    Graph graph = catanBoard.getGraph();
    Node[] nodes = graph.getNodes();
    Edge[][] edges = graph.getGraph();
    Set<String> drawnEdges = new HashSet<>();

    for (int i = 0; i < nodes.length; i++) {
      for (int j = i + 1; j < nodes.length; j++) {
        Edge edge = edges[i][j];
        if (edge == null || edge.getStreet() == null) continue;

        String edgeKey = i + "-" + j;
        if (drawnEdges.contains(edgeKey)) continue;
        drawnEdges.add(edgeKey);

        Node nodeA = nodes[i];
        Node nodeB = nodes[j];

        log.debug("Drawing street at nodeA: {}, nodeB: {}", nodeA.getId(), nodeB.getId());

        // Try to find a common tile shared by both nodes
        Tile sharedTile = nodeA.getHexNeighbors().stream()
                .filter(nodeB.getHexNeighbors()::contains)
                .findFirst()
                .orElse(null);

        if (sharedTile == null) {
          continue; // skip drawing
        }

        Optional<NodeFX> maybeNode = tile_layer.getChildren().stream()
                .filter(node1 -> node1 instanceof NodeFX && node1.getId().equals("node_" + nodeA.getId()))
                .map(node1 -> (NodeFX) node1)
                .findFirst();

        double Ax = 0;
        double Ay = 0;

        if (maybeNode.isPresent()) {
          NodeFX nodeFX = maybeNode.get();
          Ax = nodeFX.getLayoutX();
          Ay = nodeFX.getLayoutY();
        }

        maybeNode = tile_layer.getChildren().stream()
                .filter(node1 -> node1 instanceof NodeFX && node1.getId().equals("node_" + nodeB.getId()))
                .map(node1 -> (NodeFX) node1)
                .findFirst();

        double Bx = 0;
        double By = 0;

        if (maybeNode.isPresent()) {
          NodeFX nodeFX = maybeNode.get();
          Bx = nodeFX.getLayoutX();
          By = nodeFX.getLayoutY();
        }

        // Estimate screen positions of the nodes
        Point2D posA = nodeScreenPositions.get(nodeA.getId());
        Point2D posB = nodeScreenPositions.get(nodeB.getId());
        if (posA == null || posB == null) continue;

        if (posA == null || posB == null) {
          continue;
        }

        double dx = Bx - Ax;
        double dy = By - Ay;
        double length = Math.hypot(dx, dy);

        Image roadImage = new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/de/dhbw/frontEnd/board/street.png")));
        ImageView roadView = new ImageView(roadImage);

        roadView.setFitWidth(length * 0.8);
        roadView.setFitHeight(size * 0.6);


        String nodeID = "road_" + nodeA.getId() + "_" + nodeB.getId();
        tile_layer.getChildren().stream()
                .filter(node1 -> node1 instanceof EdgeFX && node1.getId().equals(nodeID))
                .forEach(node1 -> {
                  ((EdgeFX) node1).setGraphic(roadView);

                  node1.setStyle("-fx-padding: 0; -fx-background-insets: 0;");;
                });
      }
    }
  }

  private double[][] getNodeScreenPositions(Tile tile, double centerX, double centerY, double size) {
    double[][] positions = new double[6][2];
    for (int i = 0; i < 6; i++) {
      double angle = Math.toRadians(60 * i - 30);
      positions[i][0] = centerX + size * Math.cos(angle);
      positions[i][1] = centerY + size * Math.sin(angle);
    }
    return positions;
  }

  private double[] findNodePosition(Node node, Tile tile, double[][] positions) {
    Node[] tileNodes = tile.getHexTileNodes();
    for (int i = 0; i < tileNodes.length; i++) {
      if (tileNodes[i] != null && tileNodes[i].getId() == node.getId()) {
        return positions[i];
      }
    }
    return null;
  }

  public void setPlayerAmount(int playerCount) {
    // Set the visibility of player labels based on the number of players

      for (javafx.scene.Node child : victory_points_container_1.getChildren()) {
        child.setVisible(playerCount >= 1);
      }
      for (javafx.scene.Node child : victory_points_container_2.getChildren()) {
        child.setVisible(playerCount >= 2);
      }
      for (javafx.scene.Node child : victory_points_container_3.getChildren()) {
        child.setVisible(playerCount >= 3);
      }
      for (javafx.scene.Node child : victory_points_container_4.getChildren()) {
        child.setVisible(playerCount >= 4);
      }
      for (javafx.scene.Node child : victory_points_container_5.getChildren()) {
        child.setVisible(playerCount >= 5);
      }
      for (javafx.scene.Node child : victory_points_container_6.getChildren()) {
        child.setVisible(playerCount >= 6);
      }


    // Log-Ausgabe zur Überprüfung
    log.info("Anzahl der Spieler in SceneBoardController gesetzt: {}", playerCount);
  }

  /**
   * Method to pass active player to UI. Needed to show in the UI whose currently  playing via player id or name
   *
   * @param player Currently active player
   */
  @Override
  public void setactivePlayer(Player player, Player[] players) {
    this.activePlayer = player; // Den übergebenen Spieler in der Instanzvariablen speichern
    int ID = player.getId();

    this.setActivePlayerLabel(ID);
    this.updateVictoryPoints(players);
    this.updatePlayerResources(player);
    this.showUserMessage("New active Player!",
            "Player " + (player.getId() + 1) + " is now active.", Alert.AlertType.INFORMATION);

    // Log-Ausgabe zur Überprüfung
    log.info("Aktiver Spieler in SceneBoardController gesetzt: ID = {}", player.getId());

  }

  private void updateVictoryPoints(Player[] players) {
    for (Player player : players) {
      switch (player.getId()) {
        case 0:
          victory_points_player_1.setText(Integer.toString(player.getVictoryPoints()));
          break;
        case 1:
          victory_points_player_2.setText(Integer.toString(player.getVictoryPoints()));
          break;
        case 2:
          victory_points_player_3.setText(Integer.toString(player.getVictoryPoints()));
          break;
        case 3:
          victory_points_player_4.setText(Integer.toString(player.getVictoryPoints()));
          break;
        case 4:
          victory_points_player_5.setText(Integer.toString(player.getVictoryPoints()));
          break;
        case 5:
          victory_points_player_6.setText(Integer.toString(player.getVictoryPoints()));
          break;
      }
    }

  }

  private void updatePlayerResources(Player player) {
    log.info("player resources: {}", player.getResources());
    grain_card_number.setText(Integer.toString(player.getResources(Resources.WHEAT)));
    brick_card_number.setText(Integer.toString(player.getResources(Resources.BRICK)));
    ore_card_number.setText(Integer.toString(player.getResources(Resources.STONE)));
    wool_card_number.setText(Integer.toString(player.getResources(Resources.SHEEP)));
    lumber_card_number.setText(Integer.toString(player.getResources(Resources.WOOD)));
  }

  private void setActivePlayerLabel(int id) {

    DropShadow glow = new DropShadow();
    glow.setColor(Color.GOLD);
    glow.setRadius(10);
    glow.setSpread(0.6);

    switch (id) {
      case 0:
        victory_points_background_1.setOpacity(1);
        victory_points_background_2.setOpacity(0.5);
        victory_points_background_3.setOpacity(0.5);
        victory_points_background_4.setOpacity(0.5);
        victory_points_background_5.setOpacity(0.5);
        victory_points_background_6.setOpacity(0.5);
        log.info("Spieler 1 ist aktiv coloured");
        break;

      case 1:
        victory_points_background_1.setOpacity(0.5);
        victory_points_background_2.setOpacity(1);
        victory_points_background_3.setOpacity(0.5);
        victory_points_background_4.setOpacity(0.5);
        victory_points_background_5.setOpacity(0.5);
        victory_points_background_6.setOpacity(0.5);
        log.info("Spieler 2 ist aktiv coloured");
        break;

      case 2:
        victory_points_background_1.setOpacity(0.5);
        victory_points_background_2.setOpacity(0.5);
        victory_points_background_3.setOpacity(1);
        victory_points_background_4.setOpacity(0.5);
        victory_points_background_5.setOpacity(0.5);
        victory_points_background_6.setOpacity(0.5);
        log.info("Spieler 3 ist aktiv coloured");
        break;

      case 3:
        victory_points_background_1.setOpacity(0.5);
        victory_points_background_2.setOpacity(0.5);
        victory_points_background_3.setOpacity(0.5);
        victory_points_background_4.setOpacity(1);
        victory_points_background_5.setOpacity(0.5);
        victory_points_background_6.setOpacity(0.5);
        log.info("Spieler 4 ist aktiv coloured");
        break;

      case 4:
        victory_points_background_1.setOpacity(0.5);
        victory_points_background_2.setOpacity(0.5);
        victory_points_background_3.setOpacity(0.5);
        victory_points_background_4.setOpacity(0.5);
        victory_points_background_5.setOpacity(1);
        victory_points_background_6.setOpacity(0.5);
        log.info("Spieler 5 ist aktiv coloured");
        break;

      case 5:
        victory_points_background_1.setOpacity(0.5);
        victory_points_background_2.setOpacity(0.5);
        victory_points_background_3.setOpacity(0.5);
        victory_points_background_4.setOpacity(0.5);
        victory_points_background_5.setOpacity(0.5);
        victory_points_background_6.setOpacity(1);
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
    log.info("🎲 Ergebnis anzeigen: " + dice1 + ", " + dice2);

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
    updatePlayerResources(players[this.activePlayer.getId()]);
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

  public CompletableFuture<Integer> waitForSettlementClick(int playerId) {
    log.debug("\uD83D\uDFE2 waitForSettlementClick CALLED");

    this.showUserMessage("Click settlement node", "Player " + (playerId + 1) + ", Please click on a settlement node",
            Alert.AlertType.INFORMATION);

    settlenemtNodeSelectionFuture = new CompletableFuture<>();

    // Set a one-time callback to complete the future when a button is clicked
    this.settlementClickCallback = (String fxId) -> {
      try {
        String nodeIdStr = fxId.replace("node_", "");  // ✅ sanitize
        int nodeId = Integer.parseInt(nodeIdStr);
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

  public CompletableFuture<IntTupel> waitForStreetClick(int playerId) {
    log.debug("\uD83D\uDFE2 waitForStreetClick CALLED");

    this.showUserMessage("Click street", "Player " + (playerId + 1) + ", Please click on a street node",
            Alert.AlertType.INFORMATION);

    streetSelectionFuture = new CompletableFuture<IntTupel>();

    // Set a one-time callback to complete the future when a button is clicked
    this.streetClickCallback = (String fxId) -> {
      try {
        // ✅ sanitize
        String idPart = fxId.replace("road_", "");
        String[] parts = idPart.split("_");
        IntTupel streetId = new IntTupel(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));

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
        setText(empty || player == null ? null : "Player " + (player.getId() + 1));
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

  private String playerIdToColor(int playerId) {
      return switch (playerId) {
          case 0 -> "red";
          case 1 -> "green";
          case 2 -> "blue";
          case 3 -> "yellow";
          case 4 -> "magenta";
          case 5 -> "cyan";
          default -> "unknown";
      };
  }
}
