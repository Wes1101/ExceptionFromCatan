
package de.dhbw.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

/**
 * Represents the start menu of the game.
 * <p>
 * Allows the user to choose between single-player and multiplayer modes.
 * </p>
 */
public class StartMenuScene {

    /** Flag indicating if multiplayer (server game) mode is selected */
    private boolean ServerGame;

    /** The JavaFX scene representing the start menu */
    private final Scene scene;

    /** Reference to the single-player scene */
    private SinglePlayerScene singlePlayerScene;

    /** Reference to the multiplayer scene */
    private MultiPlayerScene multiPlayerScene;

    /**
     * Constructs the layout and initializes the buttons for the start menu.
     *
     * @param primaryStage the main application window where this scene is displayed
     */
    public StartMenuScene(Stage primaryStage) {
        URL fontUrl = getClass().getResource("/de/dhbw/frontEnd/menu/GotischeInitialien.ttf");
        System.out.println(">>> FONT URL = " + fontUrl);

        Font.loadFont(
                Objects.requireNonNull(getClass().getResource("/fonts/GrusskartenGotisch.ttf")).toExternalForm(), 36);

        VBox root = new VBox(30);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #222;");
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Exception von Catan");
        title.setId("launcher-title");
        StackPane redPane = new StackPane(title);
        redPane.setPadding(new Insets(15));

        Button btnEinzel = new Button("Singleplayer");
        btnEinzel.setId("buttonEinzelMulti");

        Button btnMehr = new Button("Multiplayer {Work in Progress}");
        btnMehr.setId("buttonEinzelMulti");

        StackPane pinkPane1 = new StackPane(btnEinzel);
        pinkPane1.setPadding(new Insets(20));

        StackPane pinkPane2 = new StackPane(btnMehr);
        pinkPane2.setPadding(new Insets(20));

        root.getChildren().addAll(redPane, pinkPane1, pinkPane2);

        // Button event handlers
        btnEinzel.setOnAction(e -> {
            ServerGame = false;
            primaryStage.setScene(singlePlayerScene.getScene());
        });

        btnMehr.setOnAction(e -> {
            ServerGame = true;
            primaryStage.setScene(multiPlayerScene.getScene());
        });

        this.scene = new Scene(root, 1344, 776);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/de/dhbw/frontEnd/menu/StartMenu.css")).toExternalForm());
    }

    /**
     * Returns the JavaFX scene of the start menu.
     *
     * @return the configured scene instance
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Sets the reference to the single-player scene.
     *
     * @param scene the single-player scene
     */
    public void setSinglePlayerScene(SinglePlayerScene scene) {
        this.singlePlayerScene = scene;
    }

    /**
     * Sets the reference to the multiplayer scene.
     *
     * @param scene the multiplayer scene
     */
    public void setMultiPlayerScene(MultiPlayerScene scene) {
        this.multiPlayerScene = scene;
    }

    /**
     * Returns whether the server game (multiplayer) option is enabled.
     *
     * @return true if multiplayer is selected, false otherwise
     */
    public boolean getServerGame() {
        return ServerGame;
    }
}
