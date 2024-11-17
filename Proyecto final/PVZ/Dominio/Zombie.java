package Dominio;
import javax.swing.ImageIcon;
public class Zombie extends Character {
    private int speed;
    private int armor;
    private String direction;
    private int brainCost;
    private int damage;
    private int damage_Time;
    private Projectile projectile;

    public Zombie(String name, int health, int x, int y, int brainCost, int speed, int armor, String direction, int damage, int damageTime, ImageIcon image) {
        super(name, health, x, y, brainCost, image);
        this.speed = speed;
        this.armor = armor;
        this.direction = direction;
        this.brainCost = brainCost;
        this.damage = damage;
        this.damage_Time = damageTime;
    }
    public void attack(Character enemy) {
        enemy.takeDamage(10);
    }

    public void move(){

    }
}


