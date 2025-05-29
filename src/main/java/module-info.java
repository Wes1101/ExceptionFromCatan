  requires javafx.controls;
  requires javafx.fxml;
  requires static lombok;
  requires org.slf4j;

    opens de.dhbw.app to javafx.fxml;
  exports de.dhbw.app ;

  opens de.dhbw.frontEnd.board to javafx.fxml;
  exports de.dhbw.frontEnd.board ;

  opens de.dhbw.frontEnd.menu to javafx.fxml;
  exports de.dhbw.frontEnd.menu ;

  exports de.dhbw.gameController ;
  exports  de.dhbw.network.client;
  exports  de.dhbw.network.server;
}
