package de.dhbw.player;

import de.dhbw.resources.Resources;
import lombok.Getter;
import java.util.EnumMap;


@Getter
public abstract class Player {

    EnumMap<Resources, Integer> resources;

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


}
