

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class pruebaCC4test.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class puzzleAtest
{
    /**
     * Default constructor for test class pruebaCC4test
     */
    public puzzleAtest()
    {
    }

    @Test
    public void testShouldSolveComplexPuzzle() {
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
    
        assertTrue(test.canSolve(starting, ending));
    }
    @Test
    public void testMultiplePuzzleOperations() {
        Puzzle p = new Puzzle(4, 4);
        
        // Agregar fichas
        p.addTile(1, 1, "red");
        p.addTile(2, 2, "blue");
        p.addTile(3, 3, "green");
        
        assertEquals('r', p.actualArrangement()[1][1]);
        assertEquals('b', p.actualArrangement()[2][2]);
        assertEquals('g', p.actualArrangement()[3][3]);
        
        // Crear agujero y eliminar una ficha
        p.makeHole(0, 0);
        p.deleteTile(1, 1);
        
        assertEquals('#', p.actualArrangement()[0][0]);
        assertEquals('.', p.actualArrangement()[1][1]);
        
        // Mover ficha a una posición vacía
        p.relocateTile(new int[]{2, 2}, new int[]{1, 1});
        assertEquals('b', p.actualArrangement()[1][1]);
        assertEquals('.', p.actualArrangement()[2][2]);
        
        // Crear un nuevo puzzle con un estado final esperado
        char[][] starting = {
            {'r', 'b', '.', '.'},
            {'.', 'g', '.', '.'},
            {'.', '.', '.', '.'},
            {'.', '.', '.', '.'}};
        
        char[][] ending = {
            {'r', 'b', '.', '.'},
            {'.', '.', '.', '.'},
            {'.', 'g', '.', '.'},
            {'.', '.', '.', '.'}};
        
        Puzzle newPuzzle = new Puzzle(starting, ending);
        
        // Verificar fichas mal ubicadas
        assertEquals(2, newPuzzle.missplacedTiles());
        
        // Intercambiar el arreglo actual con el objetivo
        newPuzzle.exChange();
        assertArrayEquals(ending, newPuzzle.actualArrangement());
        
        // Comprobar si se alcanzó el objetivo
        assertTrue(newPuzzle.isGoal());
    }
}
