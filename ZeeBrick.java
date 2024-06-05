
/* This is ZeeBrick subclass for TetrisBrick superclass
/* Niraj Ojha
/* modified on: 11/28/2023
*/

public class ZeeBrick extends TetrisBrick
{
   public ZeeBrick(int colorNum, int colNum)
    {
        super(colorNum, colNum);
    }
    
    @Override
    public int[][] initPosition(int centerColumn)
    {
        int initialCol = (centerColumn  / 2) - 1;
        int[][]brickPosition = new int[][] {{0, initialCol}, {0, initialCol + 1},
                               {1, initialCol + 1}, {1, initialCol + 2}};
        return brickPosition;
    }
    
    
    @Override
    public int[][] rotate(int totRows, int totCols) 
    {
        int[][] temp = new int[numSegments][2];

        for (int brickIndex = 0; brickIndex < numSegments; brickIndex++) 
        {
            temp[brickIndex][0] = position[brickIndex][0];
            temp[brickIndex][1] = position[brickIndex][1];
        }

        // Adjust pivot point to the central segment (segment index 2)
        int pivotRow = position[1][0];
        int pivotCol = position[1][1];

        for (int brickIndex = 0; brickIndex < numSegments; brickIndex++) 
        {
            int row = pivotRow - (position[brickIndex][1] - pivotCol);
            int col = pivotCol + (position[brickIndex][0] - pivotRow);

            if (row < 0 || row >= totRows || col < 0 || col >= totCols) 
            {
                for (int blockIndex = 0; blockIndex < numSegments; blockIndex++) 
                {
                    position[blockIndex][0] = temp[blockIndex][0];
                    position[blockIndex][1] = temp[blockIndex][1];
                }
                return temp;
            }

            position[brickIndex][0] = row;
            position[brickIndex][1] = col;
        }
        return temp;
    }

    @Override
    public void unrotate(int totRows, int totCols) 
    {
        for (int brickIndex = 0; brickIndex < 3; brickIndex++) 
        {
            rotate(totRows, totCols);
        }
    }
}