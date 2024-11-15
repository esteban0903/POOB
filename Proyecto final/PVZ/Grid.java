import java.util.ArrayList;
import java.util.List;

public class Grid {

    private List<Character> characters;


    public Grid() {
        this.characters = new ArrayList<>();
    }

    public void addCharacter(Character character) {
        characters.add(character);
    }


    public void removeCharacter(Character character) {
        characters.remove(character);
    }


    public void moveCharacter(Character character, double newX, double newY) {
        if (characters.contains(character)) {
            character.setPosition(newX, newY);
        }
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public List<Character> findCharactersNear(double x, double y, double radius) {
        List<Character> nearbyCharacters = new ArrayList<>();
        for (Character character : characters) {
            double distance = Math.sqrt(Math.pow(character.getX() - x, 2) + Math.pow(character.getY() - y, 2));
            if (distance <= radius) {
                nearbyCharacters.add(character);
            }
        }
        return nearbyCharacters;
    }
}