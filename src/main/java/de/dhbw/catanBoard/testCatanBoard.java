package de.dhbw.catanBoard;

import de.dhbw.catanBoard.hexGrid.IntTupel;
import de.dhbw.gamePieces.*;
import de.dhbw.player.Bank;
import de.dhbw.player.Player;
import de.dhbw.resources.Resources;

public class testCatanBoard {
    public static void main(String[] args) {
        // Create board with radius 3
        CatanBoard board = new CatanBoard(3);

        // Create dummy bank and players
        Player player = new Player(1);
        Player[] players = {player};
        Bank bank = new Bank(19, players);

        // Build settlements and city
        board.buildSettlement(0, new Settlement(player));
        board.buildCity(0, new City(player), bank);

        // Build a street between two nodes
        board.buildStreet(0, 1, new Street(player));
        board.buildStreet(1, 2, new Street(player));
        board.buildStreet(2, 3, new Street(player));

        // Trigger the board with a test dice number
        for (int i = 2; i <= 12; i++) {
            board.triggerBoard(i, bank);
        }

        // Block and unblock a tile
        IntTupel desert = board.getDesertCoords();
        if (desert != null) {
            board.blockHex(desert);  // block
            board.blockHex(desert);  // unblock
        }

        // Access tiles and graph
        System.out.println("Tile count: " + board.getHex_coords().length);
        System.out.println("Graph node count: " + board.getGraph().getNodes().length);

        // Test longest road functions
        int longestRoute = board.getGraph().findLongestTradeRoutes(player);
        System.out.println("Longest route length: " + longestRoute);

        Player longestRoadOwner = board.getGraph().findPlayerLongestStreet(players);
        if (longestRoadOwner != null) {
            System.out.println("Player with longest road: " + longestRoadOwner.getId());
        } else {
            System.out.println("No player has the longest road.");
        }
    }
}
