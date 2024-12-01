package Dominio;

import javax.swing.*;

import Presentation.GameController;


public class Sun {
    private JPanel panel;
    private int value = 25; // valor del sol al dar el click 

    public Sun(String imagePath, int size, Runnable onClickAction) {
        panel = GameController.createButtonWithImage(imagePath, e -> onClickAction.run(), "Sun", size, size);

    }

    public JPanel getPanel() {
        return panel;
    }


    public int getValue() {
        return value;
    }
}
