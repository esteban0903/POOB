package Dominio;

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

    public static void setInitialSuns(int suns) {
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

    public static void setGameMode(String mode) {
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

    public static int getPuntaje() {
        return puntaje;
    }

    public static void setPuntaje(int newPuntaje) {
        puntaje = newPuntaje;
    }

    public static void setName(String name) {
        namePlayer = name;
    }

    public static String getName(){
        return namePlayer;
    }

    public static int getRounds(){
        return rounds;
    }

    public static void setRounds(int newRounds){
        rounds = newRounds;
    }
}
