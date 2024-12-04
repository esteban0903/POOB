package Presentation;

import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {
    private int sunCount;
    private List<Character> characters;
    private int[][] gridState; // Estado del tablero (ej., posiciones ocupadas)
    private int timerState;

    // Getters y setters
    public int getSunCount() {
        return sunCount;
    }

    public void setSunCount(int sunCount) {
        this.sunCount = sunCount;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public int[][] getGridState() {
        return gridState;
    }

    public void setGridState(int[][] gridState) {
        this.gridState = gridState;
    }

    public int getTimerState() {
        return timerState;
    }

    public void setTimerState(int timerState) {
        this.timerState = timerState;
    }
}

