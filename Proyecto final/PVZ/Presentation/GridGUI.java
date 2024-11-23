package Presentation;

import Dominio.BasicZombie;
import Dominio.Character;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class GridGUI extends Window {
    public static final int CELL_SIZE = 80;
    public static final int GRID_X_BASE = 220;  // coord de x del tablero (1 casilla)
    public static final int GRID_Y_BASE = 140;  // coord de y del tablero (1 casilla)
    private SunGenerator sunGenerator;
    private JLabel sunCounterLabel;

    AudioPlayer player = new AudioPlayer("resources/easyMusic.wav");
    private JLayeredPane layeredPane; 
    private JPanel gridPanel; 
    private CharacterGUI characterPanel; 
    private Character selectedCharacter;

    public static void main(String[] args) {
        Map<String, String> characterTypes = new HashMap<>();
        characterTypes.put("Peashooter", "resources/Peashooter.png"); // para probar algunas plantas 
        characterTypes.put("Sunflower", "resources/Sunflower.png");
        characterTypes.put("BasicZombie", "resources/BasicZombie.png");

        new GridGUI(characterTypes);
    }

    public GridGUI(Map<String, String> characterTypes) {
        super("Plants vs Zombies", "resources/gridGame.jpg");

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0 , 0 , getWidth() , getHeight());
        add(layeredPane);

        sunCounterLabel = new JLabel("Suns: 0");
        sunCounterLabel.setBounds(20, 20, 100, 30); // tamaño modificable (revisar)
        sunCounterLabel.setForeground(Color.YELLOW);
        layeredPane.add(sunCounterLabel, Integer.valueOf(4));

        createGrid(5, 10, CELL_SIZE);

        characterPanel = new CharacterGUI(CELL_SIZE, 5, 10); 
        characterPanel.setBounds(0, 0, getWidth(), getHeight()); 
        characterPanel.setOpaque(false);
        layeredPane.add(characterPanel, Integer.valueOf(2));

        createCharacterButtons(characterTypes);
        sunGenerator = new SunGenerator(gridPanel, CELL_SIZE, sunCounterLabel);

        new Timer(3000, e -> sunGenerator.addRandomSun()).start();
        player.playMusic();
        showWindow();
    }

    private void createCharacterButtons(Map<String, String> characterTypes) {
        int sizeButtonX = 50;
        int sizeButtonY = 50;
        for (var entry : characterTypes.entrySet()) {
            var buttonPanel = GameController.createButtonWithImage(entry.getValue(), e -> {
                selectedCharacter = CharacterFactory.createCharacter(entry.getKey(), 0, 0, characterPanel);
            }, entry.getKey(), 60, 60);

            buttonPanel.setBounds(sizeButtonX, sizeButtonY, 70, 70);
            buttonPanel.setOpaque(false);
            layeredPane.add(buttonPanel, Integer.valueOf(3));
            sizeButtonY += 100; 
        }
    }
    private void createGrid(int rows, int cols, int cellSize){
        gridPanel = new JPanel(new GridLayout(rows, cols)); // va un gridLayout adentro del panel para dividir con una cuadricula
        gridPanel.setBounds(GRID_X_BASE, GRID_Y_BASE, cols*cellSize, rows*cellSize + 100); //dimensiones teniendo en cuenta el fondo
        gridPanel.setOpaque(false);
        layeredPane.add(gridPanel, Integer.valueOf(1));
        for (int row = 0 ; row < rows ; row++) {
            for ( int col =0 ; col < cols ; col++){
                JPanel cell = new JPanel();
                cell.setOpaque(false);
                if (col < 8 || col == 9) configureClickOnCell(row, col, cell, cellSize);
                gridPanel.add(cell);
            }
        }

    }

    
    public void configureClickOnCell(int row, int col, JPanel cell, int cellSize) {
        cell.addMouseListener(new MouseAdapter() { // se crea el adaptador del mouse 
            @Override
            public void mouseClicked(MouseEvent e) { //para el click del mouse 
                if (selectedCharacter == null) return;
    
                String type = selectedCharacter.getType();
                int cost = selectedCharacter.getCost();
    
                if (!isPlacementValid(type, col)) {
                    System.out.println("No se puede colocar en esa posición.");
                    return;
                }
    
                if (!hasEnoughResources(type, cost)) {
                    System.out.println("No tienes suficientes recursos.");
                    return;
                }
    
                int x = GRID_X_BASE + col * cellSize;
                int y = GRID_Y_BASE + row * (cellSize + 20);
                selectedCharacter.setPosition(x, y);
    
                if (characterPanel.addCharacter(selectedCharacter)) {
                    performCharacterActions(type, cost);
                    selectedCharacter = null;
                }
            }
        });
    }

    private boolean isPlacementValid(String type, int col) {
        if (type.equals("Plant") && col >= 8) return false;
        if (type.equals("Zombie") && col != 9) return false;
        return true;
    }

    private boolean hasEnoughResources(String type, int cost) {
        if (type.equals("Plant") && sunGenerator.getSunCount() < cost) {
            return false;
        }
        return true;
    }
    private void performCharacterActions(String type, int cost) {
        if (type.equals("Plant")) {
            sunGenerator.subtractSun(cost);
        }
        if (type.equals("Zombie")) {
            ((BasicZombie) selectedCharacter).move();
        }
    } 
}


   /*
     * Método para mostrar el estado del tablero en un cuadro de diálogo
     */
    /*private void showBoard() {
        StringBuilder boardState = new StringBuilder();
        boardState.append("Estado del Tablero:\n");
        boardState.append("-".repeat(characterPanel.getBoard()[0].length * 8 + 1)).append("\n"); // Bordes superiores

        for (int row = 0; row < characterPanel.getBoard().length; row++) {
            for (int col = 0; col < characterPanel.getBoard()[0].length; col++) {
                if (characterPanel.getBoard()[row][col] == null) {
                    boardState.append("| Vacío "); // Celda vacía
                } else {
                    boardState.append(String.format("| %-6s", characterPanel.getBoard()[row][col].getName())); // Nombre del personaje
                }
            }
            boardState.append("|\n"); // Cierra la fila
            boardState.append("-".repeat(characterPanel.getBoard()[0].length * 8 + 1)).append("\n"); // Bordes entre filas
        }

        // Mostrar el estado del tablero en un cuadro de diálogo
        JOptionPane.showMessageDialog(null, boardState.toString(), "Estado del Tablero", JOptionPane.INFORMATION_MESSAGE);
    } 
    


    */ 