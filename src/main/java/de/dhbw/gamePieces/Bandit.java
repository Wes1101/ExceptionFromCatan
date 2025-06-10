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

 public class Bandit extends GamePieces{

     public Bandit(HexTile location) {
         super(location);
     }

     public void triggerBandit

     @Override
     public String getType () {
         return "Bandit";
     }
 }
