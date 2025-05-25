module de.dhbw.execptionfromcatan {
  requires javafx.controls;
  requires javafx.fxml;

  opens de.dhbw.execptionfromcatan to javafx.fxml;
  exports de.dhbw.execptionfromcatan ;
}
