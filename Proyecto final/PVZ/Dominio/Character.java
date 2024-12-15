package Dominio;

import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 * This abstract class represents a character in the game, providing common functionalities for all characters.
 * Each character has attributes such as name, health, position, cost, and an associated image.
 * Characters can take damage, move, and can be checked for their alive status.
 * Specific subclasses will implement the particular behaviors for different types of characters.
 */
public abstract class Character implements Serializable {   
    protected String name;
    protected int health;
    private int x, y;
    private boolean isAlive;   
    protected ImageIcon image;
    protected int cost;
    private String type;

    /**
     * Constructor to create a new instance of Character with specified attributes.
     * @param name The name of the character.
     * @param health The initial health of the character.
     * @param x The x-coordinate of the character's initial position.
     * @param y The y-coordinate of the character's initial position.
     * @param cost The deployment cost of the character.
     * @param image The image icon associated with the character.
     * @param type The type of the character.
     */
    public Character(String name, int health, int x, int y, int cost, ImageIcon image, String type) {
        this.name = name;
        this.health = health;
        this.x = x;
        this.y = y;
        this.isAlive = true;
        this.cost = cost;
        this.image = image;
        this.type = type;
    }
    
    /**
     * Inflicts damage on the character, potentially changing its alive status if health reaches zero or below.
     * @param damage The amount of damage to inflict.
     */
    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            this.isAlive = false;
        }
    }

    /**
     * Moves the character. This method should be overridden by subclasses to define specific movement behaviors.
     */
    public void move() {
        // Movement logic goes here (to be overridden by subclasses)
    }

    /**
     * Checks if the character is still alive.
     * @return true if the character is alive, false otherwise.
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Sets the position of the character.
     * @param x New x-coordinate.
     * @param y New y-coordinate.
     */
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate of the character's position.
     * @return The x-coordinate.
     */
    public int getCoordenatesX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the character's position.
     * @return The y-coordinate.
     */
    public int getCoordenatesY() {
        return y;
    }

    /**
     * Retrieves the image icon associated with the character.
     * @return The image icon.
     */
    public ImageIcon getImage() {
        return image;
    }

    /**
     * Gets the name of the character.
     * @return The character's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the cost associated with the character.
     * @return The cost.
     */
    public int getCost() {
        return cost;
    }

    /**
     * Gets the type of the character.
     * @return The character type.
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the current health of the character.
     * @return The health value.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets a new image for the character.
     * @param image New ImageIcon to set.
     */
    protected void setImage(ImageIcon image) {
        this.image = image;
    }

    /**
     * Sets a new name for the character.
     * @param name New name to set.
     */
    protected void setName(String name) {
        this.name = name;
    }

    /**
     * Sets a new cost for the character.
     * @param cost New cost to set.
     */
    protected void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Sets the health of the character.
     * @param health The new health value to set.
     */
    protected void setCharacterHealth(int health) {
        this.health = health;
    }
    
    /**
     * Checks if the character is a zombie.
     * @return True if the character is a zombie, false otherwise.
     */
    public boolean isZombie() {
        return false;
    }
    
    /**
     * Checks if the character is a lawn mower.
     * @return True if the character is a lawn mower, false otherwise.
     */
    public boolean isLawnMower() {
        return false;
    }

    public void stop(){
        
    }
    
    
}
