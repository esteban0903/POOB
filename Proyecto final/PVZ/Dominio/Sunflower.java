package Dominio;


import javax.swing.ImageIcon;

import Presentation.SunGenerator;

public class Sunflower extends Plant {
    protected int sunValue = 25; 
    protected int sunProductionTime = 2000; 
    private boolean producing;

    public Sunflower(int x, int y) {
        super("Sunflower", 300, x, y, 50, new ImageIcon("resources/Sunflower.png"));
        this.producing = true;
    }

    @Override
    public void startAction(Grid grid, SunGenerator sunGenerator) {
        new Thread(() -> {
            try {
                while (producing) {
                    Thread.sleep(sunProductionTime);
                    
                    int row = grid.getRowFromY(getCoordenatesY());
                    int col = grid.getColFromX(getCoordenatesX());

                    if (grid.isValidPosition(row, col)) {
                        sunGenerator.addSunToCell(row, col, sunValue); 
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Se paro al produccion de suns");
            }
        }).start();
    }

    public void stopProducing() {
        producing = false;
    }
    
    protected void setSunValue(int sunValue) {
        this.sunValue = sunValue;
    }

    protected void setSunProductionTime(int sunProductionTime) {
        this.sunProductionTime = sunProductionTime;
    }
}
