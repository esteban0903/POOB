package Dominio;

import Presentation.AudioPlayer;
import Presentation.CharacterGUI;
import javax.swing.ImageIcon;

/**
 * Represents a LawnMower character in the game that destroys zombies when activated.
 */

public class LawnMower extends Character {
    private static final ImageIcon IMAGE = new ImageIcon("resources/Characters/LawnMower.png");
    private static final String TYPE = "LawnMower";
    private CharacterGUI characterGUI;
    private boolean activated = false; 
    boolean soundPlayed = false; 
    private final transient AudioPlayer player = new AudioPlayer("resources/Music/Efects/killLawnMoner.wav");

    /**
     * Constructs a LawnMower with specified position and GUI component.
     * @param x The initial x-coordinate.
     * @param y The initial y-coordinate.
     * @param characterGUI The GUI component associated with this LawnMower.
     */
    public LawnMower(int x, int y, CharacterGUI characterGUI) {
        super("LawnMower", 3000, x, y, 0, IMAGE, TYPE);
        this.characterGUI = characterGUI;   
        move();
    }

    /**
     * Confirms if this character is a LawnMower.
     * @return Always returns true for LawnMower instances.
     */
    @Override
    public boolean isLawnMower() {
        return true;
    }
    
    /**
     * Handles the movement and functionality of the LawnMower on a separate thread.
     */
    @Override
    public void move() {
        new Thread(() -> {
            try {
                int currentRow = characterGUI.getGrid().getRowFromY(getCoordenatesY());
                int currentCol = characterGUI.getGrid().getColFromX(getCoordenatesX());
                while (!hasReachedBoardLimit(currentRow, currentCol)) {
        
                    if (checkPause()) continue;

                    if (!activated) {
                        activateIfZombiePresent(currentRow, currentCol);
                        continue;
                    }
                    if (!soundPlayed) {
                        player.playSoundOnce();  // Reproduce el sonido solo una vez
                        soundPlayed = true;    // Marca el sonido como reproducido
                    }
                    moveLawnMower(currentRow, currentCol);
                    
                    int[] newPosition = calculateNewPosition(2);
                    int newX = newPosition[0];
                    int newY = newPosition[1];
                    int newRow = characterGUI.getGrid().getRowFromY(newY);
                    int newCol = characterGUI.getGrid().getColFromX(newX);

                    updatePosition(newX, newY, currentRow, currentCol, newRow, newCol);

                    characterGUI.repaint();
                    currentRow = newRow;
                    currentCol = newCol;
                }
                removeLawnMower(currentRow, currentCol);
            } catch (InterruptedException e) {
            }
        }).start();
    }
    
    /**
     * Checks if the game is paused and the thread should wait.
     * @return True if the game is paused.
     * @throws InterruptedException if the thread is interrupted during sleep.
     */
    private boolean checkPause() throws InterruptedException {
        if (GameConfig.getIsPaused()) {
            Thread.sleep(50);
            return true;
        }
        return false;
    }

    /**
     * Activates the LawnMower if a zombie is present in the specified cell.
     * @param row The row of the cell to check.
     * @param col The column of the cell to check.
     * @throws InterruptedException if the thread is interrupted during sleep.
     */
    private void activateIfZombiePresent(int row, int col) throws InterruptedException {
        if (characterGUI.getGrid().hasZombieInCell(row, col)) {
            activated = true; 
            Thread.sleep(10);
        }
    }

    /**
     * Moves the LawnMower forward and attacks zombies if present.
     * @param currentRow The current row of the LawnMower.
     * @param currentCol The current column of the LawnMower.
     * @throws InterruptedException if the thread is interrupted during sleep.
     */
    private void moveLawnMower(int currentRow, int currentCol) throws InterruptedException {
        Thread.sleep(10);
        if (characterGUI.getGrid().hasZombieInCell(currentRow, currentCol)) {
            Character zombie = characterGUI.getGrid().getZombieInCell(currentRow, currentCol);
            characterGUI.getGrid().removeCharacter(zombie, currentRow, currentCol);
            zombie.takeDamage(zombie.getHealth());
        }
    }

    /**
     * Removes the LawnMower from the game once it reaches the board limit.
     * @param currentRow The row from which to remove the LawnMower.
     * @param currentCol The column from which to remove the LawnMower.
     */
    private void removeLawnMower(int currentRow, int currentCol) {
        characterGUI.getGrid().removeCharacter(this, currentRow, currentCol-1); 
        characterGUI.repaint();
        System.out.println("LawnMower removed at board limit");
    }

    /**
     * Sets the position of the LawnMower and triggers a repaint of the character GUI.
     * @param x The new x-coordinate of the LawnMower.
     * @param y The new y-coordinate of the LawnMower.
     */
    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
        characterGUI.repaint();
    }

    /**
     * Checks if the current position of the LawnMower is outside the valid game board boundaries.
     * @param currentRow The current row of the LawnMower.
     * @param currentCol The current column of the LawnMower.
     * @return true if the LawnMower has reached the board limit, false otherwise.
     */
    private boolean hasReachedBoardLimit(int currentRow, int currentCol) {
        return !characterGUI.getGrid().isValidPosition(currentRow, currentCol);
    }
    
    /**
     * Updates the position of the LawnMower in the game grid.
     * @param newX The new x-coordinate.
     * @param newY The new y-coordinate.
     * @param currentRow The current row before the move.
     * @param currentCol The current column before the move.
     * @param newRow The new row after the move.
     * @param newCol The new column after the move.
     */
    private void updatePosition(int newX, int newY, int currentRow, int currentCol, int newRow, int newCol) {
        setPosition(newX, newY);
        if (currentRow != newRow || currentCol != newCol) {
            characterGUI.getGrid().moveCharacter(this, currentRow, currentCol, newRow, newCol);
        }
    }

    /**
     * Calculates the new position of the LawnMower based on a step value.
     * @param step The distance to move in pixels.
     * @return An array containing the new x and y coordinates.
     */
    private int[] calculateNewPosition(int step) {
        int newX = getCoordenatesX();
        int newY = getCoordenatesY();

        newX += step;

        return new int[]{newX, newY};
    }
    
    /**
     * Stops the current action of the LawnMower.
     */
    @Override
    public void stop() {
    }

}
