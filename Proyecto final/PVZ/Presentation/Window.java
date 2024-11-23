package Presentation;
import javax.swing.*;

public abstract class Window extends JFrame {
    public Window(String title, String backgroundPath) {
        setTitle(title);
        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundPath));
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight()); 
        setContentPane(backgroundLabel);  // Poner el fondo como fondo principal
        configureInitialSettings();
    }

    public void configureInitialSettings(){
        setSize(1346, 765);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);  
        setLayout(null);

    }

    public void showWindow() {
        setVisible(true);
    }
    

}