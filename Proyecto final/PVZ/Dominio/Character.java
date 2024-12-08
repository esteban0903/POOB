package Dominio;

import java.io.Serializable;

import javax.swing.ImageIcon;

public abstract class Character implements Serializable {   
    protected String name;
    protected int health;
    private int x,y;
    private boolean isAlive;   
    protected ImageIcon image;
    protected int cost;
    private String type;

    public Character(String name,int health, int x, int y, int cost, ImageIcon image, String type) {
        this.name = name;
        this.health = health;
        this.x = x;
        this.y = y;
        this.isAlive=true;
        this.cost = cost;
        this.image = image;
        this.type = type;

    }
    
    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            this.isAlive = false;
        }
    }

    public void move(){

    }

    public boolean isAlive() {
        return isAlive;
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
    public int getCost(){
        return cost;
    }
    public String getType(){
        return type;
    }

    public int getHealth(){
        return health;
    }


    protected void setImage(ImageIcon image){
        this.image = image;
    }

    protected void setName(String name){
        this.name = name;
    }

    protected void setCost(int cost){
        this.cost = cost;
    }

    protected void setCharacterName(String name){
        this.name = name;
    }

    protected void setCharacterHealth(int health){
        this.health = health;
    }

    public boolean isZombie(){
        return false;
    }

    public boolean isLawnMower(){
        return false;
    }
    
    public abstract void stop();

    
}
