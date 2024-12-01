package Dominio;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import Presentation.AudioPlayer;
import Presentation.CharacterGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Zombie extends Character {
    private int speed; // Velocidad en píxeles
    private int armor;
    private String direction; // Dirección de movimiento
    private int damage; // Daño que inflige a las plantas
    private CharacterGUI characterGUI; // Referencia a la interfaz gráfica
    private static final String TYPE = "Zombie";
    private static final int DAMAGE_TIME = 500;
    private Timer attackTimer; // Timer para manejar el ataque periódico
    private static final AudioPlayer player = new AudioPlayer("resources/Efects/attackZombie.wav");

    public Zombie(String name, int health, int x, int y, int brainCost, int speed, int armor, String direction, int damage, ImageIcon image, CharacterGUI characterGUI) {
        super(name, health, x, y, brainCost, image, TYPE);
        this.speed = speed;
        this.armor = armor;
        this.direction = direction;
        this.damage = damage;
        this.characterGUI = characterGUI;
        this.attackTimer = null; // Inicialmente no hay Timer
    }

    public void attack(Character plant) {
        System.out.println(getName() + " bajando vida " + plant.getHealth());
        plant.takeDamage(damage);
    }

    private void startAttack(Character plant, int row, int col) {
        if (attackTimer != null && attackTimer.isRunning()) {
            return; 
        }

        attackTimer = new Timer(DAMAGE_TIME, new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                if (plant.isAlive() && characterGUI.getGrid().getCharactersInCell(row, col).contains(plant)) {
                    attack(plant);
                    player.playMusic();
                    if (!plant.isAlive()) {
                        System.out.println(plant.getName() + " ha sido eliminado.");
                        characterGUI.getGrid().removeCharacter(plant, row, col);
                        characterGUI.repaint();
                        attackTimer.stop(); // Detener el Timer al eliminar la planta
                        player.stopMusic();
                    }
                } else {
                    attackTimer.stop(); // Detener el Timer si la planta no está viva o no está en la celda
                }
            }
        });
        attackTimer.start();
    }

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
        characterGUI.repaint();
    }

    public void move() {
        new Thread(() -> {
            try {
                int step = 2; // Movimiento incremental en píxeles
                while (isAlive()) {
                    Thread.sleep(50); // Intervalo de actualización (20 FPS)

                    // Calcular nueva posición en píxeles
                    int[] newPosition = calculateNewPosition(step);
                    int newX = newPosition[0];
                    int newY = newPosition[1];

                    // Determinar la celda lógica actual y futura
                    int currentRow = characterGUI.getGrid().getRowFromY(getCoordenatesY());
                    int currentCol = characterGUI.getGrid().getColFromX(getCoordenatesX());
                    int newRow = characterGUI.getGrid().getRowFromY(newY);
                    int newCol = characterGUI.getGrid().getColFromX(newX);

                    // Verificar si la nueva posición está dentro del tablero
                    if (!characterGUI.getGrid().isValidPosition(newRow, newCol)) {
                        
                        System.out.println(getName() + " alcanzó el límite del tablero." + newRow + newCol);
                        break;
                    }

                    // Verificar si hay una planta en la celda futura
                    if (currentRow == newRow && currentCol == newCol) {
                        if (characterGUI.getGrid().hasPlantInCell(newRow, newCol)) {
                            Character plant = characterGUI.getGrid().getPlantInCell(newRow, newCol);
                            startAttack(plant, newRow, newCol); // Iniciar ataque periódico con Timer
                            continue;
                        }
                    }

                    // Actualizar posición del zombie
                    setPosition(newX, newY);

                    // Si cruza el borde de una celda, actualizar la matriz lógica
                    if (newRow != currentRow || newCol != currentCol) {
                        characterGUI.getGrid().moveCharacter(this, currentRow, currentCol, newRow, newCol);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private int[] calculateNewPosition(int step) {
        int newX = getCoordenatesX();
        int newY = getCoordenatesY();

        switch (direction.toLowerCase()) {
            case "left":
                newX -= step;
                break;
            case "right":
                newX += step;
                break;
            case "up":
                newY -= step;
                break;
            case "down":
                newY += step;
                break;
        }

        return new int[]{newX, newY};
    }
}
