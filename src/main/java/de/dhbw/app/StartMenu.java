package de.dhbw.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartMenu extends Application {

  @Override
  public void start(Stage primaryStage) {
    //----------------------------------------------------------------------------------------
    //StartMenu
    //----------------------------------------------------------------------------------------
    // Haupt-Layout
    VBox StartMenu = new VBox(30);
    StartMenu.setPadding(new Insets(30));
    StartMenu.setStyle("-fx-background-color: #222;"); // Dunkler Hintergrund

    // Roter Bereich: Titel
    Label title = new Label("Exception von Catan Launcher");
    title.setStyle(
      "-fx-font-size: 26px; -fx-text-fill: #ff4444; -fx-font-weight: bold;"
    );
    StackPane redPane = new StackPane(title);
    redPane.setPadding(new Insets(15));
    redPane.setStyle(
      "-fx-border-color: #ff4444; -fx-border-width: 2; -fx-background-radius: 10;"
    );

    // Pinke Bereiche: Einzelspieler & Mehrspieler Buttons
    Button btnEinzel = new Button("Einzelspieler");
    btnEinzel.setPrefWidth(200);
    btnEinzel.setStyle(
      "-fx-font-size: 18px; -fx-background-color: #ff99ff; -fx-text-fill: #222;"
    );

    Button btnMehr = new Button("Mehrspieler");
    btnMehr.setPrefWidth(200);
    btnMehr.setStyle(
      "-fx-font-size: 18px; -fx-background-color: #ff99ff; -fx-text-fill: #222;"
    );

    StackPane pinkPane1 = new StackPane(btnEinzel);
    pinkPane1.setPadding(new Insets(10));
    pinkPane1.setStyle(
      "-fx-border-color: #ff99ff; -fx-border-width: 2; -fx-background-radius: 10;"
    );

    StackPane pinkPane2 = new StackPane(btnMehr);
    pinkPane2.setPadding(new Insets(10));
    pinkPane2.setStyle(
      "-fx-border-color: #ff99ff; -fx-border-width: 2; -fx-background-radius: 10;"
    );

    // Alles ins Haupt-Layout einfügen
    StartMenu.getChildren().addAll(redPane, pinkPane1, pinkPane2);
    StartMenu.setAlignment(Pos.CENTER);

    Scene startScene = new Scene(StartMenu, 600, 400);
    //----------------------------------------------------------------------------------------
    //SinglePlayerMenu
    //----------------------------------------------------------------------------------------
    VBox Singleplayer = new VBox(25);
    Singleplayer.setPadding(new Insets(40));
    Singleplayer.setStyle("-fx-background-color: #222;");
    Singleplayer.setAlignment(Pos.CENTER);

    // Rot: Titel
    Label titleSingleplayer = new Label("Catan Singleplayer");
    titleSingleplayer.setStyle(
      "-fx-font-size: 22px; -fx-text-fill: #ff4444; -fx-font-weight: bold;"
    );
    StackPane SingleplayerTitlePane = new StackPane(titleSingleplayer);
    SingleplayerTitlePane.setPadding(new Insets(15));
    SingleplayerTitlePane.setStyle(
      "-fx-border-color: #ff4444; -fx-border-width: 2;"
    );

    // Grün: Spieleranzahl Slider
    VBox greenBox = new VBox(8);
    greenBox.setAlignment(Pos.CENTER_LEFT);
    Label greenLabel = new Label("Spieleranzahl");
    greenLabel.setStyle("-fx-text-fill: #44ff44; -fx-font-size: 16px;");
    Slider spielerSlider = new Slider(2, 6, 4);
    spielerSlider.setMajorTickUnit(1);
    spielerSlider.setMinorTickCount(0);
    spielerSlider.setSnapToTicks(true);
    spielerSlider.setShowTickLabels(true);
    spielerSlider.setShowTickMarks(true);
    greenBox.getChildren().addAll(greenLabel, spielerSlider);
    StackPane SpielerSliderPane = new StackPane(greenBox);
    SpielerSliderPane.setPadding(new Insets(15));
    SpielerSliderPane.setStyle(
      "-fx-border-color: #44ff44; -fx-border-width: 2;"
    );

    // Pink: Siegpunktanzahl Slider
    VBox pinkBox = new VBox(8);
    pinkBox.setAlignment(Pos.CENTER_LEFT);
    Label pinkLabel = new Label("Siegpunktanzahl");
    pinkLabel.setStyle("-fx-text-fill: #ff88ff; -fx-font-size: 16px;");
    Slider siegpunktSlider = new Slider(5, 20, 10);
    siegpunktSlider.setMajorTickUnit(5);
    siegpunktSlider.setMinorTickCount(4);
    siegpunktSlider.setShowTickLabels(true);
    siegpunktSlider.setShowTickMarks(true);
    pinkBox.getChildren().addAll(pinkLabel, siegpunktSlider);
    StackPane pinkPane = new StackPane(pinkBox);
    pinkPane.setPadding(new Insets(15));
    pinkPane.setStyle("-fx-border-color: #ff88ff; -fx-border-width: 2;");

    // Braun: Max. abwerfbare Karten Slider
    VBox brownBox = new VBox(8);
    brownBox.setAlignment(Pos.CENTER_LEFT);
    Label brownLabel = new Label("Max. abwerfbare Karten");
    brownLabel.setStyle("-fx-text-fill: #ffbb66; -fx-font-size: 16px;");
    Slider abwerfenSlider = new Slider(1, 7, 4);
    abwerfenSlider.setMajorTickUnit(1);
    abwerfenSlider.setMinorTickCount(0);
    abwerfenSlider.setSnapToTicks(true);
    abwerfenSlider.setShowTickLabels(true);
    abwerfenSlider.setShowTickMarks(true);
    brownBox.getChildren().addAll(brownLabel, abwerfenSlider);
    StackPane brownPane = new StackPane(brownBox);
    brownPane.setPadding(new Insets(15));
    brownPane.setStyle("-fx-border-color: #ffbb66; -fx-border-width: 2;");

    // Start Game Button
    Button startButton = new Button("Start Game");
    startButton.setStyle(
      "-fx-font-size: 18px; -fx-background-color: #fff200; -fx-text-fill: #222; -fx-font-weight: bold;"
    );
    startButton.setPrefWidth(220);

    // Komponenten ins Layout
    Singleplayer
      .getChildren()
      .addAll(
        SingleplayerTitlePane,
        SpielerSliderPane,
        pinkPane,
        brownPane,
        startButton
      );

    Scene SinglePlayerScene = new Scene(Singleplayer, 700, 500);

    //----------------------------------------------------------------------------------------
    //MultiplayerStage
    //----------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------
    //Settings
    //----------------------------------------------------------------------------------------

    // Scene und Stage
    primaryStage.setScene(startScene);
    primaryStage.setTitle("Catan Launcher");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
