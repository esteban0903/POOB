package Dominio;
import Presentation.CharacterGUI;
import javax.swing.ImageIcon;

public class ECIZombie extends Zombie {
    private static final ImageIcon IMAGE = new ImageIcon("resources/Characters/EciZombie.png");
    public ECIZombie(int x, int y, CharacterGUI characterGUI) {
        super("EciZombie", 200, x, y, 250, 1, 700, "l", 50, IMAGE, characterGUI,3000);
    }

}
