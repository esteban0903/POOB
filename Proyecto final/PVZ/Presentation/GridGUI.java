package Presentation;

import Dominio.Character;
import Dominio.CharacterFactory;
import Dominio.GameController;
import Dominio.Grid;
import Dominio.Zombie;
import Dominio.Plant;

import javax.swing.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.concurrent.TimeUnit;

 public class GridGUI extends Window {
    private JPanel gridPanel;
    private CharacterGUI characterPanel;
    private SunGenerator sunGenerator;
    private ZombieGenerator zombieGenerator;
    private LawnMowerGenerator lawnMowerGenerator;
    private JLabel sunCounterLabel;
    private Character selectedCharacter;
    private JLayeredPane layeredPane;
    private JPanel shovel;
    private boolean isShovelActive = false;
    private Grid grid;
    private int rows = 5;
    private int cols = 10;
    private int sunCount;
    private JButton pauseButton = AssistantGraphic.createButton("Pausa", 1100, 30, 200, 40, Color.black, Color.white);
    private PauseMenu pauseMenu; 
    private static final int CELL_SIZE = 80;
    private static final int GRID_X_BASE = 140;
    private static final int GRID_Y_BASE = 140;
    private AudioPlayer player = new AudioPlayer("resources/easyMusic.wav");
    private boolean isPaused;
    private Timer sunTimer;
    private Timer zombieTimer;
    private Timer gameTimer;
    private long gameTimeDuration;
    private JProgressBar progressBar;
    private Map<String, String> characterTypes;


    public GridGUI(Map<String, String> characterTypes) {
        super("Plants vs Zombies", "resources/gridGame.jpg");
        this.grid = new Grid(rows, cols, CELL_SIZE);
        this.sunCount = GameConfig.getInitialSuns();
        this.isPaused = false;
        this.characterTypes = characterTypes;
        createPanelBase(); 
        createGrid(rows, cols);
        createCounterSuns();
        createButtons(characterTypes);
        setupProgressBar();
        showWindow();
        startGame();
        //showBoard();
        verifyGameStatus();
    }

    private void createPanelBase() {
        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, getWidth(), getHeight());
        layeredPane.setOpaque(false);
        getContentPane().add(layeredPane);
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    private void createButtons(Map<String, String> characterTypes){
        createPanelCharacters();
        createShovelButton();
        createPauseButton();
        createCharacterButtons(characterTypes);
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


    private void startGame(){
        createSunGridGenerator();
        createZombieGridGenerator();
        createLawnMowerGridGenerator();
        player.playMusic();
        gameTimer = new Timer(500, e -> verifyGameStatus());
        gameTimer.start();
        gameTimeDuration =  System.currentTimeMillis();
    }

    private boolean checkTime() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - gameTimeDuration;  
        long elapsedMinutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
      
        long totalDuration = GameConfig.getGameDuration();
        int percentageProgress = (int) ((elapsedTime * 100) / (totalDuration * 60 * 1000)); 

        progressBar.setValue(percentageProgress); //actualiza el valor y pinta otra vez la barrita de progreso

        updateSpamZombiesByTimeGame();

        if (elapsedMinutes >= totalDuration) {
            gameTimer.stop();
            return true;
        }
        return false;
    } 


    private void verifyGameStatus() {
        String gameOverMessage = "";  
        AudioPlayer gameFinishPlayer;
        if (GameConfig.getIsGameOver()) {
            gameOverMessage = "Game Over"; 
            gameFinishPlayer = new AudioPlayer("resources/Efects/loseSound.wav");
        } else if (checkTime()) {
            gameOverMessage = "Ganaste";  
            gameFinishPlayer = new AudioPlayer("resources/Efects/winSound.wav");
        } else {
            return; 
        }
    
        pauseGame();
        gameTimer.stop();
        GameConfig.setIsPaused();
        gameFinishPlayer.playSoundOnce();

        GameOverMenu gameOverMenu = new GameOverMenu(this, gameOverMessage);
        layeredPane.add(gameOverMenu, Integer.valueOf(10));
        layeredPane.revalidate();
        layeredPane.repaint();
        gameOverMenu.setVisible(true);           
        
    }

    private void setupProgressBar() {
        progressBar = AssistantGraphic.createProgressBar(550, 20, 400, 30); 
        layeredPane.add(progressBar, Integer.valueOf(10));
    }
    private void configurePauseButton(JButton button) {
        button.addActionListener(e -> {
            pauseGame(); 
            GameConfig.setIsPaused();
            pauseMenu.setVisible(true); 
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
        sunTimer = new Timer(10000, e -> { if (!isPaused) {sunGenerator.addRandomSun(50);}} );
        sunTimer.start();
    }

    private void createZombieGridGenerator() {
        zombieGenerator = new ZombieGenerator(grid, characterPanel);
        zombieTimer = new Timer(20000, e -> {if (!isPaused) zombieGenerator.addRandomZombie();});
        zombieTimer.start();
    }

    private void createLawnMowerGridGenerator() {
        lawnMowerGenerator = new LawnMowerGenerator(grid, characterPanel);
    }

    private void updateSpamZombiesByTimeGame() {
        long elapsedTime = System.currentTimeMillis() - gameTimeDuration; 
        long totalDuration = GameConfig.getGameDuration();  
    
        long totalGameTimeInMs = totalDuration * 60 * 1000; 
        long adjustedGameTimeInMs = totalGameTimeInMs - 20000; 
    
        long timeSecondRound = adjustedGameTimeInMs / 3;
        long timeThirdRound = timeSecondRound * 2;
    
        //System.out.println("Tiempo total en milisegundos: " + elapsedTime + " delay: " + zombieTimer.getDelay());
    
        if (elapsedTime < 20000){
            return;
        }
        if (elapsedTime < (20000 + timeSecondRound)) {  
            if (zombieTimer.getDelay() != 10000) {  
                resetZombieTimer(10000);  
            }
        } else if (elapsedTime < (20000 + timeThirdRound)) { 
            System.out.println("Tercera ronda: Zombies cada 7 segundos.");
            if (zombieTimer.getDelay() != 7000) {  
                resetZombieTimer(7000);  
            }
        } else {  
            System.out.println("Cuarta ronda: Zombies cada 5 segundos.");
            if (zombieTimer.getDelay() != 5000) {  
                resetZombieTimer(5000);  
            }
        }
    }
    
    private void resetZombieTimer(int delay) {
        if (zombieTimer != null) {
            zombieTimer.stop(); 
        }
    
        zombieTimer = new Timer(delay, e -> {
            if (!isPaused) {
                zombieGenerator.addRandomZombie();
            }
        });
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
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
            JPanel buttonPanel = AssistantGraphic.createButtonWithImage(entry.getValue(), action, entry.getKey(), 60, 60);

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
        shovel = AssistantGraphic.createButtonWithImage("resources/Shovel.png", action, "shovel", 60, 60);
        shovel.setBounds(300, 20, 80, 80);
        shovel.setOpaque(false);
        layeredPane.add(shovel, Integer.valueOf(3));
    }

    private ActionListener createShovelSelectionAction() {
        return e -> {
            isShovelActive = !isShovelActive;
    
            if (isShovelActive) {
                selectedCharacter = null; 
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
                //showBoard();
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
            characters.removeIf(character -> !character.isZombie());
            characterPanel.repaint();
            System.out.println("Planta eliminada en la celda (" + row + ", " + col + ").");
        }
    }

    private boolean checkPlacementConditions(String type, int cost, int row, int col) {
        if (!grid.isPlacementValid(type, col)) {
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

        if (grid.placeCharacter(character, row, col)) {
            sunGenerator.subtractSun(character.getCost());
            characterPanel.repaint();
            
            //showBoard();
            if (!character.isZombie()) {
                ((Plant) character).startAction(grid, sunGenerator);
                ((Plant) character).startAction();
                
            }
            if (character.isZombie()) {
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

    public void exitGame() {
        player.stopMusic();
        grid.stop();
        grid.reset();
        dispose();
    }

    public void saveGame() {
        GameController.saveGame(grid, characterTypes); // Llama al método de GameController para guardar el Grid
    }

    public void setLoadGame(Grid grid) {
        this.grid = grid;
        characterPanel.setGrid(grid);
        grid.continueGame();
        isPaused = false;
    }

    
 

    public void showBoard() {
        StringBuilder boardState = new StringBuilder();
        boardState.append("Estado del Tablero:\n");
        boardState.append("-".repeat(grid.getColumns() * 8 + 1)).append("\n"); // Bordes superiores
        
        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getColumns(); col++) {
                List<Character> cellCharacters = grid.getCharactersInCell(row, col);
                
                if (cellCharacters == null || cellCharacters.isEmpty()) {
                    boardState.append("| Vacío "); // Celda vacía
                } else {
                    StringBuilder cellContent = new StringBuilder("| ");
                    for (Character character : cellCharacters) {
                        cellContent.append(character.getType().charAt(0)); // Agrega la inicial del tipo de personaje
                    }
                    boardState.append(String.format("%-6s", cellContent.toString())); // Ajusta el formato
                }
            }
            boardState.append("|\n"); // Cierra la fila
            boardState.append("-".repeat(grid.getColumns() * 8 + 1)).append("\n"); // Bordes entre filas
        }
    
        JOptionPane.showMessageDialog(null, boardState.toString(), "Estado del Tablero", JOptionPane.INFORMATION_MESSAGE);
    }

}
