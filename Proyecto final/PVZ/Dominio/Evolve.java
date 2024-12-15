package Dominio;


import Presentation.CharacterGUI;
import java.util.Timer;
import javax.swing.ImageIcon;

/**
 * Represents an Evolve plant that changes phases and increases its attack capabilities over time.
 */
public class Evolve extends Plant{ 
    private static final int DAMAGE = 20; 
    private CharacterGUI characterGUI;
    private long timeInitial;
    private Timer timerInitial;
    private int phase = 1;

    /**
     * Initializes an Evolve plant with specific coordinates and associated GUI.
     *
     * @param x The x-coordinate where the plant will be placed.
     * @param y The y-coordinate where the plant will be placed.
     * @param characterGUI The graphical user interface component associated with this plant.
     */
    public Evolve(int x, int y, CharacterGUI characterGUI){
         super("Evolve", 300, x, y, 0, new ImageIcon("resources/Characters/Evolve.png"));
         this.characterGUI = characterGUI;;
         
    }

    /**
     * Starts the action sequence of the plant, handling its evolution and attack phases.
     */
    @Override
    public void startAction() {
        timeInitial= System.currentTimeMillis();
        new Thread(() -> {
            try {
                while (isAlive()) {
                    if (GameConfig.getIsPaused()) {
                        continue;
                    }
                    long timeCurrent= System.currentTimeMillis();
                    long elapsedTime = timeCurrent-timeInitial;
                    //System.out.println(elapsedTime);
                    if (elapsedTime<=20000){
                        continue;
                    }

                    if(elapsedTime>20000 && elapsedTime<=40000){
                        if(phase==1){
                            this.setImage(new ImageIcon("resources/Characters/Evolve1.png"));
                            phase +=1;
                        }
                        //System.out.println(elapsedTime);
                        Thread.sleep(3000);
                        int currentRow = characterGUI.getGrid().getRowFromY(getCoordenatesY());
                        if (!characterGUI.getGrid().hasZombieInRow(currentRow)) continue;
                        attackZombie();
                        
                    }

                    if(elapsedTime >40000){
                        if(phase==2){
                            this.setImage(new ImageIcon("resources/Characters/Evolve2.png"));
                            phase +=3;
                        }
                        //System.out.println(elapsedTime);
                        Thread.sleep(1000);
                        int currentRow = characterGUI.getGrid().getRowFromY(getCoordenatesY());
                        if (!characterGUI.getGrid().hasZombieInRow(currentRow)) continue;
                        attackZombie();

                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace(); 
            }
        }).start();
    }

    /**
     * Stops the current action of the plant.
     */
    public void stop(){

    }

    /**
     * Attacks zombies in the same row by launching projectiles.
     */
    public void attackZombie(){
        Projectile projectile = new Projectile(DAMAGE, 5, "right", 800, getCoordenatesX(), getCoordenatesY(), characterGUI, true);
        characterGUI.addProjectile(projectile);
        projectile.startMoving();
    }
    
}
