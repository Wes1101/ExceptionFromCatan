package de.dhbw.frontEnd.board;

import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

@Getter
public class SceneBoard extends Application {
    Scene scene = null;

  @Override
  public void start(Stage stage) throws IOException {
    Parent root = FXMLLoader.load(
            Objects.requireNonNull(getClass().getResource("/de/dhbw/frontEnd/board/card-bar.fxml"))
    );

    // Start properties
    scene = new Scene(root, 1280, 720);

    // Minimum/maximum height/width
    stage.setMinHeight(720);
    stage.setMinWidth(1280);
    stage.setTitle("Exception from Catan - SettlementOverflow");

    stage.setScene(scene);

    stage.show();
  }
}
