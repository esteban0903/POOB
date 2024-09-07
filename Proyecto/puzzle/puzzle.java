import javax.swing.JOptionPane;
import java.util.Arrays;
/**
 * Write a description of class puzzle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
    
    String[] colorNames = {"aqua", "blue", "cyan", "darkGray", "emerald", "fuchsia", 
    "green", "hotPink", "ivory", "jade", "khaki", "lavender", 
    "magenta", "navy", "orange", "purple", "quartz", "red", 
    "silver", "turquoise", "ultramarine", "violet", "white", 
    "xanadu", "yellow", "zucchini" // Código generado por ChatGPT - OpenAI (2024)
    };
    /**
    * Constructor for objects of class puzzle
    */
    public puzzle(int height, int width){
        if (height<1 || width>500){
            showMessage( "Invalid dimensions: Height = " + height + ", Width = " + width);
        }else{
            this.height=height;
            this.width=width;
            arrangement=new char[width][height];
            boardRectanglesEdit = new Rectangle[width][height];
            createBoard();
        }   
    }
    private void showMessage(String message){
        if (isVisible){
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
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
            createBoardEnding();
            createBoard();
            
        }
    }
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
    private String getLetterColor(char letter){
        return colorNames[getLetterIndex(letter)];
    }
    
    private int getLetterIndex(char letter) {
        letter = Character.toUpperCase(letter);
        return letter - 'A';
    }
    
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
    
    public void deleteTile(int row, int column){
        if (!verifyRanges(row,column)){
            return;
        }if (arrangement[row][column] != '.'){
            boardRectanglesEdit[row][column].makeInvisible();
            arrangement[row][column]='.';
            boardRectanglesEdit[row][column]=null;
        }else{ 
            showMessage("Error: There is a null tile at position [" + row + "][" + column + "].");
        }
    }
    private boolean verifyRanges(int row, int column){
        if (row>=height || row<0  || column>=width || column<0){
            showMessage("Error: The specified row or column is out of range.");
            return false;
        }else{
            return true;
        }
    }
    
    private void positionTile(int row, int column, Rectangle rectangle){
        rectangle.moveHorizontal(column*50+boardWidth);
        rectangle.moveVertical(row*50);
        if (isVisible){

            rectangle.makeVisible();
        }
    }
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
    public void addGlue(int row, int column){
        if (verifyRanges(row,column)){
            return;
        }
        if (boardRectanglesEdit[row][column]!=null && boardGlue[row][column]!=2){
            boardGlue[row][column]=2;
            
        }
    }
    private void stickAroundTiles(int row, int column){
        int[][] positions={{0,-1},{0,1},{1,0},{-1,0}};
        for (int[]directions: positions){
            int y=row+directions[0];
            int x=column+directions[1];
            if (verifyRanges(y,x)){
                boardGlue[y][x]=1;
            }
        }
    }
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
    
    public char[][] actualArrangement(){
        return arrangement;
    }
    
    public void finish(){
        JOptionPane.showMessageDialog(null, "Programa Terminado", "Finish", JOptionPane.ERROR_MESSAGE);
    }
    
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
