package Dominio;

import Presentation.AssistantGraphic;
import Presentation.AudioPlayer;
import javax.swing.*;

/**
 * Represents a sun in the game, which players can collect to gain resources.
 */
public class Sun {
    private final JPanel PANEL;
    private int value = 25; // valor del sol al dar el click 
    private static final AudioPlayer player = new AudioPlayer("resources/Music/Efects/sunSound.wav");

    /**
     * Constructs a new Sun with an image, size, and an action to perform on click.
     *
     * @param imagePath Path to the image representing the sun.
     * @param size The size of the sun icon.
     * @param onClickAction The action to perform when the sun is clicked.
     */

    public Sun(String imagePath, int size, Runnable onClickAction) {
        PANEL = AssistantGraphic.createButtonWithImage(imagePath, e -> onClickAction.run(), "Sun", size-20, size-20);

    }

    /**
     * Returns the JPanel component of the sun, which contains its graphical representation.
     *
     * @return The JPanel containing the sun's image and configured action.
     */
    public JPanel getPanel() {
        return PANEL;
    }

    /**
     * Returns the resource value of the sun, which players receive upon clicking the sun.
     *
     * @return The integer value of the sun.
     */
    public int getValue() {
        return value;
    }

    /**
     * Plays a sound effect when the sun is clicked.
     */
    public void makeSound(){
        player.playSoundOnce();
    }
}
