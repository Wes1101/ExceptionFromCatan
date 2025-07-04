package de.dhbw.player;

import de.dhbw.gamePieces.Street;
import de.dhbw.resources.Resources;
import de.dhbw.player.Bank;
import lombok.Getter;

import java.util.*;

/**
 * This abstract class represents a Player in the game.
 * A player can own, gain, and lose resources during gameplay.
 */
@Getter
public abstract class Player {

    int id;
    List<Street> cards;
    EnumMap<Resources, Integer> resources;

    /**
     * Stores the player's resources using an EnumMap for efficiency.
     */
    EnumMap<Resources, Integer> resources;

    /**
     * Constructor initializes each resource type with a given starting amount.
     *
     * @param amountResources the starting amount of each resource type
     */
    public Player(int amountResources) {
        resources = new EnumMap<>(Resources.class);
        for (Resources res : Resources.values()) {
            addResources(res, amountResources);
        }
    }

    /**
     * Adds a specific amount of a resource type to the player.
     *
     * @param type   the type of resource
     * @param amount the amount to add
     */
    public void addResources(Resources type, int amount) {
        for (Resources res : resources.keySet()) {
            if (res == type) {
                resources.put(res, resources.get(res) + amount);
                return;
            }
        }
    }
    /**
     * Transfers a specific amount of a resource from this player to another player.
     *
     * @param type   the type of resource
     * @param amount the amount to transfer
     * @param target the player receiving the resource
     */
    public void removeResources(Resources type, int amount, Player target) {
        for (Resources res : resources.keySet()) {
            if (res == type) {
                resources.put(res, resources.get(res) - amount);
                target.addResources(type, amount);
                return;
            }
        }
    }


    public int getResources(Resources type) {
        return resources.get(type);
    }

    /**
     * Calculates the total number of resource cards the player currently has.
     *
     * @return the total resource count
     */
  
    public int getTotalResources() {
        int sum = 0;
        for (int count : resources.values()) {
            sum += count;
        }
        return sum;
    }

    /**
     * Randomly steals one resource from another player and transfers it to a second player.
     * Currently commented out and may be used later for game mechanics
     *
     * @param from the player to steal from
     * @param to   the player to receive the stolen resource
     */
    public void stealRandomResources(Player from, Player to) {
//        List<Resources> resources = new ArrayList<>();
//
//        // checkt die Ressoucen des Spielers und schaut, ob der Spieler Res besitzt.
//        // Wenn nicht gibt es 0 zurück
//        for (Resources res : Resources.values()) {
//            Integer count = from.getResources().getOrDefault(res, 0);
//
//            // es sollen nur ressourcen geklaut werden, die existieren.
//            // Wenn Res >0 dann wird diese zu der liste available hinzugefügt.
//            if (count > 0) {
//                resources.add(res);
//            }
//        }
//
//        // Falls Res leer - abbrechen
//        if (resources.isEmpty()) {
//            return;
//        }
//
//        // Zufällige Ressource wählen
//        Random r = new Random();
//        Resources chosen = resources.get(r.nextInt(resources.size()));
//
//        // Ressoucen Transfer
//        from.removeResources(chosen, 1, to);
    }

    /**
     * If a player has more resources than the threshold, this method forces them
     * to discard half of their resources (rounded down). The discarded resources
     * are returned to the bank.
     *
     * @param threshold the maximum allowed resources before discarding is required
     * @param bank      the bank to return discarded resources to
     */
    public void banditRemovesResources(int threshold, Bank bank) {
            int total = this.getTotalResources();
            if (total > threshold) {
                int toDiscard = total / 2;

                // TODO: GUI soll resToRemove liefern
                Resources[] resToRemove = {Resources.WOOD, Resources.STONE}; //dummy placeholder

                // Discard each resource and return it to the bank
                for (Resources res : resToRemove) {
                    this.removeResources(res, 1);
                    bank.addResources(res, 1);
                }
            }


            // Gui Platzhalter
//            Map<Resources, Integer> selected = gui.askPlayerWhichCardsToDiscard(player, toDiscard);
//            for (Map.Entry<Resources, Integer> entry : selected.entrySet()) {
//                player.removeResources(entry.getKey(), entry.getValue());
            }

}

