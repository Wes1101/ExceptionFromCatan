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
 * Diese Klasse repräsentiert das Startmenü des Spiels.
 * Von hier aus kann der Benutzer zwischen Einzelspieler- und Mehrspielermodus wählen.
 */

public class StartMenuScene {
    boolean ServerGame;
    private final Scene scene;
    private SinglePlayerScene singlePlayerScene;
    private MultiPlayerScene multiPlayerScene;

    /**
     * Konstruktor, der das Layout und die Buttons des Startmenüs erstellt.
     *
     * @param primaryStage Die Stage, auf der die Szene angezeigt wird.
     */
    public StartMenuScene(Stage primaryStage) {  // Aufbau des GUI-Layouts mit Buttons und Eventhandlern
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


        Button btnEinzel = new Button("Einzelspieler");
        btnEinzel.setId("buttonEinzelMulti");



        Button btnMehr = new Button("Mehrspieler");
        btnMehr.setId("buttonEinzelMulti");



        StackPane pinkPane1 = new StackPane(btnEinzel);
        pinkPane1.setPadding(new Insets(20));


        StackPane pinkPane2 = new StackPane(btnMehr);
        pinkPane2.setPadding(new Insets(20));


        root.getChildren().addAll(redPane, pinkPane1, pinkPane2);

        // Eventhandler
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
     * Gibt die JavaFX-Szene zurück.
     *
     * @return Die Szene des Startmenüs.
     */

    public Scene getScene() {
        return scene;
    }

    /**
     * Setzt die Szene für den Einzelspielermodus.
     *
     * @param scene Die Einzelspieler-Szene.
     */

    public void setSinglePlayerScene(SinglePlayerScene scene) {
        this.singlePlayerScene = scene;
    }

    /**
     * Setzt die Szene für den Mehrspielermodus.
     *
     * @param scene Die Mehrspieler-Szene.
     */

    public void setMultiPlayerScene(MultiPlayerScene scene) {
        this.multiPlayerScene = scene;
    }

    /**
     * Gibt zurück, ob der Mehrspielermodus (ServerGame) aktiviert wurde.
     *
     * @return true, wenn Mehrspielermodus aktiviert ist, sonst false.
     */

    public boolean getServerGame() {
        return ServerGame;
    }
}
//