package Presentation;
import javax.swing.*;

public abstract class Window extends JFrame {
    AudioPlayer player; 
    public Window(String title, String backgroundPath) {
        setTitle(title);
        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundPath));
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight()); 
        setContentPane(backgroundLabel); 
        configureInitialSettings();
    }

    public void configureInitialSettings() {
        setSize(1346, 765);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        setResizable(false); 

        //quita la opcion de maximizar la ventana 
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                setSize(1346, 765); 
            }
        });
    }

    public void showWindow() {
        setVisible(true);
    }

}
