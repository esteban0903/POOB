package Presentation;

import java.io.Serializable;
import java.util.List;

/**
 * Represents the state of a game session, including all relevant data needed to save and restore the game.
 */

public class GameState implements Serializable {
    private int sunCount;
    private List<Character> characters;
    private int[][] gridState; // Estado del tablero (ej., posiciones ocupadas)
    private int timerState;

     /**
     * Gets the current count of sun points.
     * @return the current sun count.
     */
    public int getSunCount() {
        return sunCount;
    }

    /**
     * Sets the current count of sun points.
     * @param sunCount the sun count to set.
     */
    public void setSunCount(int sunCount) {
        this.sunCount = sunCount;
    }

    /**
     * Gets the list of all characters currently active on the grid.
     * @return a list of active characters.
     */
    public List<Character> getCharacters() {
        return characters;
    }

    /**
     * Sets the list of characters currently active on the grid.
     * @param characters the list of characters to set.
     */
    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    /**
     * Gets the state of the grid as a 2D array where each element represents a cell state.
     * @return the 2D array representing the grid state.
     */
    public int[][] getGridState() {
        return gridState;
    }

    /**
     * Sets the state of the grid with a 2D array where each element represents a cell state.
     * @param gridState the 2D array to represent the grid state.
     */
    public void setGridState(int[][] gridState) {
        this.gridState = gridState;
    }

    /**
     * Gets the current state of the game timer.
     * @return the current timer state.
     */
    public int getTimerState() {
        return timerState;
    }

    /**
     * Sets the current state of the game timer.
     * @param timerState the timer state to set.
     */
    public void setTimerState(int timerState) {
        this.timerState = timerState;
    }
}

