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

    public void attack(Character enemy) {
        enemy.takeDamage(10);
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
                    Thread.sleep(500); // velocidad del zombie
    
                    // Obtén las coordenadas actuales
                    int currentRow = (getCoordenatesY() - GridGUI.GRID_Y_BASE) / characterGUI.getCellSize();
                    int currentCol = (getCoordenatesX() - GridGUI.GRID_X_BASE) / characterGUI.getCellSize();
    
                    // Calcula la nueva posición basada en la dirección
                    int[] newPosition = calculateNewPosition();
                    int newX = newPosition[0];
                    int newY = newPosition[1];
    
                    // Calcula las nuevas celdas
                    int newRow = (newY - GridGUI.GRID_Y_BASE) / characterGUI.getCellSize();
                    int newCol = (newX - GridGUI.GRID_X_BASE) / characterGUI.getCellSize();
    
                    // Valida si el movimiento es permitido
                    if (!isValidMove(newRow, newCol, characterGUI)) {
                        System.out.println("Zombie detenido: encontró una planta o salió del tablero.");
                        break; // Detén el movimiento si no es válido
                    }
    
                    // Mueve el zombie a la nueva posición
                    setPosition(newX, newY);
    
                    // Si cambia de celda, actualiza la matriz
                    if (newRow != currentRow || newCol != currentCol) {
                        characterGUI.updatePositionInBoard(this, currentRow, currentCol, newRow, newCol);
                    }
    
                    characterGUI.repaint(); // Redibuja el tablero
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
    
    private boolean isValidMove(int newRow, int newCol, CharacterGUI characterGUI) {
        if (newRow < 0 || newRow >= characterGUI.getBoard().length ||  newCol < 0 || newCol >= characterGUI.getBoard()[0].length) {
            return false;
        }
        return !characterGUI.hasPlantInCell(newRow, newCol);
    }
}


