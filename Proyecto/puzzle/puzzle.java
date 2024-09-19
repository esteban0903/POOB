import javax.swing.JOptionPane;
import java.util.Arrays;
/**
 * The `puzzle` class represents a board game where colored tiles are arranged in a grid.
 * The goal is to rearrange the tiles to match a final configuration.
 * 
 * This class includes methods to add, move, and remove tiles, as well as check if 
 * the goal state has been reached. It also handles board visibility and displays error messages.
 * 
 * Main attributes:
 * - `height`, `width`: Dimensions of the board.
 * - `arrangement`, `ending`: Matrices representing the initial and final arrangements of the tiles.
 * - `boardRectangles`: Matrix of `Rectangle` objects representing the tiles on the board.
 * - `boardGlue`: Indicates if a tile is glued and cannot be moved.
 * - `isVisible`: Controls the visibility of the board and tiles.
 * 
 * Main methods:
 * - `addTile(int row, int column, String color)`: Adds a tile at a specified position.
 * - `deleteTile(int row, int column)`: Removes a tile from a specified position.
 * - `relocateTile(int[] from, int[] to)`: Moves a tile from one position to another.
 * - `addGlue(int row, int column)`, `deleteGlue(int row, int column)`: Adds or removes glue from a tile.
 * - `isGoal()`: Checks if the current arrangement matches the final arrangement.
 * - `makeVisible()`, `makeInvisible()`: Controls the visibility of the board.
 * - `finish()`: Ends the program by displaying a message.
 * 
 * @author (Esteba Aguilera/Sebastian Beltran )
 * @version (08/09/2024)
 */
public class puzzle
{
    private int  height;
    private int  width;
    private char[][] ending;
    private Rectangle[][] boardRectangles;
    private Rectangle[][] boardRectanglesEdit;
    private Rectangle[][] boardRectanglesEnding;
    private char[][] matrixColorsEdit;
    private char[][] arrangement;
    private int[][] boardGlue;
    private boolean isVisible;
    private int boardWidth;
    private Rectangle backgroundEdit;
    private Rectangle edgesEdit;
    private Rectangle backgroundEnding;
    private Rectangle edgesEnding;
    private boolean showError=true;
    String[] colorNames = {"aqua", "blue", "cyan", "darkGray", "emerald", "fuchsia", 
    "green", "hotPink", "ivory", "jade", "khaki", "lavender", 
    "magenta", "navy", "orange", "purple", "quartz", "red", 
    "silver", "turquoise", "ultramarine", "violet", "white", 
    "xanadu", "yellow", "zucchini" // Código generado por ChatGPT - OpenAI (2024)
    };
    /**
    * Constructor to create an object of the puzzle class with the specified dimensions.
    * If the dimensions are invalid, an error message is displayed.
    * 
    * @param height Height of the board.
    * @param width Width of the board.
    */
    public puzzle(int height, int width){
        if (height<1 || width>500){
            showMessage( "Invalid dimensions: Height = " + height + ", Width = " + width);
        }else{
            this.height=height;
            this.width=width;
            arrangement=new char[width][height];
            ending=new char[width][height];
            
            boardRectanglesEdit = new Rectangle[width][height];
            boardRectanglesEnding = new Rectangle[width][height];
            boardGlue=new int[width][height];
            createBoard();
            createBoardEnding();
        }   
    }
    /**
    * Displays an error message in a dialog box if the board is currently visible.
    * 
    * This method checks if the puzzle board is visible (`isVisible` is true). 
    * If so, it shows an error message in a modal dialog box using the `JOptionPane` class.
    * 
    * @param message The error message to be displayed in the dialog box.
    */
    private void showMessage(String message){
        if (isVisible && showError){
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
    * Constructor to create an object of the puzzle class using an ending matrix.
    * If the dimensions are invalid, an error message is displayed.
    * 
    * @param ending Matrix that defines the final state of the board.
    */
    public puzzle(char ending[][]){
        int rows=ending.length;
        int columns=ending[0].length;
        if ((rows<1 || columns>500)){
            showMessage( "Invalid dimensions: Height = " + rows + ", Width = " + columns);
        }else{
            height=rows;
            width=columns;
            this.ending = ending;
            arrangement=new char[rows][columns];
            boardRectanglesEnding = new Rectangle[rows][columns];
            boardRectanglesEdit = new Rectangle[rows][columns];
            boardGlue=new int[rows][columns];
            createBoard();
            createBoardEnding();
        }
    }
    /**
     * This method checks the dimensions of the input matrices and initializes the
     * `starting` and `ending` matrices. If the dimensions are invalid, it shows an error message.
     * 
     * @param  starting   the initial matrix configuration (2D array of integers)
     * @param  ending     the final matrix configuration to be initialized (2D array of integers)
     * 
     * If the number of rows in `starting` is less than 1 or the number of columns is less than 500,
     * an error dialog is shown. Otherwise, the method initializes both `starting` and `ending` matrices
     * with the given dimensions.
     */
    public puzzle(char starting[][], char ending[][]){
        int rows=starting.length;   
        int columns=starting[0].length;
        
        if (rows<1 || columns>500){
            showMessage( "Invalid dimensions: Height = " + rows + ", Width = " + columns);
        }else{ 
            height=rows;
            width=columns;
            this.ending = ending;
            arrangement=starting;
            boardRectanglesEnding = new Rectangle[rows][columns];
            boardRectanglesEdit = new Rectangle[rows][columns];
            boardGlue=new int[rows][columns];
            createBoardEnding();
            createBoard();
            
        }
    }
    /**
    * Creates the initial board using the defined dimensions and arrays.
    */
    private void createBoard(){
        backgroundEdit= new Rectangle();
        edgesEdit=new Rectangle();
        backgroundEdit.changeSize(height*50,width*50);
        backgroundEdit.changeColor("maroon");
        edgesEdit.changeSize(50*height+10,50*width+10);
        edgesEdit.changeColor("black");
        edgesEdit.moveHorizontal(-5);
        edgesEdit.moveVertical(-5);
        fillBoard(arrangement);
    }
    /**
    * Creates the final board using the defined dimensions and arrays.
    */
    private void createBoardEnding(){
        boardWidth=(50*width)+50 ;
        backgroundEnding= new Rectangle();
        edgesEnding=new Rectangle();
        backgroundEnding.changeSize(height*50,width*50);
        backgroundEnding.changeColor("maroon");
        backgroundEnding.moveHorizontal(boardWidth);
        edgesEnding.changeSize(50*height+10,50*width+10);
        edgesEnding.changeColor("black");
        edgesEnding.moveHorizontal(boardWidth-5);
        edgesEnding.moveVertical(-5);
        boardRectangles=boardRectanglesEnding;
        matrixColorsEdit=ending;
        
        fillBoard(ending);
        boardRectangles=boardRectanglesEdit;
        matrixColorsEdit=arrangement;
        boardWidth=0;
    }
    /**
    * Fills the board with tiles based on a given matrix.
    * 
    * @param matrixFill Matrix containing the tile configuration to fill the board.
    */ 
    private void fillBoard(char [][]matrixFill){
        for (int i=0; i<matrixFill.length; i++){
            for(int j=0; j<matrixFill[0].length; j++){
                if ( matrixFill[i][j]!='.' && matrixFill[i][j]!='\u0000'){
                    addTile(i,j,getLetterColor(matrixFill[i][j]));
                }else{
                   matrixFill[i][j]='.'; 
                }
            }
        }
    }
    /**
    * Gets the color associated with a specific letter.
    * 
    * @param letter Letter for which the color is retrieved.
    * @return Color corresponding to the letter.
    */
    private String getLetterColor(char letter){
        return colorNames[getLetterIndex(letter)];
    }
    /**
    * Gets the index in the color array based on the given letter.
    * 
    * @param letter Letter for which the index is retrieved.
    * @return Index in the color array.
    */
    private int getLetterIndex(char letter) {
        letter = Character.toUpperCase(letter);
        return letter - 'A';
    }
    /**
    * Adds a tile to the board at the specified position with the given color.
    * Displays an error message if the position is already occupied.
    * 
    * @param row Row where the tile will be added.
    * @param column Column where the tile will be added.
    * @param color Color of the tile.
    */
    public void addTile( int row, int column, String color){
        if (!verifyRanges(row,column)){
            return;
        }if (boardRectangles[row][column] == null){
            matrixColorsEdit[row][column]=color.charAt(0);
            Rectangle rectangle = new Rectangle();
            rectangle.changeSize(50,50);
            rectangle.changeColor(color);
            boardRectangles[row][column]=rectangle;
            positionTile(row,column,rectangle) ;
            
        
        }else{ 
            showMessage( "Error: There is already a tile at position [" + row + "][" + column + "].");
        }
    }
    /**
    * Deletes a tile at the specified position if it is not glued and is valid.
    * Displays an error message if the tile cannot be deleted.
    * 
    * @param row Row of the tile to be deleted.
    * @param column Column of the tile to be deleted.
    */
    public void deleteTile(int row, int column){
        if (!verifyRanges(row,column)){
            return;
        }if (boardGlue[row][column]!=0){
            showMessage("Error: The tile that you are trying to delete is glued");
        }if (arrangement[row][column] != '.'){
            boardRectanglesEdit[row][column].makeInvisible();
            arrangement[row][column]='.';
            boardRectanglesEdit[row][column]=null;
        }else{ 
            showMessage("Error: There is a null tile at position [" + row + "][" + column + "].");
        }
    }
    /**
    * Verifies if a row and column are within the board limits.
    * 
    * @param row Row to be verified.
    * @param column Column to be verified.
    * @return true if the row and column are within the limits, false otherwise.
    */
    private boolean verifyRanges(int row, int column){
        if (row>=height || row<0  || column>=width || column<0){
            showMessage("Error: The specified row or column is out of range.");
            return false;
        }else{
            return true;
        }
    }
    /**
    * Positions a tile on the board at the specified row and column.
    * 
    * @param row Row where the tile will be positioned.
    * @param column Column where the tile will be positioned.
    * @param rectangle Rectangle object representing the tile.
    */
    private void positionTile(int row, int column, Rectangle rectangle){
        rectangle.moveHorizontal(column*50+boardWidth);
        rectangle.moveVertical(row*50);
        if (isVisible){

            rectangle.makeVisible();
        }
    }
    /**
    * Relocates a tile from one position to another on the board.
    * Displays an error message if the move is not valid.
    * 
    * @param from Array containing the row and column of the starting position.
    * @param to Array containing the row and column of the new position.
    */
    public void relocateTile(int[] from, int[] to){
        int rowFrom=from[0], columnFrom=from[1], rowTo=to[0], columnTo=to[1];
        if (!verificationRelocate(rowFrom,columnFrom,rowTo,columnTo)){
            return;
        }
        if(boardGlue[rowFrom][columnFrom]==0){
            String color= boardRectanglesEdit[rowFrom][columnFrom].getColor();
            deleteTile(rowFrom,columnFrom);
            addTile(rowTo,columnTo,color);
        }else{
            showMessage("Error: The tile that you are trying to move is glued");
        }
    
    }
    /**
    * Verifies if a relocation move is valid.
    * 
    * @param rowOne Row of the starting position.
    * @param columnOne Column of the starting position.
    * @param rowTwo Row of the new position.
    * @param columnTwo Column of the new position.
    * @return true if the move is valid, false otherwise.
    */
    private boolean verificationRelocate(int rowOne, int columnOne, int rowTwo, int columnTwo){
        if (!verifyRanges(rowOne,columnOne ) || !verifyRanges(rowTwo,columnTwo)){
            return false;
        }
        if (boardRectangles[rowOne][columnOne]==null){
            showMessage("Error: There is a null tile at position [" + rowOne + "][" + columnOne + "].");
            return false;
        }if (boardRectangles[rowTwo][columnTwo]!=null){
            showMessage("Error: There is already a Rectangle at position [" + rowTwo + "][" + columnTwo + "].");
            return false;
        }
        return true;   
        
        
    }
    /**
    * Adds glue to a tile at the specified position.
    * Displays an error message if the tile is already glued.
    * 
    * @param row Row of the tile to be glued.
    * @param column Column of the tile to be glued.
    */
    public void addGlue(int row, int column){
        if (!verifyRanges(row,column)){
            return;
        }
        if ((boardGlue[row][column])!=0){
            showMessage("Error: The tile that you are to put glue is glued");
            return;
        }
        if (boardRectanglesEdit[row][column]!=null ){
            boardGlue[row][column]=2;
            stickAroundTiles(row,column,1);
            
        }else{
            showMessage("Error: There is a null tile at position [" + row + "][" + column + "].");
        }
    }
    /**
    * Removes glue from a tile at the specified position.
    * Displays an error message if the tile is not glued.
    * 
    * @param row Row of the tile from which glue will be removed.
    * @param column Column of the tile from which glue will be removed.
    */
    public void deleteGlue(int row, int column){
        if (!verifyRanges(row,column)){
            return;
        }
        if ((boardGlue[row][column])!=2){
            showMessage("Error: The tile that you are to quit glue is not glued");
            return;
        }
        if (boardRectanglesEdit[row][column]!=null ){
            boardGlue[row][column]=0;
            stickAroundTiles(row,column,0);
            
        }else{
            showMessage("Error: There is a null tile at position [" + row + "][" + column + "].");
        }
    }
    /**
    * Glues or unglues the tiles around a specific position on the board.
    * 
    * @param row Row of the central tile.
    * @param column Column of the central tile.
    * @param glued Value indicating whether the surrounding tiles should be glued or unglued (1 for glued, 0 for unglued).
    */
    private void stickAroundTiles(int row, int column,int glued){
        int[][] positions={{0,-1},{0,1},{1,0},{-1,0}};
        for (int[]directions: positions){
            int y=row+directions[0];
            int x=column+directions[1];
            if (verifyRanges(y,x)){
                boardGlue[y][x]=glued;
            }
        }
    }
    
    public void tilt(char direction){
        showError=false;
        if (direction=='l'){
            tiltDirectionPositive(0,-1,boardRectanglesEdit);
        }else if (direction=='r'){
            tiltDirectionNegative(0,1,boardRectanglesEdit);
        }else if(direction=='d'){
            tiltDirectionNegative(1,0,boardRectanglesEdit);
        }else if (direction=='u'){
            tiltDirectionPositive(-1,0,boardRectanglesEdit);
        }else{
            showMessage("Error: You put a invalid direction");
        }
        showError=true;
    }
    private void tiltDirectionPositive(int row, int column, Rectangle[][] boardRectanglesEdit){
        int rowLength = boardRectanglesEdit.length;
        int columnLength= boardRectanglesEdit[0].length;
        for (int i=0; i<rowLength;i++){
            for (int j=0; j< columnLength;j++){
                if (verifyRanges(i+row,j+column)){
                    if (boardRectanglesEdit[i+row][j+column]==null && boardRectanglesEdit[i][j]!=null){
                        moveTileContinuously(i,j,row,column);
                    }
                }
            }
        }
    }
    private void tiltDirectionNegative(int row, int column, Rectangle[][] boardRectanglesEdit){
        int rowLength = boardRectanglesEdit.length;
        int columnLength= boardRectanglesEdit[0].length;
        for (int i=rowLength; i>-1;i--){
            for (int j=columnLength; j>-1;j--){
                if (verifyRanges(i+row,j+column)){
                    if (boardRectanglesEdit[i+row][j+column]==null && boardRectanglesEdit[i][j]!=null){
                        moveTileContinuously(i,j,row,column);
                    }
                }
            }
        }
    }
    private void moveTileContinuously(int i, int j , int row, int column){
        int nextRow = i;
        int nextCol = j;
    
        while (verifyRanges(nextRow + row, nextCol + column)
               && boardRectanglesEdit[nextRow + row][nextCol + column] == null) {
            nextRow += row;
            nextCol += column;
            relocateTile(new int[]{i, j}, new int[]{nextRow, nextCol});
            i=nextRow;
            j=nextCol;
        }
    }
    
    public void exChange(){
        moveBoards();
        moveRectangles(boardRectanglesEdit,(50*width)+50);
        moveRectangles(boardRectanglesEnding,-(50*width)-50);
        changeMatrices();
        changeMatrixColors();
        
    }
    private void moveBoards(){
        edgesEnding.moveHorizontal(boardWidth);
        edgesEdit.moveHorizontal(boardWidth);
        backgroundEnding.moveHorizontal(-boardWidth);
        backgroundEdit.moveHorizontal(-boardWidth);
    }
    private void moveRectangles(Rectangle[][] matrix, int xMoving) {
    for (int i = 0; i < matrix.length; i++) {              
        for (int j = 0; j < matrix[i].length; j++) {      
            Rectangle rectangle = matrix[i][j];
            if (rectangle != null) {
                rectangle.moveHorizontal(xMoving);
            }
        }
    }
    }
    private void changeMatrices(){
        for (int i = 0; i < boardRectanglesEdit.length; i++) {
            for (int j = 0; j < boardRectanglesEdit[0].length; j++) {
                Rectangle temp = boardRectanglesEdit[i][j];
                boardRectanglesEdit[i][j] = boardRectanglesEnding[i][j];
                boardRectanglesEnding[i][j] = temp;
        }
    }
    }
    private void changeMatrixColors(){
        for (int i = 0; i < matrixColorsEdit.length; i++) {
            for (int j = 0; j < matrixColorsEdit[0].length; j++) {
                matrixColorsEdit[i][j] = ending[i][j];
            }
        }
    }
    /**
    * Checks if the current board state matches the final state.
    * 
    * @return true if the current state matches the final state, false otherwise.
    */
    public boolean isGoal(){
        for (int i=0; i<arrangement.length; i++){
            for (int j=0; j<arrangement[0].length; j++){
                if (arrangement[i][j]!=ending[i][j]){
                    return false;
                }
            }
            
        }
        return true;
    }
    /**
    * Returns the current arrangement of tiles on the board.
    * 
    * @return 2D char array representing the current arrangement of the board.
    */
    public char[][] actualArrangement(){
        return arrangement;
    }
    /**
    * Ends the program and displays a termination message.
    */
    public void finish(){
        JOptionPane.showMessageDialog(null, "Programa Terminado", "Finish", JOptionPane.ERROR_MESSAGE);
    }
    /**
    * Makes the board and all tiles visible.
    */
    public void makeVisible(){
        isVisible=true;
        edgesEdit.makeVisible();
        backgroundEdit.makeVisible();
        edgesEnding.makeVisible();
        backgroundEnding.makeVisible();
        for (int i=0; i<boardRectangles.length; i++){
            for(int j=0; j<boardRectangles[0].length; j++){
                if (boardRectanglesEdit[i][j]!=null){
                    boardRectanglesEdit[i][j].makeVisible();
                }if (boardRectanglesEnding[i][j]!=null){
                    boardRectanglesEnding[i][j].makeVisible();
                }
            }
        }
        
    }
    /**
    * Makes the board and all tiles invisible.
    */
    public void makeInvisible(){
        isVisible=false;
        edgesEdit.makeInvisible();
        backgroundEdit.makeInvisible();
        edgesEnding.makeInvisible();
        backgroundEnding.makeInvisible();
        for (int i=0; i<boardRectangles.length; i++){
            for(int j=0; j<boardRectangles[0].length; j++){
                if (boardRectanglesEdit[i][j]!=null){
                    boardRectangles[i][j].makeInvisible();
                }if (boardRectanglesEnding[i][j]!=null){
                    boardRectanglesEnding[i][j].makeInvisible();
                }
            }
        }
        
    }
    


    
    


    
}
