package Dominio;

import Presentation.CharacterGUI;
import javax.swing.ImageIcon;


/**
 * Represents a Peashooter, a plant that shoots projectiles at zombies.
 */

public class Peashooter extends Plant {
    private static final int DAMAGE = 20;
    private static final int SHOOT_INTERVAL = 1500; 
    private CharacterGUI characterGUI;
    private boolean isZombieTarjet = true;

    /**
     * Constructs a Peashooter with specified position and GUI component.
     * 
     * @param x The x-coordinate where the Peashooter will be placed.
     * @param y The y-coordinate where the Peashooter will be placed.
     * @param characterGUI The graphical user interface component associated with this Peashooter.
     */

    public Peashooter(int x, int y, CharacterGUI characterGUI) {
        super("Peashooter", 300, x, y, 100, new ImageIcon("resources/Characters/Peashooter.png"));
        this.characterGUI = characterGUI;
    }

    /**
     * Starts the action of the Peashooter, shooting projectiles at intervals if zombies are present.
     */

    @Override
    public void startAction() {
        new Thread(() -> {
            try {
                while (isAlive()) {
                    if (GameConfig.getIsPaused()) {
                        Thread.sleep(SHOOT_INTERVAL); // revisar 
                        continue;
                    }
                    Thread.sleep(SHOOT_INTERVAL);
                    int currentRow = characterGUI.getGrid().getRowFromY(getCoordenatesY());
                    if (verifyTarjet(currentRow)) continue;

                    Projectile projectile = new Projectile(DAMAGE, 5, "right", 800, getCoordenatesX(), getCoordenatesY(), characterGUI,true);
                    characterGUI.addProjectile(projectile);
                    projectile.startMoving();
                }
            } catch (InterruptedException e) {
                e.printStackTrace(); 
            }
        }).start();
    }
    
    /**
     * Verifies if there is a valid target in the row based on the Peashooter's targeting settings.
     * 
     * @param currentRow The row to check for targets.
     * @return True if there is no target in the row, false if targets are present.
     */

    public boolean verifyTarjet(int currentRow){
        if (isZombieTarjet){
            return !characterGUI.getGrid().hasZombieInRow(currentRow);
        }
        else{
            return !characterGUI.getGrid().hasPlantInRow(currentRow);
        }
    }

    /**
     * Stops the current action of the Peashooter.
     */
    
    @Override
    public void stop(){
        
    }
}
