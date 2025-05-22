module org.example.execptionfromcatan {
  requires javafx.controls;
  requires javafx.fxml;

  opens org.example.execptionfromcatan to javafx.fxml;
  exports org.example.execptionfromcatan ;
}
