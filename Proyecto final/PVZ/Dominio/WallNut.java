package Dominio;

import javax.swing.ImageIcon;

public class WallNut extends Plant{
    private static final String NAME = "WallNut";
    private static final int HEALTH = 4000;  
    private static final int COST = 50;
    private static final ImageIcon image  = new ImageIcon("resources/Wallnut.png");

    public WallNut(int  x, int y) {
        super(NAME, HEALTH, x, y, COST, image);
    }

}
