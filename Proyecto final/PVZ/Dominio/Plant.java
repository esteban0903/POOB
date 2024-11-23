package Dominio;

import javax.swing.ImageIcon;

public abstract class Plant extends Character{
    private int sunCost;
    private static final String TYPE = "Plant";

    public Plant(String name, int health, int x, int y, int sunCost, ImageIcon image) {
        super(name, health, x, y, sunCost, image, TYPE);
    }
}
