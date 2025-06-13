package de.dhbw.player;

import de.dhbw.resources.Resources;
import lombok.Getter;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;


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
    public void banditRemovesResources() {
//        List<Resources> resources = new ArrayList<>();
//        for (Resources res : Resources.values()) {
//            Integer count = from.getResources().getOrDefault(res, <= 7);
//            if (count <= 7) {

            }
        }
    }


}
