package de.dhbw.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        VBox root = new VBox(25);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #222;");
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Catan Settings");
        title.setStyle("-fx-font-size: 22px; -fx-text-fill: #ff4444; -fx-font-weight: bold;");
        StackPane titlePane = new StackPane(title);
        titlePane.setPadding(new Insets(15));
        titlePane.setStyle("-fx-border-color: #ff4444; -fx-border-width: 2;");

        VBox greenBox = new VBox(8);
        greenBox.setAlignment(Pos.CENTER_LEFT);
        Label greenLabel = new Label("Spieleranzahl");
        greenLabel.setStyle("-fx-text-fill: #44ff44; -fx-font-size: 16px;");
        Slider spielerSlider = new Slider(3, 6, 1);
        spielerSlider.setMajorTickUnit(1);
        spielerSlider.setMinorTickCount(0);
        spielerSlider.setSnapToTicks(true);
        spielerSlider.setShowTickLabels(true);
        spielerSlider.setShowTickMarks(true);
        greenBox.getChildren().addAll(greenLabel, spielerSlider);
        StackPane spielerSliderPane = new StackPane(greenBox);
        spielerSliderPane.setPadding(new Insets(15));
        spielerSliderPane.setStyle("-fx-border-color: #44ff44; -fx-border-width: 2;");

        VBox pinkBox = new VBox(8);
        pinkBox.setAlignment(Pos.CENTER_LEFT);
        Label pinkLabel = new Label("Siegpunktanzahl");
        pinkLabel.setStyle("-fx-text-fill: #ff88ff; -fx-font-size: 16px;");
        Slider siegpunktSlider = new Slider(6, 20, 1);
        siegpunktSlider.setMajorTickUnit(2);
        siegpunktSlider.setMinorTickCount(1);
        siegpunktSlider.setShowTickLabels(true);
        siegpunktSlider.setShowTickMarks(true);
        siegpunktSlider.setSnapToTicks(true);
        pinkBox.getChildren().addAll(pinkLabel, siegpunktSlider);
        StackPane pinkPane = new StackPane(pinkBox);
        pinkPane.setPadding(new Insets(15));
        pinkPane.setStyle("-fx-border-color: #ff88ff; -fx-border-width: 2;");

        VBox brownBox = new VBox(8);
        brownBox.setAlignment(Pos.CENTER_LEFT);
        Label brownLabel = new Label("Max. abwerfbare Karten");
        brownLabel.setStyle("-fx-text-fill: #ffbb66; -fx-font-size: 16px;");
        Slider abwerfenSlider = new Slider(4, 10, 1);
        abwerfenSlider.setMajorTickUnit(2);
        abwerfenSlider.setMinorTickCount(1);
        abwerfenSlider.setSnapToTicks(true);
        abwerfenSlider.setShowTickLabels(true);
        abwerfenSlider.setShowTickMarks(true);
        brownBox.getChildren().addAll(brownLabel, abwerfenSlider);
        StackPane brownPane = new StackPane(brownBox);
        brownPane.setPadding(new Insets(15));
        brownPane.setStyle("-fx-border-color: #ffbb66; -fx-border-width: 2;");

        Button startButton = new Button("Start Game");
        startButton.setStyle("-fx-font-size: 18px; -fx-background-color: #fff200; -fx-text-fill: #222; -fx-font-weight: bold;");
        startButton.setPrefWidth(220);

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 18px; -fx-background-color: #fff200; -fx-text-fill: #222; -fx-font-weight: bold;");

        root.getChildren().addAll(titlePane, spielerSliderPane, pinkPane, brownPane, startButton, backButton);

        // Eventhandler
        backButton.setOnAction(e -> primaryStage.setScene(startMenuScene.getScene()));

        startButton.setOnAction(e -> {
            boolean isServergame = startMenuScene.getServerGame();
            int playerCount = (int) spielerSlider.getValue();
            int winPoints = (int) siegpunktSlider.getValue();
            int maxCardThrow = (int) abwerfenSlider.getValue();
            System.out.println(isServergame + " " + playerCount + " " + winPoints + " " + maxCardThrow);

            if (isServergame) {
                /*@TODO Server(David) erstellen mit Übergabe der parameter*/
            } else{
                /*@TODO Spielsteureung(Fabian) erstellen mit Übergabe parameter*/
            }

        });

        this.scene = new Scene(root, 1152, 648);
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
