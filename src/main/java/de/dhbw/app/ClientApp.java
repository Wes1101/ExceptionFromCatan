package de.dhbw.app;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * The ClientApp class is the main entry point for the JavaFX application.
 */
@Slf4j
public class ClientApp extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(
      ClientApp.class.getResource("hello-view.fxml")
    );
    Scene scene = new Scene(fxmlLoader.load(), 320, 240);
    stage.setTitle("Hello!");
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void stop() throws Exception {
    super.stop();
    log.info("Client Application stopped.");
  }

  public static void main(String[] args) {
    log.info("Client Application is starting...");
    launch();
  }
}
