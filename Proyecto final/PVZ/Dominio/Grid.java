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


    /**
     * Initializes the grid with a specified number of rows, columns, and cell size. Each cell is filled with an empty list of characters.
    */
    @SuppressWarnings("unchecked") 
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
    }

    /**
     * Attempts to place a character at the specified row and column, checking if the position is valid and not already occupied.
    */
    public boolean placeCharacter(Character character, int row, int col) {
        if (isValidPosition(row, col)) {
            if( grid[row][col].size()<1){
                grid[row][col].add(character);
                return true;
            }
        }
        return false;
    }

    /**
     * Moves a character from one cell to another, ensuring both old and new positions are valid.
    */
    public boolean moveCharacter(Character character, int oldRow, int oldCol, int newRow, int newCol) {
        if (isValidPosition(oldRow, oldCol) && isValidPosition(newRow, newCol)) {
            grid[oldRow][oldCol].remove(character);
            grid[newRow][newCol].add(character);
            return true;
        }
        return false;
    }

    /**
     * Checks if a specified row and column are within the grid bounds.
    */
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    /**
     * Determines if a character of a certain type can be placed in a specified column.
    */
    public boolean isPlacementValid(String type, int col) {
        if (type.equalsIgnoreCase("Plant")) {
            return !(col ==0 || col >=9);
        }
        return !(type.equalsIgnoreCase("Zombie") && col < 9);
    }

    /**
     * Returns a list of characters in a specified cell, ensuring the position is valid.
    */
    public List<Character> getCharactersInCell(int row, int col) {
        if (isValidPosition(row, col)) {
            return grid[row][col];
        }
        return null;
    }

    /**
     * Returns the total number of rows in the grid.
    */
    public int getRows() {
        return ROWS;
    }

    /**
     * Returns the total number of columns in the grid.
    */
    public int getColumns() {
        return COLS;
    }

    /**
     * Returns the size of each cell in the grid.
    */
    public int getCellSize() {
        return CELL_SIZE; 
    }

    /**
     * Converts a y-coordinate into a grid row, adjusting for the grid's vertical base.
     * @param y The y-coordinate to convert.
     * @return The grid row index corresponding to the given y-coordinate, clamped to the valid row range.
     */
    public int getRowFromY(int y) {
        int row = (y - GridGUI.getGridYBase()) / CELL_SIZE;
    
        if (row < 0) {
            row = 0; 
        } else if (row >= ROWS) {
            row = ROWS - 1;
        }
    
        return row;
    }

    /**
     * Converts an x-coordinate into a grid column, adjusting for the grid's horizontal base.
     * @param x The x-coordinate to convert.
     * @return The grid column index corresponding to the given x-coordinate.
     */
    public int getColFromX(int x) {
        return (x - GridGUI.getGridXBase()) / CELL_SIZE;
    }

    /**
     * Checks if there is at least one plant in the specified cell.
     * @param row the row index of the cell to check
     * @param col the column index of the cell to check
     * @return true if there is at least one plant in the cell, false otherwise
     */
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
    
    
    /**
     * Retrieves the first plant character in the specified cell.
     * @param row the row index of the cell to check
     * @param col the column index of the cell to check
     * @return the first plant character in the cell, or null if no plant is present
    */
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

    /**
     * Retrieves a list of all characters currently present on the grid.
     * @return a list containing all characters on the grid
    */
    public List<Character> getAllCharacters() {
        List<Character> allCharacters = new ArrayList<>();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                allCharacters.addAll(grid[row][col]); 
            }
        }
        return allCharacters;
    }

    /**
     * Removes a character from a specified cell if the position is valid.
     *
     * @param character The character to remove.
     * @param row The row of the cell.
     * @param col The column of the cell.
    */
    public void removeCharacter(Character character, int row, int col) {
        if (isValidPosition(row, col)) {
            grid[row][col].remove(character);
        }
    }

    /**
     * Checks if there is at least one zombie in any cell of the specified row.
     *
     * @param row The row to check.
     * @return true if there is a zombie in the row, false otherwise.
     */
    public boolean hasZombieInRow(int row) {
        for (int col = 0; col < getColumns(); col++) {
            if (hasZombieInCell(row, col)) return true;
        }
        return false;
    }

    /**
     * Checks if there is at least one plant in any cell of the specified row.
     *
     * @param row The row to check.
     * @return true if there is a plant in the row, false otherwise.
    */
    public boolean hasPlantInRow(int row) {
        for (int col = 0; col < getColumns(); col++) {
            if (hasPlantInCell(row, col)) return true;
        }
        return false;
    }

/**
 * Determines if there is at least one zombie in the specified cell.
 *
 * @param row The row of the cell.
 * @param col The column of the cell.
 * @return true if there is a zombie in the cell, false otherwise.
*/
    public boolean hasZombieInCell(int row, int col) {
        List<Character> characters = getCharactersInCell(row, col);
        if (characters != null) {
            for (Character character : characters) {
                if (character.isZombie()) return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the first zombie found in the specified cell.
     *
     * @param row The row of the cell.
     * @param col The column of the cell.
     * @return The first zombie character found, or null if no zombie is present.
     */
    public Character getZombieInCell(int row, int col) {
        List<Character> characters = getCharactersInCell(row, col);
        if (characters != null) {
            for (Character character : characters) {
                if (character.isZombie()) return character;
            }
        }
        return null;
    }

    /**
     * Pauses the actions of all zombies in the grid.
     */
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

    /**
     * Continues the game by resuming actions for all characters in the grid, specifically restarting any paused zombies or plants.
     */
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

    /**
     * Clears all characters from every cell in the grid, effectively resetting the grid state.
     */
    public void reset() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                grid[row][col].clear();
            }
        }
    }

}
