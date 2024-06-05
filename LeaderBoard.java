/*  This is a Leader Board Class for storing scores
    Niraj Ojha
    Modified on: 11/28/2023 
 */

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.*;

public class LeaderBoard extends JFrame
{
    private ArrayList<ScoreEntry> highScores;
    private static final String SCORE_FILE = "highscores.dat";
    private DefaultListModel<String> listModel;
    private int ledWid = 300;
    private int ledHei = 320;

    public LeaderBoard() 
    {
        setTitle("Leader Board");
        setSize(ledWid, ledHei);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        highScores = new ArrayList<>();
        listModel = new DefaultListModel<>();
        loadScores();

        JList<String> scoreList = new JList<>(listModel);
        add(new JScrollPane(scoreList), BorderLayout.CENTER);

        JButton clearButton = new JButton("Clear High Scores");
        clearButton.addActionListener(e -> clearScores());
        add(clearButton, BorderLayout.SOUTH);
    }
    
    public void addScore(String name, int score) 
    {
        highScores.add(new ScoreEntry(name, score));
        Collections.sort(highScores);
        if (highScores.size() > 10) 
        {
            highScores.remove(highScores.size() - 1);
        }
        saveScores();
        updateListModel();
    }

    public void clearScores() 
    {
        highScores.clear();
        saveScores();
        updateListModel();
    }

    public void loadScores() 
    {
        try (ObjectInputStream ois = new ObjectInputStream
        (new FileInputStream(SCORE_FILE))) 
        {
            highScores = (ArrayList<ScoreEntry>) ois.readObject();
        } 
        catch (IOException | ClassNotFoundException e) 
        {
            highScores = new ArrayList<>();
        }
        updateListModel();
    }

    public void saveScores() 
    {
        try (ObjectOutputStream oos = new ObjectOutputStream
        (new FileOutputStream(SCORE_FILE))) 
        {
            oos.writeObject(highScores);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public void updateListModel() 
    {
        listModel.clear();
        highScores.forEach(entry -> listModel.addElement(entry.toString()));
    }

    private static class ScoreEntry implements Serializable, Comparable<ScoreEntry> 
    {
        String name;
        int score;

        public ScoreEntry(String name, int score) 
        {
            this.name = name;
            this.score = score;
        }

        @Override
        public int compareTo(ScoreEntry other) 
        {
            return Integer.compare(other.score, this.score); 
        }

        @Override
        public String toString() 
        {
            return name + ": " + score;
        }
    }
}

