package tron;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;


/**
 * Basic object representation class, allows to be drawn and located by default
 * @author Yousef Al-Akhali
*/
public class Sprite {
    /**
     * The coordinates of the top left corner of the sprite
    */
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Image image;

    public Sprite(int x, int y, int width, int height, Image image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }
    
    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }
    
    public void drawTrail(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }
    
    
    /**
    * Returns true if this sprite collides with the other sprite
    * @param other
    * @return 
    */
    public boolean collides(Sprite other) {
        Rectangle rect = new Rectangle(x, y, width, height);
        Rectangle otherRect = new Rectangle(other.x, other.y, other.width, other.height);        
        return rect.intersects(otherRect);
    }
    
    
    /**
    * Getters and setters
    */
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
}