    package Presentation;

    import javax.swing.*;
    import java.awt.*;

    public class PauseMenu extends JPanel {

        private GridGUI parentWindow;
        JButton resumeButton = AssistantGraphic.createButton("Reanudar", 150, 100, 200, 40, new Color(128, 0, 128), Color.WHITE);
        JButton safeButton = AssistantGraphic.createButton("Guardar partida", 150, 160, 200, 40, new Color(128, 0, 128), Color.WHITE);
        JButton mainMenuButton = AssistantGraphic.createButton("Salir de la partida", 150, 220, 200, 40, new Color(128, 0, 128), Color.WHITE);
        JLabel pauseLabel = new JLabel("Juego en Pausa");
        JPanel pauseBox = new JPanel();
        public PauseMenu(GridGUI parentWindow) {
            this.parentWindow = parentWindow; // Ventana donde va el PauseMenu 
            configureMenu();
        }

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

        private void configureResumeButton() {
            resumeButton.addActionListener(e -> {
                parentWindow.resumeGame(); 
                setVisible(false); 
            });
        }

        private void configureSafeButton(){
            safeButton.addActionListener(e -> {
                parentWindow.saveGame();
            });
        }

        private void configureMainMenuButton(){
            mainMenuButton.addActionListener(e -> {
                MenuWindow menuWindow = new MenuWindow();
                parentWindow.exitGame();
                menuWindow.setVisible(true);
            });

        }   
    /* 
        private void configuresafeButton(){
            safeButton.addActionListener(e -> {
                parentWindow.saveGame();
            });
        }
    */
        private void addTextPauseLabel(){
            pauseLabel.setForeground(Color.WHITE);
            pauseLabel.setFont(new Font("Arial", Font.BOLD, 24));
            pauseLabel.setHorizontalAlignment(SwingConstants.CENTER);
            pauseLabel.setBounds(0, 20, 500, 40);
        }

        private void createBoxPause(){
            pauseBox.setBounds(parentWindow.getWidth() / 2 - 250, parentWindow.getHeight() / 2 - 150, 500, 300);
            pauseBox.setBackground(new Color(0, 0, 0, 200));  // color semitransparante
            pauseBox.setLayout(null);
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
