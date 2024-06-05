
/* Customizing Tetris game window, width, height , number of rows and columns
 * Niraj Ojha
 * modified on: 11/28/2023
 */

import javax.swing.*;
import java.io.*;

public class TetrisWindow extends JFrame
{
    private int winWid = 500;  
    private int winHei = 400;  
    private int gameRows = 20; 
    private int gameCols = 10;
    private TetrisGame game;   
    private TetrisDisplay display;
    private LeaderBoard leaderBoard;

    //Constructor for TetrisWindow
    public TetrisWindow()
    {
        this.setTitle("My Tetris Game By Niraj Ojha");
        
        this.setSize(winWid,winHei);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        game = new TetrisGame(gameRows, gameCols);
        
        display = new TetrisDisplay(game);
        
        this.add(display);
        
        setJMenuBar(createMenuBar());
        this.setResizable(false);
        
        this.setLocationRelativeTo(null);
        
        leaderBoard = new LeaderBoard();
        JMenuItem showLeaderboard = new JMenuItem("Show Leaderboard");
        showLeaderboard.addActionListener(e -> leaderBoard.setVisible(true));

        this.setVisible(true);
    }
    
    private void showLeaderBoard() 
    {
        leaderBoard.setVisible(true);
    }
    
    private JMenuBar createMenuBar() 
    {
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem saveGame = new JMenuItem("Save Game");
        JMenuItem loadGame = new JMenuItem("Load Game");
        gameMenu.add(newGame);
        gameMenu.add(saveGame);
        gameMenu.add(loadGame);
        
        JMenu scoreMenu = new JMenu("Scores");
        JMenuItem showLeaderboard = new JMenuItem("Show Leaderboard");
        JMenuItem resetScores = new JMenuItem("Reset Scores");
        scoreMenu.add(showLeaderboard);
        scoreMenu.add(resetScores);

        newGame.addActionListener(e -> startNewGame());
        saveGame.addActionListener(e -> saveGameToFile());
        loadGame.addActionListener(e -> loadGameFromFile());
        
        showLeaderboard.addActionListener(e -> showLeaderBoard());
        resetScores.addActionListener(e -> leaderBoard.clearScores());

        menuBar.add(gameMenu);
        menuBar.add(scoreMenu);
        return menuBar;
    }
     
    private void saveGameToFile() 
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) 
        {
            File fileToSave = fileChooser.getSelectedFile();
            game.saveGameToFile(fileToSave.getAbsolutePath());
            JOptionPane.showMessageDialog(this, "Game saved successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
     
    private void loadGameFromFile() 
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a game file to load");

        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) 
        {
            File fileToLoad = fileChooser.getSelectedFile();
            TetrisGame loadedGame = TetrisGame.loadGameFromFile(
                    fileToLoad.getAbsolutePath());
            if (loadedGame != null) {
                game = loadedGame;
                display.repaint();
                
                 JOptionPane.showMessageDialog(this, "Game loaded successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Failed to load the game.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void startNewGame() 
    {
        game.newGame(); 
        display.repaint(); 
        leaderBoard.loadScores();
    }
     
    //Main method to start the tetris game 
    public static void main(String[] args) 
    {
        TetrisWindow window = new TetrisWindow();
    }
}

