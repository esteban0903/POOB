package Presentation;

import javax.swing.JButton;

import java.awt.Color;
import java.util.Map;
public class GameModeWindow extends Window {
    public static void main(String[] args) {
        new GameModeWindow();
    }
    AudioPlayer player = new AudioPlayer("resources/musicMainMenu.wav");
    private Color purple = new Color(210, 105, 30);
    private JButton onePlayerButton = GameController.createButton("Un jugador", 590, 240, 200, 40, purple, Color.WHITE);
    private JButton twoPlayerButton = GameController.createButton("Dos jugadores", 590, 300, 200, 40, purple, Color.WHITE);
    private JButton playerMachineButton = GameController.createButton("CPU VS CPU", 590, 360, 200, 40, purple, Color.WHITE);
    private JButton returnButton = GameController.createButton("Volver", 590, 420, 200, 40, purple, Color.WHITE);
    public GameModeWindow() {
        super("Modo de juego", "resources/gameModeWindoww.jpg");

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
            player.stopMusic();
            Map<String, String> characterTypes = Map.of(
            "Peashooter", "resources/Peashooter.png",
            "Sunflower", "resources/Sunflower.png" );
            new GridGUI(characterTypes);
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