module de.dhbw {
  requires javafx.controls;
  requires javafx.fxml;

  opens de.dhbw.app to javafx.fxml;
  exports de.dhbw.app ;
}
