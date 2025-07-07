package de.dhbw.player;

import de.dhbw.catanBoard.CatanBoard;
import de.dhbw.gamePieces.Building;
import de.dhbw.gamePieces.BuildingTypes;
import de.dhbw.gamePieces.Settlement;
import de.dhbw.resources.Resources;
import lombok.Getter;

import javax.smartcardio.Card;
import java.util.EnumMap;
import java.util.Map;


@Getter
public class Human extends Player {

    private String name;

    public Human(String name, int id) {
        resources = new EnumMap<>(Resources.class);
        this.name = name;
        this.id = id;
    }



}
