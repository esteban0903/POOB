package Dominio;

import Presentation.CharacterGUI;

/**
 * A factory class for creating new instances of characters based on the specified type.
 * This class uses a factory method to return instances of different types of characters.
 * Each character type corresponds to a specific class that extends the abstract Character class.
 */
public class CharacterFactory {
    /**
     * Creates a character based on the specified type. The character's initial position and GUI representation are also specified.
     * This method uses a switch statement to determine which type of character to create based on the given type parameter.
     * @param type The type of character to create. This should correspond to one of the known character types.
     * @param x The x-coordinate where the character will be initially placed.
     * @param y The y-coordinate where the character will be initially placed.
     * @param characterGUI The graphical user interface component associated with the character, used in some character types.
     * @return A new instance of a Character, depending on the type parameter.
     * @throws IllegalArgumentException If the type is not recognized, an exception is thrown indicating the unknown type.
     */
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
            case "evolve" -> {
                return new Evolve(x, y, characterGUI);
            }
            default -> throw new IllegalArgumentException("Unknown character type: " + type);
        }
    }
}
