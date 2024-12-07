package Dominio;

import javax.swing.ImageIcon;

import Presentation.AudioPlayer;
import Presentation.CharacterGUI;
import Presentation.GameConfig;

public class Projectile {
    private int damage;
    private int speed;
    private int range;
    private int x, y;
    private CharacterGUI characterGUI;
    private static final ImageIcon IMAGE = new ImageIcon("resources/bullet.png");
    private static final AudioPlayer player = new AudioPlayer("resources/Efects/ProjectilePlant.wav");

    public Projectile(int damage, int speed, String direction, int range, int x, int y, CharacterGUI characterGUI) {
        this.damage = damage;
        this.speed = speed;
        this.range = range;
        this.x = x+50; //para que se pinte un poco despues ( se vea salir de la boca del peashooter)
        this.y = y;
        this.characterGUI = characterGUI;
    }

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

                    if (verifyRangesBullet() || makeDamageZombie()) break;
                    
                    characterGUI.repaint();
                }
                characterGUI.removeProjectile(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private boolean verifyRangesBullet(){
        if (x > CharacterGUI.GRID_X_BASE + characterGUI.getGrid().getColumns() * characterGUI.getCellSize()) {
            System.out.println("Proyectil fuera del tablero.");
            characterGUI.removeProjectile(this);
            return true;
        }
        return false;
    }
    private boolean makeDamageZombie() {
        int currentRow = characterGUI.getGrid().getRowFromY(y);
        int currentCol = characterGUI.getGrid().getColFromX(x);
        if (characterGUI.getGrid().hasZombieInCell(currentRow, currentCol)) {
            Character zombie = characterGUI.getGrid().getZombieInCell(currentRow, currentCol);
            zombie.takeDamage(damage);
            player.playSoundOnce();
            System.out.println("Zombie golpeado. Vida restante: " + zombie.getHealth());
    
            characterGUI.removeProjectile(this);
            if (!zombie.isAlive()) {
                killZombie(zombie, currentRow, currentCol);
            }
            return true; // parar bala
        }
        return false; // continuar bala
    }

    private void killZombie(Character zombie, int currentRow, int currentCol) {
            characterGUI.getGrid().removeCharacter(zombie, currentRow, currentCol);
            characterGUI.repaint();     
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ImageIcon getImage() {
        return IMAGE;
    }

    public void stop(){
        
    }
}
