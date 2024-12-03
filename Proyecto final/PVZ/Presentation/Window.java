package Presentation;
import javax.swing.*;

public abstract class Window extends JFrame {
    AudioPlayer player; 
    public Window(String title, String backgroundPath) {
        setTitle(title);
        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundPath));
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight()); 
        setContentPane(backgroundLabel); // Poner el fondo como fondo principal
        configureInitialSettings();
    }

    public void configureInitialSettings() {
        setSize(1346, 765);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        setResizable(false); // Evitar que se pueda cambiar el tamaño de la ventana

        // Deshabilitar el evento de maximización
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                setSize(1346, 765); // Mantener el tamaño original
            }
        });
    }

    public void showWindow() {
        setVisible(true);
    }

    public void stopMusic() {
        player.stopMusic();
    }
}
