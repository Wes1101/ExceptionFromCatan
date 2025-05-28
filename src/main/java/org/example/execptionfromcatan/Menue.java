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
/*Product until here is a empty non resisable Window 1152x648px with the name "Exception from ..." */

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

        Splitpane topsecondsplit = new SplitPane();
        topsecondsplit.setOrientation(javafx.geometry.Orientation.VERTICAL);
        topsecondsplit.setPrefSize(, 648);



        menue.show();//Show the Stage with the defined settings and content
    }

    public static void main(String[] args) {
        launch(args);
    }
}
