    package Presentation;

    import javax.swing.ImageIcon;
    import javax.swing.JButton;
    import javax.swing.border.LineBorder;
    import javax.swing.JPanel;
    import javax.swing.SwingConstants;

    import java.awt.Color;
    import java.awt.FlowLayout;
    import java.awt.Font;
    import java.awt.Image;
import java.awt.event.ActionListener;

    public class GameController {

        public static JButton createButton(String text, int x, int y, int width, int height, Color backgroundColor, Color foregroundColor) {
            JButton button = new JButton(text);
            button.setBounds(x, y, width, height);
            button.setFocusPainted(false);
            button.setBackground(backgroundColor);
            button.setForeground(foregroundColor);
            button.setFont(new Font("Monospaced", Font.BOLD, 19));
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

        public static JPanel createButtonWithImage(String pathImage, ActionListener action, String nameButton) {
            JPanel panel = new JPanel(new FlowLayout());
            panel.setOpaque(false);
    
            // Cambiarle las dimensiones a la imagen (No pude manualmente)
            ImageIcon iconoOriginal = new ImageIcon(pathImage);
            Image imagenRedimensionada = iconoOriginal.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            ImageIcon iconoRedimensionado = new ImageIcon(imagenRedimensionada);
    
            // Crear el icono con las nuevas dimensiones
            JButton boton = new JButton(iconoRedimensionado);
            boton.setText(nameButton);
            boton.setHorizontalTextPosition(SwingConstants.CENTER);
            boton.setVerticalTextPosition(SwingConstants.BOTTOM);
    
            boton.addActionListener(action);
            boton.setFocusPainted(false);
            

            panel.add(boton);
            panel.setVisible(true);
    
            return panel;
        }
    }
