/*
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 */
package accommodationsystem.library;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author simpl_000
 */
public class Database {
    /**
     * Variables
     */
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
    
    public String encryptString(String text) {
        return "";
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
    
    // ObservableList<String> hallSelectorList = FXCollections.observableArrayList("Option 1", "Option 2", "Option 3");
    public static List<Hall> getHalls() {        
        // Check if user is logged in
        if(!User.loggedIn())
            return new ArrayList<>();
        
        // Initialise Leases List
        List<Hall> hallList = new ArrayList<>();
        
        // Check User Permissions
        if(User.hasPermission(Permissions.VIEW_LEASES))
        {
            try {
                String query = "SELECT * FROM `halls`";
                Statement stmt = Database._conn.createStatement();
                ResultSet rS = stmt.executeQuery(query);

                while(rS.next()) {
                    hallList.add(new Hall(rS.getInt("id"), rS.getString("name"), rS.getString("address"), rS.getString("postcode"), rS.getString("phone"), rS.getInt("room_count")));
                }
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        
        return hallList;
    }
    
    public static ObservableList<String> getHallNames(boolean allTag) {      
        // Check if user is logged in
        if(!User.loggedIn())
            return FXCollections.observableArrayList("");
        
        // Initialise Leases List
        ObservableList<String> hallList = FXCollections.observableArrayList();
        
        // Check User Permissions
        if(User.hasPermission(Permissions.VIEW_LEASES))
        {
            // Are we adding the "All" tag?
            if(allTag)
                hallList.add("All");
            
            // Get Hall Names
            Database.getHalls().stream().forEach((h) -> {
                hallList.add(h.getName());
            });
        }
        
        return hallList;
    }
    
    public static List<LeaseData> getLeases() {
        // Check if user is logged in
        if(!User.loggedIn())
            return new ArrayList<>();
        
        // Initialise Leases List
        List<LeaseData> leases = new ArrayList<>();
        
        // Check User Permissions
        if(User.hasPermission(Permissions.VIEW_LEASES))
        {
            try {
                String query = "SELECT * FROM `leases`";
                Statement stmt = Database._conn.createStatement();
                ResultSet rS = stmt.executeQuery(query);

                while(rS.next()) {
                    leases.add(new LeaseData(rS.getInt("hall_id"), rS.getInt("room_number"), rS.getInt("lease_id"), rS.getInt("student_id"), rS.getInt("occupied"), rS.getInt("clean_status")));
                }
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        
        return leases;
    }
    
    public static Student getStudentFromId(Integer studentId) {
        // Check if user is logged in
        if(!User.loggedIn() || studentId == null || studentId == 0)
            return null;
        
        // Initialise Leases List
        Student student = null;
        
        // Check User Permissions
        if(User.hasPermission(Permissions.VIEW_LEASES))
        {
            try {
                String query = "SELECT * FROM `students` WHERE `id` = ?";
                PreparedStatement stmt = Database._conn.prepareStatement(query);
                stmt.setInt(1, studentId);
                ResultSet rS = stmt.executeQuery();

                if(rS.next())
                    student = new Student(studentId, rS.getString("first_name"), rS.getString("last_name"));
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
        
        return student;
    }
    
    public static List<String> getUserPermissions(int userId) throws SQLException {
        // Check if user is logged in
        if(!User.loggedIn())
            return new ArrayList<>();
        
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
