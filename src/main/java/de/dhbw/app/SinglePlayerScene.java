package de.dhbw.app;

import de.dhbw.client.NetworkClient;
import de.dhbw.frontEnd.board.SceneBoard;
import de.dhbw.gameController.GameController;
import de.dhbw.gameController.GameControllerTypes;
import de.dhbw.server.NetworkServer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Diese Klasse stellt die Benutzeroberfläche für den Einzelspielermodus dar.
 * Der Spieler kann Parameter wie Spieleranzahl, Siegpunktziel und Kartenabwurfgrenze einstellen.
 */

public class SinglePlayerScene {
    private final Scene scene;
    private StartMenuScene startMenuScene;

    /**
     * Erstellt die Benutzeroberfläche mit Konfigurations-Slidern und Buttons.
     *
     * @param primaryStage Die Hauptbühne der Anwendung.
     */

    public SinglePlayerScene(Stage primaryStage) {  // Aufbau der Konfigurations-UI für den Einzelspieler-Modus
        Font.loadFont(
                Objects.requireNonNull(getClass().getResource("/fonts/GrusskartenGotisch.ttf")).toExternalForm(), 36);

        VBox root = new VBox(25);
        root.setPadding(new Insets(1));
        root.setStyle("-fx-background-color: #222;");
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Catan Settings");
        title.setId("launcher-title");
        StackPane titlePane = new StackPane(title);
        titlePane.setPadding(new Insets(10));

        VBox greenBox = new VBox(8);
        greenBox.setAlignment(Pos.CENTER_LEFT);
        Label greenLabel = new Label("Spieleranzahl");

        Slider spielerSlider = new Slider(3, 6, 1);
        spielerSlider.setMajorTickUnit(1);
        spielerSlider.setMinorTickCount(0);
        spielerSlider.setSnapToTicks(true);
        spielerSlider.setShowTickLabels(true);
        spielerSlider.setShowTickMarks(true);

        greenBox.getChildren().addAll(greenLabel, spielerSlider);
        StackPane spielerSliderPane = new StackPane(greenBox);
        spielerSliderPane.setPadding(new Insets(10));


        VBox pinkBox = new VBox(8);
        pinkBox.setAlignment(Pos.CENTER_LEFT);
        Label pinkLabel = new Label("Siegpunktanzahl");

        Slider siegpunktSlider = new Slider(6, 20, 1);
        siegpunktSlider.setMajorTickUnit(2);
        siegpunktSlider.setMinorTickCount(1);
        siegpunktSlider.setShowTickLabels(true);
        siegpunktSlider.setShowTickMarks(true);
        siegpunktSlider.setSnapToTicks(true);
        pinkBox.getChildren().addAll(pinkLabel, siegpunktSlider);
        StackPane pinkPane = new StackPane(pinkBox);
        pinkPane.setPadding(new Insets(10));


        VBox brownBox = new VBox(8);
        brownBox.setAlignment(Pos.CENTER_LEFT);
        Label brownLabel = new Label("Max. abwerfbare Karten");

        Slider abwerfenSlider = new Slider(4, 10, 1);
        abwerfenSlider.setMajorTickUnit(2);
        abwerfenSlider.setMinorTickCount(1);
        abwerfenSlider.setSnapToTicks(true);
        abwerfenSlider.setShowTickLabels(true);
        abwerfenSlider.setShowTickMarks(true);
        brownBox.getChildren().addAll(brownLabel, abwerfenSlider);
        StackPane brownPane = new StackPane(brownBox);
        brownPane.setPadding(new Insets(10));


        Button startButton = new Button("Start Game");
        startButton.setId("buttonsb");
        startButton.setPrefWidth(220);
        startButton.setPrefHeight(50);

        Button backButton = new Button("Back");
        backButton.setId("buttonsb");

        root.getChildren().addAll(titlePane, spielerSliderPane, pinkPane, brownPane, startButton, backButton);

        // Eventhandler
        backButton.setOnAction(e -> primaryStage.setScene(startMenuScene.getScene()));

        startButton.setOnAction(e -> {
            boolean isServergame = startMenuScene.getServerGame();
            int playerCount = (int) spielerSlider.getValue();
            int winPoints = (int) siegpunktSlider.getValue();
            int maxCardThrow = (int) abwerfenSlider.getValue();
            System.out.println(isServergame + " " + playerCount + " " + winPoints + " " + maxCardThrow);

            SceneBoard gameBoard = new SceneBoard();

            if(isServergame) {
                NetworkServer server = new NetworkServer(new GameController(playerCount, winPoints, GameControllerTypes.SERVER, true));

                // connect clients in a new thread
                new Thread(() -> {
                    try {
                        server.initConnections();
                    } catch (IOException | InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }, "ServerInitThread").start();

                // connect local player as client
                GameController controllerClient = new GameController(playerCount, winPoints, GameControllerTypes.CLIENT, true);
                controllerClient.setGui(gameBoard);

                NetworkClient client = new NetworkClient();
                try {
                    client.connect(server.getIp(), server.getPort());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    gameBoard.start(primaryStage);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            } else{
                GameController controller = new GameController(playerCount, winPoints, GameControllerTypes.LOCAL, true);
                controller.setGui(gameBoard);

                try {
                    gameBoard.start(primaryStage);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                controller.gameStart();
            }

        });

        this.scene = new Scene(root, 1344, 776);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/de/dhbw/frontEnd/menu/SinglePlayer.css")).toExternalForm());
    }

    /**
     * Gibt die Szene dieser Klasse zurück.
     *
     * @return Die konfigurierte JavaFX-Szene.
     */

    public Scene getScene() {
        return scene;
    }

    /**
     * Setzt die Referenz auf die Startmenü-Szene.
     *
     * @param scene Die Startmenü-Szene.
     */

    public void setStartMenuScene(StartMenuScene scene) {
        this.startMenuScene = scene;
    }
}
