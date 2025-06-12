package de.dhbw.player;

import de.dhbw.player.Player;
import lombok.Getter;
import de.dhbw.resources.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

@Getter
public class Bank  extends Player {

    public Bank(int amountResources) {
        super(amountResources);
    }


}
