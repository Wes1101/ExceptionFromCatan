 package de.dhbw.gamePieces;

 import de.dhbw.catanBoard.hexGrid.IntTupel;

 /**
  * This Class is administrating the Bandit, which is a subclass of GamePieces.
  *
  * @author Atussa Mehrawari
  * @version 0.1
  *
  */

 public class Bandit{
     private IntTupel coords;
     public Bandit(IntTupel location) {
         this.coords = location;
     }

     public String getType () {
         return "Bandit";
     }

     /**
      * Triggers the Bandit.
      * Changes the location, blocks the new location and the new coordinates.
      *
      * @param newCoords is the new coordinates where the bandit stands.
      */
     public void trigger(IntTupel newCoords) {
         this.coords = newCoords;
     }
 }
