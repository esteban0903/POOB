package Dominio;

import javax.swing.ImageIcon;

public class Peashooter extends Plant{
    private Projectile projectile;
    private static final String NAME = "Peashooter";
    private static final int HEALTH = 30; 
    private static final int COST = 100;
    private static final int DAMAGE = 20;
    private static final double TIME_DAMAGE = 1.5;
    private static final ImageIcon image  = new ImageIcon("resources/Peashooter.png");

    public Peashooter(int  x, int y) {
        super(NAME, HEALTH, x, y, COST, image);
    }
    public void attack(Character enemy) {
        enemy.takeDamage(10);
    }
    
}
