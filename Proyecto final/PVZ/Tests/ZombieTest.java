package Tests;

import Dominio.Zombie;
import Dominio.Character;
import Dominio.Grid;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import javax.swing.ImageIcon;
import Presentation.CharacterGUI;
import Presentation.Grid;

public class ZombieTest {
    private TestZombie zombie;
    private CharacterGUI characterGUI;
    private Grid grid;
    
    private class TestZombie extends Zombie {
        public TestZombie(String name, int health, int x, int y, int brainCost, 
                         int speed, int armor, String direction, int damage, 
                         ImageIcon image, CharacterGUI characterGUI, int damageTime) {
            super(name, health, x, y, brainCost, speed, armor, direction, 
                  damage, image, characterGUI, damageTime);
        }
    }
    
    @Before
    public void setUp() {
        grid = new Grid(5, 5, 100, 100);
        characterGUI = new CharacterGUI(grid);
        zombie = new TestZombie("TestZombie", 100, 400, 200, 50, 
                               1, 20, "left", 25, 
                               new ImageIcon(), characterGUI, 1000);
    }
    
    @Test
    public void testZombieInitialization() {
        assertEquals("TestZombie", zombie.getName());
        assertEquals(120, zombie.getHealth()); 
        assertEquals(400, zombie.getCoordenatesX());
        assertEquals(200, zombie.getCoordenatesY());
        assertTrue(zombie.isZombie());
    }
    
    @Test
    public void testZombieAttack() {
        Plant testPlant = new Plant("TestPlant", 100, 0, 0, 50, 
                                   new ImageIcon(), "Plant") {};
        zombie.attack(testPlant);
        assertEquals(75, testPlant.getHealth()); 
    }
    
    @Test
    public void testPositionUpdate() {
        zombie.setPosition(300, 300);
        assertEquals(300, zombie.getCoordenatesX());
        assertEquals(300, zombie.getCoordenatesY());
    }
    
    @Test
    public void testZombieStop() {
        zombie.stop();
        zombie.setPosition(200, 200);
        assertEquals(200, zombie.getCoordenatesX());
        assertEquals(200, zombie.getCoordenatesY());
    }
    
    @Test
    public void testContinuePlaying() {
        zombie.stop();
        zombie.continuePlaying();t
        assertTrue(zombie.isAlive());
    }
    
    @Test
    public void testZombieTakeDamage() {
        zombie.takeDamage(50);
        assertEquals(70, zombie.getHealth()); 
    }
    
    @Test
    public void testZombieDeathState() {
        zombie.takeDamage(150);
        assertFalse(zombie.isAlive());
    }
}
