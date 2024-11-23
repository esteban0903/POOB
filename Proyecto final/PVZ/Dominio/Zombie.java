package Dominio;
import java.awt.Point;

import javax.swing.ImageIcon;

import Presentation.CharacterGUI;
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

    public void move() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(500); // Velocidad del movimiento (500 ms por paso)
    
                    // Determina la nueva posición basada en la dirección
                    switch (direction.toLowerCase()) {
                        case "left":
                            setPosition(getCoordenatesX() - speed, getCoordenatesY());
                            break;
                        case "right":
                            setPosition(getCoordenatesX() + speed, getCoordenatesY());
                            break;
                        case "up":
                            setPosition(getCoordenatesX(), getCoordenatesY() - speed);
                            break;
                        case "down":
                            setPosition(getCoordenatesX(), getCoordenatesY() + speed);
                            break;
                    }
    
                    // Asegúrate de que el zombie no salga del tablero
                    if (getCoordenatesX() < 0 || getCoordenatesX() > characterGUI.getWidth()
                            || getCoordenatesY() < 0 || getCoordenatesY() > characterGUI.getHeight()) {
                        break; // Detén el movimiento si está fuera del tablero
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }).start();
    }
}


