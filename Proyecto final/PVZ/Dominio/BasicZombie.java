package Dominio;

import Presentation.CharacterGUI;
import javax.swing.ImageIcon;

/**
 * Represents a basic zombie character in the game.
 * The BasicZombie class extends the Zombie class and provides a default implementation
 * of a zombie with basic attributes such as health, speed, and an image.
 */
public class BasicZombie extends Zombie {
    private static final ImageIcon IMAGE = new ImageIcon("resources/Characters/BasicZombie.png");

    public BasicZombie(int x, int y, CharacterGUI characterGUI) {
        super("BasicZombie", 100, x, y, 0, 1, 0, "left", 100,IMAGE, characterGUI, 500 );
    }
}
