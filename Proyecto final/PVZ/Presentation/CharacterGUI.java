package Presentation;

import Dominio.Character;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterGUI extends JPanel {
    public static CharacterGUI instance;
    private List<Character> characters = new ArrayList<>();
    private int cellSize;
    private List<Character>[][] board;

    public CharacterGUI(int cellSize, int rows, int columns) {
        CharacterGUI.instance = this;
        this.cellSize = cellSize;
        this.board = new ArrayList[rows][columns]; 
    
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                board[row][col] = new ArrayList<>(); 
            }
        }
    
        setOpaque(false);
        setPreferredSize(new Dimension(cellSize * columns, (cellSize + 20) * rows));
    }

    public boolean addCharacter(Character character) {
        int row = (character.getCoordenatesY() - GridGUI.GRID_Y_BASE) / (cellSize + 20);
        int col = (character.getCoordenatesX() - GridGUI.GRID_X_BASE) / cellSize;
    
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
            return false;
        } 
        board[row][col].add(character);
        characters.add(character);
    
        repaint();
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                for (Character character : board[row][col]) {
                    paintCharacter(g, character);
                }
            }
        }
    }

    private void paintCharacter(Graphics g, Character character) {
        ImageIcon icon = character.getImage();
        Image image = icon.getImage();

        int x = character.getCoordenatesX();
        int y = character.getCoordenatesY();

        g.drawImage(image, x, y, cellSize, cellSize, null);
    }

    public List<Character>[][] getBoard(){
        return board;
    }

    public int getCellSize() {
        return cellSize;
    }

    public void updatePositionInBoard(Character character, int oldRow, int oldCol, int newRow, int newCol) {
        board[oldRow][oldCol].remove(character);
        board[newRow][newCol].add(character);
    }

    public boolean hasPlantInCell(int row, int col) {
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
            return false; // Fuera del tablero
        }
        List<Character> cellCharacters = board[row][col];

        for (Character character : cellCharacters) {
            if (character.getType().equalsIgnoreCase("Plant")) {
                return true; // Encontro una plant 
            }
        }
        return false; // No encontro una plant 
    }

    public Character getPlantInCell(int row, int col) {
        for (Character character : board[row][col]) {
            if (character.getType().equals("Plant")) {
                return character;
            }
        }
        return null; // No hay planta en esta celda
    }

    public void removeCharacter(Character character, int row, int col) {
        board[row][col].remove(character);
        characters.remove(character);
        repaint();
    }
    
}