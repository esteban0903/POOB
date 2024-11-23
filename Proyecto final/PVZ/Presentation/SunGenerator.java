package Presentation;

import javax.swing.*;

import Dominio.Sun;

public class SunGenerator {
    private int cellSize;
    private JPanel gridPanel;
    private JLabel sunCounterLabel;
    private int sunCount;

    public SunGenerator(JPanel gridPanel, int cellSize, JLabel sunCounterLabel) {
        this.gridPanel = gridPanel;
        this.cellSize = cellSize;
        this.sunCounterLabel = sunCounterLabel;
        this.sunCount = 0; 
    }

    public void addRandomSun() {
        int row = (int) (Math.random() * 5); //revisar
        int col = (int) (Math.random() * 8); 

        JPanel cell = (JPanel) gridPanel.getComponent(row * 8 + col);

        Sun sun = createSun(cell);

        cell.add(sun.getPanel());
        cell.revalidate();
        cell.repaint();
    }

    private Sun createSun(JPanel cell) {
        return new Sun("resources/sun.png", cellSize, () -> {
            if (cell.getComponentCount() > 0) {
                cell.remove(cell.getComponent(0)); 
                sunCount += 200; 
                sunCounterLabel.setText("Suns: " + sunCount); 
                cell.revalidate(); 
                cell.repaint(); 
            }
        });
    }

    public int getSunCount() {
        return sunCount;
    }

    public void addSun(int amount) {
        sunCount += amount;
        sunCounterLabel.setText("Suns: " + sunCount);
    }

    public void subtractSun(int amount) {
        if (sunCount >= amount) {
            sunCount -= amount;
            sunCounterLabel.setText("Suns: " + sunCount);
        }
    }
}
