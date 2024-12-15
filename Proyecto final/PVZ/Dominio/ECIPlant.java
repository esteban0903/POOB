package Dominio;

import javax.swing.ImageIcon;

/**
 * Represents the ECIPlant, a specialized version of the Sunflower in the game.
 * This plant class is themed for the game setting and has modified attributes such as cost, health, and the amount of sun it generates.
 * It inherits from the Sunflower class, utilizing its base functionalities while customizing specific properties.
 */
public class ECIPlant extends Sunflower {

    /**
     * Constructs a new ECIPlant with specified position.
     * Initializes the ECIPlant with a unique name, cost, health, and sun production value.
     * An image specific to the ECIPlant is set to visually distinguish it from other plants.
     * @param x The x-coordinate where the plant will be placed.
     * @param y The y-coordinate where the plant will be placed.
     */
    public ECIPlant(int x, int y) {
        super(x, y);
        setName("ECIPlant"); 
        setCost(75); 
        setImage(new ImageIcon("resources/Characters/ECIPlant.png"));
        setSunValue(50); 
        setCharacterHealth(150);
    }
}
