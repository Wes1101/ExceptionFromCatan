/**
 * GameController Class
 * <p>
 * Handles all logic/interaction between objects for playing the game. According to the gameControllerType, the required
 * actions are either send to the gui or the server.
 * </p>
 *
 * @author: Fabian Weller
 * @version: 0.1
 */
package de.dhbw.gameController;

import java.awt.*;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import de.dhbw.gameRules.Rules;
import de.dhbw.resources.Resources;
import javafx.application.Platform;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import de.dhbw.catanBoard.CatanBoard;
import de.dhbw.catanBoard.hexGrid.IntTupel;
import de.dhbw.frontEnd.board.SceneBoardController;
import de.dhbw.gamePieces.Bandit;
import de.dhbw.player.Bank;
import de.dhbw.player.Player;

@Slf4j
public class GameController {
    @Getter
    private Player[] players;
    private final Bank bank;
    @Getter
    private final CatanBoard catanBoard;
    private final Rules rules;
    private int gameRound;
    private int dice1;
    private int dice2;
    private Bandit bandit;
    private final boolean syso;

    @Getter
    private int victoryPoints;

    @Getter
    private final GameControllerTypes gameControllerType;

    @Getter
    private MajorGameStates majorGameState;

    @Getter
    private MinorGameStates minorGameState;

    @Setter
    private SceneBoardController gui;

    /**
     * Creates new GameController
     * <p>
     * Initializes all players, creates a Bank object and CatanBoard. Additionally, sets variables gameRound to 0 and
     * victoryPoints and gameControllerType accordingly
     * </p>
     *
     * @param playerAmount       Amount of players in game
     * @param victoryPoints      Victory points necessary to win a game
     * @param gameControllerType Type, of how the game is played -> How the class should react to interaction
     */
    public GameController(int playerAmount, int victoryPoints, GameControllerTypes gameControllerType, boolean syso) {
        log.debug("GameController created with type {}", gameControllerType);
        this.players = new Player[playerAmount];
        for (int i = 0; i < playerAmount; i++) {
            players[i] = new Player(i);
        }

        if (playerAmount > 4) {
            this.catanBoard = new CatanBoard(4);
            this.bank = new Bank(25, players);
        } else {
            this.catanBoard = new CatanBoard(3);
            this.bank = new Bank(19, players);
        }

        this.rules = new Rules(victoryPoints);
        this.gameRound = 0;
        this.victoryPoints = victoryPoints;
        this.gameControllerType = gameControllerType;
        this.bandit = new Bandit(catanBoard.getDesertCoords());
        this.catanBoard.blockHex(catanBoard.getDesertCoords());
        this.syso = syso;
    }

    /**
     * Starts a new game
     * <p>
     * Starting a new game by creating all players, rolling the dices for all players and reordering the player array
     * based on the highest number. Then, all players (in order) can place their first two settlements and streets.
     * Also, they receive the according resources based on their second settlement. The current states are stored in
     * majorGameState and minorGameState and accessible via getters.
     * </p>
     */
    public void gameStart() {
        log.info("Starting game...");
        majorGameState = MajorGameStates.BEGINNING;
        minorGameState = MinorGameStates.NO_STATE;

        this.initGui();

        int[] playerDiceNumber = new int[this.players.length];
        log.info("Creating all players...");
        for (int i = 0; i < this.players.length; i++) {

            //this.activePlayer(this.players[i]);

            this.rollDice();

            //this.showDice(dice1, dice2);

            playerDiceNumber[i] = (this.dice1 + this.dice2);
        }

        log.info("...done. Sorting players...");


        //check highest number
        int highestNumber = 0;
        int highestNumberIndex = 0;
        for (int i = 0; i < playerDiceNumber.length; i++) {
            if (playerDiceNumber[i] > highestNumber) {
                highestNumber = playerDiceNumber[i];
                highestNumberIndex = i;
            }
        }

        log.info("...done. Placing first settlements and streets");
        //place first settlement
        int currentIndex = highestNumberIndex;
        Player[] orderedPlayers = this.players.clone();
        for (int i = 0; i < this.players.length; i++) {
            this.activePlayer(this.players[currentIndex]);

            minorGameState = MinorGameStates.BUILDING_TRADING_SPECIAL;

            int coordinatesFirstSettlement;
            do {
                coordinatesFirstSettlement = getCoordinatesFirstSettlement(this.players[currentIndex].getId());

            } while (!rules.buildFirstSettlement(catanBoard, coordinatesFirstSettlement, this.players[currentIndex]));
            log.info("*Building first settlement " + this.players[currentIndex]);
            this.players[currentIndex].buyFirstSettlement(coordinatesFirstSettlement, bank, catanBoard);

            this.updatePlayerResources(players);

            IntTupel coordinatesFirstStreet;
            do {
                coordinatesFirstStreet = getCoordinatesFirstStreet(this.players[currentIndex].getId());
            } while (!rules.buildFirstStreet(catanBoard, coordinatesFirstStreet.q(), coordinatesFirstStreet.r(), this.players[currentIndex]));
            int node1 = coordinatesFirstStreet.q();
            int node2 = coordinatesFirstStreet.r();
            log.info("*Building first street " + this.players[currentIndex]);
            this.players[currentIndex].buyFirstStreet(node1, node2, bank, catanBoard);

            this.updatePlayerResources(players);

            orderedPlayers[this.players.length - 1 - i] = this.players[currentIndex];
            currentIndex = currentIndex - 1;
            if (currentIndex < 0) {
                currentIndex = this.players.length - 1;
            }
        }
        this.players = orderedPlayers;
        minorGameState = MinorGameStates.NO_STATE;

        gameRound++;

        log.info("...done. Placing second settlements and streets...");

        //place second settlement and get according resources
        for (Player player : this.players) {
            this.activePlayer(player);

            minorGameState = MinorGameStates.BUILDING_TRADING_SPECIAL;

            int coordinatesSecondSettlement;
            do {
                coordinatesSecondSettlement = getCoordinatesFirstSettlement(player.getId());

            } while (!rules.buildFirstSettlement(catanBoard, coordinatesSecondSettlement, player));
            log.info("*Building second settlement " + player);
            player.buyFirstSettlement(coordinatesSecondSettlement, bank, catanBoard);
            player.getNodeResources(coordinatesSecondSettlement, bank, catanBoard);

            this.updatePlayerResources(players);

            IntTupel coordinatesSecondStreet = new IntTupel(-100, -100);;
            do {
                coordinatesSecondStreet = getCoordinatesFirstStreet(player.getId());
            } while (!rules.buildFirstStreet(catanBoard, coordinatesSecondStreet.q(), coordinatesSecondStreet.r(), player));
            int node1 = coordinatesSecondStreet.q();
            int node2 = coordinatesSecondStreet.r();
            log.info("*Building first street " + player);
            player.buyFirstStreet(node1, node2, bank, catanBoard);

            this.updatePlayerResources(players);
        }
        minorGameState = MinorGameStates.NO_STATE;

        gameRound ++;
        log.info("...done!");

    }

    private void initGui() {
        if (this.gameControllerType == GameControllerTypes.CLIENT ||
                this.gameControllerType == GameControllerTypes.LOCAL) {
            log.debug("initializing gui from GameController");
            gui.setGameController(this);
            gui.setPlayerAmount(this.players.length);
        }
    }

    /**
     * Starts the main game logic
     * <p>
     * Every loop starts with a check if someone already has enough victory points to win the game. Then the dice are
     * rolled and if a seven was rolled the bandit is activated. After that the resources are distributed. Then the
     * player gets the option to build, trade or use special cards. This repeats for all players and the loop starts
     * again.
     * </p>
     */
    public void mainGame() {
        log.info("Starting main game...");
        majorGameState = MajorGameStates.MAIN;
        while (!checkVictory()) {
            for (Player player : this.players) {
                /*---Roll dice---*/
                minorGameState = MinorGameStates.DICE;

                this.activePlayer(player);

                this.rollDice();

                this.showDice(dice1, dice2);

                if (dice1 + dice2 == 7) {
                    log.info("7 was rolled! Activating bandit...");
                    minorGameState = MinorGameStates.BANDIT_ACTIVE;

                    PlayerTupelVar robbedPlayerLocation = this.activateBandit();
                    IntTupel selectedNewLocation = robbedPlayerLocation.intTupel();
                    Player robbedPlayer = robbedPlayerLocation.player();

                    bandit.trigger(catanBoard, selectedNewLocation, robbedPlayer, player, players, bank);
                } else {
                    log.info("Distributing all resources...");
                    minorGameState = MinorGameStates.DISTRIBUTE_RESOURCES;
                    catanBoard.triggerBoard(dice1 + dice2, bank);
                }

                this.updatePlayerResources(players);

                log.info("Entering building, trading, special cards phase");
                /*---Trade, build and play special cards---*/
                minorGameState = MinorGameStates.BUILDING_TRADING_SPECIAL;

                this.awaitFinishTurnClicked();

            }
            gameRound++;
        }
        log.info("Someone won the game after {} Rounds. Terminating gameController...", gameRound);
    }

    /**
     * Rolls the dice
     * <p>
     * Rolling the dice by getting new random ints and storing them in the local variables dice1 and dice 2
     * </p>
     */
    private void rollDice() {
        log.debug("rolling the dice:");
        Random rand = new Random();
        this.dice1 = rand.nextInt(6) + 1;
        this.dice2 = rand.nextInt(6) + 1;
        log.debug(dice1 + " " + dice2);
    }

    /**
     * Checks for a victory
     * <p>
     * Checks victory by iterating through the whole players array and if one player has enough victory points true is
     * returned. If no one has enough points false is returned.
     * </p>
     */
    private boolean checkVictory() {
        log.debug("checking victory");
        for (Player player : this.players) {
            if (this.rules.checkWin(player)) {
                log.debug(player + " wins!");
                return true;
            }
        }
        log.debug("no one was good enough so far");
        return false;
    }

    /**
     * Returns the number of players in the game.
     *
     * @return the player amount
     */
    public int getPlayerAmount() {
        log.debug("ahhh, someone wants to know how many players are currently playing...");
        return this.players.length;
    }

    /**
     * Tells the gui the active player
     *
     * @param player Player to be sent to gui
     */
    public void activePlayer(Player player) {
        if (this.gameControllerType == GameControllerTypes.CLIENT ||
                this.gameControllerType == GameControllerTypes.LOCAL) {
            log.debug("im just a client and was told to tell the gui the active player");
            if (!this.syso) {
                Platform.runLater(() -> {
                    gui.setactivePlayer(player, players);
                });
            }
        } else if (this.gameControllerType == GameControllerTypes.SERVER) {
            /*   TODO: @David   */
        } else {
            log.warn("activePlayer() was called but GameControllerType is {}", this.gameControllerType);
        }
    }

    /**
     * Starts the roll dice animation in the gui
     */
    private void rollDiceAnimation() {
        if (this.gameControllerType == GameControllerTypes.CLIENT ||
                this.gameControllerType == GameControllerTypes.LOCAL) {
            log.debug("im just a client and was told to tell the gui to start the rollDiceAnimation");
            if (!this.syso) {
                //Platform.runLater(() -> ui.updateScore(score));
                gui.startRollDiceAnimation();
            }
        } else if (this.gameControllerType == GameControllerTypes.SERVER) {
            /*   TODO: @David   */
        } else {
            log.warn("rollDiceAnimation() was called but GameControllerType is {}", this.gameControllerType);
        }
    }

    /**
     * Shows the transferred dice values in the gui
     *
     * @param dice1 Value of dice 1
     * @param dice2 Value of dice 2
     */
    public void showDice(int dice1, int dice2) {
        if (this.gameControllerType == GameControllerTypes.CLIENT ||
                this.gameControllerType == GameControllerTypes.LOCAL) {
            log.debug("Who wants to see the dice? You want to see the dice: {} {}", dice1, dice2);
            if (!this.syso) {
                gui.setDiceResult(dice1, dice2);
                this.rollDiceAnimation();
            }
        } else if (this.gameControllerType == GameControllerTypes.SERVER) {
            /*   TODO: @David   */
        } else {
            log.warn("showDice() was called but GameControllerType is "  + this.gameControllerType);
        }
    }

    /**
     * Gets the coordinates for the first (two) settlements
     *
     * @return Returns the coordinates of the first settlement
     */
    public Integer getCoordinatesFirstSettlement(int playerId) {
        if (this.gameControllerType == GameControllerTypes.CLIENT ||
                this.gameControllerType == GameControllerTypes.LOCAL) {
            log.debug("What, you want the location of the first settlement?");
            try {
                log.info("You have 30 seconds to click on a Settlement location");
                return gui.waitForSettlementClick(playerId)
                        .orTimeout(30, TimeUnit.SECONDS)
                        .join();  // Blocks until click or timeout
            } catch (Exception e) {
                log.error("Timeout or invalid selection");
                return -1;  // or handle however you want
            }

        } else if (this.gameControllerType == GameControllerTypes.SERVER) {
            /*   TODO: @David   */
        } else {
            log.warn("getCoordinatesFirstSettlement() was called, but GameControllerType is {}",
                    this.gameControllerType);
        }
        return null;
    }

    /**
     * Gets the coordinates for the first (two) streets
     *
     * @return Returns the coordinates of the first street
     */
    public IntTupel getCoordinatesFirstStreet(int playerId) {
        if (this.gameControllerType == GameControllerTypes.CLIENT ||
                this.gameControllerType == GameControllerTypes.LOCAL) {
            log.debug("What, you want the location of the first street?");

            try {
                log.info("You have 30 seconds to click on a street");
                return gui.waitForStreetClick(playerId)
                        .orTimeout(30, TimeUnit.SECONDS)
                        .join();  // Blocks until click or timeout
            } catch (Exception e) {
                log.error("Timeout or invalid selection");
                return null;  // or handle however you want
            }

        } else if (this.gameControllerType == GameControllerTypes.SERVER) {
            /*   TODO: @David   */
        } else {
            log.warn("getCoordinatesFirstStreet() was called, but GameControllerType is {}",
                    this.gameControllerType);
        }
        return null;
    }

    /**
     * Updates the players in the gui
     *
     * @param players Array of players to be updated
     */
    public void updatePlayerResources(Player[] players) {
        if (this.gameControllerType == GameControllerTypes.CLIENT ||
                this.gameControllerType == GameControllerTypes.LOCAL) {
            log.debug("Another update for the players resources, seriously?");
            if (!this.syso) {
                Platform.runLater(() -> {
                    gui.updatePlayerResources(players);
                    gui.updateBoard(catanBoard);
                });
            }
        } else if (this.gameControllerType == GameControllerTypes.SERVER) {
            /*   TODO: @David   */
        } else {
            log.warn("updatePlayerResources() was called but GameControllerType is {}", this.gameControllerType);
        }
    }

    /**
     * Activates the Bandit and returns the new location of the bandit and the player who is robbed
     *
     * @return A Tuple and Player. The tuple is the new bandit location and Player is the robbed player
     */
    public PlayerTupelVar activateBandit (){
        if (this.gameControllerType == GameControllerTypes.CLIENT ||
                this.gameControllerType == GameControllerTypes.LOCAL) {
            log.debug("Grrrr... Grrrr... the bandit was activated");
            try {
                log.info("You have 2 minutes to click on a new Bandit location and select a player to rob");
                return gui.waitForBanditLoctionAndPlayer(players)
                        .orTimeout(2, TimeUnit.MINUTES)
                        .join();  // Blocks until click or timeout
            } catch (Exception e) {
                log.error("Timeout or invalid");
                return null;  // or handle however you want
            }
        } else if (this.gameControllerType == GameControllerTypes.SERVER) {
            /*   TODO: @David   */
        } else {
            log.warn("activateBandit() was called but GameControllerType is {}", this.gameControllerType);
        }
        return null;
    }

    public String awaitFinishTurnClicked() {
        if (this.gameControllerType == GameControllerTypes.CLIENT ||
                this.gameControllerType == GameControllerTypes.LOCAL) {
            log.debug("await finish turn called");
            try {
                log.info("You have 2 minutes to click on a Settlement location");
                return gui.waitForFinishTurnClick()
                        .orTimeout(2, TimeUnit.MINUTES)
                        .join();  // Blocks until click or timeout
            } catch (Exception e) {
                log.error("Timeout or invalid");
                return "ERROR";  // or handle however you want
            }

        } else if (this.gameControllerType == GameControllerTypes.SERVER) {
            /*   TODO: @David   */
        } else {
            log.warn("getCoordinatesFirstSettlement() was called, but GameControllerType is {}",
                    this.gameControllerType);
        }
        return null;
    }

    public void buildSettlement(int nodeId, Player activePlayer) {
        if (this.gameControllerType == GameControllerTypes.LOCAL) {
            if (rules.buildSettlement(catanBoard, nodeId, activePlayer)) {
                activePlayer.buildSettlement(nodeId, bank, activePlayer, catanBoard);
                log.info("Build settlement successful");
            }
            else {
                log.warn("build Settlement was called but rule was not successful");
            }

        }
        //TODO: Necessary for Server and Client????
    }

    public void buildStreet(IntTupel location, Player activePlayer) {
        if (this.gameControllerType == GameControllerTypes.LOCAL) {
            if (rules.buildStreet(catanBoard, location.q(), location.r(), activePlayer)) {
                activePlayer.buildStreet(location.q(), location.r(), bank, activePlayer, catanBoard);
                log.info("Build street successful");
            }
            else {
                log.warn("build Street was called but rule was not successful");
            }

        }
        //TODO: Necessary for Server and Client????
    }

    public void buildCity(int nodeId, Player activePlayer) {
        if (this.gameControllerType == GameControllerTypes.LOCAL) {
            if (rules.buildCity(catanBoard, nodeId, activePlayer)) {
                activePlayer.buildCity(nodeId, bank, activePlayer, catanBoard);
                log.info("Build street successful");
            }
            else {
                log.warn("build City was called but rule was not successful");
            }
        }
        //TODO: Necessary for Server and Client????
    }
}
