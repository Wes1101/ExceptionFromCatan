module org.example.exeptionfromcatan {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.exeptionfromcatan to javafx.fxml;
    exports org.example.exeptionfromcatan;
}