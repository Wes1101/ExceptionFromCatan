module org.example.execptionfromcatan {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens org.example.execptionfromcatan to javafx.fxml;
    exports org.example.execptionfromcatan;
}