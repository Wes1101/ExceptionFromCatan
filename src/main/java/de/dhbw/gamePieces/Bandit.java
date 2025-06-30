package de.dhbw.gamePieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.dhbw.catanBoard.CatanBoard;
import de.dhbw.catanBoard.hexGrid.IntTupel;
import de.dhbw.catanBoard.hexGrid.Node;
import de.dhbw.frontEnd.board.HexTile;
import de.dhbw.gameController.GameController;
import de.dhbw.player.Player;
import de.dhbw.resources.Resources;


/**
  * This Class is administrating the Bandit, which is a subclass of GamePieces.
  *
  * @author Atussa Mehrawari
  * @version 0.1
  *
  */
public class Bandit {
     private IntTupel coords;

      /**
       * Creates a new Bandit at the given location and coordinates.
       * The corresponding field is marked as blocked.
       *
       * @param coords the coordinates of the hex tile
       */
     public Bandit(IntTupel coords) {
         this.coords = coords;
     }
     /**
      * Triggers the Bandit.
      * Changes the location, blocks the new location and the new coordinates.
      *
      * @param newCoords is the new coordinates where the bandit stands.
      * @param board the new location of the Bandit
      * @param activePlayer
      * @param allPlayers
      */
     public void trigger(CatanBoard board, IntTupel newCoords, Player targetPlayer, Player activePlayer, Player[] allPlayers) {
         board.blockHex(this.coords); // alte location wird entblockt
         this.coords = newCoords;
         board.blockHex(this.coords); //neue location wird geblockt

         activePlayer.stealRandomResources(targetPlayer, activePlayer); //überprüfen ob richtig

         for (Player player : allPlayers) {
             //player.banditRemovesResources(); TODO: @Atussa
         }
     }

     public String getType () {
         return "Bandit";
     }
}

//TODO: tragetSpieler muss als parameter angegeben werden,
// zielfeld muss angebeen werden und welchen spieler du etwas klauen willst.

//TODO: Funktion: inventar des spielers aufrufen,
// random resource aufrufen und an den jetztigen spieler weiterschieben.


//TODO: Depo von allen spielern kontrollieren
// wenn spieler mehr <= 7 karten hat, dann häfte der karten abgeben
// Spieler kann aussuchen welche Karten zurück gegeben werden
