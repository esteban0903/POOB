package Dominio;

import javax.swing.ImageIcon;
import Presentation.CharacterGUI;

public class BasicZombie extends Zombie {
    private static final ImageIcon IMAGE = new ImageIcon("resources/BasicZombie.png");
    private static final int SPEED = 20; 
    private static final int ARMOR = 0;
    private static final String DIRECTION = "left"; 
    private static final int BRAIN_COST = 0;
    private static final int HEALTH = 100;
    private static final String NAME = "BasicZombie";
    private static final int DAMAGE = 10;
    private static final int DAMAGE_TIME = 500;

    public BasicZombie(int x, int y, CharacterGUI characterGUI) {
        super(NAME, HEALTH, x, y, BRAIN_COST, SPEED, ARMOR, DIRECTION, DAMAGE,IMAGE, characterGUI);
    }
}
