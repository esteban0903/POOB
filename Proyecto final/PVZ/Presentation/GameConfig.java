package Presentation;

public class GameConfig {
    private static GameConfig instance;

    private static  int initialSuns; // Soles iniciales
    private int gameDuration; // Duración de la partida en segundos
    private String gameMode; // Modo de juego seleccionado
    private static boolean isPaused= false;

    // Constructor privado para asegurar el patrón Singleton
    private GameConfig() {
        initialSuns = 0; // Valor predeterminado de soles
        this.gameDuration = 300; // Valor predeterminado de duración (5 minutos)
        this.gameMode = "Modo 1"; // Valor predeterminado del modo de juego
    }

    // Obtener instancia única
    public static GameConfig getInstance() {
        if (instance == null) {
            instance = new GameConfig();
        }
        return instance;
    }

    // Métodos de acceso y modificación para los soles iniciales
    public static int getInitialSuns() {
        return initialSuns;
    }

    public void setInitialSuns(int initialSuns) {
        initialSuns = initialSuns;
    }

    // Métodos de acceso y modificación para la duración de la partida
    public int getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(int gameDuration) {
        this.gameDuration = gameDuration;
    }

    // Métodos de acceso y modificación para el modo de juego
    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    @Override
    public String toString() {
        return "Configuración del Juego:\n" +
               "Soles iniciales: " + initialSuns + "\n" +
               "Duración: " + (gameDuration / 60) + " minutos\n" +
               "Modo de juego: " + gameMode;
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
