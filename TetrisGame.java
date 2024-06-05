
/*  Implementing the logic for tetris game
/*  Niraj Ojha
/*  modified on: 11/28/2023 
 */ 

 import java.util.*;
import java.io.*;
import javax.swing.*;

public class TetrisGame implements Serializable
{
    private TetrisBrick fallingBrick; 
    private int numRows; 
    private int numCols; 
    private int numBrickTypes = 7;
    private int[][] background;
    private int score;
    Random randGen = new Random(); 
    
    //Constructor for TetrisGame with number of rows and columns
    public TetrisGame(int numRows, int numCols)
    {
        this.numRows = numRows;
        this.numCols = numCols;
        this.score = 0;
        this.randGen = new Random();
        this.background = new int [numRows][numCols];
        initBoard();
        spawnBrick();
    }
    
    public void initBoard() 
    {
        for (int row = 0; row < numRows; row++) 
        {
            for (int col = 0; col < numCols; col++) 
            {
                background[row][col] = 0;
            }
        }
    }
    
    public void newGame() 
    {
        initBoard(); 
        score = 0;
        spawnBrick();
    }
    
    public int fetchBoardPosition(int row, int col) 
    {
        return background[row][col];
    }
    
    private void transferColor() 
    {
        for (int seg = 0; seg < fallingBrick.numSegments; seg++) 
        {
            int row = fallingBrick.position[seg][0];
            int col = fallingBrick.position[seg][1];
        
            if (row >= 0 && col >= 0 && row < numRows && col < numCols) 
            {
                background[row][col] = fallingBrick.getColorNumber();
            }
        }
    }
    
    //Method to spawn a new Tetris Brick
    private void spawnBrick()
    {
        if (checkGameOver()) 
        {
            handleGameOver();
        }
        
        int brickType = randGen.nextInt(numBrickTypes);
        TetrisBrick newBrick;
        switch(brickType)
        {
            case 0:
                fallingBrick = new ElBrick(1, numCols);
                break;
            
            case 1:
                fallingBrick = new EssBrick(2, numCols);
                break;
                
            case 2:
                fallingBrick = new JayBrick(3, numCols);
                break;
                
            case 3:
                fallingBrick = new LongBrick(4, numCols);
                break;
                
            case 4:
                fallingBrick = new SquareBrick(5, numCols);
                break;
                
            case 5:
                fallingBrick = new StackBrick(6, numCols);
                break;
                
            case 6:
                fallingBrick = new ZeeBrick(7, numCols);
                break;
        }
    }
    
    //Method to make a move in the game
    public void makeMove(String moveCode)
    {
        if (isGameOver())
        {
            return;
        }
    
        int moveKey = 0;
        
        switch(moveCode)
        {
            case "Down":
                if (validateMove(moveKey))
                {
                    fallingBrick.moveDown();
                }
                else
                { 
                    transferColor();
                    spawnBrick();
                    checkAndClearFullLines();
                }
                break;
            case "Left":
                moveKey = 1;
                if (validateMove(moveKey))
                {
                    fallingBrick.moveLeft();
                }
                break;
            case "Right":
                moveKey = 2;
                if (validateMove(moveKey))
                {
                    fallingBrick.moveRight();
                }
                break;
            case "Up":
                moveKey = 3;
                
                if(!validateMove(moveKey)){
                    fallingBrick.rotate(numRows, numCols);
                }  
                break;
        }
    }

    //Method to validate a move
    private boolean validateMove(int keyCode)
    {
        for (int seg = 0; seg < fallingBrick.numSegments; seg++) 
        {
            int row = fallingBrick.position[seg][0];
            int col = fallingBrick.position[seg][1];

            // Check down movement
            if (keyCode == 0 && (row == numRows - 1 || background[row + 1][col] != 0))
            {
                return false; 
            }

            // Check left movement
            if (keyCode == 1 && (col == 0 || background[row][col - 1] != 0))
            {
                return false;
            }

            //check right movement
            if (keyCode == 2 && (col == numCols - 1 || background[row][col + 1] != 0)) 
            {
                return false;
            }

            if (keyCode == 3) 
            {
                return false;
            }
        }
        return true;   
    }
    
    private boolean checkGameOver() 
    {
        for (int col = 0; col < numCols; col++) 
        {
            if (background[0][col] != 0) 
            {
                return true; 
            }
        }
        return false; 
    }

    
    
    private boolean isFullLine(int row) 
    {
        for (int col = 0; col < numCols; col++) 
        {
            if (background[row][col] == 0) 
            {
                return false;
            }
        }
        return true;
    }
    
    private void clearLine(int row) 
    {
        for (int col = 0; col < numCols; col++) 
        {
            background[row][col] = 0;
        }
    }
    
    private void dropLinesAbove(int clearedRow) 
    {
        for (int row = clearedRow; row >= 1; row--) 
        {
            for (int col = 0; col < numCols; col++) 
            {
                background[row][col] = background[row - 1][col];
            }
        }

        for (int col = 0; col < numCols; col++) 
        {
            background[0][col] = 0;
        }
    }
    
    private void checkAndClearFullLines() 
    {
        int linesCleared = 0;
        for (int row = numRows - 1; row >= 0; row--) 
        {
            if (isFullLine(row))
            {
                clearLine(row);
                dropLinesAbove(row);
                linesCleared++;
                row++; 
            }
        }
    
        if (linesCleared > 0) 
        {
            updateScore(linesCleared);
        }
    }
    
    private void updateScore(int linesCleared) 
    {
        switch (linesCleared) 
        {
            case 1:
                score += 100;
                break;
            case 2:
                score += 300;
                break;
            case 3:
                score += 600;
                break;
            case 4:
                score += 1200;
                break;
        }
    }
    
    public boolean isGameOver() 
    {
        for (int col = 0; col < numCols; col++) 
        {
            if (background[0][col] != 0) 
            {
                return true;
            }
        }
        return false;
    }
    
    private void placeBrick()
    {
        checkAndClearFullLines();
        if (isGameOver()) 
        {
            handleGameOver();
        } 
        else 
        {
            spawnBrick();
        }
    }
    
    private void handleGameOver() 
    {
       if(isGameOver())
       {
            String playerName = "Player";
            LeaderBoard leaderBoard = new LeaderBoard();
            leaderBoard.addScore(playerName, score);
            leaderBoard.saveScores();
            leaderBoard.updateListModel();
       }
    }
    
    public void saveGameToFile(String filePath)
    {
        try (FileWriter savingBackground = new FileWriter(filePath, false)) 
        {
            savingBackground.write(numRows + "," + numCols + "\n");

            for (int tempRow = 0; tempRow < numRows; tempRow++) 
            {
                for (int tempCol = 0; tempCol < numCols; tempCol++) 
                {
                    savingBackground.write(background[tempRow][tempCol] + ",");
                }
                savingBackground.write("\n");
            }
        } 
        catch (IOException ioe) 
        {
            String userOutput = "Warning: error in data from\n" + "file: " + filePath;
            JOptionPane.showMessageDialog(null, userOutput, "File IO Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static TetrisGame loadGameFromFile(String filePath) 
    {
        try (Scanner scanner = new Scanner(new File(filePath))) 
        {
            String[] dimensions = scanner.nextLine().split(",");
            int numRows = Integer.parseInt(dimensions[0].trim());
            int numCols = Integer.parseInt(dimensions[1].trim());

            TetrisGame game = new TetrisGame(numRows, numCols);

            for (int row = 0; row < numRows; row++) 
            {
                String[] values = scanner.nextLine().split(",");
                for (int col = 0; col < numCols; col++) {
                    game.setBackgroundCell(row, col, 
                            Integer.parseInt(values[col].trim()));
                }
            }
            return game;
        } 
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        } 
        catch (NumberFormatException e) 
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public int getScore() 
    {
        return score;
    }
    
    //Method to get the filling color of the falling brick
    public int getFallingColorBrick()
    {
        return fallingBrick.getColorNumber();
    }  
    
    //Method to get the total number of segments in the game grid
    public int getNumSegments()
    {
        return fallingBrick.numSegments;
    }
    
    public int getRows()
    {
        return numRows;
    }
    
    public int getCols()
    {
        return numCols;
    }
    
    public int getSegRow(int seg)
    {
       return fallingBrick.position[seg][0];
    }
    
    public int getSegCol(int seg)
    {
        return fallingBrick.position[seg][1];
    }
    
    public int colorBrick(int numRows, int numCols)
    {
        int brickColor = background[numRows][numCols];
        return brickColor;
    }
    
    public void setBackgroundCell(int row, int col, int value) 
    {
        this.background[row][col] = value;
    }
}

