package org.example.execptionfromcatan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.execptionfromcatan.resources.Wood;

import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
    }

    public static void main(String[] args) {
        //launch();
        Bank testBank = new Bank(10);
        Player player1 = new Player();
        Player player2 = new Player();

        System.out.println(testBank.getWood().size());

        testBank.addResources(List.of(new Wood(), new Wood()));

        //System.out.println(testBank.getWood().size());
        System.out.println(player1.getWood().size());
        testBank.removeResources(1, "wood", player1);
        System.out.println(player1.getWood().size());
        System.out.println(testBank.getAmountOfResources("wood"));


        //System.out.println(payout.get(0).name);
    }
}