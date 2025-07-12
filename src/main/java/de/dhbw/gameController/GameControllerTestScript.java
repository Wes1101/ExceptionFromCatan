package de.dhbw.gameController;

public class GameControllerTestScript {

    public static void main(String[] args) {
        System.out.println("=== Test: GameController.gameStart() ===");

        int playerAmount = 2;
        int victoryPointsToWin = 10;
        boolean syso = true;  // true = keine GUI, sondern Dummywerte

        GameController controller = new GameController(playerAmount, victoryPointsToWin, GameControllerTypes.LOCAL, syso);

        System.out.println("[INFO] Starte Spiel mit " + playerAmount + " Spielern, Siegpunkte zum Gewinnen: " + victoryPointsToWin);
        controller.gameStart();

        System.out.println("[INFO] Spieleranzahl: " + controller.getPlayerAmount());

        // Spielerinformationen ausgeben (per Reflection)
        try {
            var field = GameController.class.getDeclaredField("players");
            field.setAccessible(true);
            var players = (de.dhbw.player.Player[]) field.get(controller);

            for (de.dhbw.player.Player player : players) {
                System.out.println("Spieler " + player.getId() + ": " + player.getVictoryPoints() + " Siegpunkte");
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Konnte nicht auf Spieler zugreifen: " + e.getMessage());
        }

        System.out.println("=== Test beendet ===");
    }
}
