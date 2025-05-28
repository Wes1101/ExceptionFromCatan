package de.dhbw.catanBoard;
import java.util.List;
import java.util.ArrayList;

/**
 *Eine Kante auf dem Brett verbindet zwei Eckpunkte und grenzt an die anderen Kacheln.
 *Auf eine Straße kann ein Spieler eine Straße bauen, die noch keinem Spieler gehört.
 */

public class Edge extends CatanBoard
{
    private int id;
    private List<HexTile> hexTiles;
    private Vertex vertexA;
    private Vertex vertexB;
    private boolean road;
    private String player;

    /**
     *Erzeugt eine Kante zwischen den zwei Eckpunkten.
     *
     * @param id (Das ist die genaue id zur genau dieser Kante.)
     * @param vertexA End Eckpunkt der kante
     * @param vertexB Das andere End Eckpunkt der kante
     */

    public Edge(int id, Vertex vertexA, Vertex vertexB)
    {
        this.id = id;
        this.hexTiles = new ArrayList<>();
        this.vertexA = vertexA;
        this.vertexB = vertexB;
        this.road = false;
        this.player = null;

        //Verbindet diese Kante mit den Eckpunkten
        vertexA.addEdge(this);
        vertexB.addEdge(this);

        //Verbindet die Eckpunkte miteinander Als "nachbarn"
        vertexA.addNeighbor(vertexB);
        vertexB.addNeighbor(vertexA);
    }
    public int getId()
    {
        return id;
    }

    /**
     * Fügt der Kante eine angrenzende Kachel hinzu (bin mir noch nicht sicher, ob er dann explodiert, weil er unendlich viele berechnet).
     *
     * @param tile die hex Kachel, die an die Kante angrenzt.
     */

    public void addTile(HexTile tile)
    {
        if (!hexTiles.contains(tile))
        {
            hexTiles.add(tile);
            tile.addEdge(this);
        }
    }

    /**
     *Baut eine Straße auf der vom Spieler ausgewählten Kante
     *
     * @param player Derzeitiger Spieler, der die Straße setzt.
     */

    public void build_road(String player)
    {
        this.road = true;
        this.player = player;
    }

    /**
     *Überprüft, ob auf dieser Kante schon eine Straße gebaut ist.
     *
     * @return true, falls auf der Kante schon eine Straße gebaut ist.
     */

    public boolean hasRoad()
    {
        return road;
    }

    /**
     *Gibt den Spieler zurück, dem die Straße auf dieser Kante gehört.
     *
     * @return der Spielername, oder null, falls keine Straße vorhanden ist.
     */

    public String getPlayer()
    {
        return player;
    }

    @Override
    public void triggerBoard(int diceNumber)
    {
        //Braucht man nicht, Kanten reagieren nicht direkt auf Würfelergebnisse.
    }
}