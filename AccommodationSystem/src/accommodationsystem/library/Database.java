/*
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 */
package accommodationsystem.library;

import accommodationsystem.library.Lease.Room;
import accommodationsystem.library.Lease.Hall;
import accommodationsystem.AccommodationSystem;
import accommodationsystem.library.Table.HallRow;
import accommodationsystem.library.Table.RoomRow;
import accommodationsystem.library.Table.StudentRow;
import accommodationsystem.library.Table.UserRow;
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
                AccommodationSystem.debug("Successfully connected to database: '" + url + "'");
            else
                AccommodationSystem.debug("Failed to connect to database.");
            return Database._conn;
        } catch(NullPointerException e) {
            AccommodationSystem.debug("Unable to connect to database: '" + url + "'");
            AccommodationSystem.debug(e.getMessage());
            return null;
        } catch(Exception e) {
            // Handle Error
            return null;
        }
    }
    
    public static boolean validateLogin(String username, String password) {
        // Validate Database Connection
        if(!Database.isConnected()) return false;
        
        // Query Variables
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
            AccommodationSystem.debug("Database Error: " + e.getMessage());
        } finally {
            try {
                if(prepStatement != null) prepStatement.close();
                if(resultSet != null) resultSet.close();
            }catch(Exception x) {}
        }
        return false;
    }
    
    public static List<Hall> getHalls() {        
        // Check if user is logged in
        if(!User.loggedIn())
            return new ArrayList<>();
        
        // Initialise Leases List
        List<Hall> hallList = new ArrayList<>();
        
        // Check User Permissions
        if(User.hasPermission(Permissions.VIEW_LEASES))
        {
            // Query Variables
            Statement pureStatement = null;
            ResultSet resultSet = null;
            
            try {
                String query = "SELECT * FROM `halls`";
                pureStatement = Database._conn.createStatement();
                resultSet = pureStatement.executeQuery(query);

                while(resultSet.next()) {
                    hallList.add(new Hall(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("short_name"), resultSet.getString("address"), resultSet.getString("postcode"), resultSet.getString("phone"), resultSet.getInt("room_count")));
                }
            } catch(SQLException e) {
                AccommodationSystem.debug(e.getMessage());
            } finally {
                try {
                    if(pureStatement != null) pureStatement.close();
                    if(resultSet != null) resultSet.close();
                }catch(Exception x) {}
            }
        }
        
        return hallList;
    }
    public static List<HallRow> getHallsAsRow() {
        // Check if user is logged in
        if(!User.loggedIn())
            return new ArrayList<>();
        
        // Initialise HallRow List
        List<HallRow> hallList = new ArrayList<>();
        
        // Check User Permissions
        if(User.hasPermission(Permissions.ADMIN_PANEL))
        {
            // Get Halls
            for(Hall h: Database.getHalls()) {
                hallList.add(new HallRow(h.getId(), h.getName(), h.getShortName(), h.getAddress(), h.getPostcode(), h.getPhone(), h.getRoomCount()));
            }
        }
        
        return hallList;
    }
    
    public static Hall getHall(int hallId) {
        for(Hall h: Database.getHalls()) {
            if(h.getId() == hallId)
                return h;
        }
        return null;
    }
    
    public static Hall getHall(String name) {
        for(Hall h: Database.getHalls()) {
            if(h.getName().toLowerCase().equals(name.toLowerCase()))
                return h;
            else if(h.getShortName().toLowerCase().equals(name.toLowerCase()))
                return h;
        }
        return null;
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
    
    public static List<Room> getRooms() {        
        // Check if user is logged in
        if(!User.loggedIn())
            return new ArrayList<>();
        
        // Initialise Leases List
        List<Room> roomList = new ArrayList<>();
        
        // Check User Permissions
        if(User.hasPermission(Permissions.VIEW_LEASES))
        {
            // Query Variables
            Statement pureStatement = null;
            ResultSet resultSet = null;
            
            try {
                String query = "SELECT * FROM `rooms`";
                pureStatement = Database._conn.createStatement();
                resultSet = pureStatement.executeQuery(query);

                while(resultSet.next()) {
                    roomList.add(new Room(resultSet.getInt("room_id"), resultSet.getInt("flat_id"), resultSet.getInt("hall_id"), resultSet.getInt("occupied"), resultSet.getInt("clean_status")));
                }
            } catch(SQLException e) {
                AccommodationSystem.debug(e.getMessage());
            } finally {
                try {
                    if(pureStatement != null) pureStatement.close();
                    if(resultSet != null) resultSet.close();
                }catch(Exception x) {}
            }
        }
        
        return roomList;
    }
    
    public static List<RoomRow> getRoomsAsRow() {        
        // Check if user is logged in
        if(!User.loggedIn())
            return new ArrayList<>();
        
        // Initialise Leases List
        List<RoomRow> roomList = new ArrayList<>();
        
        // Check User Permissions
        if(User.hasPermission(Permissions.ADMIN_PANEL))
        {
            // Get Rooms
            for(Room h: Database.getRooms()) {
                roomList.add(new RoomRow(h.getRoomId(), h.getHallId(), h.getOccupied(), h.getCleanStatus()));
            }
        }
        
        return roomList;
    }
    
    public static List<StudentRow> getStudentsAsRow() {        
        // Check if user is logged in
        if(!User.loggedIn())
            return new ArrayList<>();
        
        // Initialise Leases List
        List<StudentRow> studentList = new ArrayList<>();
        
        // Check User Permissions
        if(User.hasPermission(Permissions.ADMIN_PANEL))
        {
            // Query Variables
            Statement pureStatement = null;
            ResultSet resultSet = null;
            
            try {
                String query = "SELECT * FROM `students`";
                pureStatement = Database._conn.createStatement();
                resultSet = pureStatement.executeQuery(query);

                while(resultSet.next()) {
                    studentList.add(new StudentRow(resultSet.getInt("id"), resultSet.getString("first_name"), resultSet.getString("last_name")));
                }
            } catch(SQLException e) {
                AccommodationSystem.debug(e.getMessage());
            } finally {
                try {
                    if(pureStatement != null) pureStatement.close();
                    if(resultSet != null) resultSet.close();
                }catch(Exception x) {}
            }
        }
        
        return studentList;
    }
    
    public static ObservableList<String> getStudentsAsCollection() {
        // Check if user is logged in
        if(!User.loggedIn())
            return FXCollections.observableArrayList();
        
        // Initialise Leases List
        ObservableList<String> studentList = FXCollections.observableArrayList();
        
        // Check User Permissions
        if(User.hasPermission(Permissions.ADMIN_PANEL))
        {
            for(StudentRow s: Database.getStudentsAsRow()) {
                studentList.add(s.getId() + ": " + s.getFirstName() + " " + s.getLastName());
            }
        }
        
        return studentList;
    }
    
    public static Room getRoom(int hallId, int flatId, int roomId) {
        for(Room r: Database.getRooms()) {
            if(r.getHallId() == hallId && r.getFlatId() == flatId && r.getRoomId() == roomId)
                return r;
        }
        return null;
    }
    
    public static List<UserRow> getUsersAsRow() {        
        // Check if user is logged in
        if(!User.loggedIn())
            return new ArrayList<>();
        
        // Initialise Leases List
        List<UserRow> userList = new ArrayList<>();
        
        // Check User Permissions
        if(User.hasPermission(Permissions.ADMIN_PANEL))
        {
            // Query Variables
            Statement pureStatement = null;
            ResultSet resultSet = null;
            
            try {
                String query = "SELECT * FROM `users`";
                pureStatement = Database._conn.createStatement();
                resultSet = pureStatement.executeQuery(query);

                while(resultSet.next()) {
                    userList.add(new UserRow(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("rank")));
                }
            } catch(SQLException e) {
                AccommodationSystem.debug(e.getMessage());
            } finally {
                try {
                    if(pureStatement != null) pureStatement.close();
                    if(resultSet != null) resultSet.close();
                }catch(Exception x) {}
            }
        }
        
        return userList;
    }
    
    public static List<LeaseData> getLeases(int hallNumber) {
        // Check if user is logged in
        if(!User.loggedIn())
            return new ArrayList<>();
        
        // Initialise Leases List
        List<LeaseData> leases = new ArrayList<>();
        
        // Check User Permissions
        if(User.hasPermission(Permissions.VIEW_LEASES))
        {
            // Query Variables
            Statement pureStatement = null;
            PreparedStatement prepStatement = null;
            ResultSet resultSet = null;
            
            try {
                // Are we retrieving a specific Hall's leases?
                if(hallNumber == 0) {
                    String query = "SELECT * FROM `leases`";
                    pureStatement = Database._conn.createStatement();
                    resultSet = pureStatement.executeQuery(query);
                } else {
                    String query = "SELECT * FROM `leases` WHERE `hall_id` = ?";
                    prepStatement = Database._conn.prepareStatement(query);
                    prepStatement.setInt(1, hallNumber);
                    resultSet = prepStatement.executeQuery();
                }

                while(resultSet.next()) {
                    leases.add(new LeaseData(resultSet.getInt("hall_id"), resultSet.getInt("flat_id"), resultSet.getInt("room_id"), resultSet.getInt("lease_id"), resultSet.getInt("student_id")));
                }
            } catch(SQLException e) {
                AccommodationSystem.debug(e.getMessage());
            } finally {
                try {
                    if(pureStatement != null) pureStatement.close();
                    if(prepStatement != null) prepStatement.close();
                    if(resultSet != null) resultSet.close();
                }catch(Exception x) {}
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
            // Query Variables
            PreparedStatement pureStatement = null;
            ResultSet resultSet = null;
            
            try {
                String query = "SELECT * FROM `students` WHERE `id` = ?";
                pureStatement = Database._conn.prepareStatement(query);
                pureStatement.setInt(1, studentId);
                resultSet = pureStatement.executeQuery();

                if(resultSet.next())
                    student = new Student(studentId, resultSet.getString("first_name"), resultSet.getString("last_name"));
            } catch(SQLException e) {
                AccommodationSystem.debug(e.getMessage());
            } catch(Exception e) {
                AccommodationSystem.debug(e.getMessage());
            } finally {
                try {
                    if(pureStatement != null) pureStatement.close();
                    if(resultSet != null) resultSet.close();
                }catch(Exception x) {}
            }
        }
        
        return student;
    }
    
    public static List<String> getUserPermissions(int userId) {
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
                AccommodationSystem.debug("Permission Added to " + userId + " -> " + permName);
            }
        } catch(Exception e) {
            AccommodationSystem.debug("Database Error: " + e.getMessage());
        } finally {
            try {
                if(prepStatement != null) prepStatement.close();
                if(resultSet != null) resultSet.close();
            }catch(Exception x) {}
        }
        
        return userPermissions;
    }
}
