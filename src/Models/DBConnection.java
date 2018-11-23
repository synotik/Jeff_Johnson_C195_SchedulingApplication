/*
 * 
 * Created by Jeff Johnson - November 2018
 * 
 */
package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    private static Connection connection;
    
    public DBConnection() {
        
    }
    
    public static Connection getConnection(){
        
        return connection;
        
    }
    
    public static void connect() throws ClassNotFoundException {
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://52.206.157.109/U04iil", "U04iil", "53688252129");
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
        
    }
    
    public static void disconnect() {
        
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Disconnecting from database failed: " + e.getMessage());
        }
        
    }
}
