package Presentation;
import Dominio.Peashooter;
import Dominio.Sunflower;
import Dominio.Character;
public class CharacterFactory {
    public static Character createCharacter(String type, int x, int y) {
        switch (type.toLowerCase()) {
            case "peashooter":
                return new Peashooter(x, y);
            case "sunflower":
                return new Sunflower(x, y);
            default:
                throw new IllegalArgumentException("Tipo de personaje desconocido: " + type);
        }
    }
}
