/**
 * GameUI Interface
 * Used to define communication from GameController to GameUI
 */

package de.dhbw.frontEnd.board;

import de.dhbw.catanBoard.hexGrid.IntTupel;
import de.dhbw.player.Player;

public interface GameUI {
    /**
     * Method to pass active player to UI. Needed to show in the UI whose currently  playing via player id or name
     *
     * @param player Currently active player
     */
    void setactivePlayer(Player player);

    /**
     * Starts the dice roll animation.
     * This is optional and can be implemented for visual effect.
     */
    void startRollDiceAnimation();

    /**
     * Displays the result of a die roll.
     *
     * @param dice1 the value of the first dice
     * @param dice2 the value of the second dice
     */
    void showDice (int dice1, int dice2);

    /**
     * Prompts the player to select a location to build a settlement.
     * The UI should display an instruction like "Please select a node to build a settlement."
     *
     * @return the node ID where the settlement should be built
     */
    int buildSettlement();

    /**
     * Prompts the player to select a location to build a street.
     * The UI should display an instruction like "Please select a location to build a street."
     *
     * @return the two adjacent nodes of where the street should be built
     */
    IntTupel buildStreet();

    /**
     * Prompts the player to select a location to build a city.
     * The UI should display an instruction like "Please select a node to build a city."
     *
     * @return the node ID where the city should be built
     */
    int buildCity();

    /**
     * Updates the UI to reflect the latest resource values and states of all players.
     *
     * @param players an array of all players with their updated states
     */
    void updatePlayerResources(Player[] players);

    /**
     * Activates the bandit placement phase in the UI.
     * Should return the new location for the bandit.
     *
     * @return the coordinates (as an IntTupel) of the new bandit location
     */
    IntTupel activateBandit();

    /**
     * Prompts the user to select which player to rob.
     * The UI should allow choosing from a list of players.
     *
     * @param players an array of all possible players to be robbed
     * @return the player who was selected to be robbed
     */
    Player getRobbedPlayer(Player[] players);
}
