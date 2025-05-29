//package de.dhbw.catanBoard;
//import java.util.List;
//import java.util.ArrayList;
//
///**
// *Eckpunkt auf dem Catan-Brett
// *Auf einem der Eckpunkte können nur Siedlungen oder Städte stehen
// */
//
//public class Vertex extends CatanBoard
//{
//    private int id;
//    private List<HexTile> hexTiles;
//    private List<Vertex> adjacentVertices; //       https://mathoer.net/art/topology4.png
//    private List<Edge> edges;
//    private String building;
//    private String player;
//
//    /**
//     *Erzeugt Eckpunkt mit eindeutigen ID
//     *
//     * @param id Eckpunkt ID
//     */
//
//    public Vertex(int id)
//    {
//        this.id = id;
//        this.hexTiles = new ArrayList<>();
//        this.adjacentVertices = new ArrayList<>();
//        this.edges = new ArrayList<>();
//        this.building = null;
//        this.player = null;
//    }
//
//    public int getId()
//    {
//        return id;
//    }
//
//    /**
//     * Gibt den Typen des Gebäudes an diesem Eckpunkt zurück
//     *
//     * @return der Gebäudetyp, oder null wenn kein Gebäude darauf vorhanden ist
//     */
//
//    public String getBuilding()
//    {
//        return building;
//    }
//
//    /**
//     *Gibt den Spieler zurück, dem das Gebäude an diesem Eckpunkt gehört.
//     *
//     * @return der Spielername, oder null, wenn kein Gebäude vorhanden ist.
//     */
//
//    public String getPlayer()
//    {
//        return player;
//    }
//
//    /**
//     *Setzt ein Gebäude an diesem Eckpunkt und speichert dies einen Spieler zu.
//     *
//     * @param building Siedlung oder Stadt
//     * @param player Gibt zurück wem das Gebäude gehört
//     */
//
//    public void setBuilding(String building, String player)
//    {
//        this.building = building;
//        this.player = player;
//    }
//
//    /**
//     *Fügt diesem Eckpunkt eine angrenzende Hex Kachel hinzu und verbindet die Kachel zurück zu diesem Eckpunkt.
//     *
//     * @param tile dies gibt Hex Kachel, die hinzugefügt wird zurück
//     */
//
//    public void addHexTile(HexTile tile)
//    {
//        if (!hexTiles.contains(tile))
//        {
//        hexTiles.add(tile);
//        tile.addVertex(this);
//        }
//    }
//
//    /**
//     *Fügt einen Benachbarten Eckpunkt ein
//     *
//     * @param vertex
//     */
//
//    public void addNeighbor(Vertex vertex)
//    {
//        if (!adjacentVertices.contains(vertex))
//        {
//            adjacentVertices.add(vertex);
//            vertex.addNeighbor(this);
//        }
//    }
//
//    /**
//     *Fügt dem Eckpunkt eine Kante hinzu
//     *
//     * @param edge
//     */
//
//    public void addEdge(Edge edge)
//    {
//        if (!edges.contains(edge))
//        {
//            edges.add(edge);
//        }
//    }
//
//    @Override
//
//    public void triggerBoard(int diceNumber)
//    {
//        // Ecken reagieren nicht direkt auf Würfelergebnisse; Ressourcen werden durch angrenzende Hex-Kacheln verteilt.
//    }
//}