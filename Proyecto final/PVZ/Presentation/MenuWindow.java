package Presentation;

import javax.swing.JButton;

import java.awt.Color;
public class MenuWindow extends Window {
    public static void main(String[] args) {
        new MenuWindow();
    }
    AudioPlayer player = new AudioPlayer("resources/musicMainMenu.wav");
    private Color purple = new Color(0, 128, 128);
    private JButton playButton = GameController.createButton("Jugar", 570, 260, 200, 40, purple, Color.WHITE);
    private JButton loadButton = GameController.createButton("Cargar Partida", 570, 320, 200, 40, purple, Color.WHITE);
    private JButton difficultyButton = GameController.createButton("Dificultad", 570, 380, 200, 40, purple, Color.WHITE);
    private JButton scores = GameController.createButton("Puntajes", 570, 440, 200, 40, purple, Color.WHITE);
    private JButton exitButton = GameController.createButton("Salir", 570, 500, 200, 40, purple, Color.WHITE);

    public MenuWindow() {
        super("Menu Principal", "resources/backgroundMainMenuu.jpg");

        add(playButton);
        add(loadButton);
        add(difficultyButton);
        add(scores);
        add(exitButton);
        configurePlayButton(playButton);
        configureExitButton(exitButton);
        configureDifficultyButton(difficultyButton);

        showWindow();
        player.playMusic();
    }


    private void configureExitButton(JButton exitButton) {
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void configureDifficultyButton(JButton difficultyButton) {
        difficultyButton.addActionListener(e -> {
            player.stopMusic();
            DifficultyWindow difficultyWindow = new DifficultyWindow();
            difficultyWindow.setVisible(true);
            dispose();
        });
    }

    private void configurePlayButton(JButton startButton) {
        startButton.addActionListener(e -> {
            player.stopMusic();
            GameModeWindow gameModeWindow = new GameModeWindow();
            gameModeWindow.setVisible(true);
            dispose();
        });
    }
    
}