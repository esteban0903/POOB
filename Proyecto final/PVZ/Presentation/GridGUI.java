package Presentation;

import Dominio.Character;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class GridGUI extends Window {
    public static final int CELL_SIZE = 80; 
    public static final int GRID_X_BASE = 220; 
    public static final int GRID_Y_BASE = 140; 

    private JLayeredPane layeredPane; 
    private JPanel gridPanel; 
    private CharacterGUI characterPanel; 
    private Character selectedCharacter = null;

    public static void main(String[] args) {
        Map<String, String> characterTypes = Map.of(
            "Peashooter", "resources/Peashooter.png",
            "Sunflower", "resources/Sunflower.png"
        );

        new GridGUI(characterTypes);
    }

    public GridGUI(Map<String, String> characterTypes) {
        super("Plants vs Zombies", "resources/gridGame.jpg");
    
        //panel de capas
        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, getWidth(), getHeight());
        add(layeredPane);

        //cuadricula
        createGrid(5, 8, CELL_SIZE); 

        //panel
        characterPanel = new CharacterGUI(CELL_SIZE, 5, 8); 
        characterPanel.setBounds(0, 0, getWidth(), getHeight()); //configuracion base
        characterPanel.setOpaque(false);
        layeredPane.add(characterPanel, Integer.valueOf(2)); // Capa superior

        // Crear botones del panel 
        int buttonX = 50;
        int buttonY = 50;
        for (Map.Entry<String, String> entry : characterTypes.entrySet()) {
            String type = entry.getKey();
            String imagePath = entry.getValue();

            JPanel buttonPanel = GameController.createButtonWithImage(imagePath, e -> {
                selectedCharacter = CharacterFactory.createCharacter(type, 0, 0);
            }, type);

            buttonPanel.setBounds(buttonX, buttonY, 100, 100);
            buttonPanel.setOpaque(false);
            layeredPane.add(buttonPanel, Integer.valueOf(3)); // Capa superior a la cuadrícula
            buttonY += 100; // Espaciado entre botones
        }

        showWindow();
    }

    private void createGrid(int rows, int cols, int cellSize) {
        gridPanel = new JPanel(new GridLayout(rows, cols));
        gridPanel.setBounds(GRID_X_BASE, GRID_Y_BASE, cols * cellSize, rows * cellSize+100 ); //ajustar con la foto 
        gridPanel.setOpaque(false); 
        layeredPane.add(gridPanel, Integer.valueOf(1)); // Abajo de los personajes 

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                JPanel cell = createCell(row, col, cellSize);
                gridPanel.add(cell);
            }
        }
    }

    //Crear cuadrito de la cuadricula 
    private JPanel createCell(int row, int col, int cellSize) {
        JPanel cell = new JPanel();
        cell.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        cell.setOpaque(false); 

        configureClickOnCell(row, col, cell, cellSize);

        return cell;
    }

    public void configureClickOnCell(int row, int col, JPanel cell, int cellSize){
        cell.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedCharacter != null) {

                    int x = GRID_X_BASE + col * cellSize;
                    int y = GRID_Y_BASE + row * (cellSize+20  );

                    selectedCharacter.setPosition(x, y);

                   
                    boolean added = characterPanel.addCharacter(selectedCharacter);
                    if (added) {
                        selectedCharacter = null; // Limpiar selección si se agregó
                    }
                }
            }

        });
    }
}
