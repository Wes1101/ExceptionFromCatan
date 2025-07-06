package de.dhbw.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Stack;
import java.util.function.UnaryOperator;

/**
 * Diese Klasse stellt die Benutzeroberfläche für den Mehrspielermodus dar.
 * Der Benutzer kann ein Spiel hosten oder einem bestehenden Spiel beitreten.
 */

public class MultiPlayerScene {
    private final Scene scene;
    private StartMenuScene startMenuScene;
    private SinglePlayerScene singlePlayerScene;

    /**
     * Erstellt das Layout für die Mehrspieler-Szene mit Buttons, IP- und Namensfeld.
     *
     * @param primaryStage Die Hauptbühne der Anwendung.
     */

    public MultiPlayerScene(Stage primaryStage) {  // Aufbau der Mehrspieler-GUI
        Font.loadFont(Objects.requireNonNull(getClass().getResource("/fonts/GrusskartenGotisch.ttf")).toExternalForm(), 36);

        VBox root = new VBox(25);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #222;");

        Label multiplayerLabel = new Label("MultiPlayer");
        multiplayerLabel.setId("launcher-title");
        StackPane labelPane = new StackPane(multiplayerLabel);
        labelPane.setPadding(new Insets(15));


        Button hostButton = new Button("Host Game");
        hostButton.setId("buttonhj");
        hostButton.setStyle("-fx-font-size: 35; -fx-text-fill: #FFFFFF");
        StackPane hostPane = new StackPane(hostButton);
        hostPane.setPadding(new Insets(15));


        Button joinButton = new Button("Join Game");
        joinButton.setId("buttonhj");
        joinButton.setStyle("-fx-font-size: 35; -fx-text-fill: #FFFFFF");
        StackPane joinPane = new StackPane(joinButton);
        joinPane.setPadding(new Insets(15));


        // IP-Feld (linke Hälfte)
        TextField ipField = new TextField();
        ipField.setPromptText("IP-Adresse eingeben");

        // Hier IP-Filter einfügen:
        UnaryOperator<TextFormatter.Change> ipFilter = change -> {
            String newText = change.getControlNewText();

            // Erlaube nur Ziffern und Punkte
            if (!newText.matches("[0-9.]*")) {
                return null;
            }

            // Prüfe grob: maximal 3 Punkte und maximal 4 Blöcke
            String[] parts = newText.split("\\.");
            if (parts.length > 4) {
                return null;
            }

            // Prüfe, ob jeder Block maximal 3 Ziffern hat und zwischen 0 und 255 liegt
            for (String part : parts) {
                if (part.length() > 0) {
                    try {
                        int num = Integer.parseInt(part);
                        if (num < 0 || num > 255) {
                            return null;
                        }
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }
            }

            return change;
        };
        ipField.setTextFormatter(new TextFormatter<>(ipFilter));

        ipField.setTooltip(new Tooltip("IPv4 Format XXX.XXX.XXX.XXX"));
// Styling
        ipField.setStyle("-fx-font-size: 16px; -fx-background-color: #222; -fx-text-fill: #66ccff;");

        StackPane ipPane = new StackPane(ipField);
        ipPane.setPadding(new Insets(15));


        // === Port-TextField definieren ===
        TextField portField = new TextField();
        portField.setPromptText("Port eingeben");
        portField.setStyle("-fx-font-size: 16px; " + "-fx-background-color: #222; " + "-fx-text-fill: #66ccff;");

        StackPane portPane = new StackPane(portField);
        portPane.setPadding(new Insets(15));

// Eingabe-Filter: Nur Zahlen bis 5 Stellen erlauben
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            if (newText.isEmpty()) return change; // Eingabe darf leer sein
            if (newText.matches("\\d{0,5}")) return change; // Max. 5 Ziffern erlaubt

            portField.setTooltip(new Tooltip("Port muss zwischen 49152 und 65535 liegen."));
            return null; // Alles andere wird blockiert
        };

        portField.setTextFormatter(new TextFormatter<>(filter));

// === Auslesen und prüfen (z. B. bei Buttonklick) ===
        String input = portField.getText();
        try {
            int port = Integer.parseInt(input);
            if (port >= 49152 && port <= 65535) {
                System.out.println("Gültiger Port: " + port);
                // → Hier kannst du den Port weiterverwenden
            } else {
                System.out.println("Port muss zwischen 49152 und 65535 liegen.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Bitte eine gültige Zahl eingeben.");
        }


        // Namensfeld (rechte Hälfte)
        TextField nameField = new TextField();
        nameField.setPromptText("Spielername eingeben");
        nameField.setStyle("-fx-font-size: 16px; -fx-background-color: #222; -fx-text-fill: #66ccff;");
        StackPane namePane = new StackPane(nameField);
        namePane.setPadding(new Insets(15));

        // HBox für horizontale Anordnung der Textfelder
        HBox inputBox = new HBox(10); // 10px Abstand zwischen den Feldern
        inputBox.setAlignment(Pos.CENTER);
        inputBox.getChildren().addAll(ipPane, portPane, namePane);

        Button backButton = new Button("Back");
        backButton.setId("buttonhj");
        backButton.setStyle("-fx-font-size: 24; -fx-pref-height: 50; -fx-pref-width: 150; -fx-text-fill: #FFFFFF ");


        root.getChildren().addAll(labelPane, hostPane, joinPane, inputBox, backButton);

        // Eventhandler
        backButton.setOnAction(e -> primaryStage.setScene(startMenuScene.getScene()));
        hostButton.setOnAction(e -> primaryStage.setScene(singlePlayerScene.getScene()));
        joinButton.setOnAction(e -> {
            String Ip = ipField.getText();
            String Port = portField.getText();
            String Name = nameField.getText();
            /*@TODO Hier übergabe an Client bzw Serverlogin*/
        });

        this.scene = new Scene(root, 1344, 776);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/de/dhbw/frontEnd/menu/MultiPlayer.css")).toExternalForm());
    }

    /**
     * Gibt die aktuelle Szene zurück.
     *
     * @return Die JavaFX-Szene für den Mehrspielermodus.
     */

    public Scene getScene() {
        return scene;
    }

    /**
     * Setzt die Referenz zur Startmenü-Szene.
     *
     * @param scene Die Startmenü-Szene.
     */

    public void setStartMenuScene(StartMenuScene scene) {
        this.startMenuScene = scene;
    }

    /**
     * Setzt die Referenz zur Spielkonfigurations-Szene.
     *
     * @param scene Die Einzelspieler-Szene (für spätere Konfiguration).
     */

    public void setGameSettingsScene(SinglePlayerScene scene) {
        this.singlePlayerScene = scene;
    }
}
//