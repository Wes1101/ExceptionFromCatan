package de.dhbw.catanBoard.hexGrid;
//import de.dhbw.gamePieces.Building;
//import de.dhbw.player.Player;
import de.dhbw.gamePieces.Building;
import de.dhbw.player.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 *Eckpunkt auf dem Catan-Brett
 *Auf einem der Eckpunkte können nur Siedlungen oder Städte stehen
 */

@Getter
@Setter
public class Node {
    private List<Tile> hexNeighbors;
    private Building building;
    private Player player;
    public final int id;

    public Node(int id)
    {
        this.id = id;
        this.hexNeighbors = new ArrayList<>();
        this.building = null;
        this.player = null;
    }

    public void setBuilding(Building building, Player player)
    {
        this.building = building;
        this.player = player;
    }

    public void addHexTile(Tile tile) {
        hexNeighbors.add(tile);
    }
}