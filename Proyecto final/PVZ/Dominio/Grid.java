package Dominio;

import Presentation.GridGUI;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Grid implements Serializable {
    private final List<Character>[][] grid;
    private final int ROWS;
    private final int COLS;
    private final int CELL_SIZE; 
    private TimeController timeController;

    @SuppressWarnings("unchecked") // gpt me sugirio agregar esto para la linea 18, puede generar errores 
    public Grid(int ROWS, int COLS, int CELL_SIZE) {
        this.ROWS = ROWS;
        this.COLS = COLS;
        this.CELL_SIZE = CELL_SIZE; 
        this.grid = new ArrayList[ROWS][COLS];

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                grid[row][col] = new ArrayList<>();
            }
        }
        this.timeController = new TimeController();
    }

    public boolean placeCharacter(Character character, int row, int col) {
        if (isValidPosition(row, col)) {
            if( grid[row][col].size()<1){
                grid[row][col].add(character);
                return true;
            }
        }
        return false;
    }

    public boolean moveCharacter(Character character, int oldRow, int oldCol, int newRow, int newCol) {
        if (isValidPosition(oldRow, oldCol) && isValidPosition(newRow, newCol)) {
            grid[oldRow][oldCol].remove(character);
            grid[newRow][newCol].add(character);
            return true;
        }
        return false;
    }

    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    public boolean isPlacementValid(String type, int col) {
        if (type.equalsIgnoreCase("Plant")) {
            return !(col ==0 || col >=9);
        }
        return !(type.equalsIgnoreCase("Zombie") && col < 9);
    }

    public List<Character> getCharactersInCell(int row, int col) {
        if (isValidPosition(row, col)) {
            return grid[row][col];
        }
        return null;
    }

    public int getRows() {
        return ROWS;
    }

    public int getColumns() {
        return COLS;
    }

    public int getCellSize() {
        return CELL_SIZE; 
    }

    public int getRowFromY(int y) {
        int row = (y - GridGUI.getGridYBase()) / CELL_SIZE;
    
        if (row < 0) {
            row = 0; 
        } else if (row >= ROWS) {
            row = ROWS - 1;
        }
    
        return row;
    }

    public int getColFromX(int x) {
        return (x - GridGUI.getGridXBase()) / CELL_SIZE;
    }

    public boolean hasPlantInCell(int row, int col) {
        List<Character> characters = getCharactersInCell(row, col);
        if (characters == null) return false;
    
        for (Character character : characters) {
            if (!character.isZombie()) {
                return true;
            }
        }
        return false;
    }
    
    
    public Character getPlantInCell(int row, int col) {
        List<Character> characters = getCharactersInCell(row, col);
        if (characters != null) {
            for (Character character : characters) {
                if (!character.isZombie()) {
                    return character;
                }
            }
        }
        return null;
    }

    public List<Character> getAllCharacters() {
        List<Character> allCharacters = new ArrayList<>();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                allCharacters.addAll(grid[row][col]); 
            }
        }
        return allCharacters;
    }

    public void removeCharacter(Character character, int row, int col) {
        if (isValidPosition(row, col)) {
            grid[row][col].remove(character);
        }
    }

    public boolean hasZombieInRow(int row) {
        for (int col = 0; col < getColumns(); col++) {
            if (hasZombieInCell(row, col)) return true;
        }
        return false;
    }

    public boolean hasZombieInCell(int row, int col) {
        List<Character> characters = getCharactersInCell(row, col);
        if (characters != null) {
            for (Character character : characters) {
                if (character.isZombie()) return true;
            }
        }
        return false;
    }

    public Character getZombieInCell(int row, int col) {
        List<Character> characters = getCharactersInCell(row, col);
        if (characters != null) {
            for (Character character : characters) {
                if (character.isZombie()) return character;
            }
        }
        return null;
    }

    public void stop() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                for (Character character : grid[row][col]) {
                    if (character.isZombie()) {
                        ((Zombie) character).stop();
                    }
                }
            }
        }
    }
    
    public void continueGame(){
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                for (Character character : grid[row][col]) {
                    if (character.isZombie()&& !character.isLawnMower()) {
                        ((Zombie) character).continuePlaying();
                    }
                    else if (!character.isZombie() && !character.isLawnMower()) {
                        ((Plant) character).startAction();
                        ((Plant) character).startAction();
                    }
                }
            }
        }
    }

    public void reset() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                grid[row][col].clear();
            }
        }
    }

    public int getPercentageProgress(){
        return timeController.getPercentageProgress();
    }
}
