module de.dhbw {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires static lombok;
    requires java.desktop;
    requires com.google.gson;
    requires java.smartcardio;
    requires javafx.graphics;
    requires java.management;
    requires java.logging;

    opens de.dhbw.app to javafx.fxml;
    exports de.dhbw.app;

    opens de.dhbw.frontEnd.board to javafx.fxml;
    exports de.dhbw.frontEnd.board;

    opens de.dhbw.frontEnd.menu to javafx.fxml;

    exports de.dhbw.gameController;
    exports de.dhbw.client;
    exports de.dhbw.server;
    exports de.dhbw.gamePieces;
    exports de.dhbw.dto;
    exports de.dhbw.network;
}
