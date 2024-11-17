package Dominio;
import javax.swing.ImageIcon;

public class ECIPlant extends Plant {
    private static final String NAME = "Sunflower";
    private static final int HEALTH = 150;
    public static final int COST = 75;
    private static final int SUN_PRODUCTION_TIME = 2000;
    private static final int SUN_PRODUCTION_QUANTITY = 50;
    private static final ImageIcon IMAGE  =new ImageIcon("resources/Sunflower.png");
    public ECIPlant(int x, int y) {
        super(NAME, HEALTH, x, y, COST, IMAGE);
    }
}
