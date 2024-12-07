package Dominio;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import Presentation.AudioPlayer;
import Presentation.CharacterGUI;
import Presentation.GameConfig;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Zombie extends Character {
    private int speed; 
    private int armor;
    private String direction; 
    private int damage; 
    private CharacterGUI characterGUI;
    private static final String TYPE = "Zombie";
    private int damageTime;
    private Timer attackTimer; // timer para que el zombie ataque cada cierto tiempo 
    private static final AudioPlayer player = new AudioPlayer("resources/Efects/attackZombie.wav");

    public Zombie(String name, int health, int x, int y, int brainCost, int speed, int armor, String direction, int damage, ImageIcon image, CharacterGUI characterGUI, int damageTime) {
        super(name, health, x, y, brainCost, image, TYPE);
        this.speed = speed;
        this.armor = armor;
        this.direction = direction;
        this.damage = damage;
        setCharacterHealth(health + armor);
        this.characterGUI = characterGUI;
        this.attackTimer = null; 
        this.damageTime = damageTime;
    }

    public void attack(Character plant) {
        System.out.println(getName() + " bajando vida " + plant.getHealth());
        plant.takeDamage(damage);
    }

    private void startAttack(Character plant, int row, int col) {
        if (attackTimer != null && attackTimer.isRunning()) {
            return; 
        }

        attackTimer = new Timer(damageTime, new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                if (plant.isAlive() && characterGUI.getGrid().getCharactersInCell(row, col).contains(plant) && !GameConfig.getIsPaused()) {
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

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
        characterGUI.repaint();
    }

    public void move() {
        new Thread(() -> {
            try {
                while (isAlive() && direction != null) {
                    if (GameConfig.getIsPaused()) {
                        Thread.sleep(50); 
                        continue;
                    }
                    Thread.sleep(50); 
                    
                    //mover al zombie con su speed 
                    int[] newPosition = calculateNewPosition(speed);
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


    private boolean hasReachedBoardLimit(int newRow, int newCol) {
        if (!characterGUI.getGrid().isValidPosition(newRow, newCol)) {
            GameConfig.setIsGameOver();
            System.out.println("Game Over");
            return true;
        }
        return false;
    }
    
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
    
    private void updatePosition(int newX, int newY, int currentRow, int currentCol, int newRow, int newCol) {
        setPosition(newX, newY);
        if (currentRow != newRow || currentCol != newCol) {
            characterGUI.getGrid().moveCharacter(this, currentRow, currentCol, newRow, newCol);
        }
    }

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
    public void stop() {
        if (this.attackTimer != null && this.attackTimer.isRunning()) {
            this.attackTimer.stop();
        }

        this.direction = null;  
    
    
    }

    @Override
    public final boolean isZombie(){
        return true;
    }

}
