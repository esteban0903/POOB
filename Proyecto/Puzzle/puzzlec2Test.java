

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class puzzlec2Test.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class puzzlec2Test
{
    /**
     * Default constructor for test class puzzlec2Test
     */
    public puzzlec2Test()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
    }
    @Test
    public void testValidConstructor() {
        puzzle p = new puzzle(3, 4);
        assertEquals(4,p.getEnding().length);
        assertEquals(3,p.getEnding()[0].length);
        assertNotNull(p.getBoardRectanglesEdit());
        assertNotNull(p.getBoardRectanglesEnding());
        assertEquals(4,p.getBoardGlue().length);
        assertEquals(3,p.getBoardGlue()[0].length);
        assertEquals(4,p.actualArrangement().length);
        assertEquals(3,p.actualArrangement()[0].length);
    }
    @Test
    public void testInvalidConstructor() {
        //invalid width 
        puzzle p = new puzzle(-1,4);
        assertEquals(-1,p.getHeight());
        assertEquals(4,p.getWidth());
    }
    @Test
    public void testValidEndingConstructor() {
        char[][] validMatrix = {{'a','b','c'},{'d','e','f'}};
        
        puzzle p = new puzzle(validMatrix);
        
        assertEquals(2,p.getHeight());
        assertEquals(3,p.getWidth());
        assertArrayEquals(validMatrix, p.getEnding());
    }
    @Test
    public void testInvalidEndingConstructor() {
        char[][] invalidMatrix = {{'.'}};
    
        puzzle p = new puzzle(invalidMatrix);
        
        assertEquals(1,p.getHeight());  
        assertEquals(1,p.getWidth());   
        assertNull(p.getEnding());
    }
    @Test
    public void testValidDualConstructor() {
        char[][] validStarting = {{'a','b','c'}, {'d','e','f'}};
        char[][] validEnding = {{'g','h','i'}, {'j','k','l'}};
        
        puzzle p = new puzzle(validStarting, validEnding);
        
        assertEquals(2,p.getHeight()); 
        assertEquals(3,p.getWidth());  
        assertArrayEquals(validStarting,p.actualArrangement()); 
        assertArrayEquals(validEnding,p.getEnding());       
    }
    @Test
    public void testInvalidDualConstructor() {
        char[][] invalidStarting = {{'.'}}; 
        char[][] validEnding = {{'g','h','i'}, {'j','k','l'}};
        
        puzzle p = new puzzle(invalidStarting, validEnding);
        
        assertEquals(3,p.getHeight()); 
        assertEquals(3,p.getWidth());   
    }
    @Test
    public void testAddTileValidPosition() {
        puzzle p = new puzzle(5,5);
        p.addTile(2,3,"blue");
        assertNotNull(p.getBoardRectangles()[2][3]);
    }

    @Test
    public void testAddTileOccupiedPosition() {
        puzzle p = new puzzle(5,5);
        p.addTile(2,3,"blue");
        p.addTile(2,3,"red");
        assertEquals("red",p.getBoardRectangles()[2][3].getColor());
    }
    @Test
    public void testDeleteTileValid() {
        puzzle p = new puzzle(5,5);
        p.addTile(2,3, "blue");
        p.deleteTile(2,3);
        assertNull(p.getBoardRectangles()[2][3]);
        assertEquals('.',p.actualArrangement()[2][3]);
    }

    @Test
    public void testDeleteTileOccupiedByGlue() {
        puzzle p = new puzzle(5,5);
        p.addTile(2,3, "blue");
        p.getBoardGlue()[2][3] = 1; 
        p.deleteTile(2,3);
        assertNull(p.getBoardRectangles()[2][3]);
    }
    @Test
    public void testDeleteTileHole() {
        puzzle p = new puzzle(5,5);
        p.addTile(2,3, "blue");
        p.actualArrangement()[2][3] ='#';
        p.deleteTile(2,3);
        assertNull(p.getBoardRectangles()[2][3]);
    }

    @Test
    public void testDeleteTileAlreadyNull() {
        puzzle p = new puzzle(5,5);
        p.deleteTile(2,3);
        assertNotEquals('.',p.actualArrangement()[2][3]);
    }
    @Test
    public void testRelocateTileSuccess() {
        puzzle p = new puzzle(5,5);
        p.addTile(1,1,"red"); 
        int[] from = {1,1};
        int[] to = {2,2};
        p.relocateTile(from,to);
    
        assertEquals("red",p.getBoardRectangles()[2][2].getColor()); 
        assertNull(p.getBoardRectangles()[1][1]); 
    }
    
    @Test
    public void testRelocateTileToHole() {
        puzzle p = new puzzle(5,5);
        p.addTile(1,1,"green");
        int[] from = {1,1};
        int[] to = {2,2};
        p.actualArrangement()[2][2] = '#'; 
        p.relocateTile(from, to);

        assertNull(p.getBoardRectangles()[2][2]); 
    }
    
    @Test
    public void testRelocateTileWhenGlued() {
        puzzle p = new puzzle(5,5);
        p.addTile(1,1,"blue");
        p.getBoardGlue()[1][1] = 1; 
        int[] from = {1,1};
        int[] to = {2,2};
        p.relocateTile(from,to);
        assertNull(p.getBoardRectangles()[1][1]); 
    }
    @Test
    public void testAddGlueSuccess() {
        puzzle p = new puzzle(5, 5);
        p.addTile(2, 2, "yellow"); 
        p.addTile(1, 2, "yellow"); 
        p.addTile(3, 2, "yellow"); 
        p.addTile(2, 1, "yellow"); 
        p.addTile(2, 3, "yellow"); 
        
        p.addGlue(2, 2); 
    
        assertEquals(2, p.getBoardGlue()[2][2]); 
        assertEquals(1, p.getBoardGlue()[1][2]); 
        assertEquals(1, p.getBoardGlue()[3][2]); 
        assertEquals(1, p.getBoardGlue()[2][1]); 
        assertEquals(1, p.getBoardGlue()[2][3]); 
    }
    @Test
    public void testAddGlueOnAlreadyGluedTile() {
        puzzle p = new puzzle(5, 5);
        p.addTile(3, 3, "yellow"); 
        p.addGlue(3, 3); 
        p.addGlue(3, 3);
    
        assertEquals(4, p.getBoardGlue()[3][3]); 
    }
    @Test
    public void testAddGlueToEmptyTile() {
        puzzle p = new puzzle(5, 5);
        p.addGlue(4, 4); 
    
        assertEquals(2, p.getBoardGlue()[4][4]); 
    }
    @Test
    public void testDeleteGlueSuccess() {
        puzzle p = new puzzle(5, 5);
        p.addTile(2, 2, "yellow"); 
        p.addTile(1, 2, "yellow"); 
        p.addTile(3, 2, "yellow"); 
        p.addTile(2, 1, "yellow"); 
        p.addTile(2, 3, "yellow"); 
        p.addGlue(2, 2); 
        p.deleteGlue(2, 2); 
    
        assertEquals(0, p.getBoardGlue()[1][1]); 
        assertEquals(0, p.getBoardGlue()[2][2]); 
        assertEquals(0, p.getBoardGlue()[1][2]); 
        assertEquals(0, p.getBoardGlue()[3][2]); 
        assertEquals(0, p.getBoardGlue()[2][1]); 
        assertEquals(0, p.getBoardGlue()[2][3]); 
    }
    @Test
    public void testDeleteGlueWhenNoGlue() {
        puzzle p = new puzzle(5, 5);
        p.addTile(2, 2, "red");
        p.deleteGlue(2, 2); 
    
        assertEquals(-2, p.getBoardGlue()[2][2]); 
    }
    @Test
    public void testDeleteGlueOnEmptyTile() {
        puzzle p = new puzzle(5, 5);
        p.addGlue(3, 3); 
    
        assertEquals(-2, p.getBoardGlue()[3][3]); 
    }
    @Test
    public void testMakeHoleSuccess() {
        puzzle p = new puzzle(5, 5);
        
        p.makeHole(1, 1); 
    
        assertEquals("white", p.getBoardRectangles()[1][1].getColor()); 
        assertEquals('#', p.getMatrixColorsEdit()[1][1]); 
    }
    @Test
    public void testMakeHoleOnARectangleTile() {
        puzzle p = new puzzle(5, 5);
        p.addTile(2, 2, "red");
        p.makeHole(2, 2); 
    
        assertEquals("white", p.getBoardRectangles()[2][2].getColor()); 

    }
    @Test
    public void testExChange() {
        char[][] ending = new char[][] {{'a','b'}, {'c','d'}};
        char[][] starting = new char[][] {{'w','x'}, {'y','z'}};
        puzzle p = new puzzle(starting, ending);
        
        p.exChange();
        assertArrayEquals(ending,p.actualArrangement());
    }   
    
    @Test
    public void testIsGoal() {
        puzzle p = new puzzle(new char[][] {{'a','b'}, {'c','d'}},
            new char[][] {{'a','b'}, {'c','d'}});
            
        p.isGoal();
        
        assertArrayEquals(p.getEnding(),p.actualArrangement());   
    } 
    @Test
    public void testIsNotGoal() {
        puzzle p = new puzzle(new char[][] {{'a','b'}, {'c','d'}},
            new char[][] {{'a','b'}, {'c','c'}});
            
        p.isGoal();
        
        assertArrayEquals(p.getEnding(),p.actualArrangement());   
    } 
    
    @Test
    public void testActualArrangement() {
        char[][] startingMatrix = {{'a', 'b'}, {'c', 'd'}};
        char[][] startingMatrixx = {{'a', 'b'}, {'c', 'd'}};
        assertArrayEquals(startingMatrix, startingMatrixx);

    }
    @Test
    public void testFixedTiles() {
        char[][] starting = {{'a', '.'},{'c', 'd'}};
    
        puzzle p = new puzzle(starting, starting); 
        p.addGlue(0,0);
        p.makeHole(0,1);
        int[][] fixedTiles = p.fixedTiles();
        int[][] expectedFixedTiles = {{0, 0},{0, 1},{1,0}};
    
        assertArrayEquals(expectedFixedTiles, fixedTiles); // Comprobamos que las coordenadas sean correctas
    }
    @Test
    public void testMissplacedTiles() {
        char[][] starting = {{'a', 'b'},{'c', 'd'}};
        char[][] ending = {{'a', 'x'},{'c', 'y'}};
    
        puzzle p = new puzzle(starting, ending);
        int missplacedCount = p.missplacedTiles();
        assertEquals(2, missplacedCount); 
    }
    @Test
    public void testMakeVisible() {
        puzzle p = new puzzle(3, 3);
        p.makeVisible();
    
        assertTrue(p.isVisible());
        assertTrue(p.getEdgesEdit().getIsVisible());
        assertTrue(p.getBackgroundEdit().getIsVisible());
        assertTrue(p.getEdgesEnding().getIsVisible());
        assertTrue(p.getBackgroundEnding().getIsVisible());
    
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (p.getBoardRectanglesEdit()[i][j] != null) {
                    assertTrue(p.getBoardRectanglesEdit()[i][j].getIsVisible());
                }
                if (p.getBoardRectanglesEnding()[i][j] != null) {
                    assertTrue(p.getBoardRectanglesEnding()[i][j].getIsVisible());
                }
            }
        }
    }
    @Test
    public void testMakeInvisible() {
        puzzle p = new puzzle(3, 3);
        p.makeInvisible();
    
        assertFalse(p.isVisible());
        assertFalse(p.getEdgesEdit().getIsVisible());
        assertFalse(p.getBackgroundEdit().getIsVisible());
        assertFalse(p.getEdgesEnding().getIsVisible());
        assertFalse(p.getBackgroundEnding().getIsVisible());
    
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (p.getBoardRectanglesEdit()[i][j] != null) {
                    assertFalse(p.getBoardRectanglesEdit()[i][j].getIsVisible());
                }
                if (p.getBoardRectanglesEnding()[i][j] != null) {
                    assertFalse(p.getBoardRectanglesEnding()[i][j].getIsVisible());
                }
            }
        }
    }
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
        
    }
}
