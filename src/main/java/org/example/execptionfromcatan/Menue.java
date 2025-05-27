package org.example.execptionfromcatan;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Menue extends Application {

    @Override
    public void start(Stage menue) {
        /*Stage Settings*/
        menue.setTitle("Exception from Catan: SiedlungsOverflow");
        menue.setWidth(1152);
        menue.setHeight(648);
        menue.setResizable(false);

        /*Pane Settings*/
        VBox ground = new VBox();
        menue.setScene(new Scene(ground, Color.WHITE));

        // SplitPane erstellen
        SplitPane firstsplit = new SplitPane();
        firstsplit.setPrefSize(1152, 648);//Setzt die Größe des SplitPanes
        firstsplit.setDividerPositions(0.333); // Setzt die Position des Teilers auf 33%

        // Linkes Pane (pink)
        StackPane leftPane = new StackPane();// Erstellen eines StackPane für das linke Pane
        leftPane.setStyle("-fx-background-color: pink;");// Setzt die Hintergrundfarbe des linken Panes auf Pink
        leftPane.setPrefSize(576, 324);// Setzt die Größe des linken Panes

        // Rechtes Pane (z.B. leer oder mit Inhalt)
        StackPane rightPane = new StackPane();// Erstellen eines StackPane für das rechte Pane
        rightPane.setStyle("-fx-background-color: lightblue;");
        rightPane.setPrefSize(576, 324);// Setzt die Größe des rechten Panes

        // Beide Panes zum SplitPane hinzufügen
        firstsplit.getItems().addAll(leftPane, rightPane);// Fügt die beiden StackPanes zum SplitPane hinzu

        // SplitPane zum Hauptlayout hinzufügen
        ground.getChildren().add(firstsplit);// Fügt das SplitPane zum Hauptlayout (VBox) hinzu

        SplitPane firstverticalSplit = new SplitPane();
        firstverticalSplit.setOrientation(javafx.geometry.Orientation.VERTICAL);
        firstverticalSplit.setPrefSize(1152, 648);

        StackPane TopFirstPane = new StackPane();// Erstellen eines StackPane für das rechte Pane
        rightPane.setStyle("-fx-background-color: pink;");
        rightPane.setPrefSize(576, 324);// Setzt die Größe des rechten Panes
        StackPane BottomFirstPane = new StackPane();// Erstellen eines StackPane für das rechte Pane
        rightPane.setStyle("-fx-background-color: lightblue;");
        rightPane.setPrefSize(576, 324);// Setzt die Größe des rechten Panes

        firstverticalSplit.getItems().addAll(TopFirstPane, BottomFirstPane);// Fügt die beiden StackPanes zum SplitPane hinzu
        leftPane.getChildren().add(firstverticalSplit);// Fügt das SplitPane zum linken Pane hinzu

        menue.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
