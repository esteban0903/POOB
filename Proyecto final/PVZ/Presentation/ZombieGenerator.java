package Presentation;

import java.util.Random;

import Dominio.Grid;
import Dominio.ZombieFactory;
import Dominio.Character;
import Dominio.Zombie;

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
