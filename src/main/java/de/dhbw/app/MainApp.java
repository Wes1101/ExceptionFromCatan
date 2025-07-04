package de.dhbw.app;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Hauptklasse der Anwendung. Startet die JavaFX-Applikation
 * und initialisiert die Szenen (Startmenü, Einzelspieler, Mehrspieler).
 */

public class MainApp extends Application {

    /**
     * Startpunkt der JavaFX-Anwendung.
     *
     * @param primaryStage Die primäre Stage (Fenster) der Anwendung.
     */

    @Override
    public void start(Stage primaryStage) {

        // Szenen-Objekte anlegen und mit Stage referenzieren
        StartMenuScene startMenu = new StartMenuScene(primaryStage);
        SinglePlayerScene singlePlayer = new SinglePlayerScene(primaryStage);
        MultiPlayerScene multiPlayer = new MultiPlayerScene(primaryStage);
      
        // Szenen gegenseitig bekannt machen (für Wechsel)
        startMenu.setSinglePlayerScene(singlePlayer);
        startMenu.setMultiPlayerScene(multiPlayer);
        singlePlayer.setStartMenuScene(startMenu);
        multiPlayer.setStartMenuScene(startMenu);
        multiPlayer.setGameSettingsScene(singlePlayer);

        // Startszene anzeigen
        primaryStage.setScene(startMenu.getScene());
        primaryStage.setTitle("Catan Launcher");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Einstiegspunkt der Anwendung.
     *
     * @param args Kommandozeilenargumente (werden nicht verwendet).
     */

    public static void main(String[] args) {
        launch(args);
    }
}
//