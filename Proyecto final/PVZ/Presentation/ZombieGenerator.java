package Presentation;

import java.util.Random;

import Dominio.Grid;
import Dominio.BasicZombie;

public class ZombieGenerator {
    private Grid grid; 
    private CharacterGUI characterGUI; 

    public ZombieGenerator(Grid grid, CharacterGUI characterGUI) {
        this.grid = grid;
        this.characterGUI = characterGUI;
    }

    public void addRandomZombie() {
        int rows = grid.getRows();
        int col = grid.getColumns() - 1; 

        int row = new Random().nextInt(rows);

        addZombieToCell(row, col);
    }

    public void addZombieToCell(int row, int col) {

        // Coords del zombie 
        int x = col * grid.getCellSize() + GridGUI.getGridXBase();
        int y = row * (grid.getCellSize() + 20) + GridGUI.getGridYBase();

        BasicZombie zombie = new BasicZombie(x, y, characterGUI);
        zombie.move();

        grid.placeCharacter(zombie, row, col);
        

        characterGUI.repaint();
    }
}
