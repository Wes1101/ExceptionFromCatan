package de.dhbw.catanBoard;

import de.dhbw.gamePieces.Building;
import de.dhbw.gamePieces.Settlement;
import de.dhbw.player.Bank;
import de.dhbw.player.Human;
import de.dhbw.player.Player;

public class main {
    public static void main(String[] args) {
        CatanBoard board = new CatanBoard(3);
        Player player1 = new Human("Player1", 8);
        Player player2 = new Human("Player2", 2);
        Bank bank = new Bank(30);

        for (int i = 0; i < 54; i++) {
            board.buildSettlement(player1, i, new Settlement(player1));
        }
        
        System.out.println("spieler1 " + player1.getResources());
        System.out.println("bank " + bank.getResources());

        board.triggerBoard(6, bank);

        System.out.println("spieler1 " + player1.getResources());
        System.out.println("bank " + bank.getResources());

        board.buildStreet(player1, 1, 2);

        for (int i = 0; i < board.graph.length; i++) {
            for (int j = 0; j < board.graph[i].length; j++) {
                System.out.print(board.graph[i][j][1] + ", ");
            }
            System.out.println();
        }

        Settlement settlement = new Settlement(player1);
        System.out.println(settlement.getBuildCost());


    }
}

