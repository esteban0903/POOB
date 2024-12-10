package Presentation;

import Dominio.Character;
import Dominio.Grid;
import Dominio.Projectile;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class CharacterGUI extends JPanel {
    private Grid grid;
    private final int CELL_SIZE;
    public static final int GRID_X_BASE = 140;
    public static final int GRID_Y_BASE = 140;
    private List<Projectile> projectiles; 

    public CharacterGUI(Grid grid, int CELL_SIZE) {
        this.grid = grid; 
        this.CELL_SIZE = CELL_SIZE;
        this.projectiles = new ArrayList<>();
        setOpaque(false);
        setPreferredSize(new Dimension(CELL_SIZE * grid.getColumns(), (CELL_SIZE + 20) * grid.getRows()));
    }

    public void paintCharacter(Graphics g, Character character) {
        ImageIcon icon = character.getImage();
        int x = character.getCoordenatesX();
        int y =  character.getCoordenatesY(); 
        g.drawImage(icon.getImage(), x, y, CELL_SIZE, CELL_SIZE, null);
    }

    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile); 
        repaint(); 
    }

    public void removeProjectile(Projectile projectile) {
        projectiles.remove(projectile); 
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintPlants(g);
        paintZombies(g);
        paintProjectiles(g);
    }
    
    public void paintPlants(Graphics g){
        // Dibuja todos las plantas 
        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getColumns(); col++) {
                List<Character> characters = grid.getCharactersInCell(row, col);

                for (Character character : characters) {
                    if (!(character.isZombie()) && !character.isLawnMower()) {
                        ImageIcon icon = character.getImage();
                        int x = GRID_X_BASE + col * CELL_SIZE;
                        int y = GRID_Y_BASE + row * (CELL_SIZE + 20);
                        g.drawImage(icon.getImage(), x, y, CELL_SIZE, CELL_SIZE, null);
                    }
                }
            }
        }

    }
    public void paintZombies(Graphics g){
        // Dibujar los zombies ( va aparte pq estos usan otro metodo de pintar ya que se mueven )
        for (Character character : grid.getAllCharacters()) {
            if (character.isZombie()|| character.isLawnMower()) {
                paintCharacter(g, character);
            }
        }
    }

    private void paintProjectiles(Graphics g) {
        for (Projectile projectile : projectiles) {
            ImageIcon icon = projectile.getImage();
            g.drawImage(icon.getImage(), projectile.getX(), projectile.getY(), CELL_SIZE / 2, CELL_SIZE / 2, null);
        }
    }
    public Grid getGrid() {
        return grid;
    }

    public int getCellSize() {
        return CELL_SIZE;
    }

    public void resetGUI() {
        projectiles.clear(); 
        repaint();           
    }


    // ---------------METODO GENERADO POR GPT PARA PRUEBAS ----------------------- 
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
    
        JOptionPane.showMessageDialog(null, boardState.toString(), "Estado del Tableroo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
        repaint();
    }
}       