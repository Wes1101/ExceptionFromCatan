package de.dhbw.gameController;

import de.dhbw.catanBoard.hexGrid.IntTupel;
import de.dhbw.player.Player;

/**
 * A simple tuple that combines a {@link Player} with a board coordinate.
 * <p>
 * Typically used in scenarios where a player interaction is tied to a specific
 * hex-grid location — for example, when the bandit is moved and a player is chosen
 * to be robbed.
 * </p>
 *
 * @param intTupel The coordinate on the board (typically a hex or node location)
 * @param player   The player involved at that location
 */
public record PlayerTupelVar(IntTupel intTupel, Player player) {}
