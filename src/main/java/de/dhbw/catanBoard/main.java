package de.dhbw.catanBoard;

import de.dhbw.gamePieces.Building;
import de.dhbw.gamePieces.Settlement;
import de.dhbw.player.Bank;
import de.dhbw.player.Human;
import de.dhbw.player.Player;

public class main {
    public static void main(String[] args) {
        CatanBoard board = new CatanBoard(3);
        Player player1 = new Human("Player1");
        Player player2 = new Human("Player2");
        Building settlement1 = new Settlement(player1);
        Bank bank = new Bank(30);

        for (int i = 0; i <= 50; i++) {
            board.buildSettlement(player1, i, settlement1);
        }
        
        System.out.println(player1.getResources());

        board.triggerBoard(6, bank);

        System.out.println(player1.getResources());
    }
}

