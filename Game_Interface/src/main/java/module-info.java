module com.version.game_interface {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.version.game_interface to javafx.fxml;
    exports com.version.game_interface;
}