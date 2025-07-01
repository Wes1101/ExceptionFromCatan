/**
 * GameController Class
 * Handles all logic/interaction between objects for playing the game.
 *
 * @author: Fabian Weller
 * @version: 0.1
 */
package de.dhbw.gameController;

import java.util.Random;

import de.dhbw.player.Human;
import lombok.Getter;
import lombok.Setter;

import de.dhbw.frontEnd.board.SceneBoard;
import de.dhbw.player.Player;
import de.dhbw.catanBoard.CatanBoard;
import de.dhbw.player.Bank;
import de.dhbw.gamePieces.Bandit;
import de.dhbw.catanBoard.hexGrid.IntTupel;

public class GameController {
    private Player[] players;
    private Bank bank;
    private CatanBoard catanBoard;
    private int gameRound;
    private int dice1;
    private int dice2;
    private Bandit bandit;

    @Getter
    private int victoryPoints;

    @Getter
    private final GameControllerTypes gameControllerType;

    @Getter
    private MajorGameStates majorGameState;

    @Getter
    private MinorGameStates minorGameState;

    @Setter
    private SceneBoard gui;

    /**
     * Creates new GameController
     * Initializes all players, creates a Bank object and CatanBoard. Additionally, sets variables gameRound to 0 and
     * victoryPoints and gameControllerType accordingly
     *
     * @param playerAmount       Amount of players in game
     * @param victoryPoints      Victory points necessary to win a game
     * @param gameControllerType Type, of how the game is played -> How the application should react to interaction
     */
    public GameController(int playerAmount, int victoryPoints, GameControllerTypes gameControllerType) {
        this.players = new Player[playerAmount];
        for (int i = 0; i < playerAmount; i++) {
            players[i] = new Human("", i);
        }

        if (playerAmount > 4) {
            this.catanBoard = new CatanBoard(4);
        } else {
            this.catanBoard = new CatanBoard(3);
        }

        this.bank = new Bank(19, players);       // Sind eig immer 19 -> Konstroktor
        this.gameRound = 0;
        this.victoryPoints = victoryPoints;
        this.gameControllerType = gameControllerType;
        //this.bandit = new Bandit(catanBoard.getDesertCoords());  //TODO: @Johann implement method in catanboard to return IntTuple of Desert location
    }

    /**
     * Returns the number of players in the game.
     *
     * @return the player amount
     */
    public int getPlayerAmount() {
        return this.players.length;
    }

    public void gameStart() {
        majorGameState = MajorGameStates.BEGINNING;
        minorGameState = MinorGameStates.NO_STATE;

        int[] playerDiceNumber = new int[this.players.length];

        for (int i = 0; i < this.players.length; i++) {
            switch (gameControllerType) {
                case LOCAL:
                    gui.activePlayer(this.players[i]);
                    gui.startRollDiceAnimation();
                    break;
                case SERVER:
                    /*   TODO: @David   */
                    break;
                case CLIENT:
                    /* Server calls methods in GameController */
                    break;
            }
            this.rollDice();
            switch (gameControllerType) {
                case LOCAL:
                    gui.showDice(dice1, dice2);
                    break;
                case SERVER:
                    /*   TODO: @David   */
                    break;
                case CLIENT:
                    /* Server calls methods in GameController */
                    break;
            }
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
        Player[] orderedPlayers = this.players;
        for (int i = 0; i < this.players.length; i++) {
            switch (gameControllerType) {
                case LOCAL:
                    gui.activePlayer(this.players[currentIndex]);
                    break;
                case SERVER:
                    /*   TODO: @David   */
                    break;
                case CLIENT:
                    /* Server calls methods in GameController */
                    break;
            }
            minorGameState = MinorGameStates.BUILDING_TRADING_SPECIAL;

            IntTupel coordinatesFirstSettlement;
            IntTupel coordinatesFirstStreet;
            switch (gameControllerType) {
                case LOCAL:
                    //coordinatesFirstSettlement = gui.buildSettlement();
                    //coordinatesFirstStreet = gui.buildStreet();
                    break;
                case SERVER:
                    /*   TODO: @David   */
                    break;
                case CLIENT:
                    /* Server calls methods in GameController */
                    break;
            }

//            this.players[currentIndex].buyFirstSettlement();
//            this.players[currentIndex].buyFirstStreet(); TODO: @Johann

            orderedPlayers[this.players.length - 1 - i] = this.players[currentIndex];
            currentIndex = currentIndex - 1;
            if (currentIndex < 0) {
                currentIndex = this.players.length - 1;
            }
        }
        this.players = orderedPlayers;
        minorGameState = MinorGameStates.NO_STATE;

        //place second settlement and get according resources
        for (Player player : this.players) {
            switch (gameControllerType) {
                case LOCAL:
                    gui.activePlayer(this.players[currentIndex]);
                    break;
                case SERVER:
                    /*   TODO: @David   */
                    break;
                case CLIENT:
                    /* Server calls methods in GameController */
                    break;
            }
            minorGameState = MinorGameStates.BUILDING_TRADING_SPECIAL;

            IntTupel coordinatesSecondSettlement;
            IntTupel coordinatesSecondStreet;
            switch (gameControllerType) {
                case LOCAL:
                    //coordinatesSecondSettlement = gui.buildSettlement();
                    //coordinatesSecondStreet = gui.buildStreet();
                    break;
                case SERVER:
                    /*   TODO: @David   */
                    break;
                case CLIENT:
                    /* Server calls methods in GameController */
                    break;
            }

//            player.buyFirstSettlement();
//            player.buyFirstStreet();

            //TODO: recieve according ressources: @Johann
        }
        minorGameState = MinorGameStates.NO_STATE;

    }

    public void mainGame() {
        majorGameState = MajorGameStates.MAIN;
        while (!checkVictory()) {
            for (Player player : this.players) {
                /*---Roll dice---*/
                minorGameState = MinorGameStates.DICE;

                switch (gameControllerType) {
                    case LOCAL:
                        gui.activePlayer(player);
                        gui.startRollDiceAnimation();
                        break;
                    case SERVER:
                        /*   TODO: @David   */
                        break;
                    case CLIENT:
                        /* Server calls methods in GameController */
                        break;
                    default:
                        break;
                }

                this.rollDice();

                switch (gameControllerType) {
                    case LOCAL:
                        gui.showDice(dice1, dice2);
                        break;
                    case SERVER:
                        /*   TODO: @David   */
                        break;
                    case CLIENT:
                        /* Server calls methods in GameController */
                        break;
                    default:
                        break;
                }

                if (dice1 + dice2 == 7) {
                    minorGameState = MinorGameStates.BANDIT_ACTIVE;

                    IntTupel selectedNewLocation;
                    int playerStolenFromId;
                    switch (gameControllerType) {
                        case LOCAL:
                            selectedNewLocation = gui.activateBandit();
                            //playerStolenFromId = gui.getStolenFromId();
                            break;
                        case SERVER:
                            /*   TODO: @David   */
                            break;
                        case CLIENT:
                            /* Server calls methods in GameController */
                            break;
                    }
                    //bandit.trigger(selectedNewLocation); TODO: @Fabian @Johann Information von welchem Spieler die Resource geklaut wird.
                }

                minorGameState = MinorGameStates.DISTRIBUTE_RESOURCES;
                catanBoard.triggerBoard(dice1 + dice2, bank);

                switch (gameControllerType) {
                    case LOCAL:
                        gui.updatePlayerResources(players);
                        break;
                    case SERVER:
                        /*   TODO: @David   */
                        break;
                    case CLIENT:
                        /* Server calls methods in GameController */
                        break;
                }

                /*---Trade, build and play special cards---*/
                minorGameState = MinorGameStates.BUILDING_TRADING_SPECIAL;

                //TODO: Clarify handling of that part as well
            }
        }
    }

    private void rollDice() {
        Random rand = new Random();
        dice1 = rand.nextInt(6) + 1;
        dice2 = rand.nextInt(6) + 1;
    }

    private boolean checkVictory() {
        for (Player player : this.players) {
        /*if (player.getVictoryPoints() >= this.victoryPoints) {
            return true;
        }*/
        }
        return false;
    }
}
