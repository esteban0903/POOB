package Presentation;

public class GameConfig {
    private static GameConfig instance;

    private static  int initialSuns = 0; 
    private static int gameDuration = 5; 
    private static String gameMode = "Modo 1"; 
    private static boolean isPaused= false;
    private static boolean gameLost = false;


    private GameConfig() {
    }


    public static GameConfig getInstance() {
        if (instance == null) {
            instance = new GameConfig();
        }
        return instance;
    }

    public static int getInitialSuns() {
        return initialSuns;
    }

    public void setInitialSuns(int suns) {
        initialSuns = suns;
    }

    public static  int getGameDuration() {
        return gameDuration;
    }

    public static void setGameDuration(int duration) {
        gameDuration = duration;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String mode) {
        gameMode = mode;
    }

    public static void setIsPaused() {
        isPaused = true;
    }

    public static void setIsNotPaused(){
        isPaused = false;
    }

    public static  boolean getIsPaused() {
        return isPaused;
    }

    public static void setIsGameOver(){
        gameLost = true;
    }

    public static boolean getIsGameOver(){
        return gameLost;
    }
    
    public static void setStartGame(){
        gameLost = false;
    }
}
