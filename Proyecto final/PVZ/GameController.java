
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;

public class GameController  {

    public static JButton createButton(String text, int x, int y, int width, int height, Color backgroundColor, Color foregroundColor) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFocusPainted(false);
        button.setBackground(backgroundColor); 
        button.setForeground(foregroundColor); 
        button.setFont(new Font("Monospaced", Font.BOLD, 19));

        button.addChangeListener(e -> {
            if (button.getModel().isRollover()) {
                button.setBackground(backgroundColor.darker()); 
                button.setBackground(backgroundColor); 
            }
        });
        return button;
    }

}