package org.example.execptionfromcatan;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;



public class Menue extends Application {

    @Override
    public void start(Stage menue) {
        /*Stage Settings*/
        menue.setTitle("Exception from Catan: SiedlungsOverflow");
        menue.setWidth(1152);
        menue.setHeight(648);
        menue.setResizable(false);
        /*Product until here is an empty non resisable Window 1152x648px with the name "Exception from ..." */

        /*Pane Settings*/
        VBox ground = new VBox();
        menue.setScene(new Scene(ground, Color.WHITE));
        /*Product got a VBox which is used to set the Background to white and serve as the first scene*/

        // SplitPane first erstellen
        SplitPane firstsplit = new SplitPane();
        firstsplit.setPrefSize(1152, 648);
        firstsplit.setDividerPositions(0.333);
        /*Product is split by a splitpane which splits the menue into a left part 2/6 big and a right part 4/6big*/
        // Linkes Pane (pink)
        StackPane leftPane = new StackPane();
        leftPane.setStyle("-fx-background-color: pink;");
        leftPane.setPrefSize(576, 324);

        // Rechtes Pane (z.B. leer oder mit Inhalt)
        StackPane rightPane = new StackPane();
        rightPane.setStyle("-fx-background-color: lightblue;");
        rightPane.setPrefSize(576, 324);
        /*Two Stack Panes are used to change the background colour of the splitpanes for better understanding which panes which*/

        // Beide Panes zum SplitPane hinzufügen
        firstsplit.getItems().addAll(leftPane, rightPane);


        // SplitPane zum Hauptlayout hinzufügen
        ground.getChildren().add(firstsplit);
        /*MAJOR Window 1152x648 split into 2 separate parts */

        SplitPane firstverticalSplit = new SplitPane();
        firstverticalSplit.setOrientation(javafx.geometry.Orientation.VERTICAL);
        firstverticalSplit.setPrefSize(1152, 648);
        /*First Vertikal Split to turn the left field into 2 Boxes*/

        StackPane TopFirstPane = new StackPane();
        TopFirstPane.setStyle("-fx-background-color: pink;");
        TopFirstPane.setPrefSize(576, 324);

        StackPane BottomFirstPane = new StackPane();
        BottomFirstPane.setStyle("-fx-background-color: lightblue;");
        BottomFirstPane.setPrefSize(576, 324);

        firstverticalSplit.getItems().addAll(TopFirstPane, BottomFirstPane);// Fügt die beiden StackPanes zum SplitPane hinzu
        leftPane.getChildren().add(firstverticalSplit);// Fügt das SplitPane zum linken Pane hinzu
        /*Window got a left split into top and bottom side which is 33% of the window and a right side*/

        SplitPane topSecondSplit = new SplitPane();
        topSecondSplit.setOrientation(javafx.geometry.Orientation.VERTICAL);
        topSecondSplit.setPrefSize(576, 324);

        TopFirstPane.getChildren().add(topSecondSplit);

        StackPane TopTopSecondPane = new StackPane();
        TopTopSecondPane.setStyle("-fx-background-color: lightgreen;");
        TopTopSecondPane.setPrefSize(288, 81);

        StackPane TopBotSecondPane = new StackPane();
        TopBotSecondPane.setStyle("-fx-background-color: red;");
        TopBotSecondPane.setPrefSize(288, 81);

        topSecondSplit.getItems().addAll(TopTopSecondPane, TopBotSecondPane);
        /*Top part gets Split a second time*/

        SplitPane botSecondSplit = new SplitPane();
        botSecondSplit.setOrientation(javafx.geometry.Orientation.VERTICAL);
        botSecondSplit.setPrefSize(576, 324);
        botSecondSplit.setDividerPositions(0.25);
        BottomFirstPane.getChildren().add(botSecondSplit);

        StackPane BotTopSecondPane = new StackPane();
        BotTopSecondPane.setStyle("-fx-background-color: yellow;");
        BotTopSecondPane.setPrefSize(288, 81);

        StackPane NamesPane = new StackPane();
        BotTopSecondPane.setStyle("-fx-background-color: violet;");
        BotTopSecondPane.setPrefSize(288, 81);

        botSecondSplit.getItems().addAll(BotTopSecondPane, NamesPane);

        SplitPane TopBotThirdSplit = new SplitPane();
        TopBotThirdSplit.setOrientation(javafx.geometry.Orientation.VERTICAL);
        TopBotThirdSplit.setPrefSize(576, 324);

        StackPane TopTopThirdPane = new StackPane();
        TopTopThirdPane.setStyle("-fx-background-color: orange;");
        TopTopThirdPane.setPrefSize(288, 81);

        StackPane TopBotThirdPane = new StackPane();
        TopBotThirdPane.setStyle("-fx-background-color: purple;");
        TopBotThirdPane.setPrefSize(288, 81);

        TopBotThirdSplit.getItems().addAll(TopTopThirdPane, TopBotThirdPane);
        TopBotSecondPane.getChildren().add(TopBotThirdSplit);

        //Button
        Button startButton = new Button("Start Game");
        startButton.setPrefSize(240, 60);

        TopTopSecondPane.getChildren().add(startButton);

        // Sliders
        Slider playerCountSlider = new Slider(3, 6, 1);
        playerCountSlider.setShowTickLabels(true);
        playerCountSlider.setSnapToTicks(true);
        playerCountSlider.setMajorTickUnit(1);
        playerCountSlider.setMinorTickCount(0);
        playerCountSlider.setPrefSize(200, 50);

        Slider winPointsSlider = new Slider(4, 20, 1);
        winPointsSlider.setShowTickLabels(true);
        winPointsSlider.setSnapToTicks(true);
        winPointsSlider.setMajorTickUnit(2);
        winPointsSlider.setMinorTickCount(1);
        winPointsSlider.setPrefSize(200, 50);

        Slider maxCardThrow = new Slider(4, 10, 1);
        maxCardThrow.setShowTickLabels(true);
        maxCardThrow.setSnapToTicks(true);
        maxCardThrow.setMajorTickUnit(1);
        maxCardThrow.setMinorTickCount(0);
        maxCardThrow.setPrefSize(200, 50);


        // SplitPanes for Slider Player
        SplitPane PlayerCountSliderSplit = new SplitPane();
        PlayerCountSliderSplit.setDividerPositions(0.35);
        PlayerCountSliderSplit.orientationProperty().set(javafx.geometry.Orientation.VERTICAL);

        StackPane PlayerCountSliderTextPane = new StackPane();

        StackPane PlayerCountSliderPane = new StackPane();

        PlayerCountSliderSplit.getItems().addAll(PlayerCountSliderTextPane, PlayerCountSliderPane);

        // SplitPanes for Slider WinPoints
        SplitPane WinPointsSliderSplit = new SplitPane();
        WinPointsSliderSplit.setDividerPositions(0.35);
        WinPointsSliderSplit.orientationProperty().set(javafx.geometry.Orientation.VERTICAL);

        StackPane WinPointsSliderTextPane = new StackPane();

        StackPane WinPointsSliderPane = new StackPane();

        WinPointsSliderSplit.getItems().addAll(WinPointsSliderTextPane, WinPointsSliderPane);

        // SplitPanes for Slider MaxCardThrow
        SplitPane MaxCardThrowSplit = new SplitPane();
        MaxCardThrowSplit.setDividerPositions(0.35);
        MaxCardThrowSplit.orientationProperty().set(javafx.geometry.Orientation.VERTICAL);

        StackPane MaxCardThrowTextPane = new StackPane();

        StackPane MaxCardThrowSliderPane = new StackPane();

        MaxCardThrowSplit.getItems().addAll(MaxCardThrowTextPane, MaxCardThrowSliderPane);
//s

        SplitPane MaxCardThrow = new SplitPane();
        MaxCardThrow.setDividerPositions(0.1);


        TopTopThirdPane.getChildren().add(PlayerCountSliderSplit);
        TopBotThirdPane.getChildren().add(WinPointsSliderSplit);
        BotTopSecondPane.getChildren().add(MaxCardThrowSplit);

        Text playerCountText = new Text("Anzahl Spieler:");
        Text winPointsText = new Text("Siegpunkte:");
        Text maxCardThrowText = new Text("Kartenabwurflimit:");

        PlayerCountSliderTextPane.getChildren().add(playerCountText);
        WinPointsSliderTextPane.getChildren().add(winPointsText);
        MaxCardThrowTextPane.getChildren().add(maxCardThrowText);

        PlayerCountSliderPane.getChildren().add(playerCountSlider);
        WinPointsSliderPane.getChildren().add(winPointsSlider);
        MaxCardThrowSliderPane.getChildren().add(maxCardThrow);



        menue.show();//Show the Stage with the defined settings and content
    }

    public static void main(String[] args) {
        launch(args);
    }
}
