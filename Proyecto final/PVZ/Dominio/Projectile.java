package Dominio;

import Presentation.AudioPlayer;
import Presentation.CharacterGUI;
import java.io.Serializable;
import javax.swing.ImageIcon;


/**
 * Represents a projectile fired by a character in the game, capable of moving and dealing damage to other characters.
 */


public class Projectile implements Serializable {
    private int damage;
    private int speed;
    private int range;
    private int x, y;
    private CharacterGUI characterGUI;
    private static final ImageIcon IMAGE = new ImageIcon("resources/Characters/bullet.png");
    private transient static AudioPlayer player = new AudioPlayer("resources/Music/Efects/ProjectilePlant.wav");
    private boolean isZombie;

    /**
     * Constructs a projectile with specified attributes and behavior.
     *
     * @param damage The damage the projectile will deal upon hitting a target.
     * @param speed The speed at which the projectile moves.
     * @param direction The direction in which the projectile is fired.
     * @param range The maximum distance the projectile can travel.
     * @param x The initial x-coordinate of the projectile.
     * @param y The initial y-coordinate of the projectile.
     * @param characterGUI The GUI component managing this projectile.
     * @param isZombie Flag indicating whether the projectile targets zombies.
     */

    public Projectile(int damage, int speed, String direction, int range, int x, int y, CharacterGUI characterGUI, Boolean isZombie) {
        this.damage = damage;
        this.speed = speed;
        this.range = range;
        this.x = x+50; //para que se pinte un poco despues ( se vea salir de la boca del peashooter)
        this.y = y;
        this.characterGUI = characterGUI;
        this.isZombie = isZombie;
    }

    /**
     * Starts the projectile's movement, handling collisions and damage application.
     */
    
    public void startMoving() {
        new Thread(() -> {
            try {
                
                int distanceTraveled = 0;
                while (distanceTraveled < range) {
                    if (GameConfig.getIsPaused()) {
                        Thread.sleep(50); 
                        continue;
                    }
                    Thread.sleep(50); 
                    x += speed; 
                    distanceTraveled += speed;

                    if (verifyRangesBullet() || makeDamageCharacter()) break;

                    characterGUI.repaint();
                }
                characterGUI.removeProjectile(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Checks if the projectile has exceeded the playable area and should be removed.
     * @return True if the projectile is out of bounds, false otherwise.
     */

    private boolean verifyRangesBullet(){
        if (x > CharacterGUI.GRID_X_BASE + characterGUI.getGrid().getColumns() * characterGUI.getCellSize()) {
            System.out.println("Proyectil fuera del tablero.");
            characterGUI.removeProjectile(this);
            return true;
        }
        return false;
    }

    /**
     * Checks for and handles collisions with other characters, applying damage if applicable.
     * @return True if a character is hit, false otherwise.
     */

    private boolean makeDamageCharacter() {
        int currentRow = characterGUI.getGrid().getRowFromY(y);
        int currentCol = characterGUI.getGrid().getColFromX(x);
        //System.out.println(currentRow + "" + currentCol);
        return takeDecisionProjectile(currentRow, currentCol);
    }

    /**
     * Determines the action taken when a projectile reaches a target, based on whether it targets zombies or plants.
     * @param currentRow The row of the projectile.
     * @param currentCol The column of the projectile.
     * @return True if a target is hit, false otherwise.
     */

    private boolean takeDecisionProjectile(int currentRow, int currentCol){
        //System.out.println(isZombie);
        if(isZombie){
            return verifyZombieInCell(currentRow, currentCol);
        }
        else{
            return verifyPlantInCell(currentRow, currentCol);
        }
    }


    /**
     * Checks if a zombie is present in the cell and applies damage.
     * @param currentRow The row to check.
     * @param currentCol The column to check.
     * @return True if a zombie is hit, false otherwise.
     */

    private boolean verifyZombieInCell(int currentRow, int currentCol){
        if (characterGUI.getGrid().hasZombieInCell(currentRow, currentCol)) {
            Character zombie = characterGUI.getGrid().getZombieInCell(currentRow, currentCol);
            zombie.takeDamage(damage);
            player.playSoundOnce();
        
            characterGUI.removeProjectile(this);
            if (!zombie.isAlive()) {
                killZombie(zombie, currentRow, currentCol);
            }
            return true; // parar bala
        } return false;
    }

    /**
     * Checks if a plant is present in the cell and applies damage.
     * @param currentRow The row to check.
     * @param currentCol The column to check.
     * @return True if a plant is hit, false otherwise.
     */

    private boolean verifyPlantInCell(int currentRow, int currentCol){
        //System.out.println(characterGUI.getGrid().hasPlantInCell(currentRow, currentCol));
        if (characterGUI.getGrid().hasPlantInCell(currentRow, currentCol)) {
            Character plant = characterGUI.getGrid().getPlantInCell(currentRow, currentCol);
            plant.takeDamage(damage);
            player.playSoundOnce();
        
            characterGUI.removeProjectile(this);
            if (!plant.isAlive()) {
                killZombie(plant, currentRow, currentCol);
            }
            return true; // parar bala
        } return false;
    }

    /**
     * Handles the removal of a zombie from the game grid upon being killed by this projectile.
     * @param zombie The zombie to remove.
     * @param currentRow The row from which to remove the zombie.
     * @param currentCol The column from which to remove the zombie.
     */

    private void killZombie(Character zombie, int currentRow, int currentCol) {
            characterGUI.getGrid().removeCharacter(zombie, currentRow, currentCol);
            GameConfig.setPuntaje(100);
            characterGUI.repaint();     
    }

    /**
     * Returns the current x-coordinate of the projectile.
     */

    public int getX() {
        return x;
    }

    /**
     * Returns the current y-coordinate of the projectile.
     */

    public int getY() {
        return y;
    }

    /**
     * Returns the image icon of the projectile.
     */
    public ImageIcon getImage() {
        return IMAGE;
    }

    /**
     * Stops the projectile's movement.
     */

    public void stop(){
        
    }
}
