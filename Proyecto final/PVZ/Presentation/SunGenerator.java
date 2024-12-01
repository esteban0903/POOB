package Presentation;

import javax.swing.*;

import Dominio.Grid;
import Dominio.Sun;

public class SunGenerator {
    private JPanel gridPanel;
    private JLabel sunCounterLabel;
    private Grid grid; // Referencia al Grid para validaciones
    private int sunCount;

    public SunGenerator(Grid grid, JPanel gridPanel, JLabel sunCounterLabel) {
        this.grid = grid;
        this.gridPanel = gridPanel;
        this.sunCounterLabel = sunCounterLabel;
        this.sunCount = 0;
    }

    public void addRandomSun(int sunValue) {
        int rows = grid.getRows();
        int cols = grid.getColumns();

        int row = (int) (Math.random() * rows);
        int col = (int) (Math.random() * cols);

        addSunToCell(row, col, sunValue);
    }

    public void addSunToCell(int row, int col, int sunValue) {
        if (!grid.isValidPosition(row, col)) {
            System.out.println("Posición inválida: fila " + row + ", columna " + col);
            return;
        }

        // Calcular índice del componente
        int index = row * grid.getColumns() + col;
        if (index >= gridPanel.getComponentCount()) {
            System.out.println("Índice inválido: " + index + ", total de componentes: " + gridPanel.getComponentCount());
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

    private Sun createSun(JPanel cell, int sunValue) {
        return new Sun("resources/sun.png", grid.getCellSize(), () -> {
            if (cell.getComponentCount() > 0) {
                cell.remove(cell.getComponent(0));
                sunCount += sunValue;
                sunCounterLabel.setText("Suns: " + sunCount);
                cell.revalidate();
                cell.repaint();
            }
        });
    }

    public int getSunCount() {
        return sunCount;
    }

    public boolean hasEnoughSuns(int cost) {
        return sunCount >= cost;
    }

    public void subtractSun(int amount) {
        if (sunCount >= amount) {
            sunCount -= amount;
            sunCounterLabel.setText("Suns: " + sunCount);
        }
    }
}
