/**
 * @Author: Fabian Weller
 * @Date: 22.05.2025
 * @Version: 0.1
 * GameController Class
 * Handles all logic/interaction between objects for playing the game.
 */
package de.dhbw.gameController;

import java.util.Random;
import de.dhbw.player.Player;
import de.dhbw.catanBoard.CatanBoard;
import de.dhbw.bank.Bank;

public class GameController {
  private final Player[] players;
  private Player[] orderedPlayers;
  private Bank bank;
  private CatanBoard catanBoard;
  private int gameRound;
  private int dice1;
  private int dice2;
  private int victoryPoints;

  public GameController(int playerAmount, int victoryPoints) {
    this.players = new Player[playerAmount];
    for (int i = 0; i < playerAmount; i++) {
        players[i] = new Player();
    }
    this.orderedPlayers = new Player[playerAmount];
    this.bank = new Bank(19);       // Sind eig immer 19 -> Konstroktor
    //this.catanBoard = new CatanBoard();          //Macht sinn abstrakt zu haben??
    this.gameRound = 0;
    this.victoryPoints = victoryPoints;
  }

  public void gameStart() {
    int[] playerDiceNumber = new int[this.players.length];

    for (int i = 0; i < this.players.length; i++) {
        /*
        gui.activePlayer(this.players[i]);
        gui.startRollDiceAnimation();
         */
        this.rollDice();
        //gui.showDice(dice1, dice2)
        playerDiceNumber[i] = (this.dice1 + this.dice2);
    }

    //check highest number
    int highestNumber = 0;
    int highestNumberIndex = 0;
    for (int i = 0; i < playerDiceNumber.length; i++) {
        if (playerDiceNumber[i] > highestNumber) {
            highestNumber = playerDiceNumber[i];
            highestNumberIndex = i;
        }
    }

    //place first settlement
    int currentIndex = highestNumberIndex;
    for (int i = 0; i < this.players.length; i++) {
        /*
        gui.activePlayer(this.players[currentIndex]);
         */

        //TODO: Build settlement and street

        this.orderedPlayers[this.players.length - 1 - i] = this.players[currentIndex];
        currentIndex = currentIndex - 1;
        if (currentIndex < 0) {
            currentIndex = this.players.length - 1;
        }
    }

    //place second settlement and get according resources
    for (Player player : this.orderedPlayers) {
      /*
      gui.activePlayer(this.players[currentIndex]);
      */

      //TODO: Build settlement and street
      //TODO: recieve according ressources
    }

  }

  public void mainGame() {
    while (!checkVictory()){
        for (Player player : this.orderedPlayers) {
              /*---Roll dice---*/
          /*
          gui.activePlayer(this.players[i]);
          gui.startRollDiceAnimation();
          */
          this.rollDice();
          //gui.showDice(dice1, dice2)

          //TODO: Clarify bandit handling

          catanBoard.triggerBoard(dice1+dice2);

          //TODO: Clarify how gui will be notified of what player has now how may resources

              /*---Trade, build and play special cards---*/

          //TODO: Clarify handling of that part as well
        }
    }
  }

  public void gameEnd() {
    //TODO: Clarify what this method should do
  }

  private void rollDice() {
    Random rand = new Random();
    dice1 = rand.nextInt(6) + 1;
    dice2 = rand.nextInt(6) + 1;
  }

  private boolean checkVictory() {
    for (Player player : this.orderedPlayers) {
        /*if (player.getVictoryPoints() >= this.victoryPoints) {
            return true;
        }*/
    }
    return false;
  }
}
