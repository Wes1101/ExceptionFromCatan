package de.dhbw.gamePieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

 //TODO: speichern der coords als referenz

 //TODO: methode trigger um bandit aufzurufen (es wurde eine 7 gewürfelt, gamecontroler ruft mich auf. braucht meine
 // Zielposition Koordinaten als tupel

 //TODO: wenn position verlassen wird feld entblocken, neue position blocken.

 //TODO: tragetSpieler muss als parameter angegeben werden, zielfeld muss angebeen werden und welchen spieler du etwas klauen willst.

 //TODO: Funktion: inventar des spielers aufrufen, random resopurce aufrufen und an den jetztigen spieler weiterschieben.

 //TODO: Depo von allen spielern kontrollieren und wenn spieler mehr <= 8 karten hat, dann häfte der karten abgeben
   /**
      * Safes the coords a a reference.
      */
public class Bandit {
     private HexTile banditLocation;
     private IntTupel coords;
     private boolean blocked;
     private Player targetPlayer;
     private Player activePlayer;


      /**
       * Creates a new Bandit at the given location and coordinates.
       * The corresponding field is marked as blocked.
       *
       * @param banditLocation the hex tile where the bandit is placed
       * @param coords the coordinates of the hex tile
       */
     public Bandit(HexTile banditLocation, IntTupel coords) {
         this.banditLocation = banditLocation;
         this.coords = coords;
         this.blocked = true;
     }
     /**
      * Triggers the Bandit.
      * Changes the location, blocks the new location and the new coordinates.
      *
      * @param newCoords is the new coordinates where the bandit stands.
      * @param newLocation the new location of the Bandit
      */
     public void trigger(HexTile newLocation, IntTupel newCoords) {
         this.banditLocation.setBlocked(false);
         newLocation.setBlocked(true);
         this.banditLocation = newLocation;
         this.coords = newCoords;
     }

     private void stealRandomResources(Player from, Player to) {
         List<Resource> resources = new ArrayList<>();
         
         

     }
     public String getType () {
         return "Bandit";
     }
 }
