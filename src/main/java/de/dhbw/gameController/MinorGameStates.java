package de.dhbw.gameController;

/**
 * Represents detailed sub-states within a {@link MajorGameStates} phase of the game.
 * These help track which specific action or step the game is currently processing.
 * <ul>
 *     <li>{@code DICE} – Waiting for or processing a dice roll.</li>
 *     <li>{@code BANDIT_ACTIVE} – The bandit has been triggered (usually after rolling a 7).</li>
 *     <li>{@code DISTRIBUTE_RESOURCES} – Distributing resources based on dice results.</li>
 *     <li>{@code BUILDING_TRADING_SPECIAL} – Player can build, trade, or play special cards.</li>
 *     <li>{@code NO_STATE} – No specific sub-state is active (idle or transitional).</li>
 * </ul>
 */
public enum MinorGameStates {
    DICE,
    BANDIT_ACTIVE,
    DISTRIBUTE_RESOURCES,
    BUILDING_TRADING_SPECIAL,
    NO_STATE
}
