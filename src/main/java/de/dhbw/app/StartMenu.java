package de.dhbw.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartMenu extends Application {

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setResizable(false);
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

    Scene startScene = new Scene(StartMenu, 1152, 648);
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
    Slider spielerSlider = new Slider(3, 6, 1);
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

    // Braun: Max. abwerfbare Karten Slider
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

    // Start Game Button
    Button startButton = new Button("Start Game");
    startButton.setStyle(
      "-fx-font-size: 18px; -fx-background-color: #fff200; -fx-text-fill: #222; -fx-font-weight: bold;"
    );
    startButton.setPrefWidth(220);

    // BackButton
    Button SinglePlayerbackButton = new Button("Back");
    SinglePlayerbackButton.setStyle(
      "-fx-font-size: 18px; -fx-background-color: #fff200; -fx-text-fill: #222; -fx-font-weight: bold;"
    );

    // Komponenten ins Layout
    Singleplayer
      .getChildren()
      .addAll(
        SingleplayerTitlePane,
        SpielerSliderPane,
        pinkPane,
        brownPane,
        startButton,
        SinglePlayerbackButton
      );

    Scene SinglePlayerScene = new Scene(Singleplayer, 1152, 648);

    //----------------------------------------------------------------------------------------
    //MultiplayerStage
    //----------------------------------------------------------------------------------------
    VBox Multiplayer = new VBox(25);
    Multiplayer.setPadding(new Insets(40));
    Multiplayer.setAlignment(Pos.CENTER);
    Multiplayer.setStyle("-fx-background-color: #222;");

    // Rot: MultiPlayer Text
    Label multiplayerLabel = new Label("MultiPlayer");
    multiplayerLabel.setStyle(
      "-fx-font-size: 22px; -fx-text-fill: #ff4444; -fx-font-weight: bold;"
    );
    StackPane MultiPlayerLabelPane = new StackPane(multiplayerLabel);
    MultiPlayerLabelPane.setPadding(new Insets(15));
    MultiPlayerLabelPane.setStyle(
      "-fx-border-color: #ff4444; -fx-border-width: 2;"
    );

    // Pink: Host Game Button
    Button hostButton = new Button("Host Game");
    hostButton.setStyle(
      "-fx-font-size: 18px; -fx-background-color: #ff99ff; -fx-text-fill: #222;"
    );
    StackPane HostButtonPane = new StackPane(hostButton);
    HostButtonPane.setPadding(new Insets(15));
    HostButtonPane.setStyle("-fx-border-color: #ff99ff; -fx-border-width: 2;");

    // Grün: Join Game Button
    Button joinButton = new Button("Join Game");
    joinButton.setStyle(
      "-fx-font-size: 18px; -fx-background-color: #44ff44; -fx-text-fill: #222;"
    );
    StackPane greenPane = new StackPane(joinButton);
    greenPane.setPadding(new Insets(15));
    greenPane.setStyle("-fx-border-color: #44ff44; -fx-border-width: 2;");

    // Blau: Eingabefeld für IP-Adresse
    TextField ipField = new TextField();
    ipField.setPromptText("IP-Adresse eingeben");
    ipField.setStyle(
      "-fx-font-size: 16px; -fx-background-color: #222; -fx-text-fill: #66ccff;"
    );
    StackPane bluePane = new StackPane(ipField);
    bluePane.setPadding(new Insets(15));
    bluePane.setStyle("-fx-border-color: #66ccff; -fx-border-width: 2;");

    Button MultiPlayerBackButton = new Button("Back");
    MultiPlayerBackButton.setStyle(
      "-fx-font-size: 18px; -fx-background-color: #fff200; -fx-text-fill: #222; -fx-font-weight: bold;"
    );

    // Alle Bereiche ins Layout einfügen
    Multiplayer
      .getChildren()
      .addAll(
        MultiPlayerLabelPane,
        HostButtonPane,
        greenPane,
        bluePane,
        MultiPlayerBackButton
      );

    Scene MultiPlayerScene = new Scene(Multiplayer, 1152, 648);
    //----------------------------------------------------------------------------------------
    //Settings
    //----------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------
    //Buttons
    //----------------------------------------------------------------------------------------
    /*Eventhandler for Singleplayer Button*/
    btnEinzel.setOnAction(
      pressedEvent -> {
        primaryStage.setScene(SinglePlayerScene);
      }
    );
    /*BackButtonSingleplayer*/
    SinglePlayerbackButton.setOnAction(
      pressedEvent -> {
        primaryStage.setScene(startScene);
      }
    );

    /*Eventhandler for Multiplayer Button*/
    btnMehr.setOnAction(
      pressedEvent -> {
        primaryStage.setScene(MultiPlayerScene);
      }
    );
    /*BackButtonSingleplayer*/
    MultiPlayerBackButton.setOnAction(
      pressedEvent -> {
        primaryStage.setScene(startScene);
      }
    );
    /*Eventhandler for Multiplayer Join Game Button saving the IP adress and giving it to ?*/
    joinButton.setOnAction(
      pressedEvent -> {
        String ip = ipField.getText();
        //@TODO Methoden aufruf bzw übergabe an Server/Client

      }
    );

    // Scene und Stage
    primaryStage.setScene(startScene);
    primaryStage.setTitle("Catan Launcher");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
