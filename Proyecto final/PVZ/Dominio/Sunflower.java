package Dominio;


import Presentation.SunGenerator;
import javax.swing.ImageIcon;


/**
 * Represents a Sunflower plant in the game that produces sun points at regular intervals.
 */

public class Sunflower extends Plant {
    protected int sunValue = 25; 
    protected int sunProductionTime = 20000; 
    private boolean producing;

    /**
     * Constructs a Sunflower with specified coordinates.
     *
     * @param x The x-coordinate where the Sunflower will be placed.
     * @param y The y-coordinate where the Sunflower will be placed.
     */
    public Sunflower(int x, int y) {
        super("Sunflower", 300, x, y, 50, new ImageIcon("resources/Characters/Sunflower.png"));
        this.producing = true;
    }

    /**
     * Placeholder for starting the default action of the Sunflower. This method is meant to be overridden.
     */
    public void StartAction(){ 
    }
    
    /**
     * Starts the action of producing suns at regular intervals, managing the production through a separate thread.
     *
     * @param grid The game grid in which the Sunflower is placed.
     * @param sunGenerator The SunGenerator that manages the distribution and creation of sun points.
     */

    @Override
    public void startAction(Grid grid, SunGenerator sunGenerator) {
        new Thread(() -> {
            try {
                while (producing) {
                    if (GameConfig.getIsPaused()) {
                        Thread.sleep(50); // Esperar mientras está en pausa
                        continue;
                    }
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

    /**
     * Stops the production of suns by setting the producing flag to false.
     */
    public void stopProducing() {
        producing = false;
    }
    
    /**
     * Sets the value of sun points that this Sunflower will produce each interval.
     *
     * @param sunValue The new sun point value to be set.
     */
    protected void setSunValue(int sunValue) {
        this.sunValue = sunValue;
    }

    /**
     * Sets the time interval for producing sun points.
     *
     * @param sunProductionTime The new production time in milliseconds.
     */
    protected void setSunProductionTime(int sunProductionTime) {
        this.sunProductionTime = sunProductionTime;
    }

    /**
     * Stops any ongoing actions or threads associated with this Sunflower.
     */
    @Override
    public void stop(){
        
    }
}
