package Presentation;

import Dominio.GameConfig;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class NameInputWindow extends Window {
    
    private Color orange = new Color(128, 0, 128); // Color naranja para botones
    private Color white = Color.WHITE;
    private AudioPlayer player = new AudioPlayer("resources/Music/SoundTrack/musicDifficultyWindow.wav");

    // Botones
    private JButton namePlayerButton = AssistantGraphic.createButton("Nombre jugador 1 ", 570, 220, 200, 40, orange, white);
    private JButton continueButton = AssistantGraphic.createButton("Continuar", 570, 280, 200, 40, orange, white);
    private JButton backButton = AssistantGraphic.createButton("Volver", 570, 340, 200, 40, orange, white);
    public NameInputWindow() {
        super("NameInput", "resources/Backgrounds/nameChoose.jpg");

        add(namePlayerButton);
        add(continueButton);
        add(backButton);

        configurenamePlayerButton(namePlayerButton);
        configureContinueButton(continueButton);
        configureReturnButton(backButton);

        
        player.playMusic();
        setVisible(true);
    }




    private void configurenamePlayerButton(JButton button) {
        button.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Ingrese su nombre:", "Jugador 1", JOptionPane.QUESTION_MESSAGE);
            try {
                if (input != null) {
                    String name = input.trim();
                    GameConfig.setName(name);
                    JOptionPane.showMessageDialog(this, "Nombre asignado como:" + name, "Configuración Guardada", JOptionPane.INFORMATION_MESSAGE);
                    
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void configureReturnButton(JButton returnButton) {
        returnButton.addActionListener(e -> {
            GameModeWindow gameModeWindow = new GameModeWindow();
            player.stopMusic();
            gameModeWindow.setVisible(true);
            dispose();
        });
    }

    private void configureContinueButton(JButton continueButton){
        continueButton.addActionListener(e -> {
            CharacterSelectionWindow characterSelectionWindow = new CharacterSelectionWindow();
            player.stopMusic();
            characterSelectionWindow.setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        new NameInputWindow();
    }
}
