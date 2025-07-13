
/**
 * GameUI Interface
 * <p>
 * Defines the communication bridge from the GameController to the UI.
 * This interface ensures consistent user interaction for game mechanics such as
 * building, dice rolling, and player updates.
 * </p>
 */

package de.dhbw.frontEnd.board;

import de.dhbw.catanBoard.hexGrid.IntTupel;
import de.dhbw.player.Player;

public interface GameUI {

    /**
     * Passes the currently active player to the UI.
     * This allows the UI to display whose turn it is.
     *
     * @param player the currently active player
     * @param players the array of all players in the game
     */
    void setactivePlayer(Player player, Player[] players);

    /**
     * Starts a dice roll animation.
     * Can be implemented to provide visual feedback during rolling.
     */
    void startRollDiceAnimation();

    /**
     * Displays the outcome of a dice roll.
     *
     * @param dice1 value of the first dice
     * @param dice2 value of the second dice
     */
    void showDice(int dice1, int dice2);

    /**
     * Updates the UI to reflect current resources and states of all players.
     */
    void updatePlayerResources();

    /**
     * Triggers the UI to allow the player to place the bandit.
     *
     * @return the coordinates where the bandit should be placed
     */
    IntTupel activateBandit();

    /**
     * Prompts the player to choose another player to rob.
     *
     * @param players an array of players eligible to be robbed
     * @return the selected player to rob
     */
    Player getRobbedPlayer(Player[] players);
}
