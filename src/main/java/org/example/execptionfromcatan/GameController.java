/**
 * @Author: Fabian Weller
 * @Date: 22.05.2025
 * @Version: 0.1
 * GameController Class
 * Handles all logic/interaction between objects for playing the game.
 */
package org.example.execptionfromcatan;

public class GameController {

    private Player[] players;
    private Player[] orderedPlayers;
    private Bank bank;
    private CatanBoard catanBoard;
    private int gameRound;
    private int dice1;
    private int dice2;
    private int vicoryPoints;

    public GameController(int playerAmount, int victoryPoints) {
        this.players = new Player[playerAmount];
        this.bank = new Bank();
        this.catanBoard = new CatanBoard();
        this.gameRound = 0;
        this.vicoryPoints = victoryPoints;
    }

    public void gameStart() {}

    public void mainGame() {}

    public void gameEnd() {}

    public int rollDice() {
        return 0;
    }
}
