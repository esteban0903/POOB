package Presentation;

import java.awt.*;
import javax.swing.*;

public class PauseMenu extends JPanel {

    private GridGUI parentWindow;
    JButton resumeButton = AssistantGraphic.createButton("Reanudar", 150, 100, 200, 40, new Color(128, 0, 128), Color.WHITE);
    JButton safeButton = AssistantGraphic.createButton("Guardar partida", 150, 160, 200, 40, new Color(128, 0, 128), Color.WHITE);
    JButton mainMenuButton = AssistantGraphic.createButton("Salir de la partida", 150, 220, 200, 40, new Color(128, 0, 128), Color.WHITE);
    JLabel pauseLabel = new JLabel("Juego en Pausa");
    JPanel pauseBox = new JPanel();

    /**
     * Constructs a PauseMenu with a reference to its parent window.
     *
     * @param parentWindow The parent window that this pause menu will be overlaying.
     */
    public PauseMenu(GridGUI parentWindow) {
        this.parentWindow = parentWindow; // Ventana donde va el PauseMenu 
        configureMenu();
    }

    /**
     * Configures the layout and components of the pause menu.
     */

    private void configureMenu() {
        setBounds(0, 0, parentWindow.getWidth(), parentWindow.getHeight());
        setOpaque(false);
        setLayout(null); 

        createBoxPause();
        addTextPauseLabel();
        configureResumeButton();
        configureSafeButton();
        configureMainMenuButton();

        pauseBox.add(safeButton);
        pauseBox.add(pauseLabel);
        pauseBox.add(resumeButton);
        pauseBox.add(mainMenuButton);
        add(pauseBox); 

        configureNotActionClick();


        setVisible(false); // inicialmente debe estar invisible
    }

    /**
     * Configures the resume button to resume the game and hide the pause menu.
     */

    private void configureResumeButton() {
        resumeButton.addActionListener(e -> {
            parentWindow.resumeGame(); 
            setVisible(false); 
        });
    }

    /**
     * Configures the save button to save the current game state.
     */

    private void configureSafeButton(){
        safeButton.addActionListener(e -> {
            parentWindow.saveGame();
        });
    }

    /**
     * Configures the main menu button to exit the game and return to the main menu.
     */

    private void configureMainMenuButton(){
        mainMenuButton.addActionListener(e -> {
            parentWindow.exitGame();
            MenuWindow menuWindow = new MenuWindow();
            menuWindow.setVisible(true);
        });

    }   

    /**
     * Adds a text label to the pause box.
     */

    private void addTextPauseLabel(){
        pauseLabel.setForeground(Color.WHITE);
        pauseLabel.setFont(new Font("Arial", Font.BOLD, 24));
        pauseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pauseLabel.setBounds(0, 20, 500, 40);
    }

    /**
     * Creates the pause box which contains all the menu items.
     */

    private void createBoxPause(){
        pauseBox.setBounds(parentWindow.getWidth() / 2 - 250, parentWindow.getHeight() / 2 - 150, 500, 300);
        pauseBox.setBackground(new Color(0, 0, 0, 200));  // color semitransparante
        pauseBox.setLayout(null);
    }

    /**
     * Configures the pause menu to consume all mouse clicks to prevent interaction with the underlying game components.
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
