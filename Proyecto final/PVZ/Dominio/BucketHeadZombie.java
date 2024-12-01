package Dominio;
import javax.swing.ImageIcon;

import Presentation.CharacterGUI;

public class BucketHeadZombie extends  Zombie {
    private static final ImageIcon IMAGE = new ImageIcon("resources/Zombie.png");
    private static final  int SPEED = 1; 
    private static final int ARMOR = 1000;
    private static final String DIRECTION = "l";
    private static final int BRAIN_COST = 125;
    private static final int HEALTH = 100;
    private static final String NAME = "BucketHead";
    private static final int DAMAGE = 10;
    private static final int DAMAGE_TIME = 500;
    public BucketHeadZombie(int x, int y, CharacterGUI characterGUI) {
        super(NAME, HEALTH, x, y, BRAIN_COST, SPEED, ARMOR, DIRECTION, DAMAGE, DAMAGE_TIME, IMAGE, characterGUI);
    }

}
