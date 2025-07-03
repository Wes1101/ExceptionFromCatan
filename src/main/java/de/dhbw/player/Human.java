package de.dhbw.player;

import de.dhbw.gamePieces.Building;
import de.dhbw.gamePieces.BuildingTypes;
import de.dhbw.resources.Resources;
import lombok.Getter;

import java.util.Map;


@Getter
public class Human extends Player {

    private String name;

    public Human(String name, int id) {
        super(0);
        this.name = name;
        this.id = id;
    }

    public void build(BuildingTypes buildingType, int node, Bank bank, Player player) {
        if (bank.containsBuilding(buildingType, player)) {
            if (buildingType == BuildingTypes.SETTLEMENT) {

            }
        }
    }

    public void trade() {

    }

    public void rollDice() {

    }

    public void endTurn() {

    }

}
