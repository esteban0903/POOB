package Dominio;

public class Grid {
    private Character[][] grid;
    private int rows, cols;
    private final int cellSize = 100;

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Character[rows][cols];
    }

    public void placeCharacter(Character character, int x, int y) {
        if (isValidPosition(x, y)) {
            grid[x][y] = character;
        }
    }

    public void moveCharacter(int startX, int startY, int endX, int endY) {
        if (isValidPosition(startX, startY) && isValidPosition(endX, endY)) {
            grid[endX][endY] = grid[startX][startY];
            grid[startX][startY] = null;
        }
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }

    public Character getCharacter(int x, int y) {
        if (isValidPosition(x, y)) {
            return grid[x][y];
        }
        return null;
    }

    public Character[][] getGrid() {
        return grid;
    }
}