
package de.dhbw.app;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class of the application.
 * <p>
 * Launches the JavaFX application and initializes all scenes (start menu, single player, multiplayer).
 * </p>
 */
public class MainApp extends Application {

    /**
     * Entry point of the JavaFX application.
     * <p>
     * Initializes and connects the different UI scenes and displays the start menu.
     * </p>
     *
     * @param primaryStage The primary window of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        // Create scene objects and link them to the primary stage
        StartMenuScene startMenu = new StartMenuScene(primaryStage);
        SinglePlayerScene singlePlayer = new SinglePlayerScene(primaryStage);
        MultiPlayerScene multiPlayer = new MultiPlayerScene(primaryStage);

        // Set up cross-references between scenes for navigation
        startMenu.setSinglePlayerScene(singlePlayer);
        startMenu.setMultiPlayerScene(multiPlayer);
        singlePlayer.setStartMenuScene(startMenu);
        multiPlayer.setStartMenuScene(startMenu);
        multiPlayer.setGameSettingsScene(singlePlayer);

        // Show the start scene
        primaryStage.setScene(startMenu.getScene());
        primaryStage.setTitle("Catan Launcher");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Main entry point of the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        launch(args);
    }
}
