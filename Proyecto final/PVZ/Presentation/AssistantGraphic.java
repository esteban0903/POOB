package Presentation;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import java.awt.*;
import java.awt.event.ActionListener;

public class AssistantGraphic {

    public static JButton createButton(String text , int x , int y, int width , int height, Color backgroundColor , Color foregroundColor) {
        AudioPlayer player = new AudioPlayer("resources/Efects/soundButton.wav");
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

    public static JProgressBar createProgressBar(int x, int y, int width, int height) {
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setBounds(x, y, width, height);
        progressBar.setStringPainted(true); // Muestra el porcentaje en la barra
        progressBar.setValue(0);  // Inicializa en 0

        return progressBar;
    }
        
}
