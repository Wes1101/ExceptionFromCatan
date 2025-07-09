package de.dhbw.player;

import de.dhbw.resources.Resources;

public interface ResourceReceiver {
    void addResources(Resources type, int amount);
}
