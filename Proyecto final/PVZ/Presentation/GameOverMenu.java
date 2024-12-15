package Presentation;

import Dominio.GameConfig;
import java.awt.*;
import javax.swing.*;


/**
 * A panel that displays game over information, including the final score and player name, and offers navigation options.
 */

public class GameOverMenu extends JPanel {

    private GridGUI parentWindow;
    JButton mainMenuButton = AssistantGraphic.createButton("Regresar al Menú Principal", 150, 200, 200, 40, new Color(128, 0, 128), Color.WHITE);
    JLabel gameOverLabel;
    JLabel scoreLabel;
    JLabel nameLabel;
    JPanel gameOverBox = new JPanel();


    /**
     * Constructs a GameOverMenu with a reference to the parent window and a specific game over message.
     *
     * @param parentWindow The GridGUI window that this panel is a part of.
     * @param gameOverMessage The message to display on game over.
     */

    public GameOverMenu(GridGUI parentWindow, String gameOverMessage) {
        this.parentWindow = parentWindow; 
        gameOverLabel = new JLabel(gameOverMessage);
        scoreLabel = new JLabel("Puntaje: " + GameConfig.getPuntaje());
        nameLabel = new JLabel("Jugador: " + GameConfig.getName());
        configureMenu();
    }


    /**
     * Configures the menu layout, positions components, and initializes settings.
     */

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

    /**
     * Creates and styles the container box that holds the game over information.
     */

    private void createBoxGameOver() {
        gameOverBox.setBounds(400, 200, 500, 300);
        gameOverBox.setBackground(new Color(0, 0, 0, 128)); // colores random 
        gameOverBox.setLayout(null);
        add(gameOverBox);
    }

    /**
     * Adds and styles the game over message label.
     */

    private void addTextGameOverLabel() {
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gameOverLabel.setForeground(Color.WHITE);
        gameOverLabel.setBounds(150, 30, 200, 40);
        gameOverBox.add(gameOverLabel);
    }

    /**
     * Adds and styles the labels for displaying the final score and the player's name.
     */

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
    
    /**
     * Adds the main menu button and configures its action.
     */

    private void addButtons() {
        mainMenuButton.addActionListener(e -> returnToMainMenu());
        gameOverBox.add(mainMenuButton);
    }

    /**
     * Defines the action to return to the main menu, re-using the main menu button's action listener.
     */

    private void returnToMainMenu() {
        mainMenuButton.addActionListener(e -> {
            MenuWindow menuWindow = new MenuWindow();
            parentWindow.exitGame();
            menuWindow.setVisible(true);
        });
    }

    /**
     * Configures the panel to consume mouse clicks, preventing any unintended actions on underlying components.
     */
    
    private void configureNotActionClick(){
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                e.consume();
            }
        });
    }
}
