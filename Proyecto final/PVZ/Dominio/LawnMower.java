package Dominio;

import javax.swing.ImageIcon;

import Presentation.AudioPlayer;
import Presentation.CharacterGUI;
import Presentation.GameConfig;


public class LawnMower extends Character {
    private static final ImageIcon IMAGE = new ImageIcon("resources/LawnMower.png");
    private static final String TYPE = "LawnMower";
    private CharacterGUI characterGUI;
    private boolean activated = false; 
    boolean soundPlayed = false; 
    AudioPlayer player = new AudioPlayer("resources/Efects/killLawnMoner.wav");

    public LawnMower(int x, int y, CharacterGUI characterGUI) {
        super("LawnMower", 3000, x, y, 0, IMAGE, TYPE);
        this.characterGUI = characterGUI;
        move();
    }

    @Override
    public boolean isLawnMower() {
        return true;
    }

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
                e.printStackTrace();
            }
        }).start();
    }
    
    
    private boolean checkPause() throws InterruptedException {
        if (GameConfig.getIsPaused()) {
            Thread.sleep(50);
            return true;
        }
        return false;
    }

    private void activateIfZombiePresent(int row, int col) throws InterruptedException {
        if (characterGUI.getGrid().hasZombieInCell(row, col)) {
            activated = true; 
            Thread.sleep(10);
        }
    }

    private void moveLawnMower(int currentRow, int currentCol) throws InterruptedException {
        Thread.sleep(10);
        if (characterGUI.getGrid().hasZombieInCell(currentRow, currentCol)) {
            Character zombie = characterGUI.getGrid().getZombieInCell(currentRow, currentCol);
            characterGUI.getGrid().removeCharacter(zombie, currentRow, currentCol);
            zombie.takeDamage(zombie.getHealth());
        }
    }

    private void removeLawnMower(int currentRow, int currentCol) {
        characterGUI.getGrid().removeCharacter(this, currentRow, currentCol-1); 
        characterGUI.repaint();
        System.out.println("LawnMower removed at board limit");
    }

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
        characterGUI.repaint();
    }

    private boolean hasReachedBoardLimit(int currentRow, int currentCol) {
        return !characterGUI.getGrid().isValidPosition(currentRow, currentCol);
    }
    
    
    private void updatePosition(int newX, int newY, int currentRow, int currentCol, int newRow, int newCol) {
        setPosition(newX, newY);
        if (currentRow != newRow || currentCol != newCol) {
            characterGUI.getGrid().moveCharacter(this, currentRow, currentCol, newRow, newCol);
        }
    }

    private int[] calculateNewPosition(int step) {
        int newX = getCoordenatesX();
        int newY = getCoordenatesY();

        newX += step;

        return new int[]{newX, newY};
    }
    public void stop() {
    }

}
