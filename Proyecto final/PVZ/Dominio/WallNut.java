package Dominio;

import javax.swing.ImageIcon;
/**
 * Represents the WallNut plant in the game. WallNuts have high health and are used primarily for defense.
 */

public class WallNut extends Plant{
    /**
     * Constructs a new WallNut with specified coordinates.
     * Initializes the WallNut with a name, health, position, and associated image.
     *
     * @param x The x-coordinate where the WallNut will be placed.
     * @param y The y-coordinate where the WallNut will be placed.
     */
    public WallNut(int  x, int y) {
        super("WallNut", 4000, x, y, 50, new ImageIcon("resources/Characters/Wallnut.png"));
    }
    /**
     * Stops any current action of the WallNut. This method overrides the Plant's stop method.
     */    
    @Override
    public void stop(){
        
    }
}
