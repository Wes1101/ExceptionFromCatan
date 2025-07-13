
package de.dhbw.app;

import de.dhbw.frontEnd.board.SceneBoardController;
import de.dhbw.gameController.GameController;
import de.dhbw.gameController.GameControllerTypes;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
 * Represents the user interface for the single-player mode.
 * <p>
 * Allows the player to configure game settings such as player count, victory points, and maximum cards to discard.
 * </p>
 */
public class SinglePlayerScene {

    /** JavaFX scene for the single-player configuration screen */
    private final Scene scene;

    /** Reference to the start menu scene */
    private StartMenuScene startMenuScene;

    /**
     * Constructs the single-player configuration screen including sliders and control buttons.
     *
     * @param primaryStage the main stage of the application
     */
    public SinglePlayerScene(Stage primaryStage) {
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
        Label greenLabel = new Label("Player count");

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
        Label pinkLabel = new Label("Victory points");

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
        Label brownLabel = new Label("Max cards to discard when bandit is active");

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

        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(startButton, backButton);
        buttonBox.setPadding(new Insets(15));

        root.getChildren().addAll(titlePane, spielerSliderPane, pinkPane, brownPane, buttonBox);

        // Event handlers
        backButton.setOnAction(e -> primaryStage.setScene(startMenuScene.getScene()));

        startButton.setOnAction(e -> {
            boolean isServergame = startMenuScene.getServerGame();
            int playerCount = (int) spielerSlider.getValue();
            int winPoints = (int) siegpunktSlider.getValue();
            int maxCardThrow = (int) abwerfenSlider.getValue();
            System.out.println(isServergame + " " + playerCount + " " + winPoints + " " + maxCardThrow);

            if (isServergame) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ALERT {WiP}");
                alert.setHeaderText("Server-Game not yet implemented");
                alert.setContentText("The local server game option is not implemented at the current time.");
                alert.showAndWait();
                // TODO: Pass parameters to server implementation
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/dhbw/frontEnd/board/card-bar.fxml"));
                Parent gameRoot;
                try {
                    gameRoot = loader.load();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                SceneBoardController boardController = loader.getController();
                GameController gameController = new GameController(playerCount, winPoints, GameControllerTypes.LOCAL, false);
                gameController.setGui(boardController);

                boardController.setCatanBoard(gameController.getCatanBoard());
                boardController.initBoard();

                Scene gameScene = new Scene(gameRoot);
                primaryStage.setScene(gameScene);
                primaryStage.show();

                boardController.setOnUIReady(() -> new Thread(() -> {
                    gameController.gameStart();
                    gameController.mainGame();
                }).start());
            }
        });

        this.scene = new Scene(root, 1344, 776);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/de/dhbw/frontEnd/menu/SinglePlayer.css")).toExternalForm());
    }

    /**
     * Returns the JavaFX scene associated with this class.
     *
     * @return the configured JavaFX scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Sets the reference to the start menu scene.
     *
     * @param scene the start menu scene to return to
     */
    public void setStartMenuScene(StartMenuScene scene) {
        this.startMenuScene = scene;
    }
}
