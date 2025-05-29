package de.dhbw.catanBoard;
//import de.dhbw.gamePieces.Building;
import de.dhbw.player.Player;
import lombok.Getter;

import java.util.List;
import java.util.ArrayList;

/**
 *Eckpunkt auf dem Catan-Brett
 *Auf einem der Eckpunkte können nur Siedlungen oder Städte stehen
 */

@Getter
public class Vertex {
    //private Building building;
    private Player player;

    public final int q;
    public final int r;
    public final int s;

    public Vertex(int q, int r, int s)
    {
        this.q = q;
        this.r = r;
        this.s = s;
        //this.building = null;
        this.player = null;
    }

    public void setBuilding(/*Building building,*/ Player player)
    {
        //this.building = building;
        this.player = player;
    }
}