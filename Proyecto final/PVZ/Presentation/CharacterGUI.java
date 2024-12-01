package Presentation;

import Dominio.Grid;
import Dominio.Projectile;
import Dominio.Zombie;
import Dominio.Character;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterGUI extends JPanel {
    private Grid grid;
    private int cellSize;
    public static final int GRID_X_BASE = 220;
    public static final int GRID_Y_BASE = 140;
    private List<Projectile> projectiles; // Lista para almacenar los proyectiles activos

    public CharacterGUI(Grid grid, int cellSize) {
        this.grid = grid; 
        this.cellSize = cellSize;
        this.projectiles = new ArrayList<>();
        setOpaque(false);
        setPreferredSize(new Dimension(cellSize * grid.getColumns(), (cellSize + 20) * grid.getRows()));
    }

    public void paintCharacter(Graphics g, Character character) {
        ImageIcon icon = character.getImage();
        int x = character.getCoordenatesX();
        int y =  character.getCoordenatesY(); 
        g.drawImage(icon.getImage(), x, y, cellSize, cellSize, null);
    }

    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile); // Agregar un proyectil a la lista
        repaint(); // Asegurar que se dibuje el nuevo proyectil
    }

    public void removeProjectile(Projectile projectile) {
        projectiles.remove(projectile); // Eliminar proyectil de la lista
        repaint(); // Actualizar la pantalla
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar todos los personajes del tablero en sus posiciones lógicas
        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getColumns(); col++) {
                List<Character> characters = grid.getCharactersInCell(row, col);

                for (Character character : characters) {
                    if (!(character instanceof Zombie)) {
                        ImageIcon icon = character.getImage();
                        int x = GRID_X_BASE + col * cellSize;
                        int y = GRID_Y_BASE + row * (cellSize + 20);
                        g.drawImage(icon.getImage(), x, y, cellSize, cellSize, null);
                    }
                }
            }
        }

        // Dibujar zombies en sus coordenadas actuales
        for (Character character : grid.getAllCharacters()) {
            if (character instanceof Zombie) {
                paintCharacter(g, character);
            }
        }

        // Dibujar proyectiles en sus coordenadas actuales
        for (Projectile projectile : projectiles) {
            ImageIcon icon = projectile.getImage();
            g.drawImage(icon.getImage(), projectile.getX(), projectile.getY(), cellSize / 2, cellSize / 2, null);
        }
    }

    public Grid getGrid() {
        return grid;
    }

    public int getCellSize() {
        return cellSize;
    }
    // este metodo es generado por gpt, solo es para realizar pruebas 
    public void showBoard() {
        StringBuilder boardState = new StringBuilder();
        boardState.append("Estado del Tablero:\n");
        boardState.append("-".repeat(grid.getColumns() * 8 + 1)).append("\n"); // Bordes superiores
        
        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getColumns(); col++) {
                List<Character> cellCharacters = grid.getCharactersInCell(row, col);
                
                if (cellCharacters == null || cellCharacters.isEmpty()) {
                    boardState.append("| Vacío "); // Celda vacía
                } else {
                    StringBuilder cellContent = new StringBuilder("| ");
                    for (Character character : cellCharacters) {
                        cellContent.append(character.getType().charAt(0)); // Agrega la inicial del tipo de personaje
                    }
                    boardState.append(String.format("%-6s", cellContent.toString())); // Ajusta el formato
                }
            }
            boardState.append("|\n"); // Cierra la fila
            boardState.append("-".repeat(grid.getColumns() * 8 + 1)).append("\n"); // Bordes entre filas
        }
    
        JOptionPane.showMessageDialog(null, boardState.toString(), "Estado del Tablero", JOptionPane.INFORMATION_MESSAGE);
    }
}       