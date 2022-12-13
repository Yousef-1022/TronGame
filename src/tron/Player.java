package tron;

import java.awt.Color;
import java.util.ArrayList;


/**
 *
 * @author Yousef Al-Akhali
*/
public class Player{
    
    private String name;
    private Integer score;
    private Color color; 
    private Bike bike;
    
    
    Player(String name,Color color,Bike newbike)
    {
        this.name = name;
        this.color = color;
        this.score = 0;
        this.bike = newbike;
    }
    
    
    /**
    * Getters and setters
    */
    public String getName(){
        if(this.name != ""){
            return this.name;
        }
        return "noname";
    }
    
    public void setScore(Integer sc){
        this.score = sc;
    }
    
    public Integer getScore(){
        return this.score;
    }

    
    public Bike getBike(){
        return this.bike;
    }
    
    /**
    * Function to check if the other player in question collides with player invoking the function
    * @param other_player
    * @return a Boolean value which lets us know if trail collision happened
    */
    public boolean collapseTrail(Player other_player){
        
        boolean crossedTrail = false;
        ArrayList<BikeTrail> theTrail = this.getBike().getTrail();
         
        for(int i=0;i<theTrail.size();i++)
        {
            crossedTrail = (theTrail.get(i).collides(other_player.getBike().getX(), other_player.getBike().getY()));
            if (crossedTrail){
                break;
            }
        }
        
        return crossedTrail; 
    }
    
    /**
     * Function to check if the player in question collided with the borders of the game
     * @return a Boolean value which lets us know if the player collided with the game borders
     */
    public boolean outOfArena(){
        boolean outofBound = (this.getBike().getX()>710 || this.getBike().getY()>430 || this.getBike().getX()<0 || this.getBike().getY()<0);
        return outofBound;
    }
    
}