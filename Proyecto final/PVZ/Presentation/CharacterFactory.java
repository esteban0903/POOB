package Presentation;

import Dominio.WallNut;
import Dominio.BasicZombie;
import Dominio.Peashooter;
import Dominio.Sunflower;
import Dominio.Character;

public class CharacterFactory {
    public static Character createCharacter(String type, int x, int y, CharacterGUI characterGUI) {
        switch (type.toLowerCase()) {
            case "peashooter":
                return new Peashooter(x, y);
            case "sunflower":
                return new Sunflower(x, y);
            case "wallnut":
                return new WallNut(x, y);
            case "basiczombie":
                return new BasicZombie(x, y, characterGUI); // Pasa la referencia al tablero
            default:
                throw new IllegalArgumentException("Tipo de personaje desconocido: " + type);
        }
    }
}
