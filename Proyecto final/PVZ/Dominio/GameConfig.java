package Dominio;

/**
 * Manages game configuration settings, providing static methods to get and set game properties.
 * This class follows the singleton pattern to ensure that only one instance of the configuration exists.
 */

public class GameConfig {
    private static GameConfig instance;

    private static  int initialSuns = 100; 
    private static int gameDuration = 2; 
    private static String gameMode = "Modo 1"; 
    private static boolean isPaused= false;
    private static boolean gameLost = false;
    private static int puntaje = 0;
    private static String namePlayer = "Player 1";
    private static int rounds = 3;


    /**
     * Constructs a new instance of the `GameConfig` class.
     */
    public GameConfig() {
    }
    
    /**
     * Returns the singleton instance of the `GameConfig` class.
     * If the instance does not exist, it creates a new one.
     * @return the singleton instance of `GameConfig`
     */
    public static GameConfig getInstance() {
        if (instance == null) {
            instance = new GameConfig();
        }
        return instance;
    }
    /**
     * Returns the initial amount of suns at the start of the game.
     */
    public static int getInitialSuns() {
        return initialSuns;
    }

    /**
     * Sets the initial amount of suns at the start of the game.
     */
    public static void setInitialSuns(int suns) {
        initialSuns = suns;
    }

    /**
     * Returns the game duration in minutes.
     */
    public static int getGameDuration() {
        return gameDuration;
    }

    /**
     * Sets the game duration in minutes.
     */
    public static void setGameDuration(int duration) {
        gameDuration = duration;
    }

    /**
     * Returns the current game mode.
     */
    public String getGameMode() {
        return gameMode;
    }

    /**
     * Sets the current game mode.
     */
    public static void setGameMode(String mode) {
        gameMode = mode;
    }

    /**
     * Sets the game state to paused.
     */
    public static void setIsPaused() {
        isPaused = true;
    }

    /**
     * Sets the game state to not paused.
     */
    public static void setIsNotPaused() {
        isPaused = false;
    }

    /**
     * Returns the paused state of the game.
     */
    public static boolean getIsPaused() {
        return isPaused;
    }

    /**
     * Sets the game state to game over (lost).
     */
    public static void setIsGameOver() {
        gameLost = true;
    }

    /**
     * Returns whether the game is over (lost).
     */
    public static boolean getIsGameOver() {
        return gameLost;
    }

    /**
     * Resets the game lost state to false, indicating the start of a new game.
     */
    public static void setStartGame() {
        gameLost = false;
    }

    /**
     * Returns the current game score.
     */
    public static int getPuntaje() {
        return puntaje;
    }

    /**
     * Sets the game score.
     */
    public static void setPuntaje(int newPuntaje) {
        puntaje = newPuntaje;
    }

    /**
     * Sets the name of the player.
     */
    public static void setName(String name) {
        namePlayer = name;
    }

    /**
     * Returns the name of the player.
     */
    public static String getName() {
        return namePlayer;
    }

    /**
     * Returns the number of rounds in the game.
     */
    public static int getRounds() {
        return rounds;
    }

    /**
     * Sets the number of rounds in the game.
     */
    public static void setRounds(int newRounds) {
        rounds = newRounds;
    }
}
