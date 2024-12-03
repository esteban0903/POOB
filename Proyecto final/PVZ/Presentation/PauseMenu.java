package Presentation;

import javax.swing.*;
import java.awt.*;

public class PauseMenu extends JPanel {

    private GridGUI parentWindow;

    public PauseMenu(GridGUI parentWindow) {
        this.parentWindow = parentWindow; // Ventana principal (GridGUI)
        configureMenu();
    }

    private void configureMenu() {
        // Configuración del panel de bloqueo semitransparente
        setBounds(0, 0, parentWindow.getWidth(), parentWindow.getHeight());
        setBackground(null);
        setOpaque(false);
        setLayout(null); // Posicionamiento manual

        // Panel central para el menú de pausa
        JPanel pauseBox = new JPanel();
        pauseBox.setBounds(parentWindow.getWidth() / 2 - 250, parentWindow.getHeight() / 2 - 150, 500, 300);
        pauseBox.setBackground(new Color(0, 0, 0, 200)); // Fondo opaco
        pauseBox.setLayout(null);

        // Etiqueta de pausa
        JLabel pauseLabel = new JLabel("Juego en Pausa");
        pauseLabel.setForeground(Color.WHITE);
        pauseLabel.setFont(new Font("Arial", Font.BOLD, 24));
        pauseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pauseLabel.setBounds(0, 20, 500, 40);
        pauseBox.add(pauseLabel);

        // Botón para reanudar
        JButton resumeButton = GameController.createButton("Reanudar", 150, 100, 200, 40, new Color(128, 0, 128), Color.WHITE);
        resumeButton.addActionListener(e -> {
            parentWindow.resumeGame(); // Reanuda el juego
            setVisible(false); // Oculta el menú de pausa
        });
        pauseBox.add(resumeButton);

        // Botón para volver al menú principal
        JButton mainMenuButton = GameController.createButton("Menú Principal", 150, 180, 200, 40, new Color(128, 0, 128), Color.WHITE);
        mainMenuButton.addActionListener(e -> {
            MenuWindow menuWindow = new MenuWindow();
            parentWindow.stopMusic(); // Detener música
            menuWindow.setVisible(true); // Mostrar menú principal
            parentWindow.dispose(); // Cerrar ventana actual
        });
        pauseBox.add(mainMenuButton);

        add(pauseBox); // Añadir el cuadro central al panel de pausa

        setVisible(false); // Inicialmente oculto
    }
}
