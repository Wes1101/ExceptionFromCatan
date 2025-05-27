module de.dhbw {
  requires javafx.controls;
  requires javafx.fxml;
  requires static lombok;
  requires org.slf4j;

  opens de.dhbw.app to javafx.fxml;
  exports de.dhbw.app ;
  exports  de.dhbw.network.client;
  exports  de.dhbw.network.server;
}
