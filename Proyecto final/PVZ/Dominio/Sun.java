package Dominio;

import javax.swing.*;

import Presentation.GameController;


public class Sun {
    private JPanel panel;
    private int value = 25; // Valor del sol (puntos que otorga)

    public Sun(String imagePath, int size, Runnable onClickAction) {
        // Crear el botón con la imagen del sol
        panel = GameController.createButtonWithImage(imagePath, e -> onClickAction.run(), "Sun", size, size);
    }

    public JPanel getPanel() {
        return panel;
    }

    public int getValue() {
        return value;
    }
}
