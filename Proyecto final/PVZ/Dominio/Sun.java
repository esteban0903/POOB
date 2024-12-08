package Dominio;

import javax.swing.*;

import Presentation.AudioPlayer;
import Presentation.AssistantGraphic;


public class Sun {
    private JPanel panel;
    private int value = 25; // valor del sol al dar el click 
    private static final AudioPlayer player = new AudioPlayer("resources/Efects/sunSound.wav");

    public Sun(String imagePath, int size, Runnable onClickAction) {
        panel = AssistantGraphic.createButtonWithImage(imagePath, e -> onClickAction.run(), "Sun", size-20, size-20);

    }

    public JPanel getPanel() {
        return panel;
    }


    public int getValue() {
        return value;
    }

    public void makeSound(){
        player.playSoundOnce();
    }
}
