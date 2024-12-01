package Presentation;

import javax.swing.*;
import java.util.Random;

import Dominio.Grid;
import Dominio.BasicZombie;

public class ZombieGenerator {
    private Grid grid; // Referencia al Grid para validaciones
    private CharacterGUI characterGUI; // Interfaz gráfica para pintar los zombies

    public ZombieGenerator(Grid grid, CharacterGUI characterGUI) {
        this.grid = grid;
        this.characterGUI = characterGUI;
    }

    public void addRandomZombie() {
        int rows = grid.getRows();
        int col = grid.getColumns() - 1; // Última columna

        // Generar una fila aleatoria
        int row = new Random().nextInt(rows);


        // Crear y posicionar el zombie
        addZombieToCell(row, col);
    }

    public void addZombieToCell(int row, int col) {

        // Coordenadas iniciales del zombie
        int x = col * grid.getCellSize() + GridGUI.getGridXBase();
        int y = row * (grid.getCellSize() + 20) + GridGUI.getGridYBase();

        // Crear un BasicZombie
        BasicZombie zombie = new BasicZombie(x, y, characterGUI);
        zombie.move();
        // Colocar el zombie en la celda del Grid
        grid.placeCharacter(zombie, row, col);
        

        // Redibujar el tablero
        characterGUI.repaint();
    }
}
