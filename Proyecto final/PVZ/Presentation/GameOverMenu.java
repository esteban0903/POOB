package Presentation;

import javax.swing.*;
import java.awt.*;

public class GameOverMenu extends JPanel {

    private GridGUI parentWindow;
    JButton mainMenuButton = AssistantGraphic.createButton("Regresar al Menú Principal", 150, 100, 200, 40, new Color(128, 0, 128), Color.WHITE);
    JLabel gameOverLabel;
    JPanel gameOverBox = new JPanel();

    public GameOverMenu(GridGUI parentWindow, String gameOverMessage) {
        this.parentWindow = parentWindow; 
        gameOverLabel = new JLabel(gameOverMessage);
        configureMenu();
    }

    private void configureMenu() {
        setBounds(0, 0, parentWindow.getWidth(), parentWindow.getHeight());
        setOpaque(false);
        setLayout(null); 

        // Diseño del panel de Game Over
        createBoxGameOver();
        addTextGameOverLabel();
        addButtons();
        configureNotActionClick();
    }

    private void createBoxGameOver() {
        gameOverBox.setBounds(400, 200, 500, 300);
        gameOverBox.setBackground(new Color(0, 0, 0, 128)); // colores random 
        gameOverBox.setLayout(null);
        add(gameOverBox);
    }

    private void addTextGameOverLabel() {
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gameOverLabel.setForeground(Color.WHITE);
        gameOverLabel.setBounds(150, 30, 200, 40);
        gameOverBox.add(gameOverLabel);
    }

    private void addButtons() {
        mainMenuButton.setBounds(150, 100, 200, 40);
        mainMenuButton.addActionListener(e -> returnToMainMenu());
        gameOverBox.add(mainMenuButton);
    }

    private void returnToMainMenu() {
        mainMenuButton.addActionListener(e -> {
            MenuWindow menuWindow = new MenuWindow();
            parentWindow.exitGame();
            menuWindow.setVisible(true);
        });
    }

    private void configureNotActionClick(){
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                e.consume();
            }
        });
    }
}
