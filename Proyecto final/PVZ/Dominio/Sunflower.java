package Dominio;

import javax.swing.ImageIcon;

public class Sunflower extends Plant{
    private static final String NAME = "Sunflower";
    private static final int HEALTH = 300;
    public static final int COST = 50;
    private static final int SUN_PRODUCTION_TIME = 2000;
    private static final int SUN_PRODUCTION_QUANTITY = 25;
    private static final ImageIcon IMAGE  =new ImageIcon("resources/Sunflower.png");
    public Sunflower(int x, int y) {
        super(NAME, HEALTH, x, y, COST, IMAGE);
    }
}
