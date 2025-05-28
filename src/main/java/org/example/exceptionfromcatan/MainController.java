package org.example.exceptionfromcatan;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private StackPane grain_group;
    @FXML private StackPane brick_group;
    @FXML private StackPane ore_group;
    @FXML private StackPane sheep_group;
    @FXML private StackPane lumber_group;
    @FXML private StackPane development_card_group;

    @FXML private ScrollPane scroll_pane;
    @FXML private StackPane board_stack;
    @FXML private Pane tile_layer;
    @FXML private Pane road_layer;
    @FXML private Pane building_layer;

    @FXML
    private ImageView trade_card;

    @FXML
    private ImageView setting_button;

    @FXML
    private StackPane trade_menue;

    @FXML
    private StackPane setting_menue;

    @FXML
    private Button close;

    @FXML
    private Button close_option_menue;

    // Duration of fade animation
    private static final Duration FADE_DURATION = Duration.millis(300);
    private static final Duration HOVER_DURATION = Duration.millis(200);

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        // Map scroll effect
        board_stack.setOnScroll(e -> {
            double zoomFactor = 1.1;
            double delta = e.getDeltaY() > 0 ? zoomFactor : 1/zoomFactor;
            board_stack.setScaleX(board_stack.getScaleX() * delta);
            board_stack.setScaleY(board_stack.getScaleY() * delta);
            e.consume();
        });

        initBoard();


        // Card hover effect
        addHover(grain_group);
        addHover(brick_group);
        addHover(ore_group);
        addHover(sheep_group);
        addHover(lumber_group);
        addHover(development_card_group);


        // Menue setup
        trade_menue.setVisible(false);
        trade_menue.setOpacity(0);

        trade_card.setOnMouseClicked(this::onOpenTrade);
        close.setOnAction(this::onCloseTrade);

        setting_menue.setVisible(false);
        setting_menue.setOpacity(0);

        setting_button.setOnMouseClicked(this::onOpenSetting);
        close_option_menue.setOnAction(this::onCloseSetting);
    }

    private void addHover(StackPane pane) {
        // Card Hover up
        pane.setOnMouseEntered(e -> {
            TranslateTransition tt = new TranslateTransition(HOVER_DURATION, pane);
            tt.setToY(-10);
            tt.play();
        });
        // Card Hover down
        pane.setOnMouseExited(e -> {
            TranslateTransition tt = new TranslateTransition(HOVER_DURATION, pane);
            tt.setToY(0);
            tt.play();
        });
    }

    private void onOpenTrade(MouseEvent event) {
        // Trade menue animation (open)
        trade_menue.setVisible(true);
        FadeTransition fadeIn = new FadeTransition(FADE_DURATION, trade_menue);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    private void onCloseTrade(ActionEvent event) {
        // Trade menue animation (close)
        FadeTransition fadeOut = new FadeTransition(FADE_DURATION, trade_menue);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> trade_menue.setVisible(false));
        fadeOut.play();
    }

    private void onOpenSetting(MouseEvent event) {
        // Setting menue animation (open)
        setting_menue.setVisible(true);
        FadeTransition fadeIn = new FadeTransition(FADE_DURATION, setting_menue);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    private void onCloseSetting(ActionEvent event) {
        // Setting menue animation (close)
        FadeTransition fadeOut = new FadeTransition(FADE_DURATION, setting_menue);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> setting_menue.setVisible(false));
        fadeOut.play();
    }

    // Map Tiles Board
    private void initBoard() {
        // parameter
        double size = 50; // Radius der Hex-Kachel
        double width = Math.sqrt(3) * size;
        double height = 1.5 * size;
        double offsetX = 400; // Mitten-Position in deinem Pane
        double offsetY = 300;

        // Catan tile layout
        for (int q = -2; q <= 2; q++) {
            for (int r = Math.max(-2, -q-2); r <= Math.min(2, -q+2); r++) {
                double x = offsetX + (q * width) + (r * width / 2);
                double y = offsetY + (r * height);

                int token = 2 + (int)(Math.random() * 11); // 2–12
                HexTile hex = new HexTile(x, y, size, token);
                tile_layer.getChildren().add(hex);
            }
        }
    }
}
