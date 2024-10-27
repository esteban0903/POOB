

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
public class puzzlec4Test
{
    /**
     * Default constructor for test class puzzlec2Test
     */
    public puzzlec4Test()
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
    public void testShouldValidConstructor() {
        Puzzle p = new Puzzle(3, 4);
        assertEquals(4,p.getEnding().length);
        assertEquals(3,p.getEnding()[0].length);
        assertEquals(4,p.getBoardGlue().length);
        assertEquals(3,p.getBoardGlue()[0].length);
        assertEquals(4,p.actualArrangement().length);
        assertEquals(3,p.actualArrangement()[0].length);
    }
    @Test
    public void testShouldNotConstructor() {
        Puzzle p = new Puzzle(-1,4);
        assertNotNull(p.getEnding());
    }
    @Test
    public void testShouldEndingConstructor() {
        char[][] validMatrix = {{'a','b','c'},{'d','e','f'}};
        
        Puzzle p = new Puzzle(validMatrix);
        
        assertEquals(2,p.getEnding().length);
        assertEquals(3,p.getEnding()[0].length);
        assertArrayEquals(validMatrix, p.getEnding());
    }
    @Test
    public void testShouldEndingStartingConstructor() {
        char[][] validStarting = {{'a','b','c'}, {'d','e','f'}};
        char[][] validEnding = {{'g','h','i'}, {'j','k','l'}};
        
        Puzzle p = new Puzzle(validStarting, validEnding);
        
        assertEquals(2,p.actualArrangement().length); 
        assertEquals(3,p.actualArrangement()[0].length);
        assertEquals(2,p.getEnding().length); 
        assertEquals(3,p.getEnding()[0].length);  
        assertArrayEquals(validStarting,p.actualArrangement()); 
        assertArrayEquals(validEnding,p.getEnding());       
    }
    @Test
    public void testShouldNotEndingStartingConstructor() {
        char[][] invalidStarting = {{'.'}}; 
        char[][] validEnding = {{'g','h','i'}, {'j','k','l'}};
        
        Puzzle p = new Puzzle(invalidStarting, validEnding);
        
        assertNotNull(p.actualArrangement()); 
        assertNotNull(p.getEnding());   
    }
    @Test
    public void testShouldAddTileValidPosition() {
        Puzzle p = new Puzzle(5,5);
        p.addTile(2,3,"blue");
        assertEquals(p.actualArrangement()[2][3],'b');
    }

    @Test
    public void testShouldNotAddTileOccupiedPosition() {
        Puzzle p = new Puzzle(5,5);
        p.addTile(2,3,"blue");
        p.addTile(2,3,"red");
        assertEquals(p.actualArrangement()[2][3],'r');
    }
    @Test
    public void testShouldDeleteTile() {
        Puzzle p = new Puzzle(5,5);
        p.addTile(2,3, "blue");
        p.deleteTile(2,3);
        assertEquals('.',p.actualArrangement()[2][3]);
    }

    @Test
    public void testShouldNotDeleteTileHole() {
        Puzzle p = new Puzzle(5,5);
        p.addTile(2,3, "blue");
        p.actualArrangement()[2][3] ='#';
        p.deleteTile(2,3);
        assertEquals('.',p.actualArrangement()[2][3]);
    }

    @Test
    public void testShouldNotDeleteTileAlreadyNull() {
        Puzzle p = new Puzzle(5,5);
        p.deleteTile(2,3);
        assertNotEquals('.',p.actualArrangement()[2][3]);
    }
    @Test
    public void testShouldRelocateTile() {
        Puzzle p = new Puzzle(5,5);
        p.addTile(1,1,"red"); 
        int[] from = {1,1};
        int[] to = {2,2};
        p.relocateTile(from,to);
    
        assertEquals('r',p.actualArrangement()[2][2]);
        assertEquals('.',p.actualArrangement()[1][1]);
    }
    
    @Test
    public void testShouldRelocateTileToHole() {
        Puzzle p = new Puzzle(5,5);
        p.addTile(1,1,"green");
        int[] from = {1,1};
        int[] to = {2,2};
        p.actualArrangement()[2][2] = '#'; 
        p.relocateTile(from, to);

        assertEquals('.',p.actualArrangement()[1][1]);
    }
    @Test
    public void testShouldAddGlue() {
        Puzzle p = new Puzzle(5, 5);
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
    public void testShouldNotAddGlueOnAlreadyGluedTile() {
        Puzzle p = new Puzzle(5, 5);
        p.addTile(3, 3, "yellow"); 
        p.addGlue(3, 3); 
        p.addGlue(3, 3);
    
        assertEquals(4, p.getBoardGlue()[3][3]); 
    }
    @Test
    public void testShouldNotAddGlueToEmptyTile() {
        Puzzle p = new Puzzle(5, 5);
        p.addGlue(4, 4); 
    
        assertEquals(2, p.getBoardGlue()[4][4]); 
    }
    @Test
    public void testShouldDeleteGlue() {
        Puzzle p = new Puzzle(5, 5);
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
    public void testShouldNotDeleteGlueWhenNoGlue() {
        Puzzle p = new Puzzle(5, 5);
        p.addTile(2, 2, "red");
        p.deleteGlue(2, 2); 
    
        assertEquals(-2, p.getBoardGlue()[2][2]); 
    }
    
    @Test
    public void testShouldNotDeleteGlueOnEmptyTile() {
        Puzzle p = new Puzzle(5, 5);
        p.addGlue(3, 3); 
    
        assertEquals(-2, p.getBoardGlue()[3][3]); 
    }
    
    @Test
    public void testShouldMakeHole() {
        Puzzle p = new Puzzle(5, 5);
        
        p.makeHole(1, 1); 
    
        assertEquals('#',p.actualArrangement()[1][1]); 
    }
    
    @Test
    public void testShouldNotMakeHoleOnARectangleTile() {
        Puzzle p = new Puzzle(5, 5);
        p.addTile(2, 2, "red");
        p.makeHole(2, 2); 
    
        assertEquals('w',p.actualArrangement()[2][2]);

    }
    
    @Test
    public void testShouldExChange() {
        char[][] ending = new char[][] {{'a','b'}, {'c','d'}};
        char[][] starting = new char[][] {{'w','x'}, {'y','z'}};
        Puzzle p = new Puzzle(starting, ending);
        
        p.exChange();
        assertArrayEquals(ending,p.actualArrangement());
    }   
    
    @Test
    public void testShouldIsGoal() {
        Puzzle p = new Puzzle(new char[][] {{'a','b'}, {'c','d'}},
            new char[][] {{'a','b'}, {'c','d'}});
            
        p.isGoal();
        
        assertArrayEquals(p.getEnding(),p.actualArrangement());   
    } 
    
    @Test
    public void testShouldNotIsGoal() {
        Puzzle p = new Puzzle(new char[][] {{'a','b'}, {'c','d'}},
            new char[][] {{'a','b'}, {'c','c'}});
            
        p.isGoal();
        
        assertArrayEquals(p.getEnding(),p.actualArrangement());   
    } 
    
    @Test
    public void testShouldActualArrangement() {
        char[][] startingMatrix = {{'a', 'b'}, {'c', 'd'}};
        char[][] startingMatrixx = {{'a', 'b'}, {'c', 'd'}};
        assertArrayEquals(startingMatrix, startingMatrixx);

    }
    @Test
    public void testShouldFixedTiles() {
        char[][] starting = {{'a', '.'},{'c', 'd'}};
    
        Puzzle p = new Puzzle(starting, starting); 
        p.addGlue(0,0);
        p.makeHole(0,1);
        int[][] fixedTiles = p.fixedTiles();
        int[][] expectedFixedTiles = {{0, 0},{0, 1},{1,0}};
    
        assertArrayEquals(expectedFixedTiles, fixedTiles); // Comprobamos que las coordenadas sean correctas
    }
    @Test
    public void testShouldMissplacedTiles() {
        char[][] starting = {{'a', 'b'},{'c', 'd'}};
        char[][] ending = {{'a', 'x'},{'c', 'y'}};
    
        Puzzle p = new Puzzle(starting, ending);
        int missplacedCount = p.missplacedTiles();
        assertEquals(2, missplacedCount); 
    }
    @Test
    public void testShouldSolve(){
        char[][] starting = {
            {'.', 'r', '.', '.'},
            {'r', 'g', 'y', 'b'},
            {'.', 'b', '.', '.'},
            {'.', 'y', 'r', '.'}};
        char[][] ending = {
            {'y', 'r', 'b', 'r'},
            {'.', '.', 'y', 'r'},
            {'.', '.', '.', 'g'},
            {'.', '.', '.', 'b'}}; 
        PuzzleContest test = new PuzzleContest();
        assertEquals(true, test.canSolve(starting,ending));
    }
    @Test
    public void testShouldNotSolve2(){
        char[][] starting = {{'.', '.', '.', '.', 'x', '.', '.'}};
        char[][] ending = {{'.', '.', '.', '.', 'x', '.', '.'}};
        PuzzleContest test = new PuzzleContest();
        assertEquals(true, test.canSolve(starting,ending));
    }
    @Test
    public void testShouldNotSolve3(){
        char[][] starting = {
            {'y', 'r', '.'},
            {'.', '.', 'b'},
            {'r', 'y', '.'},
            {'b', '.', '.'}};
        char[][] ending = {
            {'.', '.', '.'},
            {'.', '.', 'b'},
            {'.', 'r', 'y'},
            {'b', 'y', 'b'}}; 
        PuzzleContest test = new PuzzleContest();
        assertEquals(true, test.canSolve(starting,ending));
    }
    @Test
    public void testShouldSimulate(){
        char[][] starting = {
            {'.', 'r', '.', '.'},
            {'r', 'g', 'y', 'b'},
            {'.', 'b', '.', '.'},
            {'.', 'y', 'r', '.'}};
        char[][] ending = {
            {'y', 'r', 'b', 'r'},
            {'.', '.', 'y', 'r'},
            {'.', '.', '.', 'g'},
            {'.', '.', '.', 'b'}}; 
        PuzzleContest test = new PuzzleContest();
        test.simulate(starting,ending);
    }
    @Test
    public void testShouldNotSimulate(){
        char[][] starting = {{'.', '.', '.', '.', 'x', '.', '.'}};
        char[][] ending = {{'.', '.', 'x', '.', '.', '.', '.'}};
        PuzzleContest test = new PuzzleContest();
        test.simulate(starting,ending);;
    }
    @Test
    public void testShouldNotSimulate2(){
        char[][] starting = {
            {'y', 'r', '.'},
            {'.', '.', 'b'},
            {'r', 'y', '.'},
            {'b', '.', '.'}};
        char[][] ending = {
            {'.', '.', '.'},
            {'.', '.', 'b'},
            {'.', 'r', 'y'},
            {'b', 'y', 'b'}}; 
        PuzzleContest test = new PuzzleContest();
        test.simulate(starting,ending);
    }
    @Test
    public void testShouldWorkFixed(){
        Puzzle p = new Puzzle(5, 5);
        p.addTile("fixed",1,1,"blue");
        p.deleteTile(1,1);
        int[] from = {1,1};
        int[] to = {2,2};
        p.relocateTile(from,to);
        assertEquals(p.actualArrangement()[1][1],'b');
        assertEquals(p.actualArrangement()[2][2],'.');
    }
    
    @Test
    public void testShouldWorkRough(){
        Puzzle p = new Puzzle(5, 5);
        p.addTile("rough",1,1,"blue");
        p.tilt('r');
        assertEquals(p.actualArrangement()[1][1],'b');
        assertEquals(p.actualArrangement()[1][4],'.');
    }
    
    @Test
    public void testShouldWorkFreelance(){
        Puzzle p = new Puzzle(5, 5);
        p.addTile("freelance",1,1,"blue");
        p.addTile(1,2,"red");
        p.addGlue(1,1);
        p.addGlue(1,2);
        assertEquals(p.getBoardGlue()[1][1],0);
    }
    
    @Test
    public void testShouldWorkFlying(){
        Puzzle p = new Puzzle(5, 5);
        p.addTile("flying",1,1,"blue");
        p.makeHole(2,2);
        int[] from = {1,1};
        int[] to = {2,2};
        p.relocateTile(from,to);
        assertEquals(p.actualArrangement()[2][2],'b');
    }
    
    @Test
    public void testShouldWorkpierceable(){
        Puzzle p = new Puzzle(5, 5);
        p.addTile("pierceable",1,1,"blue");
        p.makeHole(1,1);
        assertEquals(p.actualArrangement()[1][1],'#');
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
