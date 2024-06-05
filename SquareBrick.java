
/* This is SquareBrick subclass for TetrisBrick superclass
/* Niraj Ojha
/* modified on: 11/28/2023
*/

public class SquareBrick extends TetrisBrick 
{
    public SquareBrick(int colorNum, int colNum)
    {
       super(colorNum, colNum);
    }
    
    @Override
    public int[][] initPosition(int centerColumn)
    {
        int initialCol = (centerColumn / 2) - 1;
        int[][] brickPosition = 
        {
        {0, initialCol},
        {0, initialCol+ 1},
        {1, initialCol},
        {1, initialCol + 1}
        };
        
        return brickPosition;
    }
    
    @Override
    public int[][] rotate(int totRows, int totCols)
    {
        return position;
    }
    
    @Override
    public void unrotate(int totRows, int totCols)
    {
        
    }
}

