package de.dhbw.catanBoard.hexGrid;

import de.dhbw.gamePieces.Street;
import de.dhbw.player.Player;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Graph {
    Edge[][] graph;
    Node[] nodes;

    public Graph(int nodes) {
        initNodes(nodes);
        graph = new Edge[nodes][nodes];
    }

    private void initNodes(int numNodes) {
        nodes = new Node[numNodes];
        for (int i = 0; i < numNodes; i++) {
            nodes[i] = new Node(i);
            System.out.println("Node " + i + " created");
        }
    }

    public void createEdge(int i, int j) {
        graph[i][j] = new Edge();
        graph[j][i] = new Edge();
    }

    public void updateEdge(int i, int j, Street street) {
        graph[i][j].setStreet(street);
        graph[j][i].setStreet(street);
    }

    /**
     *Dies ist der Algorithmus, welcher die längste Handel-Strecke heraussucht.
     *Es wird als Algorithms Depth-First Search genutzt,
     *es wird erst geschaut, sobald ein Spieler 3 Gebäude aneinander hat.
     * @param player dies ist die ID des spielers
     * @return Gibt die Länge des shits raus.
     */

    public int findLongestTradeRoutes(Player player) {
        int maxLength = 0;
        for (int startNode = 0; startNode < graph.length; startNode++) {
            boolean[] searched = new boolean[graph.length];
            maxLength =
                    Math.max(maxLength, dfsLength(startNode, -1, searched, player));
        }
        System.out.println("Winner: Player" + maxLength);
        return maxLength >= 3 ? maxLength : 0;
    }

    /**
     *Führt die Suche durch (DFS) zur Ermittlung der Längsten durchgehenden Straße eines Spielers.
     * @param current Aktueller Knoten
     * @param from Vorheriger Knoten (Vermeidung von Rücklaufen)
     * @param player ID des Spielers, dessen Straße aktuell geprüft wird.
     * @param searched Merkt sich, welche Knoten schon durchsucht wurden.
     * @return Länge des längsten Pfades.
     */

    private int dfsLength(int current, int from, boolean[] searched, Player player)
    {
        searched[current] = true;
        int maxTiefe = 1;

        for (int nachbar = 0; nachbar < graph.length; nachbar++)
        {
            if (graph[current][nachbar].getStreet().getOwner() == player)
            {
                if (nachbar != from && !searched[nachbar])
                {
                    maxTiefe = Math.max(maxTiefe, 1 + dfsLength(nachbar, current, searched, player));
                }
            }
        }
        searched[current] = false;
        System.out.println("DFS von node:" + current + "ergibt die Tiefe:" + maxTiefe);
        return maxTiefe;
    }

    /**
     *Dies hier findet den Spieler mit der längsten Handelsstraße.
     * @param players Anzahl aller Spieler.
     * @return Spieler ID mit der längsten Straße oder gibt halt -1 aus, falls keiner derzeit die längste Straße hat.
     */

    public Player findPlayerLongestStreet(Player[] players)
    {
        int maxLength = 0;
        Player winner = null;

        for (Player player : players)
        {
            int length = findLongestTradeRoutes(player);
            if (length > maxLength)
            {
                maxLength = length;
                winner = player;
            }
            logRouteDetails(player,length,winner,maxLength);
        }
        return winner;
    }

    /**
     *Helfer Methode zur Protokollierung von den Routeninformationen
     * @param player spieler id
     * @param length Länge des in Führung liegenden Spielers
     * @param winner Spieler Id mi der bislang längsten Straße
     * @param maxLength Länge der bislang längsten Straße
     */

    private void logRouteDetails(Player player,int length, Player winner,int maxLength)
    {
        System.out.println("Spieler" + player + "Hat eine Straßen länge" + length);
        System.out.println("Aktueller Sieger ist Spieler" + winner + "mit der Straßenlänge:" + maxLength);
    }

}
