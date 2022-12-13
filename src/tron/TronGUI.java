package tron;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Toolkit;


/**
* GUI Class
* @author Yousef Al-Akhali
*/
public class TronGUI {
    private String[]  cols = {"cyan","magneta","yellow","red","green","blue","gray","white","black"};
    private JFrame theFrame;
    private Engine gameArea;    

    public TronGUI() {
        theFrame = new JFrame("TronGame");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("MainMenu");
        JMenuItem menuHighScore = new JMenuItem("Leaderboard");
        JMenuItem menuRestart = new JMenuItem(new AbstractAction("Restart!") {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.restart();
                gameArea.restartTimer();
            }
        });
        JMenuItem menuGameExit = new JMenuItem(new AbstractAction("Exit!") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        menuGame.add(menuGameExit);
        menuGame.add(menuRestart);
        menuBar.add(menuGame);
        theFrame.setJMenuBar(menuBar);
        
        //  When clicked on <Leaderboard>
        menuHighScore.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg) {
                JFrame tableFrame = new JFrame();
                //  Uncomment size and its related matters to show the whole table!
                /* 
                int size = 0;
                try {
                    size = gameArea.getHs().getHighScores().size();
                } catch (SQLException ex) {
                    Logger.getLogger(TronGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                */
                tableFrame.setTitle("High Scores");
                String[] header = {"Name", "Score"};
                String[][] body = new String[10][2];
                //String[][] body = new String[size][2];
                
                try {
                    //for(int i = 0; i < size; i++) {
                    for (int i = 0; i < 10;i++){
                        body[i][0] = gameArea.getHs().getHighScores().get(i).getName();
                        body[i][1] = Integer.toString(gameArea.getHs().getHighScores().get(i).getScore());
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(TronGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
               
                //  Creation of table and Scroll object
                JTable table = new JTable(body, header);
                JScrollPane sp = new JScrollPane(table);
                tableFrame.add(sp);
                tableFrame.setSize(625, 300);
                tableFrame.setVisible(true);
            }
        });
        
        menuGame.add(menuHighScore);
        
        //  Get player1 name, then color
        String firstPlayerName;
        firstPlayerName = JOptionPane.showInputDialog("Player1 name: ");
        String fCol = (String)JOptionPane.showInputDialog(null, "Choose a color for Player1: ", 
                "Pick a color", JOptionPane.QUESTION_MESSAGE, null, cols, cols[3]);
        Color firstColor;
        try {
            Field field = Class.forName("java.awt.Color").getField(fCol);
            firstColor = (Color)field.get(null);
        } catch (Exception e) { //  Incase the color was undefined.
            firstColor = Color.RED; 
        }
        
        
        //  Get player2 name, then color
        String secondPlayerName;
        secondPlayerName = JOptionPane.showInputDialog("Player2 name: ");
      
        String sCol = (String)JOptionPane.showInputDialog(null, "Choose a color for Player2: ", 
                "Pick a color", JOptionPane.QUESTION_MESSAGE, null, cols, cols[5]);
        Color secondColor;
        try {
            Field field = Class.forName("java.awt.Color").getField(sCol);
            secondColor = (Color)field.get(null);
            if (secondColor == firstColor) {
                if (secondColor == Color.BLUE) {
                    secondColor = Color.RED;
                }
                else {
                    secondColor = Color.BLUE;
                }
            }
        } catch (Exception e) { //  Incase the color was undefined.
            if(firstColor != Color.BLUE) {
                secondColor = Color.BLUE;
            }
            else {
                secondColor = Color.RED;
            }
        }

        //  Name checkers
        if (firstPlayerName == null || firstPlayerName.equals("")) {
            int randomNum = ((int) ((Math.random() * (126 - 33)) + 33));
            firstPlayerName = "Player1_"+(char)randomNum+randomNum;
        }
        if (secondPlayerName == null || secondPlayerName.equals("")) {
            int randomNum = ((int) ((Math.random() * (126 - 33)) + 33));
            secondPlayerName = "Player2_"+(char)randomNum+randomNum;
        }
        if (secondPlayerName.equals(firstPlayerName)) {
            secondPlayerName += "theSecond";
        }
        

        //  General formation of the game theFrame
        gameArea = new Engine(firstPlayerName,firstColor,secondPlayerName,secondColor);
        theFrame.getContentPane().add(gameArea);
        theFrame.getContentPane().add(gameArea.getTurnLabel(), BorderLayout.NORTH);
        theFrame.getContentPane().add(gameArea.getTimer(), BorderLayout.SOUTH);
        theFrame.setPreferredSize(new Dimension(800, 600));
        theFrame.setResizable(false);
        theFrame.pack();
        theFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("data/images/logo.jpg"));
        theFrame.setVisible(true);
    }
}