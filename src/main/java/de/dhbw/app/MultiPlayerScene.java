package de.dhbw.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MultiPlayerScene {
    private final Scene scene;
    private StartMenuScene startMenuScene;
    private SinglePlayerScene singlePlayerScene;


    public MultiPlayerScene(Stage primaryStage) { // Aufbau der Mehrspieler-GUI
        VBox root = new VBox(25);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #222;");

        Label multiplayerLabel = new Label("MultiPlayer");
        multiplayerLabel.setStyle(
                "-fx-font-size: 22px; -fx-text-fill: #ff4444; -fx-font-weight: bold;"
        );
        StackPane labelPane = new StackPane(multiplayerLabel);
        labelPane.setPadding(new Insets(15));
        labelPane.setStyle("-fx-border-color: #ff4444; -fx-border-width: 2;");

        Button hostButton = new Button("Host Game");
        hostButton.setStyle(
                "-fx-font-size: 18px; -fx-background-color: #ff99ff; -fx-text-fill: #222;"
        );
        StackPane hostPane = new StackPane(hostButton);
        hostPane.setPadding(new Insets(15));
        hostPane.setStyle("-fx-border-color: #ff99ff; -fx-border-width: 2;");

        Button joinButton = new Button("Join Game");
        joinButton.setStyle(
                "-fx-font-size: 18px; -fx-background-color: #44ff44; -fx-text-fill: #222;"
        );
        StackPane joinPane = new StackPane(joinButton);
        joinPane.setPadding(new Insets(15));
        joinPane.setStyle("-fx-border-color: #44ff44; -fx-border-width: 2;");

        // IP-Feld (linke Hälfte)
        TextField ipField = new TextField();
        ipField.setPromptText("IP-Adresse eingeben");
        ipField.setStyle(
                "-fx-font-size: 16px; -fx-background-color: #222; -fx-text-fill: #66ccff;"
        );
        StackPane ipPane = new StackPane(ipField);
        ipPane.setPadding(new Insets(15));
        ipPane.setStyle("-fx-border-color: #66ccff; -fx-border-width: 2;");

        // Namensfeld (rechte Hälfte)
        TextField nameField = new TextField();
        nameField.setPromptText("Spielername eingeben");
        nameField.setStyle(
                "-fx-font-size: 16px; -fx-background-color: #222; -fx-text-fill: #66ccff;"
        );
        StackPane namePane = new StackPane(nameField);
        namePane.setPadding(new Insets(15));
        namePane.setStyle("-fx-border-color: #66ccff; -fx-border-width: 2;");

        // HBox für horizontale Anordnung der Textfelder
        HBox inputBox = new HBox(10); // 10px Abstand zwischen den Feldern
        inputBox.setAlignment(Pos.CENTER);
        inputBox.getChildren().addAll(ipPane, namePane);

        Button backButton = new Button("Back");
        backButton.setStyle(
                "-fx-font-size: 18px; -fx-background-color: #fff200; -fx-text-fill: #222; -fx-font-weight: bold;"
        );

        root
                .getChildren()
                .addAll(labelPane, hostPane, joinPane, inputBox, backButton);

        // Eventhandler
        backButton.setOnAction(
                e -> primaryStage.setScene(startMenuScene.getScene())
        );
        hostButton.setOnAction(
                e -> primaryStage.setScene(singlePlayerScene.getScene())
        );
        joinButton.setOnAction(e -> {
                    String Ip = ipField.getText();
                    String Name = nameField.getText();
                    /*@TODO Hier übergabe an Client bzw Serverlogin*/
                }
        );

        this.scene = new Scene(root, 1152, 648);
    }


    public Scene getScene() {
        return scene;
    }


    public void setStartMenuScene(StartMenuScene scene) {
        this.startMenuScene = scene;
    }


    public void setGameSettingsScene(SinglePlayerScene scene) {
        this.singlePlayerScene = scene;
    }
}
//