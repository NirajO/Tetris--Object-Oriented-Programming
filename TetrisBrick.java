
/* An abstract class for representing Tetris Brick
/* Niraj Ojha
/* modified on: 11/28/2023
*/

public abstract class TetrisBrick 
{
    protected int numSegments = 4;
    protected int[][] position = new int [numSegments][2];
    protected int orientation;
    protected int colorNum;
    
    public TetrisBrick(int colorNum, int colNum)
    {
       this.colorNum = colorNum;
       this.position = this.initPosition(colNum);
    }
    
    public void moveDown()
    {
        for(int row = 0; row < numSegments; row++)
        {
            position[row][0]++;
        }
    }
    
    public void moveUp()
    {
        for(int row = 0; row < numSegments; row++)
        {
            position[row][0]--;
        }
    }
    
    public int getColorNumber()
    { 
        return colorNum;
    }
    
    public abstract int[][] initPosition(int centerColumn);
    
    public abstract int[][] rotate(int totRows, int totCols);
    
    public abstract void unrotate(int totRows, int totCols);
    
    public void moveLeft()
    {
        for (int segment = 0; segment < numSegments; segment++) 
        {
            position[segment][1]--;
        }
    }
    
    public void moveRight()
    {
        for (int segment = 0; segment < numSegments; segment++) 
        {
            position[segment][1]++;
        }
    }
    
    @Override
    public String toString() 
    {
        return "TetrisBrick{colorNum=" + colorNum + "}";
    }
}

