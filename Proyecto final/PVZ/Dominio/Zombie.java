package Dominio;

import java.awt.Point;

import javax.swing.ImageIcon;

import Presentation.CharacterGUI;
import Presentation.GridGUI;
public class Zombie extends Character {
    private CharacterGUI characterGUI;
    private int speed;
    private int armor;
    private String direction;
    private int brainCost;
    private int damage;
    private int damage_Time;
    private Projectile projectile;
    private static final String TYPE = "Zombie";

    public Zombie(String name, int health, int x, int y, int brainCost, int speed, int armor, String direction, int damage, int damageTime, ImageIcon image, CharacterGUI characterGUI) {
        super(name, health, x, y, brainCost, image, TYPE);
        this.speed = speed;
        this.armor = armor;
        this.direction = direction;
        this.brainCost = brainCost;
        this.damage = damage;
        this.damage_Time = damageTime;
        this.characterGUI = characterGUI;
    }
    public void attack(Character plant) {
        System.out.println(getName() + " está atacando a " + plant.getName() + plant.getHealth());
        plant.takeDamage(10);
    }

    private void handleAttack(Character plant, int row, int col, CharacterGUI characterGUI) throws InterruptedException { 
        attack(plant); 
        if (plant.getHealth() <= 0) {
            characterGUI.removeCharacter(plant, row, col); 
        } else {
            Thread.sleep(500); // El thread necesita la excepcion InterruptedException :)
        }
    }

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
        if (characterGUI != null) {
            characterGUI.repaint(); // Redibuja automáticamente
        }
    }

    public void move(CharacterGUI characterGUI) {
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(500); // Velocidad (revisar)
    
                    // Posicion donde va el zombie
                    int currentRow = (getCoordenatesY() - GridGUI.GRID_Y_BASE) / characterGUI.getCellSize();
                    int currentCol = (getCoordenatesX() - GridGUI.GRID_X_BASE) / characterGUI.getCellSize();
    
                    // Posicion a donde se movio el zombie
                    int[] newPosition = calculateNewPosition();
                    int newX = newPosition[0];
                    int newY = newPosition[1];
    
                    // Calcula la nueva posición del zombie en el tablero (matriz)
                    int newRow = (newY - GridGUI.GRID_Y_BASE) / characterGUI.getCellSize();
                    int newCol = (newX - GridGUI.GRID_X_BASE) / characterGUI.getCellSize();
    
                    // Mirar si hay una planta
                    if (characterGUI.hasPlantInCell(newRow, newCol)) {
                        Character plant = characterGUI.getPlantInCell(newRow, newCol);
                        handleAttack(plant, newRow, newCol, characterGUI);
                        continue; // Vuelve a verificar después del ataque
                    }
    
                    setPosition(newX, newY);
    
                    // Si el zombie se movio de casilla, lo cambia en la matriz
                    if (newRow != currentRow || newCol != currentCol) {
                        characterGUI.updatePositionInBoard(this, currentRow, currentCol, newRow, newCol);
                    }
    
                    characterGUI.repaint(); 
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    
       
    private int[] calculateNewPosition() {
        int newX = getCoordenatesX();
        int newY = getCoordenatesY();
    
        switch (direction.toLowerCase()) {
            case "left":
                newX -= speed;
                break;
            case "right":
                newX += speed;
                break;
            case "up":
                newY -= speed;
                break;
            case "down":
                newY += speed;
                break;
        }
        return new int[]{newX, newY};
    }
    
}


