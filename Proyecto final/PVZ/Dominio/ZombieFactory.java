package Dominio;

import java.util.Random;
import Presentation.CharacterGUI;

public class ZombieFactory {

    private static Random random = new Random();

    public static Character createZombie(int x, int y, CharacterGUI characterGUI) {
        int zombieType = random.nextInt(3);  // Genera un número entre 0 y 2

        switch (zombieType) {
            case 0:
                return new BasicZombie(x, y, characterGUI);
            case 1:
                return new BucketHeadZombie(x, y, characterGUI);
            case 2:
                return new ConeheadZombie(x, y, characterGUI);
            default:
                return new BasicZombie(x, y, characterGUI);  // Fallback seguro
        }
    }
}
