package Dominio;

public class Projectile {
    private int damage;
    private int speed;
    private int targetX;
    private int targetY;
    private int direction;
    private int range;

    public Projectile(int damage, int speed, int targetX, int targetY, int direction, int range) {
        this.damage = damage;
        this.speed = speed;
        this.targetX = targetX;
        this.targetY = targetY;
        this.direction = direction;
        this.range = range;
    }
}
