package Presentation;

import Dominio.GameConfig;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class SettingsWindow extends Window {
    
    private Color purple = new Color(128, 0, 128); // Color naranja para botones
    private Color white = Color.WHITE;
    private AudioPlayer player = new AudioPlayer("resources/Music/SoundTrack/musicDifficultyWindow.wav");

    // Botones
    private JButton sunConfigButton = AssistantGraphic.createButton("Configurar Soles", 570, 220, 200, 40, purple, white);
    private JButton modeButton = AssistantGraphic.createButton("Seleccionar Modo", 570, 280, 200, 40, purple, white);
    private JButton timeConfigButton = AssistantGraphic.createButton("Configurar Tiempo", 570, 340, 200, 40, purple, white);
    private JButton roundConfigButton = AssistantGraphic.createButton("Numero de rondas ", 570, 400, 200, 40, purple, white);
    private JButton backButton = AssistantGraphic.createButton("Volver", 570, 460, 200, 40, purple, white);
    public SettingsWindow() {
        super("Configuración del Juego", "resources/Backgrounds/difficultyWindow.jpg");

        add(sunConfigButton);
        add(modeButton);
        add(timeConfigButton);
        add(roundConfigButton);
        add(backButton);

        configureSunConfigButton(sunConfigButton);
        configureModeButton(modeButton);
        configureTimeConfigButton(timeConfigButton);
        configureRoundConfigButton(roundConfigButton);
        configureReturnButton(backButton);

        
        player.playMusic();
        setVisible(true);
    }




    private void configureSunConfigButton(JButton button) {
        button.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Ingrese la cantidad inicial de soles:", "Configuración de Soles", JOptionPane.QUESTION_MESSAGE);
            try {
                if (input != null) {
                    int initialSuns = Integer.parseInt(input);
                    GameConfig.getInstance().setInitialSuns(initialSuns);
                    JOptionPane.showMessageDialog(this, "Soles iniciales configurados a: " + initialSuns, "Configuración Guardada", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void configureModeButton(JButton button) {
        button.addActionListener(e -> {
            String[] options = {"Modo 1", "Modo 2"};
            int choice = JOptionPane.showOptionDialog(
                this,
                "Seleccione un modo de máquina:",
                "Modo de Máquina",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );
            if (choice != -1) {
                String selectedMode = options[choice];
                GameConfig.setGameMode(selectedMode);
                JOptionPane.showMessageDialog(this, "Modo seleccionado: " + selectedMode, "Configuración Guardada", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void configureTimeConfigButton(JButton button) {
        button.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Ingrese el tiempo de partida en minutos:", "Configuración de Tiempo", JOptionPane.QUESTION_MESSAGE);
            try {
                if (input != null) {
                    int gameTime = Integer.parseInt(input);
                    if (gameTime <= 0) {
                        throw new NumberFormatException();
                    }
                    GameConfig.setGameDuration(gameTime);
                    JOptionPane.showMessageDialog(this, "Tiempo de partida configurado a: " + gameTime + " minutos", "Configuración Guardada", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void configureRoundConfigButton(JButton button) {
        button.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Ingrese la cantidad de rondas a jugar:", "Configuracion de rondas", JOptionPane.QUESTION_MESSAGE);
            try {
                if (input != null) {
                    int rounds = Integer.parseInt(input);
                    if(rounds>0){
                        GameConfig.setRounds(rounds);
                        JOptionPane.showMessageDialog(this, "Soles iniciales configurados a: " + rounds, "Configuración Guardada", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(this, "Numero de rondas invalido","Error",JOptionPane.ERROR_MESSAGE);
                    }    
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void configureReturnButton(JButton returnButton) {
        returnButton.addActionListener(e -> {
            MenuWindow menuWindow = new MenuWindow();
            player.stopMusic();
            menuWindow.setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        new SettingsWindow();
    }
}
