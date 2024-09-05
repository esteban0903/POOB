import javax.swing.JOptionPane;
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
    private char[][] arrangement;
    private boolean isVisible;
    private Rectangle background;
    private Rectangle edges;
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
            JOptionPane.showMessageDialog(null, "Invalid dimensions: Height = " + height + ", Width = " + width, "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            this.height=height;
            this.width=width;
            ending = new char[width][height];
            arrangement=new char[width][height];
            boardRectangles = new Rectangle[width][height];
            createBoard();
        }   
    }
    public puzzle(char ending[][]){
        int rows=ending.length;
        int columns=ending[0].length;
        if (rows<1 || columns>500){
            JOptionPane.showMessageDialog(null, "Invalid dimensions: Height = " + rows + ", Width = " + columns, "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            height=rows;
            width=columns;
            this.ending = ending;
            arrangement=new char[rows][columns];
            boardRectangles = new Rectangle[rows][columns];
            createBoard();
            fillBoard(arrangement);
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
            JOptionPane.showMessageDialog(null, "Invalid dimensions: Row = " + rows + ", Column = " + columns, "Error", JOptionPane.ERROR_MESSAGE);
        }else{ 
            height=rows;
            width=columns;
            this.ending = ending;
            arrangement=starting;
            boardRectangles = new Rectangle[rows][columns];
            createBoard();
            fillBoard(arrangement);
        }
    }
    private void createBoard(){
        background= new Rectangle();
        edges=new Rectangle();
        background.changeSize(height*50,width*50);
        background.changeColor("maroon");
        edges.changeSize(50*height+10,50*width+10);
        edges.changeColor("black");
        edges.moveHorizontal(-5);
        edges.moveVertical(-5);
        
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
    
    public int getLetterIndex(char letter) {
        letter = Character.toUpperCase(letter);
        return letter - 'A';
    }
    public void addTile( int row, int column, String color){
        if (!verifyRanges(row,column)){
            return;
        }if (boardRectangles[row][column] == null){
            arrangement[row][column]=color.charAt(0);
            Rectangle rectangle = new Rectangle();
            rectangle.changeSize(50,50);
            rectangle.changeColor(color);
            boardRectangles[row][column]=rectangle;
            positionTile(row,column,rectangle) ;
        
        }else{ 
            JOptionPane.showMessageDialog(null, "Error: There is already a tile at position [" + row + "][" + column + "].", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void deleteTile(int row, int column){
        if (!verifyRanges(row,column)){
            return;
        }if (arrangement[row][column] != '.'){
            boardRectangles[row][column].makeInvisible();
            arrangement[row][column]='.';
            boardRectangles[row][column]=null;
        }else{ 
            JOptionPane.showMessageDialog(null, "Error: There is a null tile at position [" + row + "][" + column + "].", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private boolean verifyRanges(int row, int column){
        if (row>=height || row<0  || column>=width || column<0){
            JOptionPane.showMessageDialog(null, "Error: The specified row or column is out of range.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }else{
            return true;
        }
    }
    
    private void positionTile(int row, int column, Rectangle rectangle){
        rectangle.moveHorizontal(column*50);
        rectangle.moveVertical(row*50);
        if (isVisible){

            rectangle.makeVisible();
        }
    }
    public void recolocateTile(int[] from, int[] to){
        int rowFrom=from[0], columnFrom=from[1], rowTo=to[0], columnTo=to[1];
        if (!verificationRecolocate(rowFrom,columnFrom,rowTo,columnTo)){
            return;
        }
        String color= boardRectangles[rowFrom][columnFrom].getColor();
        deleteTile(rowFrom,columnFrom);
        addTile(rowTo,columnTo,color);
    
    }
    public boolean verificationRecolocate(int rowOne, int columnOne, int rowTwo, int columnTwo){
        if (!verifyRanges(rowOne,columnOne ) || !verifyRanges(rowTwo,columnTwo)){
            return false;
        }
        if (boardRectangles[rowOne][columnOne]==null){
            JOptionPane.showMessageDialog(null, "Error: There is a null tile at position [" + rowOne + "][" + columnOne + "].", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }if (boardRectangles[rowTwo][columnTwo]!=null){
            JOptionPane.showMessageDialog(null, "Error: There is already a Rectangle at position [" + rowTwo + "][" + columnTwo + "].", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;   
        
        
    }
    public void makeVisible(){
        isVisible=true;
        edges.makeVisible();
        background.makeVisible();
        for (Rectangle[] rectangles:boardRectangles){
            for(Rectangle rectangle:rectangles){
                if (rectangle!=null){
                    rectangle.makeVisible();
                }
            }
        }
        
    }
    
    public void makeInvisible(){
        isVisible=false;
        edges.makeInvisible();
        background.makeInvisible();
        for (Rectangle[] rectangles:boardRectangles){
            for(Rectangle rectangle:rectangles){
                if (rectangle!=null){
                    rectangle.makeInvisible();
                }
            }
        }
        
    }
    


    
    


    
}
