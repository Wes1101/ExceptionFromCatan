
package de.dhbw.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 * Represents the user interface for the multiplayer mode.
 * <p>
 * Allows the user to host a game or join an existing one by entering IP address, port, and player name.
 * </p>
 */
public class MultiPlayerScene {

    /** JavaFX scene representing the multiplayer screen */
    private final Scene scene;

    /** Reference to the start menu scene for navigation */
    private StartMenuScene startMenuScene;

    /** Reference to the single-player scene for later configuration */
    private SinglePlayerScene singlePlayerScene;

    /**
     * Constructs the multiplayer scene layout, including all input fields and navigation buttons.
     *
     * @param primaryStage the main stage of the application
     */
    public MultiPlayerScene(Stage primaryStage) {
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

        TextField ipField = new TextField();
        ipField.setPromptText("Insert IP-Address");
        ipField.setTooltip(new Tooltip("IPv4 format: XXX.XXX.XXX.XXX"));
        ipField.setStyle("-fx-font-size: 16px; -fx-background-color: #222; -fx-text-fill: #66ccff;");

        UnaryOperator<TextFormatter.Change> ipFilter = change -> {
            String newText = change.getControlNewText();
            if (!newText.matches("[0-9.]*")) return null;
            String[] parts = newText.split("\\.");
            if (parts.length > 4) return null;
            for (String part : parts) {
                if (!part.isEmpty()) {
                    try {
                        int num = Integer.parseInt(part);
                        if (num < 0 || num > 255) return null;
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }
            }
            return change;
        };
        ipField.setTextFormatter(new TextFormatter<>(ipFilter));
        StackPane ipPane = new StackPane(ipField);
        ipPane.setPadding(new Insets(15));

        TextField portField = new TextField();
        portField.setPromptText("Enter Port");
        portField.setStyle("-fx-font-size: 16px; -fx-background-color: #222; -fx-text-fill: #66ccff;");
        StackPane portPane = new StackPane(portField);
        portPane.setPadding(new Insets(15));

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty()) return change;
            if (newText.matches("\\d{0,5}")) return change;
            portField.setTooltip(new Tooltip("Port must be between 49152 and 65535."));
            return null;
        };
        portField.setTextFormatter(new TextFormatter<>(filter));

        TextField nameField = new TextField();
        nameField.setPromptText("Insert Playername");
        nameField.setStyle("-fx-font-size: 16px; -fx-background-color: #222; -fx-text-fill: #66ccff;");
        StackPane namePane = new StackPane(nameField);
        namePane.setPadding(new Insets(15));

        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.getChildren().addAll(ipPane, portPane, namePane);

        Button backButton = new Button("Back");
        backButton.setId("buttonhj");
        backButton.setStyle("-fx-font-size: 24; -fx-pref-height: 50; -fx-pref-width: 150; -fx-text-fill: #FFFFFF");

        root.getChildren().addAll(labelPane, hostPane, joinPane, inputBox, backButton);

        // Button handlers
        backButton.setOnAction(e -> primaryStage.setScene(startMenuScene.getScene()));
        hostButton.setOnAction(e -> primaryStage.setScene(singlePlayerScene.getScene()));
        joinButton.setOnAction(e -> {
            String Ip = ipField.getText();
            String Port = portField.getText();
            String Name = nameField.getText();
            System.out.println(Ip + " " + Port + " " + Name);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ALERT {WiP}");
            alert.setHeaderText("Server-Game not yet implemented");
            alert.setContentText("The local server game option is not implemented at the current time.");
            alert.showAndWait();
            // TODO: Handle connection to client/server login here
        });

        this.scene = new Scene(root, 1344, 776);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/de/dhbw/frontEnd/menu/MultiPlayer.css")).toExternalForm());
    }

    /**
     * Returns the current scene instance.
     *
     * @return the JavaFX scene for multiplayer mode
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

    /**
     * Sets the reference to the single-player scene for later game configuration.
     *
     * @param scene the single-player scene
     */
    public void setGameSettingsScene(SinglePlayerScene scene) {
        this.singlePlayerScene = scene;
    }
}
