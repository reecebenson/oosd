/*
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 */
package accommodationsystem.library;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author simpl_000
 */
public class Database {
    private static Connection _conn;
    
    public static boolean isConnected() {
        // Connection Status
        try {
            return !Database._conn.isClosed();
        } catch(Exception e) {
            return false;
        }
    }
    
    public static Connection Connect() {
        // Validate Database Connection
        if(Database.isConnected()) return null;
        
        // Connect
        String url = "jdbc:sqlite:src\\uwe.sqlite";
        try {
            Class.forName("org.sqlite.JDBC");
            Database._conn = DriverManager.getConnection(url);
            if(Database.isConnected())
                System.out.println("Successfully connected to database: '" + url + "'");
            else
                System.out.println("Failed to connect to database.");
            return Database._conn;
        } catch(NullPointerException e) {
            System.out.println("Unable to connect to database: '" + url + "'");
            System.out.println(e.getMessage());
            return null;
        } catch(Exception e) {
            // Handle Error
            return null;
        }
    }
    
    public static boolean validateLogin(String username, String password) throws SQLException {
        // Validate Database Connection
        if(!Database.isConnected()) return false;
        
        // Setup Statement
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM `users` WHERE `username` = ? AND `password` = ?";
        
        try {
            prepStatement = Database._conn.prepareStatement(query);
            prepStatement.setString(1, username);
            prepStatement.setString(2, password);
            resultSet = prepStatement.executeQuery();
            if(resultSet.next()) {
                User.login(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getInt("rank"));
                return true;
            } else {
                return false;
            }
        } catch(Exception e) {
            System.out.println("Database Error: " + e.getMessage());
        } finally {
            prepStatement.close();
            resultSet.close();
        }
        return false;
    }
    
    public static List<String> getUserPermissions(int userId) throws SQLException {
        // User Check
        if(!User.loggedIn()) {
            System.out.println("User is not logged in!");
            return new ArrayList<>();
        }
        
        // Get User Permissions
        List<String> userPermissions = new ArrayList<>();
        
        // Prepare SQL Statement
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM `permissions` WHERE `uid` = ?";
        
        try {
            prepStatement = Database._conn.prepareStatement(query);
            prepStatement.setInt(1, userId);
            resultSet = prepStatement.executeQuery();
            
            while(resultSet.next()) {
                String permName = resultSet.getString("name");
                userPermissions.add(permName);
                System.out.println("Permission Added to " + userId + " -> " + permName);
            }
        } catch(Exception e) {
            System.out.println("Database Error: " + e.getMessage());
        } finally {
            prepStatement.close();
            resultSet.close();
        }
        
        return userPermissions;
    }
}
