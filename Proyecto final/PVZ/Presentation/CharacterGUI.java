package Presentation;

import Dominio.Character;
import Dominio.Grid;
import Dominio.Projectile;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;


/**
 * A JPanel subclass that manages the graphical representation of the game board, including characters and projectiles.
 */

public class CharacterGUI extends JPanel {
    private Grid grid;
    private final int CELL_SIZE;
    public static final int GRID_X_BASE = 140;
    public static final int GRID_Y_BASE = 140;
    private List<Projectile> projectiles; 

    /**
     * Constructs a CharacterGUI with a specified grid and cell size.
     *
     * @param grid The game grid that holds characters and their positions.
     * @param CELL_SIZE The size of each cell in the grid.
     */

    public CharacterGUI(Grid grid, int CELL_SIZE) {
        this.grid = grid; 
        this.CELL_SIZE = CELL_SIZE;
        this.projectiles = new ArrayList<>();
        setOpaque(false);
        setPreferredSize(new Dimension(CELL_SIZE * grid.getColumns(), (CELL_SIZE + 20) * grid.getRows()));
    }

    /**
     * Paints a character at its current position on the grid.
     *
     * @param g The Graphics object to paint on.
     * @param character The character to paint.
     */

    public void paintCharacter(Graphics g, Character character) {
        ImageIcon icon = character.getImage();
        int x = character.getCoordenatesX();
        int y =  character.getCoordenatesY(); 
        g.drawImage(icon.getImage(), x, y, CELL_SIZE, CELL_SIZE, null);
    }

    /**
     * Adds a projectile to the GUI and triggers a repaint.
     *
     * @param projectile The projectile to add.
     */

    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile); 
        repaint(); 
    }

    /**
     * Removes a projectile from the GUI and triggers a repaint.
     *
     * @param projectile The projectile to remove.
     */

    public void removeProjectile(Projectile projectile) {
        projectiles.remove(projectile); 
        repaint();
    }

    /**
     * Custom paint component method to draw plants, zombies, and projectiles.
     *
     * @param g The Graphics object to paint on.
     */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintPlants(g);
        paintZombies(g);
        paintProjectiles(g);
    }

    /**
     * Paints all plant characters on the grid.
     *
     * @param g The Graphics object to paint on.
     */

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

    /**
     * Paints all zombie characters on the grid.
     *
     * @param g The Graphics object to paint on.
     */

    public void paintZombies(Graphics g){
        // Dibujar los zombies ( va aparte pq estos usan otro metodo de pintar ya que se mueven )
        for (Character character : grid.getAllCharacters()) {
            if (character.isZombie()|| character.isLawnMower()) {
                paintCharacter(g, character);
            }
        }
    }

    /**
     * Paints all projectiles on the grid.
     *
     * @param g The Graphics object to paint on.
     */

    private void paintProjectiles(Graphics g) {
        for (Projectile projectile : projectiles) {
            ImageIcon icon = projectile.getImage();
            g.drawImage(icon.getImage(), projectile.getX(), projectile.getY(), CELL_SIZE / 2, CELL_SIZE / 2, null);
        }
    }

    /**
     * Returns the game grid associated with this GUI.
     *
     * @return The current grid.
     */

    public Grid getGrid() {
        return grid;
    }

    /**
     * Returns the cell size used in the grid.
     *
     * @return The cell size.
     */
    public int getCellSize() {
        return CELL_SIZE;
    }

    /**
     * Resets the GUI by clearing projectiles and triggering a repaint.
     */

    public void resetGUI() {
        projectiles.clear(); 
        repaint();           
    }

    /**
     * Sets a new grid for this GUI and triggers a repaint.
     *
     * @param grid The new grid to set.
     */
    
    public void setGrid(Grid grid) {
        this.grid = grid;
        repaint();
    }
}       