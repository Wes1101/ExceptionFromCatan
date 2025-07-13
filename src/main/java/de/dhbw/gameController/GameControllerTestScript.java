package de.dhbw.gameController;

/**
 * <p>Standalone test harness for {@link GameController}. It creates a local {@code GameController}
 * instance with a configurable number of players and victory‐point threshold, triggers
 * {@link GameController#gameStart()} and prints a short summary of the resulting game state to
 * {@code System.out}.</p>
 *
 * <p>The script is designed to be run from the command line and does not rely on any GUI. If the
 * {@code syso} flag is {@code true}, the {@link GameController} will operate in headless,
 * console‐only mode using dummy values for inputs that would normally come from the UI.</p>
 */
public class GameControllerTestScript {

    /**
     * Launches the test script.
     *
     * @param args command-line arguments (currently ignored)
     */
    public static void main(String[] args) {
        System.out.println("=== Test: GameController.gameStart() ===");

        // --- configuration ---------------------------------------------------
        int playerAmount = 2;            // number of players that will participate in the game
        int victoryPointsToWin = 10;     // victory-point threshold that ends the game
        boolean syso = true;             // if true, run without GUI and use dummy inputs
        // ---------------------------------------------------------------------

        /*
         * Instantiate a new GameController.
         * Constructor parameters:
         *   playerAmount         – number of players participating in the game
         *   victoryPointsToWin   – victory-point threshold at which the game ends
         *   GameControllerTypes.LOCAL – run the game locally (no network play)
         *   syso                 – if true, disable the GUI and rely on console I/O
         */
        GameController controller = new GameController(
                playerAmount,
                victoryPointsToWin,
                GameControllerTypes.LOCAL,
                syso
        );

        System.out.printf("[INFO] Starting game with %d players, victory points to win: %d%n",
                playerAmount, victoryPointsToWin);
        controller.gameStart();

        System.out.printf("[INFO] Player count: %d%n", controller.getPlayerAmount());

        // Print player information (using reflection to access the private 'players' field)
        try {
            var field = GameController.class.getDeclaredField("players");
            field.setAccessible(true);
            var players = (de.dhbw.player.Player[]) field.get(controller);

            for (de.dhbw.player.Player player : players) {
                System.out.printf("Player %d: %d victory points%n",
                        player.getId(), player.getVictoryPoints());
            }
        } catch (Exception e) {
            System.err.printf("[ERROR] Could not access players: %s%n", e.getMessage());
        }

        System.out.println("=== Test finished ===");
    }
}