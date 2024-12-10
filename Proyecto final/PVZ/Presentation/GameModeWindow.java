package Presentation;

import java.awt.Color;
import javax.swing.JButton;
public class GameModeWindow extends Window {
    public static void main(String[] args) {
        new GameModeWindow();
    }
    AudioPlayer player = new AudioPlayer("resources/Music/SoundTrack/gameMode.wav");
    private final Color orange = new Color(210, 105, 30);
    private final Color white = Color.WHITE;

    private final JButton onePlayerButton = AssistantGraphic.createButton("Un jugador", 590, 240, 200, 40, orange , white);
    private final JButton twoPlayerButton = AssistantGraphic.createButton("Dos jugadores", 590, 300, 200, 40, orange , white);
    private final JButton playerMachineButton = AssistantGraphic.createButton("CPU VS CPU", 590, 360, 200, 40, orange , white);
    private final JButton returnButton = AssistantGraphic.createButton("Volver", 590, 420, 200, 40, orange , white);
    public GameModeWindow() {   
        super("Modo de juego", "resources/Backgrounds/gameModeWindoww.jpg");

        add(twoPlayerButton);
        add(onePlayerButton);
        add(playerMachineButton);
        add(returnButton);
        configureOnePlayerButton(onePlayerButton);
        configureReturnButton(returnButton);

        showWindow();
        player.playMusic();
    }

    public void configureOnePlayerButton(JButton onePlayerButton) {
        onePlayerButton.addActionListener(e -> {
            NameInputWindow nameInputWindow = new NameInputWindow();
            player.stopMusic();
            nameInputWindow.setVisible(true);
            dispose();
        });
    }
    public void configureReturnButton(JButton returnButton) {
        returnButton.addActionListener(e -> {
            MenuWindow menuWindow = new MenuWindow();
            player.stopMusic();
            menuWindow.setVisible(true);
            dispose();
        });
    }
}