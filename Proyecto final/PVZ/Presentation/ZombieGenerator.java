package Presentation;

import Dominio.Character;
import Dominio.Grid;
import Dominio.ZombieFactory;
import java.util.Random;

/**
 * Manages the generation and placement of zombies within the game grid.
 */

public class ZombieGenerator {
    private Grid grid; 
    private CharacterGUI characterGUI; 

    /**
     * Constructs a ZombieGenerator with a specified game grid and character GUI.
     *
     * @param grid The game grid where zombies will be placed.
     * @param characterGUI The graphical interface for character display and interaction.
     */

    public ZombieGenerator(Grid grid, CharacterGUI characterGUI) {
        this.grid = grid;
        this.characterGUI = characterGUI;
    }

    /**
     * Adds a zombie at a random row but in the last column of the grid, simulating an entrance from the edge.
     */

    public void addRandomZombie() {
        int rows = grid.getRows();
        int col = grid.getColumns() - 1; 

        int row = new Random().nextInt(rows);

        addZombieToCell(row, col);
    }

    /**
     * Places a zombie in a specific cell within the grid.
     *
     * @param row The row in the grid where the zombie will be placed.
     * @param col The column in the grid where the zombie will be placed.
     */
    
    public void addZombieToCell(int row, int col) {
        // Coordenadas del zombi
        int x = col * grid.getCellSize() + GridGUI.getGridXBase();
        int y = row * (grid.getCellSize() + 20) + GridGUI.getGridYBase();
    
        // Crea un zombi de forma aleatoria usando ZombieFactory
        
        Character zombie = ZombieFactory.createZombie(x, y, characterGUI);
        zombie.move();  // Asume que todos los zombis tienen un método move
    
        // Coloca el zombi en la grilla y actualiza la interfaz
        grid.placeCharacter(zombie, row, col);
        characterGUI.repaint();
    }
}
