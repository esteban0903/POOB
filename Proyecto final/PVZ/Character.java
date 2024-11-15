public class Character {   

    protected int health;
    protected double x,y;

    public Character(int health, double x, double y){
        this.health = health;
        this.x = x;
        this.y = y;

    }
    
    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }                                



















    

}
