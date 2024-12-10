package Presentation;

import Dominio.GameConfig;
import java.awt.*;
import javax.swing.*;

public class GameOverMenu extends JPanel {

    private GridGUI parentWindow;
    JButton mainMenuButton = AssistantGraphic.createButton("Regresar al Menú Principal", 150, 200, 200, 40, new Color(128, 0, 128), Color.WHITE);
    JLabel gameOverLabel;
    JLabel scoreLabel;
    JLabel nameLabel;
    JPanel gameOverBox = new JPanel();

    public GameOverMenu(GridGUI parentWindow, String gameOverMessage) {
        this.parentWindow = parentWindow; 
        gameOverLabel = new JLabel(gameOverMessage);
        scoreLabel = new JLabel("Puntaje: " + GameConfig.getPuntaje());
        nameLabel = new JLabel("Jugador: " + GameConfig.getName());
        configureMenu();
    }

    private void configureMenu() {
        setBounds(0, 0, parentWindow.getWidth(), parentWindow.getHeight());
        setOpaque(false);
        setLayout(null); 

        // Diseño del panel de Game Over
        createBoxGameOver();
        addTextGameOverLabel();
        addScoreAndNameLabels();
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

    private void addScoreAndNameLabels() {
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        scoreLabel.setForeground(Color.CYAN);
        scoreLabel.setBounds(50, 80, 400, 30);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        nameLabel.setForeground(Color.YELLOW);
        nameLabel.setBounds(50, 120, 400, 30);
        
        gameOverBox.add(scoreLabel);
        gameOverBox.add(nameLabel);
    }
    
    private void addButtons() {
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
