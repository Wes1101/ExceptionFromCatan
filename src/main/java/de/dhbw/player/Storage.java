package de.dhbw.player;

import de.dhbw.gamePieces.City;
import de.dhbw.gamePieces.Settlement;
import de.dhbw.gamePieces.Street;
import de.dhbw.resources.Resources;
import lombok.Getter;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

@Getter
public class Storage {



    public Storage(int amountRes) {

        initResources(amountRes);
    }

    private void initResources(int amount) {
//        for (Resources resource : resources.keySet()) {
//            resources.put(resource, amount);
//        }
    }
}
