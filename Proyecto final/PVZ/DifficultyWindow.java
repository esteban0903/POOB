
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;

public class DifficultyWindow extends JFrame {

    public static void main(String[] args) {
        new DifficultyWindow();
    }

    private Color violet = new Color(128, 0, 128);
    AudioPlayer player = new AudioPlayer("resources/musicDifficultyWindow.wav");
    private JButton easyButton = GameController.createButton("Fácil", 570, 220, 200, 40, violet, Color.WHITE);
    private JButton mediumButton = GameController.createButton("Medio", 570, 280, 200, 40, violet, Color.WHITE);
    private JButton hardButton = GameController.createButton("Difícil", 570, 340, 200, 40, violet, Color.WHITE);
    private JButton backButton = GameController.createButton("Volver", 570, 400, 200, 40, violet, Color.WHITE);


    public DifficultyWindow() {
        setTitle("Dificultad");
        setSize(1346, 765);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new JLabel(new ImageIcon("resources/difficultyWindow.jpg")));
        setLayout(null);

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
}
