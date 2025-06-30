package de.dhbw.gamePieces;

import de.dhbw.frontEnd.board.HexTile;

 /**
  * This Class is administrating the Bandit, which is a subclass of GamePieces.
  *
  * @author Atussa Mehrawari
  * @version 0.1
  *
  */

 // speichern der coords als referenz

 // methode trigger um bandit aufzurufen   (es wurde eine 7 gewürfelt, gamecontroler ruft mich auf. braucht meine
 // Zielposition Koordinaten als tupel
 // wenn position verlassen wird feld entblocken, neue position blocken.
 // tragetSpieler muss als parameter angegeben werden, zielfeld muss angebeen werden und welchen spieler du etwas klauen willst.
 // Funktion: inventar des spielers aufrufen, random resopurce aufrufen und an den jetztigen spieler weiterschieben.

 // Depo von allen spielern kontrollieren und wenn spieler mehr <= 8 karten hat, dann häfte der karten abgeben
 public class Bandit {
     private HexTile location;
     private int [] coords;

    
     public String getType () {
         return "Bandit";
     }
 }
