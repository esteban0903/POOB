package Dominio;
import Presentation.CharacterGUI;
import javax.swing.ImageIcon;

public class ConeheadZombie extends Zombie {
    private static final ImageIcon IMAGE = new ImageIcon("resources/Characters/ConeheadZombie.png");

    public ConeheadZombie(int x, int y, CharacterGUI characterGUI) {
        super("ConeheadZombie", 1000, x, y, 150, 1, 280, "left", 100, IMAGE, characterGUI,500);
    }

}
