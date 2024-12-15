package Dominio;
import Presentation.CharacterGUI;
import javax.swing.ImageIcon;

/**
 * Represents a Conehead Zombie character in the game.
 * This type of zombie has a cone on its head which makes it more resilient than a basic zombie.
 * It inherits from the Zombie class and utilizes additional attributes specific to Conehead Zombies such as increased health.
 */
public class ConeheadZombie extends Zombie {
    private static final ImageIcon IMAGE = new ImageIcon("resources/Characters/ConeheadZombie.png");

    /**
     * Constructs a new ConeheadZombie with specified position and graphical user interface.
     * This constructor initializes the ConeheadZombie character with specific attributes like name, health,
     * position, speed, damage rate, movement direction, and cost along with an associated image and GUI component.
     * @param x The x-coordinate where the zombie will start.
     * @param y The y-coordinate where the zombie will start.
     * @param characterGUI The graphical user interface component associated with this zombie.
     */
    public ConeheadZombie(int x, int y, CharacterGUI characterGUI) {
        super("ConeheadZombie", 1000, x, y, 150, 1, 280, "left", 100, IMAGE, characterGUI, 500);
    }
}
