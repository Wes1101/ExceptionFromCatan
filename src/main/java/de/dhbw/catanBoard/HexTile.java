//package de.dhbw.catanBoard;
//import java.util.List;
//import java.util.ArrayList;
//
//    /**
//     *Sechseck Kachel auf dem Spielfeld.
//     *Jede davon bekommt eine zugeordnete Würfelzahl zugeordnet.
//     *Sie kennt auch anliegende Felder und Kanten.
//     * */
//
//public class HexTile
//{
//    //private AxialCoord axialCoord;
//    private int diceNumbers;
//    private List<Vertex> vertices;
//    private List<Edge> edges;
//
//    /**
//     *Erzeugt eine Hex-Kachel mit den gegebenen Koordinaten sowie Würfelzahl.
//     * @param coord (Anzahl der Koordinaten ( x , y ) )
//     * @param diceNumber (Die Würfelzahl, die die dazugehörige Kachel erzeugt)
//     */
//
//    public HexTile(/*AxialCoord coord, */int diceNumber)
//    {
////        this.axialCoord = coord;
////        this.diceNumber = diceNumber;
//        this.vertices = new ArrayList<>();
//        this.edges = new ArrayList<>();
//    }
//    /**
//     * Gibt die axialen Koordinaten der Kachel zurück.
//     *
//     * @return axial Koordinate
//     */
//
////    public AxialCoord getCoord()
////    {
////        return axialCoord;
////    }
//
//    /**
//     *Gibt die Würfelzahl auf der Kachel zurück
//     *
//     * @return Würfelzahl
//     */
//
////    public int getDiceNumber()
////    {
////        return diceNumber;
////    }
//
//    /**
//     *Fügt dieser Kachel einen Eckpunkt hinzu und verknüpft diesen mit dem Eckpunkt der Kachel.
//     * @param vertex der Eckpunkt wird hinzugefügt.
//     */
//
//    public void addVertex(Vertex vertex)
//    {
//        if (!vertices.contains(vertex))
//        {
//            vertices.add(vertex);
//            verteces.addHexTile(this);
//        }
//    }
//
//    /**
//     *Fügt der Kachel eine Kante hinzu und verknüpft diese zurück zu dieser Kachel.
//     *
//     * @param edge die hinzugefügte Kante.
//     */
//
//    public void addEdge(Edge edge)
//    {
//        if (!edges.contains(edge))
//        {
//            edges.add(edge);
//            edge.addHexTile(this);
//        }
//    }
//
//    /**
//     *Startet die Erstellung der Ressourcen für die Kachel basierend auf dem Würfelergebnis.
//     *
//     * @param rolledNumber (gewürfelte Zahl)
//     */
//    public void triggerBoard(int rolledNumber)
//    {
//        if (this.diceNumbers == rolledNumber)
//        {
//            for (Vertex v : vertices)   //Ressourcenverteilung an die Ecken mit Gebäuden
//            {
//                if (v.getBuilding() != null)
//                {
//                    //Logik implementieren, um Ressourcen an v.getPlayer() zu vergeben. V kann noch angepasst werden.
//                }
//            }
//        }
//    }
//}
