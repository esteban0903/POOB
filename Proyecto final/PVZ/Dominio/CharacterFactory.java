package Dominio;

import Presentation.CharacterGUI;

public class CharacterFactory {
    public static Character createCharacter(String type, int x, int y, CharacterGUI characterGUI) {
        switch (type.toLowerCase()) {
            case "peashooter" -> {
                return new Peashooter(x, y, characterGUI);
            }
            case "sunflower" -> {
                return new Sunflower(x, y);
            }
            case "wallnut" -> {
                return new WallNut(x, y);
            }
            case "basiczombie" -> {
                return new BasicZombie(x, y, characterGUI);
            }
            case "eciplant" -> {
                return new ECIPlant(x, y);
            }
            default -> throw new IllegalArgumentException("Tipo de personaje desconocido: " + type);
        }
    }
}
