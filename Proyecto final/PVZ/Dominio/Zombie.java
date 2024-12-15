package Dominio;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import Presentation.AudioPlayer;
import Presentation.CharacterGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Represents a Zombie, a character that moves and attacks plants in the game.
 */

public abstract class Zombie extends Character {
    private final int SPEED; 
    private final int ARMOR;
    private String direction; 
    private final int DAMAGE; 
    private CharacterGUI characterGUI;
    private static final String TYPE = "Zombie";
    private final int DAMAGE_TIME;
    private Timer attackTimer; // timer para que el zombie ataque cada cierto tiempo 
    private transient static final AudioPlayer player = new AudioPlayer("resources/Music/Efects/attackZombie.wav");

    /**
     * Constructs a Zombie with specified attributes and initializes its properties.
     * @param name The name of the zombie.
     * @param health The initial health of the zombie.
     * @param x The initial x-coordinate of the zombie.
     * @param y The initial y-coordinate of the zombie.
     * @param brainCost The cost in brains for deploying the zombie.
     * @param SPEED The moving speed of the zombie.
     * @param ARMOR The armor rating adding to health.
     * @param direction The initial moving direction of the zombie.
     * @param DAMAGE The damage amount the zombie can inflict.
     * @param image The image icon representing the zombie.
     * @param characterGUI The GUI component associated with this zombie.
     * @param DAMAGE_TIME The time interval between consecutive attacks.
     */

    public Zombie(String name, int health, int x, int y, int brainCost, int SPEED, int ARMOR, String direction, int DAMAGE, ImageIcon image, CharacterGUI characterGUI, int DAMAGE_TIME) {
        super(name, health, x, y, brainCost, image, TYPE);
        this.SPEED = SPEED;
        this.ARMOR = ARMOR;
        this.direction = direction;
        this.DAMAGE = DAMAGE;
        setCharacterHealth(health + ARMOR);
        this.characterGUI = characterGUI;
        this.attackTimer = null; 
        this.DAMAGE_TIME = DAMAGE_TIME;
    }

    /**
     * Attacks the specified plant, dealing damage.
     * @param plant The plant character to attack.
     */

    public void attack(Character plant) {
        System.out.println(getName() + " bajando vida " + plant.getHealth());
        plant.takeDamage(DAMAGE);
    }

    /**
     * Starts an attack on a specific plant, managed by a timer to handle attack intervals.
     * @param plant The plant character to attack.
     * @param row The grid row where the attack occurs.
     * @param col The grid column where the attack occurs.
     */

    private void startAttack(Character plant, int row, int col) {
        if (attackTimer != null && attackTimer.isRunning()) {
            return; 
        }

        attackTimer = new Timer(DAMAGE_TIME, new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                if (plant.isAlive() && characterGUI.getGrid().getCharactersInCell(row, col).contains(plant) && !GameConfig.getIsPaused()&& isAlive()) {
                    attack(plant);
                    player.playMusic();
                    if (!plant.isAlive()) {
                        System.out.println(plant.getName() + " ha sido eliminado.");
                        characterGUI.getGrid().removeCharacter(plant, row, col);
                        characterGUI.repaint();
                        attackTimer.stop(); 
                        player.stopMusic();
                    }
                } else {
                    attackTimer.stop(); // parar el timer si la planta se murio 
                    player.stopMusic();
                }
            }
        });
        attackTimer.start();
    }

    /**
     * Updates the position of the zombie in the grid.
     * @param x The new x-coordinate of the zombie.
     * @param y The new y-coordinate of the zombie.
     */

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
        characterGUI.repaint();
    }

    /**
     * Manages the movement of the zombie, updating its position based on its speed and direction.
     */

    @Override
    public void move() {
        new Thread(() -> {
            try {
                //System.out.println(direction);
                while (isAlive() && direction != null) {
                    if (GameConfig.getIsPaused()) {
                        Thread.sleep(SPEED*50); 
                        continue;
                    }
                    Thread.sleep(SPEED*100); 
                    
                    //mover al zombie con su SPEED 
                    int[] newPosition = calculateNewPosition(SPEED);
                    int newX = newPosition[0];
                    int newY = newPosition[1];

                    // Calcular la row y col de la antigua y nueva posicion
                    int currentRow = characterGUI.getGrid().getRowFromY(getCoordenatesY());
                    int currentCol = characterGUI.getGrid().getColFromX(getCoordenatesX());
                    int newRow = characterGUI.getGrid().getRowFromY(newY);
                    int newCol = characterGUI.getGrid().getColFromX(newX);
                    
                    //bordes
                    if (hasReachedBoardLimit(newRow, newCol)) break;
    
                    // Colision con plantas 
                    if (handlePlantCollision(currentRow, currentCol, newRow, newCol)) continue;
    
                    updatePosition(newX, newY, currentRow, currentCol, newRow, newCol);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Checks if the zombie has reached the edge of the board.
     * @param newRow The new row position of the zombie.
     * @param newCol The new column position of the zombie.
     * @return True if the zombie is out of bounds, otherwise false.
     */

    private boolean hasReachedBoardLimit(int newRow, int newCol) {
        if (!characterGUI.getGrid().isValidPosition(newRow, newCol)) {
            GameConfig.setIsGameOver();
            System.out.println("Game Over");
            return true;
        }
        return false;
    }
    
    /**
     * Handles collisions with plants during movement.
     * @param currentRow The current row of the zombie.
     * @param currentCol The current column of the zombie.
     * @param newRow The new row after moving.
     * @param newCol The new column after moving.
     * @return True if a collision occurred, otherwise false.
     */

    private boolean handlePlantCollision(int currentRow, int currentCol, int newRow, int newCol) {
        if (currentRow == newRow && currentCol == newCol) {
            if (characterGUI.getGrid().hasPlantInCell(newRow, newCol)) {
                Character plant = characterGUI.getGrid().getPlantInCell(newRow, newCol);
                startAttack(plant, newRow, newCol);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Updates the position of the zombie on the grid and manages position change events.
     * @param newX The new x-coordinate.
     * @param newY The new y-coordinate.
     * @param currentRow The current row before moving.
     * @param currentCol The current column before moving.
     * @param newRow The new row after moving.
     * @param newCol The new column after moving.
     */

    private void updatePosition(int newX, int newY, int currentRow, int currentCol, int newRow, int newCol) {
        setPosition(newX, newY);
        if (currentRow != newRow || currentCol != newCol) {
            characterGUI.getGrid().moveCharacter(this, currentRow, currentCol, newRow, newCol);
        }
    }

    /**
     * Calculates a new position based on the current step distance.
     * @param step The step distance to move in the current direction.
     * @return An array containing the new x and y coordinates.
     */

    private int[] calculateNewPosition(int step) {
        int newX = getCoordenatesX();
        int newY = getCoordenatesY();

        switch (direction.toLowerCase()) {
            case "left":
                newX -= step;
                break;
            case "right":
                newX += step;
                break;
            case "up":
                newY -= step;
                break;
            case "down":
                newY += step;
                break;
        }

        return new int[]{newX, newY};
    }

    /**
     * Stops the zombie's movement and any ongoing actions.
     */

    public void stop() {
        if (this.attackTimer != null && this.attackTimer.isRunning()) {
            this.attackTimer.stop();
        }

        this.direction = null;  
    
    
    }

    /**
     * Continues playing by setting the direction to left and starting the movement.
     */
    public void continuePlaying(){
        setDirection("left");
        move();
    }

    /**
     * Sets the movement direction of the zombie.
     * @param direction The new direction for the zombie to move.
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Confirms if this character is a Zombie.
     * @return Always returns true for Zombie instances.
     */
    @Override
    public final boolean isZombie(){
        return true;
    }

}
