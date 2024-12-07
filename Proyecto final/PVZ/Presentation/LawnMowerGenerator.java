package Presentation;


import Dominio.Grid;
import Dominio.LawnMower;

public class LawnMowerGenerator {
    private Grid grid; 
    private CharacterGUI characterGUI; 

    public LawnMowerGenerator(Grid grid, CharacterGUI characterGUI) {
        this.grid = grid;
        this.characterGUI = characterGUI;
        adLawnMowerToCells();
    }



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

