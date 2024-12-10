package Presentation;

import Dominio.GameController;
import Dominio.Grid;
import java.awt.Color;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
public class MenuWindow extends Window {
    public static void main(String[] args) {
        new MenuWindow();
    }
    AudioPlayer player = new AudioPlayer("resources/Music/SoundTrack/musicMainMenu.wav");
    private Color purple = new Color(0, 128, 128);
    private JButton playButton = AssistantGraphic.createButton("Jugar", 570, 260, 200, 40, purple, Color.WHITE);
    private JButton loadButton = AssistantGraphic.createButton("Cargar Partida", 570, 320, 200, 40, purple, Color.WHITE);
    private JButton difficultyButton = AssistantGraphic.createButton("Configuracion", 570, 380, 200, 40, purple, Color.WHITE);
    //private JButton scores = AssistantGraphic.createButton("Puntajes", 570, 440, 200, 40, purple, Color.WHITE);
    private JButton exitButton = AssistantGraphic.createButton("Salir", 570, 440, 200, 40, purple, Color.WHITE);

    public MenuWindow() {
        super("Menu Principal", "resources/Backgrounds/backgroundMainMenuu.jpg");

        add(playButton);
        add(loadButton);
        add(difficultyButton);
        //add(scores);    
        add(exitButton);
        configureLoadButton(loadButton);
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
            SettingsWindow difficultyWindow = new SettingsWindow();
            difficultyWindow.setVisible(true);
            dispose();
        });
    }

    private void configureLoadButton(JButton loadButton) {
    loadButton.addActionListener(e -> {
        player.stopMusic();  
        Object[] gameData = GameController.loadGame();

        if (gameData != null) {
            Grid loadedGrid = (Grid) gameData[0];
            Map<String, String> loadedCharacterTypes = (Map<String, String>) gameData[1];

            GridGUI gridGUI = new GridGUI(loadedCharacterTypes); 
            gridGUI.setLoadGame(loadedGrid);
            gridGUI.setVisible(true);
            dispose();  
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo cargar el juego.", "Error de Carga", JOptionPane.ERROR_MESSAGE);
        }
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