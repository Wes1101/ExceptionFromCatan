
/**
 * SceneBoard is the JavaFX application entry point for launching the main Catan game board UI.
 * <p>
 * It loads the FXML layout defined in {@code card-bar.fxml} and sets up the main game window,
 * including dimensions and title.
 * </p>
 */

package de.dhbw.frontEnd.board;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

@Getter
public class SceneBoard extends Application {
  private Scene scene = null;

  /**
   * Starts the JavaFX application and initializes the game board scene.
   *
   * @param stage the primary stage provided by the JavaFX runtime
   * @throws IOException if the FXML resource cannot be loaded
   */
  @Override
  public void start(Stage stage) throws IOException {
    Parent root = FXMLLoader.load(
            getClass().getResource("/de/dhbw/frontEnd/board/card-bar.fxml")
    );

    // Initialize scene with fixed size
    scene = new Scene(root, 1280, 720);

    // Configure stage properties
    stage.setMinHeight(720);
    stage.setMinWidth(1280);
    stage.setTitle("Exception from Catan - SiedlungsOverflow");

    stage.setScene(scene);
    stage.show();
  }
}
