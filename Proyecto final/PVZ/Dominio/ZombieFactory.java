package Dominio;

import Presentation.CharacterGUI;
import java.util.Random;

public class ZombieFactory {

    private static final Random random = new Random();

    public static Character createZombie(int x, int y, CharacterGUI characterGUI) {
        int zombieType = random.nextInt(3);  // Genera un número entre 0 y 2
        return switch (zombieType) {
            case 0 -> new BasicZombie(x, y, characterGUI);
            case 1 -> new BucketHeadZombie(x, y, characterGUI);
            case 2 -> new ConeheadZombie(x, y, characterGUI);
            default -> new BasicZombie(x, y, characterGUI);
        }; // Fallback seguro
    }
}
