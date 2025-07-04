package de.dhbw.player;

import de.dhbw.catanBoard.CatanBoard;
import de.dhbw.gamePieces.Building;
import de.dhbw.gamePieces.BuildingTypes;
import de.dhbw.gamePieces.Settlement;
import de.dhbw.resources.Resources;
import lombok.Getter;

import javax.smartcardio.Card;
import java.util.Map;


@Getter
public class Human extends Player {

    private String name;

    public Human(String name, int id) {
        super(0);
        this.name = name;
        this.id = id;
    }

    public boolean buildSettlement(int node, Bank bank, Player player, CatanBoard board) {
        Building building = bank.getBuilding(BuildingTypes.SETTLEMENT, player);

        if (building != null) {

            if (enoughResources(building.getBuildCost())) {
                buyBuilding(building.getBuildCost(), bank);
                board.buildSettlement(node, building);
                return true;
            }
        }
        return false;
    }

    public boolean buildCity(int node, Bank bank, Player player, CatanBoard board) {
        Building building = bank.getBuilding(BuildingTypes.CITY, player);

        if (building != null) {

            if (enoughResources(building.getBuildCost())) {
                buyBuilding(building.getBuildCost(), bank);
                board.buildCity(node, building, bank);
                return true;
            }
        }
        return false;
    }

    public boolean buildStreet(int node1, int node2, Bank bank, Player player, CatanBoard board) {
        Building building = bank.getBuilding(BuildingTypes.STREET, player);

        if (building != null) {

            if (enoughResources(building.getBuildCost())) {
                buyBuilding(building.getBuildCost(), bank);
                board.buildStreet(node1, node2, building);
                return true;
            }
        }
        return false;
    }

    private boolean enoughResources(Map<Resources, Integer> costs) {
        for (Resources resource : costs.keySet()) {
            if (costs.get(resource) > this.getResources(resource)) {
                return false;
            }
        }
        return true;
    }

    private void buyBuilding(Map<Resources, Integer> costs, Bank bank) {
        for (Resources resource : costs.keySet()) {
            this.removeResources(resource, costs.get(resource), bank);
        }
    }

    public void trade() {

    }

    public void rollDice() {

    }

    public void endTurn() {

    }

}
