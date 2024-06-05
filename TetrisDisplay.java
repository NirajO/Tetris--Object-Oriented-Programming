
/* Class for positioning and sizing of display
 *  Niraj Ojha 
 *  11/28/2023
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TetrisDisplay extends JPanel
{
    private TetrisGame game; 
    private int startX;
    private int startY; 
    private int cellSize = 10; 
    private int speed = 300; 
    private Timer timer; 
    private boolean pause = false;
    private Color[] colors = {Color.red, Color.green, Color.orange, Color.magenta,
                                Color.yellow, Color.pink, Color.CYAN, Color.blue};
    
    //constructor for TetrisDisplay
    public TetrisDisplay(TetrisGame game)
    {
        this.game = game;
        setFocusable(true);
        timer = new Timer(speed, new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                cycleMove();
            }
        });
        timer.start();
        
        addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                translateKey(e);
            }
        });
    }

    //Override the paintComponent method to perform custom drawing
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        drawWell(g);  
        drawBackground(g);
        drawFallingBrick(g);
        drawScore(g);
        drawGameOver(g);
    }

    //Method to draw the well
    public void drawWell(Graphics g)
    {  
        //calculate the well dimensions
        int wellWidth = game.getCols() * cellSize;
        int wellHeight = game.getRows() * cellSize;
        
        startX = (getWidth() - wellWidth) / 2;
        startY = (getHeight() - wellHeight) / 2;
        
        //calculate the coordinates for three rectangles
        int leftX = startX - cellSize;
        int leftY = startY;
        int leftWidth = cellSize;
        int leftHeight = cellSize * game.getRows();
        
        int bottomX = startX - cellSize;
        int bottomY = startY + wellHeight;
        int bottomWidth = cellSize * game.getCols() + cellSize * 2;
        int bottomHeight = cellSize;
        
        int rightX = startX + game.getCols() * cellSize;
        int rightY = startY;
        int rightWidth = cellSize;
        int rightHeight = cellSize * game.getRows();
        
        //Draw the left, top and right rectangle in black color
        g.setColor(Color.black);
        g.fillRect(leftX, leftY, leftWidth, leftHeight);
        g.fillRect(bottomX, bottomY, bottomWidth, bottomHeight);
        g.fillRect(rightX, rightY, rightWidth, rightHeight);
        
        //Fill the interior with white color
        g.setColor(Color.white);
        g.fillRect(startX, startY, wellWidth, wellHeight);
    }
    
    //Method to draw the falling brick
    private void drawFallingBrick(Graphics g)
    {
        for(int i = 0; i < game.getNumSegments(); i++)
        {
            Color brickColor = colors[game.getFallingColorBrick()];
                
            int row = game.getSegRow(i);
            int col = game.getSegCol(i);
                
            int x = col * cellSize;
            int y = row * cellSize;
                
            if(row >= 0)
            {
                g.setColor(brickColor);
                g.fillRect(startX + x, startY + y, cellSize, cellSize);
                
                g.setColor(Color.black);
                g.drawRect(startX + x, startY + y, cellSize, cellSize);
            }           
        }
    }
    
    private void translateKey(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        String moveCode = null;
        
        switch(keyCode)
        {
            case KeyEvent.VK_UP:
                moveCode = "Up";
                break;
                
            case KeyEvent.VK_DOWN:
                moveCode = "Down";
                break;
                
            case KeyEvent.VK_LEFT:
                moveCode = "Left";
                break;
                
            case KeyEvent.VK_RIGHT:
                moveCode = "Right";
                break;
                
            case KeyEvent.VK_SPACE:
                pause = !pause;
                if(pause)
                {
                   timer.stop(); 
                }
                else
                {
                    timer.start();
                }
                break;
                
            case KeyEvent.VK_N:
                game.newGame();
                repaint();
        }
        
        if(moveCode != null)
        {
           game.makeMove(moveCode);
           repaint();
        }
    }
    
    private void drawBackground(Graphics g)
    {  
        for(int row = 0; row < game.getRows(); row++)
        {
            for(int col = 0; col < game.getCols(); col++)
            {
                int colorNumber = game.colorBrick(row, col);
                
                if(colorNumber > 0)
                {
                    int x = startX + col * cellSize;
                    int y = startY + row * cellSize;
                    
                    g.setColor(colors[colorNumber]);
                    g.fillRect(x, y, cellSize, cellSize);
                    
                    g.setColor(Color.black);
                    g.drawRect(x, y, cellSize, cellSize);
                }
            }
        }
    }
    
    private void drawScore(Graphics g) 
    {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        String scoreText = "Score: " + game.getScore();
        g.drawString(scoreText, 10, 30);
    }
    
    private void drawGameOver(Graphics g)
    {
        if (game.isGameOver()) 
        {
            Font gameOverFont = new Font("Arial", Font.BOLD, 30);
            g.setFont(gameOverFont);

            FontMetrics metrics = g.getFontMetrics(gameOverFont);
            String message = "Game Over!";
            int messageWidth = metrics.stringWidth(message);
            int messageHeight = metrics.getHeight();

            int bannerWidth = messageWidth + 20; 
            int bannerHeight = messageHeight + 20; 

            
            int bannerX = (getWidth() - bannerWidth) / 2;
            int bannerY = (getHeight() - bannerHeight) / 2;

            g.setColor(Color.BLUE);
            g.fillRect(bannerX, bannerY, bannerWidth, bannerHeight);

            int textX = bannerX + (bannerWidth - messageWidth) / 2;
            int textY = bannerY + ((bannerHeight - messageHeight) / 2) + 
                    metrics.getAscent();
            g.setColor(Color.WHITE);
            g.drawString(message, textX, textY);
        }
    }
    
    private void cycleMove()
    {
        game.makeMove("Down");
        repaint();
    }
}
