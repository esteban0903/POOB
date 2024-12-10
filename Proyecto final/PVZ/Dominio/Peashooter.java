package Dominio;

import Presentation.CharacterGUI;
import javax.swing.ImageIcon;

public class Peashooter extends Plant {
    private static final int DAMAGE = 20;
    private static final int SHOOT_INTERVAL = 1500; 
    private CharacterGUI characterGUI;

    public Peashooter(int x, int y, CharacterGUI characterGUI) {
        super("Peashooter", 300, x, y, 100, new ImageIcon("resources/Characters/Peashooter.png"));
        this.characterGUI = characterGUI;
    }
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
                    if (!characterGUI.getGrid().hasZombieInRow(currentRow)) continue;

                    Projectile projectile = new Projectile(DAMAGE, 5, "right", 800, getCoordenatesX(), getCoordenatesY(), characterGUI);
                    characterGUI.addProjectile(projectile);
                    projectile.startMoving();
                }
            } catch (InterruptedException e) {
                e.printStackTrace(); 
            }
        }).start();
    }
    
    @Override
    public void stop(){
        
    }
}
