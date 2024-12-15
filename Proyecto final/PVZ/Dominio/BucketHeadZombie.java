package Dominio;
import Presentation.CharacterGUI;
import javax.swing.ImageIcon;

/**
 * Represents a BucketHeadZombie, a type of Zombie in the game.
 * The BucketHeadZombie has a bucket on its head and increased health compared to a regular Zombie.
 */
public class BucketHeadZombie extends  Zombie {
    private static final ImageIcon IMAGE = new ImageIcon("resources/Characters/BucketHeadZombie.png");
    public BucketHeadZombie(int x, int y, CharacterGUI characterGUI) {
        super("BucketHeadZombie", 100, x, y, 200, 1, 700, "left", 100, IMAGE, characterGUI,500);
    }

}
