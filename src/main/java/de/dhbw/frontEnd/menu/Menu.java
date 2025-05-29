package de.dhbw.frontEnd.menu;

import java.util.Objects;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Menu extends Application {

  @Override
  public void start(Stage menu) {
    menu.setTitle("Launcher Exception from Catan");
    menu.setWidth(1152);
    menu.setHeight(648);
    menu.setResizable(false);

    // Hauptlayout: HBox für linke und rechte Seite
    HBox mainLayout = new HBox();
    mainLayout.setPrefSize(1152, 648);

    // Linke Seite (Menü)
    VBox leftMenu = new VBox();
    leftMenu.setPrefWidth(384); // 1/3 von 1152
    leftMenu.setPadding(new Insets(30, 10, 10, 10));
    leftMenu.setSpacing(15);
    leftMenu.getStyleClass().add("leftMenu");

    // Start-Button
    Button startButton = new Button("Start Game");
    startButton.setPrefSize(340, 70);

    // Multiplayer Checkbox
    CheckBox multiplayerCheckBox = new CheckBox("Multiplayer");

    // Spieleranzahl
    VBox playerCountBox = new VBox(5);
    Text playerCountText = new Text("Anzahl Spieler:");
    Slider playerCountSlider = new Slider(3, 6, 1);
    playerCountSlider.setMaxSize(340, 70);
    playerCountSlider.setShowTickLabels(true);
    playerCountSlider.setShowTickMarks(true);
    playerCountSlider.setMajorTickUnit(1);
    playerCountSlider.setMinorTickCount(0);
    playerCountSlider.setSnapToTicks(true);
    playerCountBox.getChildren().addAll(playerCountText, playerCountSlider);

    // Siegpunkte
    VBox winPointsBox = new VBox(5);
    Text winPointsText = new Text("Siegpunkte:");
    Slider winPointsSlider = new Slider(4, 20, 1);
    winPointsSlider.setMaxSize(340, 70);
    winPointsSlider.setShowTickLabels(true);
    winPointsSlider.setShowTickMarks(true);
    winPointsSlider.setMajorTickUnit(2);
    winPointsSlider.setMinorTickCount(1);
    winPointsSlider.setSnapToTicks(true);
    winPointsBox.getChildren().addAll(winPointsText, winPointsSlider);
    System.out.println(winPointsSlider.getProperties().size());

    // Kartenabwurflimit
    VBox discardLimitBox = new VBox(5);
    Text discardLimitText = new Text("Kartenabwurflimit:");
    Slider discardLimitSlider = new Slider(4, 10, 1);
    discardLimitSlider.setMaxSize(340, 70);
    discardLimitSlider.setValue(10);
    discardLimitSlider.setShowTickLabels(true);
    discardLimitSlider.setShowTickMarks(true);
    discardLimitSlider.setMajorTickUnit(1);
    discardLimitSlider.setSnapToTicks(true);
    discardLimitBox.getChildren().addAll(discardLimitText, discardLimitSlider);

    // Alles links anordnen
    leftMenu
      .getChildren()
      .addAll(
        startButton,
        multiplayerCheckBox,
        playerCountBox,
        winPointsBox,
        discardLimitBox
      );

    // Rechter Bereich (leer)
    Pane rightPane = new Pane();
    rightPane.setPrefWidth(778); // 2/3 von 1152
    rightPane.getStyleClass().add("rightPane");

    // Layout zusammenbauen
    mainLayout.getChildren().addAll(leftMenu, rightPane);

    //Trennlinie zeichnen
    Line SeperationLine = new Line();
    SeperationLine.setStartX(0);
    SeperationLine.setStartY(0);
    SeperationLine.setEndX(0);
    SeperationLine.setEndY(648);
    SeperationLine.setStrokeWidth(4);

    rightPane.getChildren().addAll(SeperationLine);

    // Szene erstellen
    Scene scene = new Scene(mainLayout, 1152, 648, Color.WHITE);
    scene
      .getStylesheets()
      .add(
        Objects
          .requireNonNull(getClass().getResource("menue.css"))
          .toExternalForm()
      );
    menu.setScene(scene);

    menu.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
