package Dominio;
import Presentation.CharacterGUI;
import javax.swing.ImageIcon;

/**
 * Represents the ECIZombie, a type of zombie character in the game.
 * This class extends the Zombie base class, adapting the standard zombie properties to fit the specific traits of an ECIZombie.
 * It comes with a unique image and adjusted parameters like health, speed, and damage capabilities.
 */
public class ECIZombie extends Zombie {
    private static final ImageIcon IMAGE = new ImageIcon("resources/Characters/EciZombie.png");

    /**
     * Constructs a new ECIZombie with specified position and graphical user interface.
     * This constructor initializes the ECIZombie with customized attributes including name, health, position, movement speed,
     * attack damage, and a unique image, tailored for this specific type of zombie.
     * 
     * @param x The x-coordinate where the zombie will start.
     * @param y The y-coordinate where the zombie will start.
     * @param characterGUI The graphical user interface component associated with this zombie.
     */
    public ECIZombie(int x, int y, CharacterGUI characterGUI) {
        super("EciZombie", 200, x, y, 250, 1, 700, "l", 50, IMAGE, characterGUI, 3000);
    }

}
