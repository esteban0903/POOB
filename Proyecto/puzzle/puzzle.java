import javax.swing.JOptionPane;
import java.util.Arrays;
import java.util.ArrayList;

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
    private int  height;//
    private int  width;//
    private char[][] ending;//
    private Rectangle[][] boardRectangles;
    private Rectangle[][] boardRectanglesEdit;//
    private Rectangle[][] boardRectanglesEnding;//
    private char[][] matrixColorsEdit;
    private char[][] arrangement;//
    private int[][] boardGlue;//
    private String[][] types;
    private boolean isVisible;//
    private int boardWidth;//
    private Rectangle backgroundEdit;//
    private Rectangle edgesEdit;//
    private Rectangle backgroundEnding;//
    private Rectangle edgesEnding;//
    private boolean showError=true;
    String[] colorNames = {"aqua", "blue", "cyan", "darkGray", "emerald", "fuchsia", 
    "green", "hotPink", "ivory", "jade", "khaki", "lavender", 
    "magenta", "navy", "orange", "purple", "quartz", "red", 
    "silver", "turquoise", "ultramarine","wheat", "violet", 
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
            JOptionPane.showMessageDialog(null,"Invalid dimensions: Height = " + height + ", Width = " + width,"Error", JOptionPane.ERROR_MESSAGE);
            return;
        }else{
            this.height=height;
            this.width=width;
            arrangement=new char[width][height];
            ending=new char[width][height];
            
            boardRectanglesEdit = new Rectangle[width][height];
            boardRectanglesEnding = new Rectangle[width][height];
            boardGlue=new int[width][height];
            types=new String[width][height];
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
            JOptionPane.showMessageDialog(null, "Invalid dimensions: Height = " + rows + ", Width = " + columns, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }else{
            height=rows;
            width=columns;
            this.ending = ending;
            arrangement=new char[rows][columns];
            boardRectanglesEnding = new Rectangle[rows][columns];
            boardRectanglesEdit = new Rectangle[rows][columns];
            boardGlue=new int[rows][columns];
            types=new String[rows][columns];
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
        int rowsEnding=ending.length;   
        int columnsEnding=ending[0].length;
        if (rows<1 || columns>500){
            JOptionPane.showMessageDialog(null, "Invalid dimensions: Height = " + rows + ", Width = " + columns, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }if (rows!= rowsEnding || columns !=columnsEnding){
            JOptionPane.showMessageDialog(null,"The matrices have different dimensions", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }else{ 
            height=rows;
            width=columns;
            this.ending = ending;
            arrangement=starting;
            boardRectanglesEnding = new Rectangle[rows][columns];
            boardRectanglesEdit = new Rectangle[rows][columns];
            boardGlue=new int[rows][columns];
            types=new String[rows][columns];
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
            createRectangle(row, column, color);      
        }else{ 
            showMessage( "Error: There is already a tile at position [" + row + "][" + column + "].");
        }
    }
    public void addTile(String type , int row, int column, String color){
        if (!verifyRanges(row,column)){
            return;
        }if (!verificationType(type)){
            showMessage( "Error: That not is a type of tile");
        }if (boardRectangles[row][column] == null){
            createRectangle(row, column, color);     
            types[row][column]=type;
        }else{ 
            showMessage( "Error: There is already a tile at position [" + row + "][" + column + "].");
        }
    }
    private boolean verificationType(String type){
        type= type.toLowerCase();
        return type.equals("normal")|| type.equals("fixed") || 
               type.equals("rough") || type.equals("flying")
               || type.equals("freelance");
    }
    private void createRectangle(int row, int column, String color){
        matrixColorsEdit[row][column]=color.charAt(0);
        if (color=="white"){
            matrixColorsEdit[row][column]='#';
        }
        Rectangle rectangle = new Rectangle();
        rectangle.changeSize(50,50);
        rectangle.changeColor(color);
        boardRectangles[row][column]=rectangle;
        positionTile(row,column,rectangle) ;
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
        }if (arrangement[row][column]=='#'){
            showMessage("Error: There is a hole in that position");
            return;
        }if (types[row][column]=="fixed"){
            showMessage("Error: The tile is fixed so you cant delete it");
            return;
        }if (arrangement[row][column] != '.'){
            boardRectangles[row][column].makeInvisible();
            arrangement[row][column]='.';
            boardRectangles[row][column]=null;
            types[row][column]=null;
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
        if(types[rowFrom][rowFrom]=="fixed"){
            showMessage("Error: The tile is fixed so you cant relocate it");
            return;
        }
        if(boardGlue[rowFrom][columnFrom]==0){
            String color= boardRectangles[rowFrom][columnFrom].getColor();
            deleteTile(rowFrom,columnFrom);
            if(arrangement[rowTo][columnTo]!='#'&& arrangement[rowFrom][columnFrom]!='#'){
                addTile(rowTo,columnTo,color);
            }
        }else if(boardGlue[rowFrom][columnFrom]==2 ){
            relocateGlue(rowFrom,columnFrom,rowTo,columnTo);
        }else{
            showMessage("Error: The tile that you are trying to move is glued but is not the support");
        }
    
    }
    private void relocateGlue(int rowFrom,int columnFrom,int rowTo,int columnTo){
        int[][] positions={{0,-1},{0,1},{1,0},{-1,0},{0,0}};
        if (!verificationRelocateGlue(rowTo,columnTo)){
            showMessage("The tiles that you are trying to move don't have space in the new position");
            return;
        }
        for (int[]directions: positions){
            int yOld=rowFrom+directions[0];
            int xOld=columnFrom+directions[1];
            int yNew=rowTo+directions[0];
            int xNew=columnTo+directions[1];
            if (verifyRanges(yOld,xOld) && verifyRanges(yNew,xNew)){
                if(arrangement[yOld][xOld] !='#' && arrangement[yOld][xOld]!='.' && types[yOld][xOld]!="freelance"){
                    addTile(yNew,xNew,boardRectangles[yOld][xOld].getColor());
                    deleteTile(yOld,xOld);
                }
            }
        }
        addGlue(rowTo,columnTo);
        
    }
    
    private boolean verificationRelocateGlue(int row, int column){
        showError=false;
        int[][] positions={{0,-1},{0,1},{1,0},{-1,0},{0,0}};
        for (int[]directions: positions){
            int y=row+directions[0];
            int x=column+directions[1];
            if (verifyRanges(y,x)){
                if(arrangement[y][x] !='.'&& arrangement[y][x]!= '#'){
                    showError=true;
                    return false;
                }
            }else{
                showError=true;
                return false;
            }
        }
        showError=true;
        return true;
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
        }if (arrangement[rowTwo][columnTwo]=='#'){
            return true;
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
        if(types[row][column]=="freelance"){
            showMessage("Error: The tile that you are to put glue is freelance");
            return;
        }
        if (boardRectangles[row][column]!=null ){
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
        if (boardRectangles[row][column]!=null ){
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
        showError=false;
        int[][] positions={{0,-1},{0,1},{1,0},{-1,0}};
        for (int[]directions: positions){
            int y=row+directions[0];
            int x=column+directions[1];
            if (verifyRanges(y,x)){
                if(arrangement[y][x] !='#' && arrangement[y][x]!='.' && types[y][x]!="freelance"){
                    boardGlue[y][x]=glued;
                }
            }
        }
        showError=true;
    }
    public int [][] actualGlue(){
        return boardGlue;
    }
    /**
    * Creates a hole at the specified position in the grid.
    * If the tile at the given row and column is null, it marks the position as a hole
    * in the matrix and adds a white tile to represent the hole.
    * 
    * @param row the row index where the hole is to be created
    * @param column the column index where the hole is to be created
    */
    public void makeHole(int row, int column){
        if(!verifyRanges(row,column)){
            return;
        }
        if(boardRectangles[row][column]==null){
            matrixColorsEdit[row][column]='#';
            addTile(row,column,"white");
            
            
        }
    }
    public void tilt(){
        makeInvisible();
        char[][] arrangementTest = createMatrixCopy(arrangement);
        Rectangle[][] boardRectanglesTest = createRectangleCopy(boardRectangles);
        int bestScore=height*width;
        char bestDirection='l';
        char [] directions= {'l','r','u','d'};
        for (char direction : directions) {
            tilt(direction);
            int scoreDirection=missplacedTiles();
            if (scoreDirection<bestScore){
                bestScore=scoreDirection;
                bestDirection=direction;
            }
            restoreOriginalState(arrangementTest, boardRectanglesTest);
        }
        tilt(bestDirection);
        makeVisible();
    }
    private char[][] createMatrixCopy(char[][] originalMatrix) {
        char[][] copy = new char[originalMatrix.length][originalMatrix[0].length];
        for (int i = 0; i < originalMatrix.length; i++) {
            for (int j = 0; j < originalMatrix[i].length; j++) {
                copy[i][j] = originalMatrix[i][j];
            }
        }
        return copy;
    }
    
    private Rectangle[][] createRectangleCopy(Rectangle[][] originalRectangles) {
        Rectangle[][] copy = new Rectangle[originalRectangles.length][originalRectangles[0].length];
        for (int i = 0; i < originalRectangles.length; i++) {
            for (int j = 0; j < originalRectangles[i].length; j++) {
                if (originalRectangles[i][j] != null) {
                    copy[i][j] = new Rectangle(originalRectangles[i][j]);
                } else {
                    copy[i][j] = null; 
                }
            }
        }
        return copy;
    }
    private void restoreOriginalState(char[][] arrangementTest, Rectangle[][] boardRectanglesTest){
        for (int i = 0; i < arrangement.length; i++) {
            for (int j = 0; j < arrangement[i].length; j++) {
                arrangement[i][j] = arrangementTest[i][j];
            }
        }

        for (int i = 0; i < boardRectangles.length; i++) {
            for (int j = 0; j < boardRectangles[i].length; j++) {
                if (boardRectanglesTest[i][j] != null) {
                    boardRectangles[i][j] = new Rectangle(boardRectanglesTest[i][j]);  
                } else {
                    boardRectangles[i][j] = null;  
                }
            }
        }
    }
    /**
    * Tilts the grid in the specified direction by moving the tiles.
    * 
    * @param direction the direction to tilt the grid. Valid values are:
    * 'l' for left,
    * 'r' for right,
    * 'd' for down,
    * 'u' for up.
    * If an invalid direction is provided, an error message is shown.
    */
    public void tilt(char direction){
        showError=false;
        if (direction=='l'){
            tiltDirectionPositive(0,-1,boardRectangles);
        }else if (direction=='r'){
            tiltDirectionNegative(0,1,boardRectangles);
        }else if(direction=='d'){
            tiltDirectionNegative(1,0,boardRectangles);
        }else if (direction=='u'){
            tiltDirectionPositive(-1,0,boardRectangles);
        }else{
            showMessage("Error: You put a invalid direction");
        }
        showError=true;
    }
    /**
    * Tilts the grid in the positive direction (downwards or rightwards) based on the specified row and column shifts.
    * Moves tiles continuously if they meet the criteria.
    *
    * @param row the number of rows to shift (positive for downwards, negative for upwards).
    * @param column the number of columns to shift (positive for rightwards, negative for leftwards).
    * @param boardRectangles the 2D array of Rectangle objects representing the grid.
    */
    private void tiltDirectionPositive(int row, int column, Rectangle[][] boardRectangles){
        int rowLength = boardRectangles.length;
        int columnLength= boardRectangles[0].length;
        for (int i=0; i<rowLength;i++){
            for (int j=0; j< columnLength;j++){
                if (verifyRanges(i+row,j+column)){
                    if ((arrangement[i+row][j+column]=='#'|| boardRectangles[i+row][j+column]==null) 
                         && boardRectangles[i][j]!=null){
                        moveTileContinuously(i,j,row,column);
                    }
                }
            }
        }
    }
    /**
    * Tilts the grid in the negative direction (upwards or leftwards) based on the specified row and column shifts.
    * Moves tiles continuously if they meet the criteria.
    *
    * @param row the number of rows to shift (positive for upwards, negative for downwards).
    * @param column the number of columns to shift (positive for leftwards, negative for rightwards).
    * @param boardRectangles the 2D array of Rectangle objects representing the grid.
    */
    private void tiltDirectionNegative(int row, int column, Rectangle[][] boardRectangles){
        int rowLength = boardRectangles.length;
        int columnLength= boardRectangles[0].length;
        for (int i=rowLength; i>-1;i--){
            for (int j=columnLength; j>-1;j--){
                if (verifyRanges(i+row,j+column)){
                    if ((arrangement[i+row][j+column]=='#'|| boardRectangles[i+row][j+column]==null) 
                         && boardRectangles[i][j]!=null){
                        moveTileContinuously(i,j,row,column);
                    }
                }
            }
        }
    }
    /**
    * Moves a tile from its current position to a new position continuously in the specified direction
    * until it encounters an obstacle or moves out of bounds.
    *
    * @param i the current row index of the tile.
    * @param j the current column index of the tile.
    * @param row the row increment for the movement (positive for downward, negative for upward).
    * @param column the column increment for the movement (positive for rightward, negative for leftward).
    */   
    private void moveTileContinuously(int i, int j, int row, int column) {
    int nextRow = i;
    int nextCol = j;

    while (verifyRanges(nextRow + row, nextCol + column) 
           && (boardRectangles[nextRow + row][nextCol + column] == null
           || arrangement[nextRow + row][nextCol + column]=='#')) {
        nextRow += row;
        nextCol += column;
        if(types[i][j]!="rough"){
            relocateTile(new int[]{i, j}, new int[]{nextRow, nextCol});
        }
        i=nextRow;
        j=nextCol;
    }
    }
    /**
    * Exchanges the state of the boards and updates matrices and colors.
    * <p>
    * Moves the edit and ending board rectangles, swaps the matrices, and updates the colors.
    */
    public void exChange(){
        moveBoards();
        moveRectangles(boardRectanglesEdit,(50*width)+50);
        moveRectangles(boardRectanglesEnding,-(50*width)-50);
        changeMatrices(boardRectanglesEdit,boardRectanglesEnding);
        changeMatrixColors();
        
    }
    /**
    * Moves the edge and background rectangles of both boards horizontally.
    */
    private void moveBoards(){
        edgesEnding.moveHorizontal(boardWidth);
        edgesEdit.moveHorizontal(boardWidth);
        backgroundEnding.moveHorizontal(-boardWidth);
        backgroundEdit.moveHorizontal(-boardWidth);
    }
    /**
    * Moves rectangles in the given matrix horizontally by a specified distance.
    * 
    * @param matrix The matrix of rectangles to be moved.
    * @param xMoving The distance to move the rectangles horizontally.
    */
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
    /**
    * Swaps the contents of the boardRectanglesEdit and boardRectanglesEnding matrices.
    */
    private void changeMatrices(Rectangle[][] boardRectanglesEdit, Rectangle[][] boardRectanglesEnding){
        for (int i = 0; i < boardRectanglesEdit.length; i++) {
            for (int j = 0; j < boardRectanglesEdit[0].length; j++) {
                Rectangle temp = boardRectanglesEdit[i][j];
                boardRectanglesEdit[i][j] = boardRectanglesEnding[i][j];
                boardRectanglesEnding[i][j] = temp;
        }
    }
    }
    /**
    * Updates matrixColorsEdit with the colors from the ending matrix.
    */
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
    * Returns the coordinates of tiles that are fixed or glued.
    * Tiles are considered fixed if they have glue or are marked as '#'.
    */
    public int[][] fixedTiles(){
        ArrayList<int[]> coordinates = new ArrayList<>();
        for (int i = 0; i < arrangement.length; i++) { 
            for (int j = 0; j < arrangement[i].length; j++) {
                if (boardGlue[i][j]==2 || boardGlue[i][j]==1 || arrangement[i][j]=='#'){
                     coordinates.add(new int[]{i, j});
                     if (isVisible){
                         boardRectangles[i][j].blink();
                     }
                }
            }
        }
        return transformInt(coordinates);
    }
    /**
    * Converts an ArrayList of int arrays to a 2D int array.
    * 
    * @param array The ArrayList to be converted.
    * @return A 2D int array representation of the input ArrayList.
    */
    private int[][] transformInt(ArrayList<int[]> array){
        int[][] arrayInt = new int[array.size()][2];
        for (int i = 0; i < array.size(); i++) {
            arrayInt[i] = array.get(i);
        }
        return arrayInt;
    }
    /**
    * Counts the number of tiles that are misplaced between the arrangement and the ending states.
    */
    public int missplacedTiles(){
    int count = 0; 

    for (int i = 0; i < boardRectangles.length; i++) {
        for (int j = 0; j < boardRectangles[i].length; j++) {
            if (arrangement[i][j] != ending[i][j]) {
                count++; 
            }
        }
    }
    
    return count; 
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
    /**
    * Returns the ending state of the arrangement.
    */
    public char[][] getEnding() {
        return ending;
    }
    /**
    * Returns the glue matrix for the board.
    */
    public int[][] getBoardGlue() {
        return boardGlue;
    }
    /**
    * Returns the matrix of colors for the edit state.
    */
    public char[][] getMatrixColorsEdit() {
        return matrixColorsEdit;
    }
}
    


    
    


    

