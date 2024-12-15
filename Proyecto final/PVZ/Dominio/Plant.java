package Dominio;

import Presentation.SunGenerator;
import javax.swing.ImageIcon;

/**
 * Abstract base class for plant characters in the game. Defines common properties and actions for all plants.
 */
public abstract class Plant extends Character {
    private static final String TYPE = "Plant"; // The type identifier for all plants

    /**
     * Constructs a Plant character with specified attributes.
     *
     * @param name The name of the plant.
     * @param health The initial health of the plant.
     * @param x The x-coordinate of the plant's position.
     * @param y The y-coordinate of the plant's position.
     * @param sunCost The cost in suns required to place the plant.
     * @param image The image icon associated with the plant.
     */
    public Plant(String name, int health, int x, int y, int sunCost, ImageIcon image) {
        super(name, health, x, y, sunCost, image, TYPE);
    }

    /**
     * Starts the plant's specific action on the grid, utilizing the given SunGenerator for any operations requiring sun generation.
     * This method is meant to be overridden by subclasses to provide specific functionality.
     *
     * @param grid The game grid on which the plant is placed.
     * @param sunGenerator The SunGenerator that manages sun production.
     */
    public void startAction(Grid grid, SunGenerator sunGenerator) {
    }

    /**
     * Starts the default action of the plant. This method provides a default implementation which may be overridden by subclasses.
     */
    public void startAction() { 
    }   
}
