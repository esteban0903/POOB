package Dominio;

import Presentation.AssistantGraphic;
import Presentation.AudioPlayer;
import javax.swing.*;


public class Sun {
    private final JPanel PANEL;
    private int value = 25; // valor del sol al dar el click 
    private static final AudioPlayer player = new AudioPlayer("resources/Music/Efects/sunSound.wav");

    public Sun(String imagePath, int size, Runnable onClickAction) {
        PANEL = AssistantGraphic.createButtonWithImage(imagePath, e -> onClickAction.run(), "Sun", size-20, size-20);

    }

    public JPanel getPanel() {
        return PANEL;
    }


    public int getValue() {
        return value;
    }

    public void makeSound(){
        player.playSoundOnce();
    }
}
