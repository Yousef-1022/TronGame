package tron;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
* Database class containing all necessary commands to interact with the database
* @author Yousef Al-Akhali
*/
public class HighScores {

    int maxScores;
    PreparedStatement defaultStatement;
    PreparedStatement insertStatement;
    PreparedStatement updateStatement;
    Connection connection;
    
    int PORT = 3306;
    String DB_NAME = "HighScores";
    String USERNAME = "root";
    String PASSWORD = "strongpassword";

    /**
    * Statements initializer along with the database connection
    * @param maxScores
    * @throws SQLException
    */
    public HighScores(int maxScores) throws SQLException {
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        
            this.maxScores = maxScores;
            String dbURL = "jdbc:mysql"
                + "://localhost:"+PORT+"/"+DB_NAME+"?serverTimezone=UTC&user="+USERNAME+"&password="+PASSWORD;
            connection = DriverManager.getConnection(dbURL);
            String createQuery = "CREATE TABLE HIGHSCORES (Id int(20) primary key auto_increment, NAME varchar(200) not null, SCORE int(20))";
            defaultStatement = connection.prepareStatement(createQuery);
            String insertQuery = "INSERT INTO HIGHSCORES ( NAME, SCORE) VALUES (?, ?)";
            insertStatement = connection.prepareStatement(insertQuery);
            String updatequery = "UPDATE HIGHSCORES SET SCORE=(?) WHERE NAME=(?)";
            updateStatement = connection.prepareStatement(updatequery); 
        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println("A fatal error occurred");
            ex.printStackTrace();
        }
    }

    /**
    * Returning an array of HighScore values from the database, contains a safety net incase there is no database
    * @throws SQLException
    */  
    public ArrayList<HighScore> getHighScores() throws SQLException {
        String query = "SELECT * FROM HIGHSCORES";
        ArrayList<HighScore> highScores = new ArrayList<>();
        Statement theStatement = connection.createStatement();
        ResultSet results;
        try{
            results = theStatement.executeQuery(query);
        }
        catch(SQLException e){
            defaultStatement.executeUpdate();
            results = theStatement.executeQuery(query);
        }
        while (results.next()) {
            String name = results.getString("NAME");
            int score = results.getInt("SCORE");
            highScores.add(new HighScore(name, score));
        }
        sortHighScores(highScores);
        return highScores;
    }
    
    /**
    * Function to increase the score, dependent on a helper function updateScores()
    * @param checkName
    * @throws SQLException
    */
    public void increaseScore(String checkName) throws SQLException {
        String query = "SELECT * FROM HIGHSCORES";
        
        Statement theStatement = connection.createStatement();
        ResultSet results;
        try{
            results = theStatement.executeQuery(query);
        }
        catch(SQLException e){
            defaultStatement.executeUpdate();
            results = theStatement.executeQuery(query);
        }
        while (results.next()) {
            String name = results.getString("NAME");
            int score = results.getInt("SCORE");
            if(checkName.equals(name)){
                updateScores(name,score);
            }
        }
    }

    /**
    * Function to insert the HighScore into the database, dependent on helper function insertScore()
    * @param name
    * @param score
    * @throws SQLException
    */
    public void putHighScore(String name, int score) throws SQLException {
        insertScore(name, score);
    }

    /**
    * Sort the high scores in descending order.
    * @param highScores 
    */
    private void sortHighScores(ArrayList<HighScore> highScores) {
        Collections.sort(highScores, new Comparator<HighScore>() {
            @Override
            public int compare(HighScore t, HighScore t1) {
                return t1.getScore() - t.getScore();
            }
        });
    }

    /**
    * Helper function for putHighScore, sets the insertStatement. Contains a saftey net
    * @param name
    * @param score
    * @throws SQLException
    */
    private void insertScore(String name, int score) throws SQLException {
        try{
            insertStatement.setString(1, name);
            insertStatement.setInt(2, score);
            insertStatement.executeUpdate();
        }
        catch(SQLException e){
            defaultStatement.executeUpdate();
            insertStatement.setString(1, name);
            insertStatement.setInt(2, score);
            insertStatement.executeUpdate();
        }
        
    }
 
    /**
    * Helper function for increaseScore(), sets the updateStatement. Contains a saftey net
    * @param name
    * @param score
    * @throws SQLException
    */
    private void updateScores(String name,int score) throws SQLException {
        try{
            updateStatement.setInt(1, score+1);
            updateStatement.setString(2, name);
            updateStatement.executeUpdate();
        }
        catch(SQLException e){
            defaultStatement.executeUpdate();
            updateStatement.setInt(1, score+1);
            updateStatement.setString(2, name);
            updateStatement.executeUpdate();
        }
    }
}