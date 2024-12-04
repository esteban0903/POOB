package Presentation;

public class GameConfig {
    private static GameConfig instance;

    private static  int initialSuns; 
    private static int gameDuration; 
    private String gameMode; 
    private static boolean isPaused= false;


    private GameConfig() {
        initialSuns = 0; 
        gameDuration = 300;
        this.gameMode = "Modo 1"; 
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

    public int getGameDuration() {
        return gameDuration;
    }

    public static void setGameDuration(int duration) {
        gameDuration = duration;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
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


}
