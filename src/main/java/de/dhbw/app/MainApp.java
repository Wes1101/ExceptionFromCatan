package de.dhbw.app;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Initialisierung der Szenen
        StartMenuScene startMenu = new StartMenuScene(primaryStage);
        SinglePlayerScene singlePlayer = new SinglePlayerScene(primaryStage);
        MultiPlayerScene multiPlayer = new MultiPlayerScene(primaryStage);

        // Verlinkung der Szenen untereinander
        startMenu.setSinglePlayerScene(singlePlayer);
        startMenu.setMultiPlayerScene(multiPlayer);
        singlePlayer.setStartMenuScene(startMenu);
        multiPlayer.setStartMenuScene(startMenu);
        multiPlayer.setGameSettingsScene(singlePlayer);

        // Startszene setzen und anzeigen
        primaryStage.setScene(startMenu.getScene());
        primaryStage.setTitle("Catan Launcher");
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
//