package org.example.exceptionfromcatan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;



public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(
                getClass().getResource("/card-bar.fxml")
        );


        // Start propertys
        Scene scene = new Scene(root, 1280, 720);

        // Minimum/maximum height/width
        stage.setMinHeight(720);
        stage.setMinWidth(1280);
        stage.setTitle("Exception von Catan - SiedlungsOverflow");

        stage.setScene(scene);

        stage.show();
    }
}
