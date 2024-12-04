package Presentation;

import Dominio.Character;
import Dominio.CharacterFactory;
import Dominio.Grid;
import Dominio.Zombie;
import Dominio.Plant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

 public class GridGUI extends Window {
    private JPanel gridPanel;
    private CharacterGUI characterPanel;
    private SunGenerator sunGenerator;
    private ZombieGenerator zombieGenerator;
    private JLabel sunCounterLabel;
    private Character selectedCharacter;
    private JLayeredPane layeredPane;
    private JPanel shovel;
    private boolean isShovelActive = false;
    private Grid grid;
    private int rows = 5;
    private int cols = 10;
    private int sunCount;
    private JButton pauseButton = GameController.createButton("Pausa", 1100, 30, 200, 40, Color.black, Color.white);
    private PauseMenu pauseMenu; 
    private static final int CELL_SIZE = 80;
    private static final int GRID_X_BASE = 220;
    private static final int GRID_Y_BASE = 140;
    private static final AudioPlayer player = new AudioPlayer("resources/easyMusic.wav");
    private boolean isPaused = GameConfig.getIsPaused();
    private Timer sunTimer;
    private Timer zombieTimer;

    public GridGUI(Map<String, String> characterTypes) {
        super("Plants vs Zombies", "resources/gridGame.jpg");
        this.grid = new Grid(rows, cols, CELL_SIZE);
        this.sunCount = GameConfig.getInitialSuns();

        createPanelBase(); 
        createGrid(rows, cols);
        createCounterSuns();
        createPauseButton();
        createSunGridGenerator();
        createPanelCharacters();
        createShovelButton();
        createCharacterButtons(characterTypes);
        createZombieGridGenerator();
        player.playMusic();
        showWindow();
    }

    private void createPanelBase() {
        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, getWidth(), getHeight());
        layeredPane.setOpaque(false);
        getContentPane().add(layeredPane);
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    private void createPauseButton(){
        layeredPane.add(pauseButton, Integer.valueOf(4));
        configurePauseButton(pauseButton);
        createPauseMenu();
    }

    private void createPauseMenu() {
        pauseMenu = new PauseMenu(this); 
        getLayeredPane().add(pauseMenu, Integer.valueOf(1)); 
    }

    

    private void configurePauseButton(JButton button) {
        button.addActionListener(e -> {
            pauseGame(); 
            GameConfig.setIsPaused();
            pauseMenu.setVisible(true); //muestra el menu de pausa 
            dispose();
        });
    }

    private void createPanelCharacters() {
        characterPanel = new CharacterGUI(grid, CELL_SIZE);
        characterPanel.setBounds(0, 0, getWidth(), getHeight());
        layeredPane.add(characterPanel, Integer.valueOf(2)); 
    }

    private void createCounterSuns() {
        sunCounterLabel = new JLabel("Suns: " + sunCount);
        sunCounterLabel.setBounds(20, 20, 100, 30);
        sunCounterLabel.setForeground(Color.YELLOW);
        layeredPane.add(sunCounterLabel, Integer.valueOf(4)); 
    }

    private void createSunGridGenerator() {
        sunGenerator = new SunGenerator(grid, gridPanel, sunCounterLabel, sunCount);
        sunTimer = new Timer(2000, e -> { if (!isPaused) sunGenerator.addRandomSun(50);});
        sunTimer.start();
    }

    private void createZombieGridGenerator() {
        zombieGenerator = new ZombieGenerator(grid, characterPanel);
        zombieTimer = new Timer(5000, e -> {if (!isPaused) zombieGenerator.addRandomZombie();});
        zombieTimer.start();
    }

    private void createGrid(int rows, int cols) {
        gridPanel = new JPanel(null);
        gridPanel.setBounds(0, 0, getWidth(), getHeight());
        gridPanel.setOpaque(false);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                JPanel cell = new JPanel();
                cell.setOpaque(false);
                Point position = calculatePosition(row, col);
                cell.setBounds(position.x, position.y, CELL_SIZE, CELL_SIZE + 20);
                configureClickOnCell(row, col, cell);
                gridPanel.add(cell);
            }
        }
        layeredPane.add(gridPanel, Integer.valueOf(1));
    }

    private void createCharacterButtons(Map<String, String> characterTypes) {
        int sizeButtonX = 50;
        int sizeButtonY = 50;

        for (var entry : characterTypes.entrySet()) {
            ActionListener action = createCharacterSelectionAction(entry.getKey());
            JPanel buttonPanel = GameController.createButtonWithImage(entry.getValue(), action, entry.getKey(), 60, 60);

            buttonPanel.setBounds(sizeButtonX, sizeButtonY, 80, 80);
            buttonPanel.setOpaque(false);
            layeredPane.add(buttonPanel, Integer.valueOf(3)); 

            sizeButtonY += 100;
        }
    }

    private ActionListener createCharacterSelectionAction(String characterType) {
        return e -> {
            isShovelActive = false; // Quita la pala si esta activada 
            selectedCharacter = CharacterFactory.createCharacter(characterType, 0, 0, characterPanel);
            System.out.println("Personaje seleccionado: " + characterType);
        };
    }

    private void createShovelButton() {
        ActionListener action = createShovelSelectionAction();
        shovel = GameController.createButtonWithImage("resources/Shovel.png", action, "shovel", 60, 60);
        shovel.setBounds(300, 20, 80, 80);
        shovel.setOpaque(false);
        layeredPane.add(shovel, Integer.valueOf(3));
    }

    private ActionListener createShovelSelectionAction() {
        return e -> {
            isShovelActive = !isShovelActive;
    
            if (isShovelActive) {
                selectedCharacter = null; // quita cualquier personaje seleccionado si se usa la pala 
                System.out.println("Pala activada.");
            } else {
                System.out.println("Pala desactivada.");
            }
        };
    }

    private void configureClickOnCell(int row, int col, JPanel cell) {
        cell.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //pala
                if (isShovelActive) {
                    removePlantFromCell(row, col);
                    isShovelActive = false; 
                    return;
                }
                
                //personajes 
                if (selectedCharacter != null) {
                    String type = selectedCharacter.getType();
                    int cost = selectedCharacter.getCost();
    
                    if (!checkPlacementConditions(type, cost, row, col)) return;
    
                    placeCharacterOnGrid(selectedCharacter, row, col);
                    selectedCharacter = null; 
                }
            }
        });
    }

    private void removePlantFromCell(int row, int col) {
        var characters = grid.getCharactersInCell(row, col);
        if (characters != null) {
            characters.removeIf(character -> character instanceof Plant);
            characterPanel.repaint();
            System.out.println("Planta eliminada en la celda (" + row + ", " + col + ").");
        }
    }

    private boolean checkPlacementConditions(String type, int cost, int row, int col) {
        if (!characterPanel.getGrid().isPlacementValid(type, col)) {
            JOptionPane.showMessageDialog(null, "No se puede colocar en esta posicion");
            return false;
        }

        if (!sunGenerator.hasEnoughSuns(cost)) {
            JOptionPane.showMessageDialog(null, "No tienes suficientes soles");
            return false;
        }

        return true;
    }

    private void placeCharacterOnGrid(Character character, int row, int col) {
        Point position = calculatePosition(row, col);
        character.setPosition(position.x, position.y);

        if (characterPanel.getGrid().placeCharacter(character, row, col)) {
            sunGenerator.subtractSun(character.getCost());
            characterPanel.repaint();
            

            if (character instanceof Plant) {
                ((Plant) character).startAction(characterPanel.getGrid(), sunGenerator);
                ((Plant) character).startAction();
                
            }
            if (character instanceof Zombie) {
                ((Zombie) character).move();
            }
        }
    }

    private Point calculatePosition(int row, int col) {
        int x = (GRID_X_BASE + col * CELL_SIZE);
        int y = GRID_Y_BASE + row * (CELL_SIZE + 20);
        return new Point(x, y);
    }

    public void pauseGame() {
        isPaused = true;
        sunTimer.stop();
        zombieTimer.stop();
        player.stopMusic();
        GameConfig.setIsPaused();
        
    }   

    public void resumeGame() {
        isPaused = false;
        sunTimer.start();
        zombieTimer.start();
        player.playMusic();
        GameConfig.setIsNotPaused();
    }

    public static void main(String[] args) {
        Map<String, String> characterTypes = Map.of(
            "Peashooter", "resources/Peashooter.png",
            "Sunflower", "resources/Sunflower.png",
            "WallNut", "resources/WallNut.png",
            "BasicZombie", "resources/BasicZombie.png",
            "ECIPlant", "resources/ECIPlant.png"
        );
        new GridGUI(characterTypes);
    }

    public static  int getGridYBase() {
        return GRID_Y_BASE;
    }

    public static  int getGridXBase() {
        return GRID_X_BASE;
    }
/* 
    public void saveGame() {
    try {
        // Crear un objeto para almacenar el estado del juego
        GameState gameState = new GameState();
        gameState.setSunCount(sunCount);
        gameState.setGridState(grid.getState());
        gameState.setTimerState(GameConfig.getGameDuration());
        gameState.setCharacters(characterPanel.getGrid().getAllCharacters());

        // Guardar el estado en un archivo
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("savedGame.dat"))) {
            out.writeObject(gameState);
        }
        JOptionPane.showMessageDialog(this, "¡Partida guardada con éxito!", "Guardar Partida", JOptionPane.INFORMATION_MESSAGE);
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al guardar la partida.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    */
}
