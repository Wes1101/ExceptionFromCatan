package de.dhbw.gamePieces;

import java.util.List;

import de.dhbw.catanBoard.CatanBoard;
import de.dhbw.catanBoard.hexGrid.IntTupel;
import de.dhbw.player.Bank;
import de.dhbw.player.Player;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Represents the Bandit game piece in Catan.
 * <p>
 * The Bandit can block resource production on a tile and steal resources from other players.
 * It is activated when a player rolls a 7 or uses a Knight card.
 * </p>
 */
@Slf4j
public class Bandit {

    /** Current coordinates of the Bandit on the board (hex tile). */
    @Getter
    private IntTupel coords;

    /**
     * Constructs a Bandit and places it on the specified tile.
     * <p>
     * The tile is immediately marked as blocked, preventing resource production.
     * </p>
     *
     * @param coords the coordinates where the Bandit is initially placed
     */
    public Bandit(IntTupel coords) {
        this.coords = coords;
    }

    /**
     * Executes the Bandit's effect:
     * <ul>
     *   <li>Moves the Bandit to a new tile (blocking that tile, unblocking the old one)</li>
     *   <li>The active player steals one random resource from the target player</li>
     *   <li>All players with more than 7 cards must discard half</li>
     * </ul>
     *
     * @param newCoords     new location for the Bandit
     * @param board         the game board
     * @param targetPlayer  the player being robbed
     * @param activePlayer  the player activating the Bandit
     * @param allPlayers    array of all players in the game
     * @param bank          reference to the bank for returning discarded resources
     */
    public void trigger(CatanBoard board,
                        IntTupel newCoords,
                        Player targetPlayer,
                        Player activePlayer,
                        Player[] allPlayers,
                        Bank bank) {
        log.info("Triggering bandit action...");
        board.blockHex(this.coords); // Unblock old tile
        this.coords = newCoords;
        board.blockHex(this.coords); // Block new tile

        activePlayer.stealRandomResources(targetPlayer, activePlayer); // Rob resource

        for (Player player : allPlayers) {
            player.banditRemovesResources(7, bank); // Discard if >7 cards
        }

        log.info("Bandit action completed.");
    }

    /**
     * Returns the type of this game piece.
     *
     * @return "Bandit"
     */
    public String getType() {
        return "Bandit";
    }
}
