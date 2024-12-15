package Dominio;

import Presentation.CharacterGUI;
import java.util.Random;


/**
 * Factory class for creating zombies with random types.
 */
public class ZombieFactory {

    private static final Random random = new Random();

    /**
     * Creates a zombie at the specified position with a randomly selected type.
     * Utilizes a random number generator to decide which type of zombie to create.
     *
     * @param x The x-coordinate where the zombie will be placed.
     * @param y The y-coordinate where the zombie will be placed.
     * @param characterGUI The graphical user interface component associated with the zombie.
     * @return A new instance of a zombie, which could be any of the defined types.
     */
    public static Character createZombie(int x, int y, CharacterGUI characterGUI) {
        int zombieType = random.nextInt(3);  
        return switch (zombieType) {
            case 0 -> new BasicZombie(x, y, characterGUI);
            case 1 -> new BucketHeadZombie(x, y, characterGUI);
            case 2 -> new ConeheadZombie(x, y, characterGUI);
            default -> new BasicZombie(x, y, characterGUI);
        }; 
    }
}
