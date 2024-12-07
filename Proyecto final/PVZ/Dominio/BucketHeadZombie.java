package Dominio;
import javax.swing.ImageIcon;

import Presentation.CharacterGUI;

public class BucketHeadZombie extends  Zombie {
    private static final ImageIcon IMAGE = new ImageIcon("resources/BucketHeadZombie.png");
    public BucketHeadZombie(int x, int y, CharacterGUI characterGUI) {
        super("BucketHeadZombie", 100, x, y, 200, 1, 700, "left", 100, IMAGE, characterGUI,500);
    }

}
