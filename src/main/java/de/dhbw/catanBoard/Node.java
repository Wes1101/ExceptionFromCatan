package de.dhbw.catanBoard;
//import de.dhbw.gamePieces.Building;
import de.dhbw.player.Player;
import lombok.Getter;

/**
 *Eckpunkt auf dem Catan-Brett
 *Auf einem der Eckpunkte können nur Siedlungen oder Städte stehen
 */

@Getter
public class Node {
    //private Building building;
    private Player player;
    public final int id;

    public Node(int s)
    {
        this.id = s;
        //this.building = null;
        this.player = null;
    }

    public void setBuilding(/*Building building,*/ Player player)
    {
        //this.building = building;
        this.player = player;
    }
}