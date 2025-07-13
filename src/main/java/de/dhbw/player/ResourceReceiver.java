package de.dhbw.player;

import de.dhbw.resources.Resources;

/**
 * Represents any entity in the game that can receive resources.
 * <p>
 * This interface is implemented by classes such as {@link Player} and {@link Bank},
 * enabling generic handling of resource transfers (e.g., during trades, production,
 * or when a bandit steals resources).
 * </p>
 */
public interface ResourceReceiver {

    /**
     * Adds a specific amount of the given resource type to the receiver.
     *
     * @param type   the type of resource to add (e.g., WOOD, STONE)
     * @param amount the quantity of the resource to add
     */
    void addResources(Resources type, int amount);
}
