package de.dhbw.gamePieces;

import de.dhbw.frontEnd.board.HexTile;
import lombok.Getter;
 /**
  * This Class is administrating the Bandit, which is a subclass of GamePieces.
  *
  * @author Atussa Mehrawari
  * @version 0.1
  *
  */

 public class Bandit {
     private HexTile location;
     private int [] coords;

    
     public String getType () {
         return "Bandit";
     }
 }
