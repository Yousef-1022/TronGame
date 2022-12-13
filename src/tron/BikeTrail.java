package tron;

import java.awt.Rectangle;


/**
 * Contains positions, and have collides method to check if rectangles intersects
 * @author Yousef Al-Akhali
*/
public class BikeTrail {
    private int x, y;
    Rectangle rec;

    /**
     * Initializes (x, y) plane coordinates of position
     * @param x
     * @param y
    */
    public BikeTrail(int x, int y) {
        this.x = x;
        this.y = y;
        rec = new Rectangle (x,y);
    }

    /**
     * Getters and Setters
    */
    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }
    
    public boolean collides(int other_X,int other_Y) {
        Rectangle rect = new Rectangle(x, y, 20, 20);
        Rectangle otherRect = new Rectangle(other_X, other_Y, 20, 20);        
        return rect.intersects(otherRect);
    }
}