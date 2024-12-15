package Tests;

import Dominio.Grid;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;
import java.util.List;

public class GridTest {
    private Grid grid;
    private int rows = 5;
    private int cols = 10;
    private int cell_size = 60;

    @Before
    public void setUp() {
        grid = new Grid(rows, cols, cell_size);
    }

    @Test
    public void testGridInitialization() {
        assertEquals(rows, grid.getRows());
        assertEquals(cols, grid.getColumns());
        assertEquals(cell_size, grid.getCellSize());
    }

    @Test
    public void testPlaceCharacter() {
        Plant plant = new Plant("Sunflower", 100, 50);
        assertTrue(grid.placeCharacter(plant, 2, 2));
        assertFalse(grid.placeCharacter(plant, 2, 2)); 
        assertFalse(grid.placeCharacter(plant, -1, 2)); 
    }

    @Test
    public void testMoveCharacter() {
        Plant plant = new Plant("Peashooter", 100, 50);
        grid.placeCharacter(plant, 1, 1);
        assertTrue(grid.moveCharacter(plant, 1, 1, 2, 2));
        assertFalse(grid.moveCharacter(plant, 1, 1, 2, 2)); 
    }

    @Test
    public void testIsValidPosition() {
        assertTrue(grid.isValidPosition(0, 0));
        assertTrue(grid.isValidPosition(rows-1, cols-1));
        assertFalse(grid.isValidPosition(-1, 0));
        assertFalse(grid.isValidPosition(rows, cols));
    }

    @Test
    public void testPlacementValidation() {
        assertTrue(grid.isPlacementValid("Plant", 1));
        assertFalse(grid.isPlacementValid("Plant", 0));
        assertFalse(grid.isPlacementValid("Plant", 9));
        assertTrue(grid.isPlacementValid("Zombie", 9));
        assertFalse(grid.isPlacementValid("Zombie", 8));
    }

    @Test
    public void testGetCharactersInCell() {
        Plant plant = new Plant("Sunflower", 100, 50);
        grid.placeCharacter(plant, 1, 1);
        List<Character> characters = grid.getCharactersInCell(1, 1);
        assertNotNull(characters);
        assertEquals(1, characters.size());
        assertEquals(plant, characters.get(0));
    }

    @Test
    public void testHasPlantInCell() {
        Plant plant = new Plant("Peashooter", 100, 50);
        grid.placeCharacter(plant, 2, 2);
        assertTrue(grid.hasPlantInCell(2, 2));
        assertFalse(grid.hasPlantInCell(1, 1));
    }

    @Test
    public void testGetAllCharacters() {
        Plant plant1 = new Plant("Sunflower", 100, 50);
        Plant plant2 = new Plant("Peashooter", 100, 50);
        grid.placeCharacter(plant1, 1, 1);
        grid.placeCharacter(plant2, 2, 2);
        List<Character> allCharacters = grid.getAllCharacters();
        assertEquals(2, allCharacters.size());
        assertTrue(allCharacters.contains(plant1));
        assertTrue(allCharacters.contains(plant2));
    }

    @Test
    public void testRemoveCharacter() {
        Plant plant = new Plant("Sunflower", 100, 50);
        grid.placeCharacter(plant, 1, 1);
        grid.removeCharacter(plant, 1, 1);
        assertFalse(grid.hasPlantInCell(1, 1));
    }

    @Test
    public void testReset() {
        Plant plant = new Plant("Sunflower", 100, 50);
        grid.placeCharacter(plant, 1, 1);
        grid.reset();
        assertNull(grid.getPlantInCell(1, 1));
        assertEquals(0, grid.getAllCharacters().size());
    }
}
