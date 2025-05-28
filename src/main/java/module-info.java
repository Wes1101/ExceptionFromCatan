module com.version.game_interface {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.exceptionfromcatan to javafx.fxml;
    exports org.example.exceptionfromcatan;
}