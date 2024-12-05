package Dominio;

import java.util.ArrayList;
import java.util.List;

import Presentation.GridGUI;

public class Grid {
    private List<Character>[][] grid;
    private int rows, cols;
    private int cellSize; 

    @SuppressWarnings("unchecked") // gpt me sugirio agregar esto para la linea 18, puede generar errores 
    public Grid(int rows, int cols, int cellSize) {
        this.rows = rows;
        this.cols = cols;
        this.cellSize = cellSize; 
        this.grid = new ArrayList[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                grid[row][col] = new ArrayList<>();
            }
        }
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
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public boolean isPlacementValid(String type, int col) {
        if (type.equalsIgnoreCase("Plant") && col >= 8) return false; // plantas van de 0-8
        if (type.equalsIgnoreCase("Zombie") && col < 8) return false; // Zombies en la ultima 
        return true;
    }

    public List<Character> getCharactersInCell(int row, int col) {
        if (isValidPosition(row, col)) {
            return grid[row][col];
        }
        return null;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return cols;
    }

    public int getCellSize() {
        return cellSize; 
    }

    public int getRowFromY(int y) {
        int row = (y - GridGUI.getGridYBase()) / cellSize;
    
        if (row < 0) {
            row = 0; 
        } else if (row >= rows) {
            row = rows - 1;
        }
    
        return row;
    }

    public int getColFromX(int x) {
        return (x - GridGUI.getGridXBase()) / cellSize;
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
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
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
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                for (Character character : grid[row][col]) {
                    if (character.isZombie()) {
                        ((Zombie) character).stop();
                    }
                }
            }
        }
    }
    

    public void reset() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                grid[row][col].clear();
            }
        }
    }
}
