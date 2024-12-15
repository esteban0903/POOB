package Presentation;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;


/**
 * Provides static utility methods to create graphical components such as buttons, panels with buttons, and progress bars.
 */

public class AssistantGraphic {

    /**
     * Creates a customized JButton with specific text, position, dimensions, and colors.
     * 
     * @param text The text to display on the button.
     * @param x The x-coordinate of the button on its container.
     * @param y The y-coordinate of the button on its container.
     * @param width The width of the button.
     * @param height The height of the button.
     * @param backgroundColor The background color of the button.
     * @param foregroundColor The text color of the button.
     * @return A customized JButton with added properties and behavior.
     */

    public static JButton createButton(String text , int x , int y, int width , int height, Color backgroundColor , Color foregroundColor) {
        AudioPlayer player = new AudioPlayer("resources/Music/Efects/soundButton.wav");
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFocusPainted(false);
        button.setBackground(backgroundColor);
        button.setForeground(foregroundColor);
        button.setFont(new Font("Monospaced", Font.BOLD, 19)); // Asignar una font mas gamer
        button.setBorder(new LineBorder(Color.BLACK, 2, true));

        button.addChangeListener(e -> {
            if (button.getModel().isRollover()) {
                button.setBackground(backgroundColor.darker());
            } else {
                button.setBackground(backgroundColor);
                button.setForeground(foregroundColor);
                button.setFont(new Font("Monospaced", Font.BOLD, 19));
                button.setBorder(new LineBorder(Color.BLACK, 2, true));
            }
        });

        button.addActionListener(e -> player.playSoundOnce());
        return button;
    }

    /**
     * Creates a JPanel containing a JButton with an image.
     * 
     * @param pathImage The path to the image file to use as the button's icon.
     * @param action The action listener to attach to the button.
     * @param nameButton The name of the button for identification.
     * @param width The desired width of the button image.
     * @param height The desired height of the button image.
     * @return A JPanel containing the customized image button.
     */

    public static JPanel createButtonWithImage(String pathImage, ActionListener action, String nameButton, int width, int height) {
        JPanel panel = new JPanel( new FlowLayout() );
        panel.setOpaque(false);
       
    
        // Cambiarle las dimensiones a la imagen
            
        ImageIcon icon = new ImageIcon (pathImage);
        Image resizeImage = icon.getImage().getScaledInstance (width, height, Image.SCALE_SMOOTH);
        ImageIcon resizeIcon = new ImageIcon (resizeImage);
    
        JButton boton = new JButton(resizeIcon);
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setVerticalTextPosition(SwingConstants.BOTTOM);
        boton.addActionListener(action);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(false);

        panel.add(boton);
        panel.setVisible(true);
    
        int panelWidth = width + 27 ;  
        int panelHeight = height+ 27; 
        panel.setPreferredSize( new Dimension(panelWidth, panelHeight));
        boton.setPreferredSize( new Dimension(panelWidth, panelHeight));
    
        return panel;
    }

    /**
     * Creates a JProgressBar with specified dimensions and location.
     * 
     * @param x The x-coordinate of the progress bar on its container.
     * @param y The y-coordinate of the progress bar on its container.
     * @param width The width of the progress bar.
     * @param height The height of the progress bar.
     * @return A JProgressBar initialized to 0% completion.
     */
    
    public static JProgressBar createProgressBar(int x, int y, int width, int height) {
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setBounds(x, y, width, height);
        progressBar.setStringPainted(true); // Muestra el porcentaje en la barra
        progressBar.setValue(0);  // Inicializa en 0

        return progressBar;
    }
        
}
