package Presentation;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.*;
import java.awt.event.ActionListener;

public class GameController {

    public static JButton createButton(String text , int x , int y, int width , int height, Color backgroundColor , Color foregroundColor) {
        
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
        return button;
    }

    public static JPanel createButtonWithImage(String pathImage, ActionListener action, String nameButton, int width, int height) {
        JPanel panel = new JPanel( new FlowLayout() );
        panel.setOpaque(false);
       
    
        // Cambiarle las dimensiones a la imagen
            
        ImageIcon icon = new ImageIcon (pathImage);
        Image resizeImage = icon.getImage().getScaledInstance (width, height, Image.SCALE_SMOOTH);
        ImageIcon resizeIcon = new ImageIcon (resizeImage);
    
        // Crear el botón con la nueva imagen redimensionada
        JButton boton = new JButton(resizeIcon);
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setVerticalTextPosition(SwingConstants.BOTTOM);
        boton.addActionListener(action);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(false);

        panel.add(boton);
        panel.setVisible(true);
    
        // Ajustar el tamaño del panel al tamaño de la imagen más un margen
        int panelWidth = width + 27 ;  // Margen adicional (por ejemplo, 10 píxeles)
        int panelHeight = height+ 27; // Margen adicional (por ejemplo, 10 píxeles)
        panel.setPreferredSize( new Dimension(panelWidth, panelHeight));
        boton.setPreferredSize( new Dimension(panelWidth, panelHeight));
    
        return panel;
    }
        
}
