package Presentation;

import javax.swing.JButton;
import java.awt.Color;

public class DifficultyWindow extends Window {

    private Color orange = new Color(128, 0, 128); //color naranja  de boton
    private Color white = Color.WHITE;
    AudioPlayer player = new AudioPlayer("resources/musicDifficultyWindow.wav");
    //los valores se asignan manualmente, x ,y ...
    private JButton easyButton = GameController.createButton("Facil", 570, 220, 200, 40, orange , white);
    private JButton mediumButton = GameController.createButton("Medio", 570, 280, 200, 40, orange , white);
    private JButton hardButton = GameController.createButton("Dificil", 570, 340, 200, 40, orange , white);
    private JButton backButton = GameController.createButton("Volver", 570, 400, 200, 40, orange , white);


    public DifficultyWindow() {
        super("Dificultad", "resources/difficultyWindow.jpg");

        add(easyButton);
        add(mediumButton);
        add(hardButton);
        add(backButton);
        setVisible(true);
        configureReturnButton(backButton);
        player.playMusic();

    }
    public void configureReturnButton(JButton returnButton) {
        returnButton.addActionListener(e -> {
            MenuWindow menuWindow = new MenuWindow();
            player.stopMusic();
            menuWindow.setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        new DifficultyWindow();
    }
}
