import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
public class MenuWindow extends JFrame {
    public static void main(String[] args) {
        new MenuWindow();
    }
    AudioPlayer player = new AudioPlayer("resources/musicMainMenu.wav");
    private Color purple = new Color(0, 128, 128);
    private JButton playButton = GameController.createButton("Jugar", 570, 260, 200, 40, purple, Color.WHITE);
    private JButton loadButton = GameController.createButton("Cargar Partida", 570, 320, 200, 40, purple, Color.WHITE);
    private JButton difficultyButton = GameController.createButton("Dificultad", 570, 380, 200, 40, purple, Color.WHITE);
    private JButton exitButton = GameController.createButton("Salir", 570, 440, 200, 40, purple, Color.WHITE);

    public MenuWindow() {
        setTitle("Menú Principal");
        setSize(1346, 765);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setContentPane(new JLabel(new ImageIcon("resources/backgroundMainMenu.jpg")));
        setLayout(null);
        
        add(playButton);
        add(loadButton);
        add(difficultyButton);
        add(exitButton);
        configureExitButton(exitButton);
        configureDifficultyButton(difficultyButton);
        setVisible(true);
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
}