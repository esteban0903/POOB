package Presentation;


import Dominio.Grid;
import Dominio.LawnMower;

/**
 * Handles the generation and placement of LawnMower objects on the game grid at the start of a game session.
 */

public class LawnMowerGenerator {
    private Grid grid; 
    private CharacterGUI characterGUI; 

    /**
     * Constructs a LawnMowerGenerator with a specified game grid and character GUI.
     *
     * @param grid The game grid where lawn mowers will be placed.
     * @param characterGUI The graphical interface for character display and interaction.
     */

    public LawnMowerGenerator(Grid grid, CharacterGUI characterGUI) {
        this.grid = grid;
        this.characterGUI = characterGUI;
        adLawnMowerToCells();
    }


    /**
     * Adds lawn mowers to the first column of each row on the grid. Typically used to place lawn mowers
     * in their starting positions at the beginning of a game.
     */
    
    public void adLawnMowerToCells() {
        for (int i = 0; i < 5; i++) {
            int x = grid.getCellSize() + GridGUI.getGridXBase() -80;
            int y = i * (grid.getCellSize() + 20) + GridGUI.getGridYBase();
            LawnMower lawnMower = new LawnMower(x, y, characterGUI);
            grid.placeCharacter(lawnMower, i,0);
            characterGUI.repaint();
        }
    }
}

