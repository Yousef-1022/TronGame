package tron;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Window 
 * @author Yousef Al-Akhali
*/
public class Engine extends JPanel {
    
    //  Coordinates
    private HighScores highScores;
    private final int FPS = 250;
    private final int BIKE_X = 40;
    private final int BIKE_Y = 150;
    private final int BIKE_WIDTH = 80;
    private final int BIKE_HEIGHT = 80;
    private final int BIKE_MOVEMENT = 2;
    
    private JLabel scoreLabel;
    
    String firstName;
    Color firstColor;
    String secondName;
    Color secondColor;

    private Bike bike1;
    private Bike bike2;
    private Player player1;
    private Player player2;
    //  Timer
    private Timer newFrameTimer;
    private JLabel timeLabel;
    private long startTime;
    private Timer timer;
    private long elapsedTime;
    private double elapsedTimeInSeconds;
    
    
    private Image background;

    //  GameLogic, movement is implemented here
    public Engine(String firstName,Color firstColor,String secondName,Color secondColor) {
        super();

        background = new ImageIcon("data/images/background.jpg").getImage();
        this.firstName = firstName;
        this.secondName = secondName;
        this.firstColor = firstColor;
        this.secondColor = secondColor;

        
        scoreLabel = new JLabel( "Player1: "+ firstName + " and Player2: " + secondName+" are playing!");
        
        try {   
            highScores = new HighScores(10);
        } catch (SQLException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //  MOVEMENT
        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "pressed left");
        this.getActionMap().put("pressed left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player1.getBike().setVelx(-BIKE_MOVEMENT);
                player1.getBike().setImage(new ImageIcon("data/images/bike_left.png").getImage());
            }
        });
        
        this.getInputMap().put(KeyStroke.getKeyStroke("A"), "pressed A");
        this.getActionMap().put("pressed A", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player2.getBike().setVelx(-BIKE_MOVEMENT);
                player2.getBike().setImage(new ImageIcon("data/images/bike_left.png").getImage());
            }
        });
        
        
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "pressed right");
        this.getActionMap().put("pressed right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player1.getBike().setVelx(BIKE_MOVEMENT);
                player1.getBike().setImage(new ImageIcon("data/images/bike_right.png").getImage());
            }
        });
        
        this.getInputMap().put(KeyStroke.getKeyStroke("D"), "pressed D");
        this.getActionMap().put("pressed D", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player2.getBike().setVelx(BIKE_MOVEMENT);
                player2.getBike().setImage(new ImageIcon("data/images/bike_right.png").getImage());
            }
        });
        
        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "pressed down");
        this.getActionMap().put("pressed down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player1.getBike().setVely(BIKE_MOVEMENT);
                player1.getBike().setImage(new ImageIcon("data/images/bike_down.png").getImage());
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("S"), "pressed S");
        this.getActionMap().put("pressed S", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player2.getBike().setVely(BIKE_MOVEMENT);
                player2.getBike().setImage(new ImageIcon("data/images/bike_down.png").getImage());
            }
        });
        
        this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "pressed up");
        this.getActionMap().put("pressed up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player1.getBike().setVely(-BIKE_MOVEMENT);
                player1.getBike().setImage(new ImageIcon("data/images/bike_up.png").getImage());
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("W"), "pressed w");
        this.getActionMap().put("pressed w", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player2.getBike().setVely(-BIKE_MOVEMENT);
                player2.getBike().setImage(new ImageIcon("data/images/bike_up.png").getImage());
            }
        });
        
        restart();
        newFrameTimer = new Timer(1000 / FPS, new NewFrameListener());
        newFrameTimer.start();
        startTimer();
    }


    public HighScores getHs() {
        return highScores;
    }
    

    /**
    * Starts a timer that updates every 100ms and puts it on the frame
    */
    public void startTimer(){
        timeLabel = new JLabel(" ");
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        startTime = System.currentTimeMillis();
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedTime = System.currentTimeMillis() - startTime;
                elapsedTimeInSeconds = (double)elapsedTime/1000;
                timeLabel.setText("Game time: " + elapsedTimeInSeconds + " s");
            }
        });
        timer.start();
    }
    

    public void restartTimer(){
        startTime = System.currentTimeMillis();
        if(timer != null) {
            timer.restart();   
        }
    }


    public JLabel getTimer(){
        return timeLabel;
    }


    /**
     *  Restart game command, used when game is first created and when clicked from the MainMenu, resets already set values.
    */
    public void restart() {
        Image first = new ImageIcon("data/images/bike_right.png").getImage();
        bike1 = new Bike(BIKE_X, BIKE_Y, BIKE_WIDTH, BIKE_HEIGHT, first);
        player1 = new Player(firstName,firstColor,bike1);
    
        Image second = new ImageIcon("data/images/bike_left.png").getImage();
        bike2 = new Bike(520,  BIKE_Y, BIKE_WIDTH, BIKE_HEIGHT, second);
        player2 = new Player(secondName,secondColor,bike2);
        restartTimer();
    }
    
    
    //  Bikes and their trails painting
    @Override
    protected void paintComponent(Graphics graphix) {
        super.paintComponent(graphix);
        graphix.drawImage(background, 0, 0, 800, 600, null);
        player1.getBike().draw(graphix);
        player2.getBike().draw(graphix);
        player1.getBike().drawTrail(graphix,firstColor);
        player2.getBike().drawTrail(graphix,secondColor);
        
    }
    
    public JLabel getTurnLabel() {
        return scoreLabel;
    }

    private HighScores HighScores(int score) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
    /**
    *   Modifed ActionListener to check the current status of the bikes and evaluate if a winner is determined 
    */  
    class NewFrameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            player1.getBike().move();
            player2.getBike().move();
            ArrayList<HighScore> scores = new ArrayList<>();
            try {
                scores = highScores.getHighScores();
            } catch (SQLException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //  If a collision with a player or with the bounds happened
            if (player1.collapseTrail(player2) || player2.collapseTrail(player1) || player1.outOfArena() || player2.outOfArena()) {
                
                timer.stop();

                //  Player1 wins if player2 collapses with player1's trail or if simply player2 hit the bounds
                if(player1.collapseTrail(player2) || player2.outOfArena() )
                {
                    JOptionPane.showMessageDialog(null,firstName + " has won the game! Time elapsed: "+elapsedTimeInSeconds+" seconds");
                    boolean name_exist = false;
                    try{
                        for(int i=0;i<scores.size();++i){
                            if(firstName.equals(scores.get(i).getName())){
                                name_exist = true;
                                break;
                            }
                        }
                        if(name_exist){
                            highScores.increaseScore(firstName);
                        }else{
                            highScores.putHighScore(firstName, 1);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                //  Player2 wins if player1 collapses with player2's trail or if simply player1 hit the bounds
                else if(player2.collapseTrail(player1) || player1.outOfArena()){
                    
                    boolean name_exist = false;
                    try{
                        for(int i=0;i<scores.size();++i){
                            if(secondName.equals(scores.get(i).getName())){
                                name_exist = true;
                            }
                        }
                        if(name_exist){
                            highScores.increaseScore(secondName);
                        }else{
                            highScores.putHighScore(secondName, 1);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    JOptionPane.showMessageDialog(null,secondName + " has won the game! Time elapsed: "+elapsedTimeInSeconds+" seconds");
                }
                restart();
            }
           
            repaint();
        }

    }
}