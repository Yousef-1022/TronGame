package tron;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;


/**
 * Extension of the basic object class, now has its own velocity movement and trail line coordinates
 * @author Yousef Al-Akhali
*/
public class Bike extends Sprite {
    
    private double velx;
    private double vely;
    private ArrayList<BikeTrail> position = new ArrayList<>();
    private BikeTrail theTrail;
    
    public Bike(int x, int y, int width, int height, Image image) {
        super(x, y, width, height, image);
    }
    
    
    /**
    *   Function to move and shift the x and y coordinates (Vel)
    */
    public void move() {
        if ((velx < 0 && x >= 0) || (velx > 0 && x + width <= 800)) {
            x += velx;
        }
        
        if ((vely < 0 && y >= 0) || (vely > 0 && y + height <= 600)) {
            y += vely;
        }
        shift(x,y);
    }
    
    /**
    * Function to draw the trail of the bike
    * @param g
    * @param c
    */
    public void drawTrail(Graphics g,Color c) {
        for(int i=0; i<position.size(); i++) {
            g.setColor(c);
            g.fillRect(position.get(i).getX()+30,position.get(i).getY()+30,20,20);
        }
    }

    public double getVelx() {
        return velx;
    }

    public void setVelx(double velx) {
        this.velx = velx;
        this.vely = 0;
    }
    
    public double getVely() {
        return vely;
    }

    public void setVely(double vely) {
        this.vely = vely;
        this.velx = 0;
    }
    
    public void setImage(Image someImage) {
        this.image = someImage;
    }
    
    public void shift(int x,int y){
        theTrail = new BikeTrail(x,y);
        position.add(theTrail);
        
    }
    public ArrayList<BikeTrail> getTrail(){
        return this.position;
    }
}