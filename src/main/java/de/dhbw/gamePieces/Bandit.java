package de.dhbw.gamePieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.dhbw.catanBoard.CatanBoard;
import de.dhbw.catanBoard.hexGrid.IntTupel;
import de.dhbw.catanBoard.hexGrid.Node;
import de.dhbw.frontEnd.board.HexTile;
import de.dhbw.gameController.GameController;
import de.dhbw.player.Bank;
import de.dhbw.player.Player;
import de.dhbw.resources.Resources;
import lombok.extern.slf4j.Slf4j;

/**
 * This class manages the Bandit, which is a game piece in Catan.
 * The Bandit can move across the board, steal resources, and force players to discard cards.
 *
 * @author Atussa Mehrawari
 * @version 0.1
 *
 */
@Slf4j
public class Bandit {
    // Current coordinates of the Bandit (on a hex tile)
    private IntTupel coords;

    /**
     * Creates a new Bandit at the given coordinates.
     * The corresponding tile is marked as blocked (no resource production).
     *
     * @param coords the coordinates of the hex tile where the Bandit is initially placed
     */
    public Bandit(IntTupel coords) {
        this.coords = coords;
    }

    /**
     * Triggers the Bandit's action:
     * - Moves the Bandit to a new tile
     * - Unblocks the previous tile and blocks the new one
     * - The active player steals one random resource from the target player
     * - All players with more than 7 cards must discard half of them
     *
     * @param newCoords the new coordinates where the Bandit is moved
     * @param board reference to the game board
     * @param targetPlayer the player from whom a resource is stolen
     * @param activePlayer the current player performing the action
     * @param allPlayers array of all players in the game
     * @param bank the bank to which discarded resources are returned
     */
    public void trigger(CatanBoard board, IntTupel newCoords, Player targetPlayer, Player activePlayer, Player[] allPlayers, Bank bank) {
        log.info("trigger bandit...");
        board.blockHex(this.coords); // unblock old location
        this.coords = newCoords;
        board.blockHex(this.coords); // block new location

        activePlayer.stealRandomResources(targetPlayer, activePlayer); // steal one resource from the target player

        for (Player player : allPlayers) {
            player.banditRemovesResources(7, bank); // players with >7 resources discard half
        }
        log.info("bandit complete!");
    }

    /**
     * Returns the type of this game piece.
     *
     * @return the string "Bandit"
     */
    public String getType () {
        return "Bandit";
    }
}