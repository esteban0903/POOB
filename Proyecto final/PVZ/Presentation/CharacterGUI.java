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
    private Character[][] board;

    public CharacterGUI(int cellSize, int rows, int columns) {
        CharacterGUI.instance = this;
        this.cellSize = cellSize;
        this.board = new Character[rows][columns];
        setOpaque(false);
        setPreferredSize(new Dimension(cellSize * columns, (cellSize + 20) * rows));
    }

    public boolean addCharacter(Character character) {
        int row = (character.getCoordenatesY() - GridGUI.GRID_Y_BASE) / (cellSize + 20);
        int col = (character.getCoordenatesX() - GridGUI.GRID_X_BASE) / cellSize;

        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
            return false;
        }

        if (board[row][col] != null) {
            return false;
        }

        board[row][col] = character;
        characters.add(character);

        character.setPosition(GridGUI.GRID_X_BASE + col * cellSize, GridGUI.GRID_Y_BASE + row * (cellSize + 20));

        repaint();
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Character character : characters) {
            paintCharacter(g, character);
        }
    }

    private void paintCharacter(Graphics g, Character character) {
        ImageIcon icon = character.getImage();
        Image image = icon.getImage();

        int x = character.getCoordenatesX();
        int y = character.getCoordenatesY();

        g.drawImage(image, x, y, cellSize, cellSize, null);
    }

    public Character[][] getBoard(){
        return board;
    }
}