package Presentation;

import Dominio.Grid;
import Dominio.Sun;
import javax.swing.*;

/**
 * Handles the generation and management of sun points within the game, including placement on the grid and updating the sun count display.
 */

public class SunGenerator {
    private JPanel gridPanel;
    private JLabel sunCounterLabel;
    private Grid grid;
    private int sunCount;

    /**
     * Constructs a SunGenerator with specified grid, panel for the grid, sun count label, and initial sun count.
     *
     * @param grid The game grid.
     * @param gridPanel The panel representing the grid.
     * @param sunCounterLabel The label displaying the sun count.
     * @param sunCount Initial number of suns.
     */

    public SunGenerator(Grid grid, JPanel gridPanel, JLabel sunCounterLabel, int sunCount) {
        this.grid = grid;
        this.gridPanel = gridPanel;
        this.sunCounterLabel = sunCounterLabel;
        this.sunCount = sunCount;
    }

    /**
     * Adds a sun at a random cell within the grid.
     *
     * @param sunValue The value of the sun being added.
     */

    public void addRandomSun(int sunValue) {
        int rows = grid.getRows();
        int cols = grid.getColumns();
        int row = (int) (Math.random() * rows);
        int col = (int) (Math.random() * cols);
        addSunToCell(row, col, sunValue);
    }

    /**
     * Adds a sun to a specific cell in the grid.
     *
     * @param row The row index for the sun.
     * @param col The column index for the sun.
     * @param sunValue The value of the sun being added.
     */

    public void addSunToCell(int row, int col, int sunValue) {
        if (!grid.isValidPosition(row, col-1)) {
            //System.out.println("Posición inválida: fila " + row + ", columna " + col);
            return;
        }

        // Calcular índice del componente
        int index = row * grid.getColumns() + col;
        if (index >= gridPanel.getComponentCount()) {
            //System.out.println("Índice inválido: " + index + ", total de componentes: " + gridPanel.getComponentCount());
            return;
        }

        // Obtener la celda y agregar el sol
        JPanel cell = (JPanel) gridPanel.getComponent(index);
        Sun sun = createSun(cell, sunValue);

        cell.add(sun.getPanel());
        sun.makeSound();
        cell.revalidate();
        cell.repaint();
    }

    /**
     * Creates a sun instance and returns it.
     *
     * @param cell The panel (cell) where the sun will be placed.
     * @param sunValue The value of the sun to be created.
     * @return A new Sun object.
     */

    private Sun createSun(JPanel cell, int sunValue) {
        return new Sun("resources/Characters/sun.png", grid.getCellSize(), () -> {
            if (cell.getComponentCount() > 0) {
                cell.remove(cell.getComponent(0));
                sunCount += sunValue;
                sunCounterLabel.setText("Suns: " + sunCount);
                cell.revalidate();
                cell.repaint();
            }
        });
    }

    /**
     * Gets the current sun count.
     *
     * @return The current count of suns.
     */

    public int getSunCount() {
        return sunCount;
    }

    /**
     * Checks if there are enough suns to cover a specified cost.
     *
     * @param cost The cost to be covered.
     * @return True if there are enough suns, false otherwise.
     */

    public boolean hasEnoughSuns(int cost) {
        return sunCount >= cost;
    }

    /**
     * Subtracts a specified amount of suns from the current count.
     *
     * @param amount The amount of suns to subtract.
     */
    
    public void subtractSun(int amount) {
        if (sunCount >= amount) {
            sunCount -= amount;
            sunCounterLabel.setText("Suns: " + sunCount);
        }
    }
}
