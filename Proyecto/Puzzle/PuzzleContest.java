import java.util.*;
import javax.swing.JOptionPane;
/**
 * Write a description of class PuzzleContest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PuzzleContest
{   
    private int height, width; 
    private ArrayList<Character> tiltSequence;

    /**
     * Constructor for objects of class PuzzleContest
     */
    public PuzzleContest(){
    }
    public void simulate(char[][] starting, char[][] ending){
        puzzle board = new puzzle(starting, ending);
        canSolve(starting,ending);
        board.makeVisible();
        System.out.println(tiltSequence);
        for (char direction : tiltSequence){
            board.tilt(direction);
        }
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public boolean canSolve(char[][] starting, char[][] ending) {
        this.height = starting.length;
        this.width = starting[0].length;
        this.tiltSequence = new ArrayList<>();
        ArrayList<char[][]> posiblyBoards = new ArrayList<>();
        ArrayList<char[][]> processedBoards = new ArrayList<>();
        ArrayList<ArrayList<Character>> sequences = new ArrayList<>();
        posiblyBoards.add(copyBoard(starting));
        sequences.add(new ArrayList<>());
        char[] directions = {'l', 'r', 'u', 'd'};

        while (!posiblyBoards.isEmpty()) {
            char[][] testingBoard = posiblyBoards.remove(0);
            ArrayList<Character> currentSequence = sequences.remove(0);
            if (Arrays.deepEquals(testingBoard, ending)) {
                this.tiltSequence = currentSequence;
                return true;
            }
            processedBoards.add(testingBoard);
            processTestingBoard(directions,testingBoard,posiblyBoards,processedBoards,currentSequence,sequences);
        }

        return false; 
    }
    private void processTestingBoard(char[] directions, char[][] testingBoard, ArrayList<char[][]> posiblyBoards, ArrayList<char[][]> processedBoards, ArrayList<Character> currentSequence,ArrayList<ArrayList<Character>> sequences){
        for (char direction : directions) {
            char[][] newTestingBoard = tilt(testingBoard, direction);  
            if (isNewTestingBoard(newTestingBoard, posiblyBoards,processedBoards)){
                posiblyBoards.add(copyBoard(newTestingBoard));
                ArrayList<Character> newSequence = new ArrayList<>(currentSequence);  
                newSequence.add(direction);  
                sequences.add(newSequence);  
            }  
        }
    }
    private boolean isNewTestingBoard(char[][] newTestingBoard, ArrayList<char[][]> posiblyBoards,ArrayList<char[][]> processedBoards){
        for (char[][] processedBoard : processedBoards) {
            if (Arrays.deepEquals(processedBoard, newTestingBoard)) {
                return false ;
            }
        }
        for (char[][] posiblyBoard : posiblyBoards) {
            if (Arrays.deepEquals(posiblyBoard, newTestingBoard)) {
                return false;  
            }
        }
        return true;
    }
    private char[][] tilt(char[][] testingBoard, char direction){
        char[][] newTestingBoard = copyBoard(testingBoard);
        if (direction == 'l') {
            tiltDirectionPositive(0, -1, newTestingBoard);
        }else if (direction == 'r') {
            tiltDirectionNegative(0, 1, newTestingBoard);  
        }else if (direction == 'd') {
            tiltDirectionNegative(1, 0, newTestingBoard); 
        }else if (direction == 'u') {
            tiltDirectionPositive(-1, 0, newTestingBoard); 
        }return newTestingBoard;
    }

    private void tiltDirectionPositive(int row, int col, char[][] board){
        for (int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {
                if (verifyRanges(i+row, j+col)) {
                    if (board[i+row][j+col] == '.') {
                        moveTileContinuously(i, j, row, col, board);
                    }
                }
            }
        }
    }

    private void tiltDirectionNegative(int row, int col, char[][] board){
        for (int i= height - 1; i>=0; i--) {
            for (int j= width - 1; j>=0; j--) {
                if (verifyRanges(i+row, j+col)) {
                    if (board[i+row][j+col] == '.') {
                        moveTileContinuously(i, j, row, col, board);
                    }
                }
            }
        }
    }

    private void moveTileContinuously(int i, int j, int row, int col, char[][] board){
        int nextRow = i;
        int nextCol = j;

        while (verifyRanges(nextRow + row, nextCol + col) && board[nextRow + row][nextCol + col] == '.'){
            board[nextRow + row][nextCol + col] = board[nextRow][nextCol];
            board[nextRow][nextCol] = '.'; 
            nextRow += row;
            nextCol += col;
        }
    }
    private boolean verifyRanges(int row, int col){
        if(row >= 0 && row < height && col >= 0 && col < width){
            return true;
        }
        return false;
    }

    private char[][] copyBoard(char[][] board){
        char[][] copy = new char[board.length][];
        for (int i=0; i<board.length; i++) {
            copy[i] = board[i].clone();
        }
        return copy;
    }
}


