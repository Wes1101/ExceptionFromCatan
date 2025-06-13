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
     private Player targetPlayer;
     private Player activePlayer;

     //TODO: speichern der coords als referenz

      /**
       * Creates a new Bandit at the given location and coordinates.
       * The corresponding field is marked as blocked.
       *
       * @param banditLocation the hex tile where the bandit is placed
       * @param coords the coordinates of the hex tile
       */
     public Bandit(IntTupel coords) {
         this.coords = coords;
     }
     //TODO: methode trigger um bandit aufzurufen
     // (es wurde eine 7 gewürfelt, gamecontroler ruft Bandit auf.
     // braucht meine Zielposition Koordinaten als tupel

     //TODO: wenn position verlassen wird alte Position entblocken, neue position blocken.

     /**
      * Triggers the Bandit.
      * Changes the location, blocks the new location and the new coordinates.
      *
      * @param newCoords is the new coordinates where the bandit stands.
      * @param newLocation the new location of the Bandit
      */
     public void trigger(CatanBoard board, IntTupel newCoords, Player targetPlayer, Player activePlayer, Player[] allPlayers) {
         board.blockHex(this.coords); // alte location wird entblockt
         this.coords = newCoords;
         board.blockHex(this.coords); //neue location wird geblockt

         stealRandomResources(targetPlayer,activePlayer);

         // schleife die durch alle player durchgeht for loop
         for (Player player : allPlayers) {
             player.banditRemovesResources();
         }
         else if
     }
     //TODO: tragetSpieler muss als parameter angegeben werden,
     // zielfeld muss angebeen werden und welchen spieler du etwas klauen willst.

      //TODO: Funktion: inventar des spielers aufrufen,
      // random resource aufrufen und an den jetztigen spieler weiterschieben.

     private void stealRandomResources(Player from, Player to) {
         List<Resources> resources = new ArrayList<>();

         // checkt die Ressoucen des Spielers und schaut, ob der Spieler Res besitzt.
         // Wenn nicht gibt es 0 zurück
         for (Resources res : Resources.values()) {
             Integer count = from.getResources().getOrDefault(res, 0);

             // es sollen nur ressourcen geklaut werden, die existieren.
             // Wenn Res >0 dann wird diese zu der liste available hinzugefügt.
             if (count > 0) {
                 resources.add(res);
             }
         }

         // Falls Res leer - abbrechen
         if (resources.isEmpty()) {
             return;
         }

         // Zufäälige Ressource wählen
         Random r = new Random();
         Resources chosen = resources.get(r.nextInt(resources.size()));

         // Ressoucen Transfer
         from.removeResources(chosen, 1,to);

     }
     //TODO: Depo von allen spielern kontrollieren
     // wenn spieler mehr <= 7 karten hat, dann häfte der karten abgeben
     // Spieler kann aussuchen welche Karten zurück gegeben werden



     public String getType () {
         return "Bandit";
     }
 }
