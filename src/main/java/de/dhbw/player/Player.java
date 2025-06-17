package de.dhbw.player;

import de.dhbw.resources.Resources;
import lombok.Getter;

import java.util.*;


@Getter
public abstract class Player {

    EnumMap<Resources, Integer> resources;
    int id;

    public Player(int amountResources) {
        resources = new EnumMap<>(Resources.class);
        for (Resources res : Resources.values()) {
            resources.put(res, amountResources);
        }
    }

    public void addResources(Resources type, int amount) {
        for (Resources res : resources.keySet()) {
            if (res == type) {
                resources.put(res, resources.get(res) + amount);
                return;
            }
        }
    }

    public void removeResources(Resources type, int amount, Player target) {
        for (Resources res : resources.keySet()) {
            if (res == type) {
                resources.put(res, resources.get(res) - amount);
                target.addResources(type, amount);
                return;
            }
        }
    }

    public int getTotalResources() {
        int sum = 0;
        for (int count : resources.values()) {
            sum += count;
        }
        return sum;
    }


    public void stealRandomResources(Player from, Player to) {
        List<Resources> resources = new ArrayList<>();

        // checkt die Ressoucen des Spielers und schaut, ob der Spieler Res besitzt.
        // Wenn nicht gibt es 0 zurück
        for (Resources res : Resources.values()) {
            Integer count = from.getResources().getOrDefault(res, 0);

            // es sollen nur ressourcen geklaut werden, die existieren.
            // Wenn Res >0 dann wird diese zu der liste available hinzugefügt.
            if (count > 0) {
                resources.add(res);
            }
        }

        // Falls Res leer - abbrechen
        if (resources.isEmpty()) {
            return;
        }

        // Zufäälige Ressource wählen
        Random r = new Random();
        Resources chosen = resources.get(r.nextInt(resources.size()));

        // Ressoucen Transfer
        from.removeResources(chosen, 1, to);
    }


    public void banditRemovesResources(int amount) {
        for (Resources res : Resources.values()) {
            int total = this.getTotalResources();
            if (total > 7) {
                int toDiscard = total / 2;
            }
            // Gui Platzhalter
//            Map<Resources, Integer> selected = gui.askPlayerWhichCardsToDiscard(player, toDiscard);
//            for (Map.Entry<Resources, Integer> entry : selected.entrySet()) {
//                player.removeResources(entry.getKey(), entry.getValue());
            }
        }
    }
}

