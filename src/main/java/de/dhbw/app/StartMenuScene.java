package de.dhbw.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        VBox root = new VBox(30);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #222;");
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Exception von Catan Launcher");
        title.setStyle("-fx-font-size: 26px; -fx-text-fill: #ff4444; -fx-font-weight: bold;");
        StackPane redPane = new StackPane(title);
        redPane.setPadding(new Insets(15));
        redPane.setStyle("-fx-border-color: #ff4444; -fx-border-width: 2; -fx-background-radius: 10;");

        Button btnEinzel = new Button("Einzelspieler");
        btnEinzel.setPrefWidth(200);
        btnEinzel.setStyle("-fx-font-size: 18px; -fx-background-color: #ff99ff; -fx-text-fill: #222;");

        Button btnMehr = new Button("Mehrspieler");
        btnMehr.setPrefWidth(200);
        btnMehr.setStyle("-fx-font-size: 18px; -fx-background-color: #ff99ff; -fx-text-fill: #222;");

        StackPane pinkPane1 = new StackPane(btnEinzel);
        pinkPane1.setPadding(new Insets(10));
        pinkPane1.setStyle("-fx-border-color: #ff99ff; -fx-border-width: 2; -fx-background-radius: 10;");
        StackPane pinkPane2 = new StackPane(btnMehr);
        pinkPane2.setPadding(new Insets(10));
        pinkPane2.setStyle("-fx-border-color: #ff99ff; -fx-border-width: 2; -fx-background-radius: 10;");

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

        this.scene = new Scene(root, 1152, 648);
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
