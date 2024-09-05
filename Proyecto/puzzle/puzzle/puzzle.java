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
    private int[][] ending;
    private int[][] starting; 
    private Rectangle[][] boardRectangles;
    private char[][] arrangement;
    private boolean isVisible;
    private Rectangle background;
    private Rectangle edges;
    /**
     * Constructor for objects of class puzzle
     */
    public puzzle(int height, int width){
        if (height<1 || width>500){
            JOptionPane.showMessageDialog(null, "Invalid dimensions: Height = " + height + ", Width = " + width, "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            this.height=height;
            this.width=width;
            ending = new int[width][height];
            starting = new int[width][height];
            arrangement=new char[width][height];
            boardRectangles = new Rectangle[width][height];
            createBoard();
        }   
    }
    public puzzle(int ending[][]){
        int rows=ending.length;
        int columns=ending[0].length;
        if (rows<1 || columns>500){
            JOptionPane.showMessageDialog(null, "Invalid dimensions: Height = " + rows + ", Width = " + columns, "Error", JOptionPane.ERROR_MESSAGE);
        }else{ 
            ending = new int[rows][columns];
            arrangement=new char[rows][columns];
            boardRectangles = new Rectangle[rows][columns];
            createBoard();
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
    public puzzle(int starting[][], int ending[][]){
        int rows=starting.length;   
        int columns=starting[0].length;
        if (rows<1 || columns>500){
            JOptionPane.showMessageDialog(null, "Invalid dimensions: Row = " + rows + ", Column = " + columns, "Error", JOptionPane.ERROR_MESSAGE);
        }else{ 
            ending = new int[rows][columns];
            starting = new int[rows][columns];
            arrangement=new char[rows][columns];
            boardRectangles = new Rectangle[rows][columns];
            createBoard();
        }
    }
    public void createBoard(){
        background= new Rectangle();
        edges=new Rectangle();
        background.changeSize(height*50,width*50);
        background.changeColor("maroon");
        edges.changeSize(50*height+10,50*width+10);
        edges.changeColor("black");
        edges.moveHorizontal(-5);
        edges.moveVertical(-5);
        
    }
    public void addTile( int row, int column, String color){
        if (!verifyRanges(row,column)){
            return;
        }if (arrangement[row][column] == '\u0000'){
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
    
    public boolean verifyRanges(int row, int column){
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
    
    
    

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    
}
