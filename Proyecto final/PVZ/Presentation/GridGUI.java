package Presentation;

import Dominio.Character;
import Dominio.CharacterFactory;
import Dominio.GameConfig;
import Dominio.GameController;
import Dominio.Grid;
import Dominio.Plant;
import Dominio.Zombie;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

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
    private AudioPlayer player = new AudioPlayer("resources/Music/SoundTrack/easyMusic.wav");
    private boolean isPaused;
    private Timer sunTimer;
    private Timer zombieTimer;
    private Timer gameTimer;
    private long gameTimeDuration;
    private JProgressBar progressBar;
    private Map<String, String> characterTypes;
    private int numberOfRounds;
    private long totalDuration;


    /**
     * Constructs a GridGUI instance to represent the game board and user interface for Plants vs Zombies.
     * 
     * @param characterTypes A map of character types and their corresponding images.
     */
    public GridGUI(Map<String, String> characterTypes) {
        super("Plants vs Zombies", "resources/Backgrounds/gridGame.jpg");
        this.grid = new Grid(rows, cols, CELL_SIZE);
        this.sunCount = GameConfig.getInitialSuns();
        this.isPaused = false;
        this.characterTypes = characterTypes;
        this.numberOfRounds = GameConfig.getRounds();
        this.totalDuration =  GameConfig.getGameDuration();
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

    /**
     * Creates the base panel for the game, using a JLayeredPane to allow layered components.
     * This panel serves as the foundation for the grid and other UI elements.
     */

    private void createPanelBase() {
        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, getWidth(), getHeight());
        layeredPane.setOpaque(false);
        getContentPane().add(layeredPane);
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    /**
     * Creates and initializes all the buttons used in the game, including character buttons,
     * the shovel button, and the pause button.
     * 
     * @param characterTypes A map of character types and their corresponding images.
     */
    private void createButtons(Map<String, String> characterTypes){
        createPanelCharacters();
        createShovelButton();
        createPauseButton();
        createCharacterButtons(characterTypes);
    }

    /**
     * Adds the pause button to the game interface and configures its functionality
     * to toggle the pause menu and pause the game.
     */

    private void createPauseButton(){
        layeredPane.add(pauseButton, Integer.valueOf(4));
        configurePauseButton(pauseButton);
        createPauseMenu();
    }

    /**
     * Creates the pause menu and adds it to the layered pane of the game interface.
     * The pause menu allows players to pause the game, resume, save the game, or return to the main menu.
     */

    private void createPauseMenu() {
        pauseMenu = new PauseMenu(this); 
        getLayeredPane().add(pauseMenu, Integer.valueOf(1)); 
    }

    /**
     * Starts the game by initializing components such as the sun, zombie, and lawnmower generators.
     * Plays background music, initializes the game timer, and tracks the game duration.
     */

    private void startGame(){
        createSunGridGenerator();
        createZombieGridGenerator();
        createLawnMowerGridGenerator();
        player.playMusic();
        gameTimer = new Timer(500, e -> verifyGameStatus());
        gameTimer.start();
        gameTimeDuration =  System.currentTimeMillis();
    }

    /**
     * Checks if the total game duration has been reached.
     * Updates the progress bar and adjusts zombie spawning rates based on the elapsed game time.
     * 
     * @return true if the total game time has elapsed, false otherwise.
     */

    private boolean checkTime() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - gameTimeDuration;  
        long elapsedMinutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
      
        int percentageProgress = (int) ((elapsedTime * 100) / (totalDuration * 60 * 1000)); 

        progressBar.setValue(percentageProgress); //actualiza el valor y pinta otra vez la barrita de progreso

        updateSpamZombiesByTimeGame();

        if (elapsedMinutes >= totalDuration) {
            gameTimer.stop();
            return true;
        }
        return false;
    } 

    /**
     * Verifies the current game status to check if the game has been won or lost.
     * If the game ends, pauses the game, stops the timer, and displays the game-over menu.
     */

    private void verifyGameStatus() {
        String gameOverMessage = "";  
        AudioPlayer gameFinishPlayer;
        if (GameConfig.getIsGameOver()) {
            gameOverMessage = "Game Over"; 
            gameFinishPlayer = new AudioPlayer("resources/Music/Efects/loseSound.wav");
            //System.out.println(GameConfig.getPuntaje());
        } else if (checkTime()) {
            gameOverMessage = "Ganaste";  
            gameFinishPlayer = new AudioPlayer("resources/Music/Efects/winSound.wav");
            //System.out.println(GameConfig.getPuntaje());
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

    /**
     * Sets up the progress bar to display the game's elapsed time.
     * The progress bar is added to the layered pane at a high z-index to ensure visibility.
     */

    private void setupProgressBar() {
        progressBar = AssistantGraphic.createProgressBar(550, 20, 400, 30); 
        layeredPane.add(progressBar, Integer.valueOf(10));
    }

    /**
     * Configures the pause button to pause the game and display the pause menu.
     * 
     * @param button The button to configure.
     */

    private void configurePauseButton(JButton button) {
        button.addActionListener(e -> {
            pauseGame(); 
            GameConfig.setIsPaused();
            pauseMenu.setVisible(true); 
        });
    }

    /**
     * Creates and initializes the character panel, which displays characters on the game grid.
     * The panel is added to the layered pane for rendering.
     */

    private void createPanelCharacters() {
        characterPanel = new CharacterGUI(grid, CELL_SIZE);
        characterPanel.setBounds(0, 0, getWidth(), getHeight());
        layeredPane.add(characterPanel, Integer.valueOf(2)); 
    }

    /**
     * Creates and initializes the sun counter label, which displays the player's current sun count.
     * The label is styled with a yellow color to visually represent the suns and added to the layered pane.
     */

    private void createCounterSuns() {
        sunCounterLabel = new JLabel("Suns: " + sunCount);
        sunCounterLabel.setBounds(20, 20, 100, 30);
        sunCounterLabel.setForeground(Color.YELLOW);
        layeredPane.add(sunCounterLabel, Integer.valueOf(4)); 
    }

    /**
     * Creates and initializes the sun generator to periodically add random suns to the game grid.
     * The generator starts a timer that adds a sun every 10 seconds unless the game is paused.
     */

    private void createSunGridGenerator() {
        sunGenerator = new SunGenerator(grid, gridPanel, sunCounterLabel, sunCount);
        sunTimer = new Timer(10000, e -> { if (!isPaused) {sunGenerator.addRandomSun(50);}} );
        sunTimer.start();
    }

    /**
     * Creates and initializes the zombie generator to periodically add random zombies to the game grid.
     * The generator starts a timer that spawns a zombie every 20 seconds unless the game is paused.
     */

    private void createZombieGridGenerator() {
        zombieGenerator = new ZombieGenerator(grid, characterPanel);
        zombieTimer = new Timer(20000, e -> {if (!isPaused) zombieGenerator.addRandomZombie();});
        zombieTimer.start();
    }

    /**
     * Creates and initializes the lawn mower generator, which places lawn mowers on the grid
     * at the beginning of the game.
     */

    private void createLawnMowerGridGenerator() {
        lawnMowerGenerator = new LawnMowerGenerator(grid, characterPanel);
    }

    /**
     * Updates the zombie spawn delay dynamically based on the elapsed game time and the current round.
     * The spawn delay decreases as the game progresses, making the game more challenging.
     */

    private void updateSpamZombiesByTimeGame() {
        long elapsedTime = System.currentTimeMillis() - gameTimeDuration; 
        long totalDurationMil = totalDuration * 60 * 1000;  
        long initialDelay = 20000;  // primeros 20 segundos 
        long gameDurationForRounds = totalDurationMil - initialDelay; //tiempo partida sin los 20 segundos 
        long durationPerRound = gameDurationForRounds / numberOfRounds;  //duracion de las rondas 
        //System.out.println(durationPerRound);
        if (elapsedTime < initialDelay) {
            return;  
        }
    
        int currentRound = (int) ((elapsedTime - initialDelay) / durationPerRound) + 1;
        if (currentRound > numberOfRounds) {
            currentRound = numberOfRounds;  
        }
    
        int newDelay = calculateZombieSpawnDelay(currentRound, numberOfRounds);
    
        if (zombieTimer.getDelay() != newDelay) {
            //System.out.println(newDelay);
            resetZombieTimer(newDelay);
        }
    }
    
    /**
     * Calculates the zombie spawn delay for the current round.
     * The delay decreases progressively with each round to make the game more challenging.
     * 
     * @param currentRound The current round of the game.
     * @param totalRounds The total number of rounds in the game.
     * @return The calculated zombie spawn delay in milliseconds.
     */

    private int calculateZombieSpawnDelay(int currentRound, int totalRounds) {
        int maxDelay = 15000;  // Maximo tiempo de spawn de zombies 
        int minDelay = 7000;   // Minimo tiempo de spawn 
        int delayDecreasePerRound = (maxDelay - minDelay) / (totalRounds - 1);
        return maxDelay - (delayDecreasePerRound * (currentRound - 1));
    }
    
    /**
     * Resets the zombie spawn timer with a new delay value.
     * The timer is restarted with the updated delay to adjust zombie spawning rates.
     * 
     * @param delay The new spawn delay for zombies in milliseconds.
     */

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
    
    /**
     * Creates the game grid as a panel of cells arranged in rows and columns.
     * Each cell is configured to respond to user interactions like placing characters.
     * 
     * @param rows The number of rows in the grid.
     * @param cols The number of columns in the grid.
     */

    private void createGrid(int rows, int cols) {
        gridPanel = new JPanel(null);
        gridPanel.setBounds(0, 0, getWidth(), getHeight());
        gridPanel.setOpaque(false);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                JPanel cell = new JPanel();
                cell.setOpaque(false);
                //cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                Point position = calculatePosition(row, col);
                cell.setBounds(position.x, position.y, CELL_SIZE, CELL_SIZE + 20);
                configureClickOnCell(row, col, cell);
                gridPanel.add(cell);
            }
        }
        layeredPane.add(gridPanel, Integer.valueOf(1));
    }

    /**
     * Creates buttons for each character type available in the game.
     * Each button is styled with an image and configured with an action listener for character selection.
     * 
     * @param characterTypes A map of character names and their corresponding image file paths.
     */

    private void createCharacterButtons(Map<String, String> characterTypes) {
        int sizeButtonX = 50;
        int sizeButtonY = 50;

        for (var entry : characterTypes.entrySet()) {
            ActionListener action = createCharacterSelectionAction(entry.getKey());
            JPanel buttonPanel = AssistantGraphic.createButtonWithImage(entry.getValue(), action, entry.getKey(), 60, 60);
            
            buttonPanel.setBounds(sizeButtonX, sizeButtonY, 80, 80);
            
            layeredPane.add(buttonPanel, Integer.valueOf(3)); 

            sizeButtonY += 100;
        }
    }

    /**
     * Creates an ActionListener for selecting a character type.
     * When triggered, the listener deselects the shovel and sets the selected character.
     *
     * @param characterType The type of character to be selected.
     * @return An ActionListener for character selection.
     */

    private ActionListener createCharacterSelectionAction(String characterType) {
        return e -> {
            isShovelActive = false; // Quita la pala si esta activada 
            selectedCharacter = CharacterFactory.createCharacter(characterType, 0, 0, characterPanel);
            System.out.println("Personaje seleccionado: " + characterType);
        };
    }

    /**
     * Creates the shovel button and configures its appearance and functionality.
     * The shovel button toggles the active state of the shovel tool.
     */

    private void createShovelButton() {
        ActionListener action = createShovelSelectionAction();
        shovel = AssistantGraphic.createButtonWithImage("resources/Characters/Shovel.png", action, "shovel", 60, 60);
        shovel.setBounds(300, 20, 80, 80);
        shovel.setOpaque(false);
        layeredPane.add(shovel, Integer.valueOf(3));
    }

    /**
     * Creates an ActionListener for toggling the shovel tool.
     * When activated, the shovel deselects any selected character and enables plant removal mode.
     *
     * @return An ActionListener for toggling the shovel.
     */

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

    /**
     * Configures the behavior for mouse clicks on a grid cell.
     * Depending on the active tool (shovel or selected character), performs the appropriate action.
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @param cell The JPanel representing the grid cell.
     */

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

    /**
     * Removes all plant characters from a specified cell on the grid.
     * This method is typically used when the shovel tool is active.
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     */

    private void removePlantFromCell(int row, int col) {
        var characters = grid.getCharactersInCell(row, col);
        if (characters != null) {
            characters.removeIf(character -> !character.isZombie());
            characterPanel.repaint();
        }
    }

    /**
     * Checks whether a character can be placed in a specific cell based on placement rules and resource availability.
     *
     * @param type The type of the character being placed.
     * @param cost The sun cost of the character.
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return True if placement is valid; false otherwise.
     */

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

    /**
     * Places a character on the specified grid cell and updates its position.
     * Deducts the character's cost from the available suns and starts its action or movement.
     *
     * @param character The character to be placed.
     * @param row The row index of the grid cell.
     * @param col The column index of the grid cell.
     */

    private void placeCharacterOnGrid(Character character, int row, int col) {
        Point position = calculatePosition(row, col);
        character.setPosition(position.x, position.y);

        if (grid.placeCharacter(character, row, col)) {
            sunGenerator.subtractSun(character.getCost());
            characterPanel.repaint();
            
            //showBoard(); mostrar tablero 
            if (!character.isZombie()) {
                ((Plant) character).startAction(grid, sunGenerator);
                ((Plant) character).startAction();
                
            }
            if (character.isZombie()) {
                ((Zombie) character).move();
            }
        }
    }

    /**
     * Calculates the pixel position of a grid cell based on its row and column indices.
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return A Point representing the top-left corner of the cell in pixels.
     */

    private Point calculatePosition(int row, int col) {
        int x = (GRID_X_BASE + col * CELL_SIZE);
        int y = GRID_Y_BASE + row * (CELL_SIZE + 20);
        return new Point(x, y);
    }

    /**
     * Pauses the game by stopping all active timers and music.
     * Updates the game configuration to reflect the paused state.
     */

    public void pauseGame() {
        isPaused = true;
        sunTimer.stop();
        zombieTimer.stop();
        player.stopMusic();
        gameTimer.stop();
        GameConfig.setIsPaused();
        
    }   

    /**
     * Resumes the game by restarting all active timers and music.
     * Updates the game configuration to reflect the resumed state.
     */

    public void resumeGame() {
        isPaused = false;
        sunTimer.start();
        zombieTimer.start();
        player.playMusic();
        gameTimer.start();
        GameConfig.setIsNotPaused();
    }

    public static void main(String[] args) {
        Map<String, String> characterTypes = Map.of(
            "Peashooter", "resources/Characters/Peashooter.png",
            "Sunflower", "resources/Characters/Sunflower.png",
            "WallNut", "resources/Characters/WallNut.png",
            "BasicZombie", "resources/Characters/BasicZombie.png",
            "ECIPlant", "resources/Characters/ECIPlant.png",
            "Evolve", "resources/Characters/Evolve.png"
        );
        new GridGUI(characterTypes);
    }

    /**
     * Retrieves the vertical base coordinate for the grid.
     *
     * @return The Y-coordinate base for the grid.
     */
    public static  int getGridYBase() {
        return GRID_Y_BASE;
    }

    /**
     * Retrieves the horizontal base coordinate for the grid.
     *
     * @return The X-coordinate base for the grid.
     */

    public static  int getGridXBase() {
        return GRID_X_BASE;
    }

    /**
     * Exits the game by stopping music, resetting the grid state, and disposing of the current window.
     */

    public void exitGame() {
        player.stopMusic();
        grid.stop();
        grid.reset();
        dispose();
    }

    /**
     * Saves the current game state using the GameController.
     */

    public void saveGame() {
        GameController.saveGame(grid, characterTypes); // Llama al método de GameController para guardar el Grid
    }

    /**
     * Sets the game to a previously loaded state, updating the grid and resuming game actions.
     *
     * @param grid The loaded grid state.
     */

    public void setLoadGame(Grid grid) {
        this.grid = grid;
        characterPanel.setGrid(grid);
        grid.continueGame();
        isPaused = false;
    }

    
 
    /**
     * Displays the current state of the grid in a formatted text-based representation.
     * Each cell is represented as either "Vacío" (empty) or the first letter of the character types present.
     */

     // generado con ayuda de gpt
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
