package Dominio;

import javax.swing.ImageIcon;

public abstract class Character {   
    private String name;
    private int health;
    private int x,y;
    private boolean isAlive;   
    private ImageIcon image;
    private int cost;

    public Character(String name,int health, int x, int y, int cost, ImageIcon image) {
        this.name = name;
        this.health = health;
        this.x = x;
        this.y = y;
        this.isAlive=true;
        this.cost = cost;
        this.image = image;

    }
    
    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public interface attackable{
        public void attack(Character enemy);
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getCoordenatesX(){
        return  x;
    }

    public int getCoordenatesY(){
        return  y;
    }

    public ImageIcon getImage(){
        return image;
    }

    public String getName(){
        return name;
    }

}
