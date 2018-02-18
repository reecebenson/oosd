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
import static java.sql.Types.NULL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

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
                User.login(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getInt("allocated_hall"));
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
                    roomList.add(new Room(resultSet.getInt("room_id"), resultSet.getInt("flat_id"), resultSet.getInt("hall_id"), resultSet.getInt("occupied"), resultSet.getInt("clean_status"), resultSet.getInt("monthly_price")));
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
                roomList.add(new RoomRow(h.getRoomId(), h.getHallId(), h.getOccupied(), h.getCleanStatus(), h.getMonthlyPrice()));
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
        if(User.hasPermission(Permissions.VIEW_LEASES))
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
    
    public static List<Student> getAssignedStudents() {
        // Get list of students assigned to a lease
        List<LeaseData> leases = Database.getLeases(0);
        List<Student> occupiedStudents = new ArrayList<>();
        leases.forEach((l) -> {
            if(l.getOccupied()) {
                if(l.getStudentId() != null)
                    occupiedStudents.add(l.getStudent());
            }
        });
        return occupiedStudents;
    }
    
    public static List<Integer> getAssignedStudentsIds() {
        // Get list of students assigned to a lease
        List<LeaseData> leases = Database.getLeases(0);
        List<Integer> occupiedStudents = new ArrayList<>();
        leases.forEach((l) -> {
            if(l.getOccupied()) {
                if(l.getStudentId() != null)
                    occupiedStudents.add(l.getStudentId());
            }
        });
        return occupiedStudents;
    }
    
    public static ObservableList<String> getStudentsAsCollection(boolean allStudents) {
        // Check if user is logged in
        if(!User.loggedIn())
            return FXCollections.observableArrayList();
        
        // Initialise Leases List
        ObservableList<String> studentList = FXCollections.observableArrayList();
        
        // Check User Permissions
        if(User.hasPermission(Permissions.VIEW_LEASES))
        {
            List<Integer> assignedStudents = Database.getAssignedStudentsIds();
            
            // Filter Students
            for(StudentRow s: Database.getStudentsAsRow()) {
                if(allStudents)
                    studentList.add(s.getId() + ": " + s.getFirstName() + " " + s.getLastName());
                else {
                    if(!assignedStudents.contains(s.getId()))
                        studentList.add(s.getId() + ": " + s.getFirstName() + " " + s.getLastName());
                }
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
                    userList.add(new UserRow(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("allocated_hall")));
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
                    String query = "SELECT * FROM `leases` ORDER BY `hall_id` ASC";
                    pureStatement = Database._conn.createStatement();
                    resultSet = pureStatement.executeQuery(query);
                } else {
                    String query = "SELECT * FROM `leases` WHERE `hall_id` = ? ORDER BY `hall_id` ASC";
                    prepStatement = Database._conn.prepareStatement(query);
                    prepStatement.setInt(1, hallNumber);
                    resultSet = prepStatement.executeQuery();
                }

                while(resultSet.next()) {
                    leases.add(new LeaseData(resultSet.getInt("hall_id"), resultSet.getInt("flat_id"), resultSet.getInt("room_id"), resultSet.getInt("lease_id"), resultSet.getInt("student_id"), resultSet.getInt("lease_duration")));
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
        if(!User.loggedIn() || studentId == null)
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
    
    public static boolean updateRoom(LeaseData lease) {
        // Check if user is logged in
        if(!User.loggedIn())
            return false;
        
        // Update Cleaning Status
        boolean updateComplete = false;
        PreparedStatement prepStatement = null;
        String query2 = "UPDATE `rooms` SET `clean_status` = ? WHERE `room_id` = ? AND `flat_id` = ? AND `hall_id` = ?";

        try {
            prepStatement = Database._conn.prepareStatement(query2);
            prepStatement.setInt(1, lease.getCleanStatus());
            prepStatement.setInt(2, lease.getRoom().getRoomId());
            prepStatement.setInt(3, lease.getRoom().getFlatId());
            prepStatement.setInt(4, lease.getRoom().getHallId());
            prepStatement.executeUpdate();
            updateComplete = true;
        } catch(SQLException ex) {
            AccommodationSystem.debug(ex.getMessage());
            updateComplete = false;
        } finally {
            try {
                if(prepStatement != null) prepStatement.close();
            }catch(Exception x) {}
        }
        
        return updateComplete;
    }
    
    public static boolean updateLeaseData(LeaseData lease) {
        // Check if user is logged in
        if(!User.loggedIn())
            return false;
        
        // Update Lease
        boolean updateComplete = false;
        PreparedStatement prepStatement = null, prepStatement2 = null;
        String query = "UPDATE `leases` SET `lease_id` = ?, `student_id` = ?, `room_id` = ?, `flat_id` = ?, `hall_id` = ?, `lease_duration` = ? WHERE `room_id` = ? AND `flat_id` = ? AND `hall_id` = ?";
        String query3 = "UPDATE `rooms` SET `occupied` = ? WHERE `room_id` = ? AND `flat_id` = ? AND `hall_id` = ?";

        try {
            prepStatement = Database._conn.prepareStatement(query);
            prepStatement.setInt(1, lease.getLeaseId());
            prepStatement.setInt(2, lease.getStudent().getStudentId());
            prepStatement.setInt(3, lease.getRoom().getRoomId());
            prepStatement.setInt(4, lease.getRoom().getFlatId());
            prepStatement.setInt(5, lease.getRoom().getHallId());
            prepStatement.setInt(6, 12);
            prepStatement.setInt(7, lease.getRoom().getRoomId());
            prepStatement.setInt(8, lease.getRoom().getFlatId());
            prepStatement.setInt(9, lease.getRoom().getHallId());
            prepStatement.executeUpdate();

            prepStatement2 = Database._conn.prepareStatement(query3);
            prepStatement2.setInt(1, lease.getOccupied() ? 1 : 0);
            prepStatement2.setInt(2, lease.getRoom().getRoomId());
            prepStatement2.setInt(3, lease.getRoom().getFlatId());
            prepStatement2.setInt(4, lease.getRoom().getHallId());
            prepStatement2.executeUpdate();

            updateComplete = true;
        } catch(SQLException ex) {
            AccommodationSystem.debug(ex.getMessage());
            updateComplete = false;
        } finally {
            try {
                if(prepStatement != null) prepStatement.close();
                if(prepStatement2 != null) prepStatement2.close();
            }catch(Exception x) {}
        }
        
        return updateComplete;
    }
    
    public static boolean updateLease(LeaseData lease) {
        // Check if user is logged in
        if(!User.loggedIn())
            return false;
        
        // Update Complete
        boolean updateComplete = false;
        
        // Check User Permissions
        if(User.hasPermission(Permissions.EDIT_LEASE)) {
            System.out.println("UPDATELEASE: lease data -> " + lease.getStudent().getFirstName());
            updateComplete = updateLeaseData(lease);
            System.out.println("UPDATELEASE: lease data (after update) -> " + lease.getStudent().getFirstName());
        } else AccommodationSystem.debug("User does not have permission for 'EDIT_LEASE'");
        
        // Check User Permissions
        if(User.hasPermission(Permissions.EDIT_CLEAN)) {
            System.out.println("UPDATECLEAN: lease data -> " + lease.getStudent().getFirstName());
            updateComplete = updateRoom(lease);
            System.out.println("UPDATECLEAN: lease data (after update) -> " + lease.getStudent().getFirstName());
        } else AccommodationSystem.debug("User does not have permission for 'EDIT_CLEAN'");
        
        if(updateComplete)
            new Alert(Alert.AlertType.INFORMATION, "Lease successfully updated.", ButtonType.OK).showAndWait();
        return updateComplete;
    }
    
 //*   > check Lease Number when updating lease to check that the Lease Number does not already exist*/
    
    public static boolean checkValidLeaseNumber(Integer leaseId) {
        // Check if user is logged in
        if(!User.loggedIn())
            return false;
        
        // Check valid Lease ID
        if(leaseId == null)
            return false;
        
        // Prepare SQL Statement
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM `leases` WHERE `lease_id` = ?";
        
        try {
            prepStatement = Database._conn.prepareStatement(query);
            prepStatement.setInt(1, leaseId);
            resultSet = prepStatement.executeQuery();
            
            return !(resultSet.next());
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
    
    public static boolean deleteLease(LeaseData lease) {
        // Check user is logged in
        if(!User.loggedIn())
            return false;
        
        // Check User Permissions
        if(User.hasPermission(Permissions.DELETE_LEASE)) {
            PreparedStatement prepStatement = null, prepStatement2 = null;
            String query = "UPDATE `leases` SET `student_id` = ?, `lease_id` = ? WHERE `room_id` = ? AND `flat_id` = ? AND `hall_id` = ?";
            String query2 = "UPDATE `rooms` SET `occupied` = ? WHERE `room_id` = ? AND `flat_id` = ? AND `hall_id` = ?";
            
            try {
                prepStatement = Database._conn.prepareStatement(query);
                prepStatement.setNull(1, NULL);
                prepStatement.setNull(2, NULL);
                prepStatement.setInt(3, lease.getRoom().getRoomId());
                prepStatement.setInt(4, lease.getRoom().getFlatId());
                prepStatement.setInt(5, lease.getRoom().getHallId());
                prepStatement.executeUpdate();
                
                prepStatement2 = Database._conn.prepareStatement(query2);
                prepStatement2.setInt(1, 0);
                prepStatement2.setInt(2, lease.getRoom().getRoomId());
                prepStatement2.setInt(3, lease.getRoom().getFlatId());
                prepStatement2.setInt(4, lease.getRoom().getHallId());
                prepStatement2.executeUpdate();
                
                return true;
            } catch(SQLException ex) {
                AccommodationSystem.debug(ex.getMessage());
            } finally {
                try {
                    if(prepStatement != null) prepStatement.close();
                    if(prepStatement2 != null) prepStatement2.close();
                }catch(Exception x) {}
            }
        }
        
        return false;
    }
    
    public static boolean createStudent(String first, String last) {
        // Check user is logged in
        if(!User.loggedIn())
            return false;
        
        // Check User Permissions
        if(User.hasPermission(Permissions.ADMIN_PANEL)) {
            PreparedStatement prepStatement = null;
            String query = "INSERT INTO `students` (`first_name`, `last_name`) VALUES (?, ?)";
            
            try {
                prepStatement = Database._conn.prepareStatement(query);
                prepStatement.setString(1, first);
                prepStatement.setString(2, last);
                prepStatement.executeUpdate();
                return true;
            } catch(SQLException ex) {
                AccommodationSystem.debug(ex.getMessage());
            } finally {
                try {
                    if(prepStatement != null) prepStatement.close();
                }catch(Exception x) {}
            }
        }
        
        return false;
    }
    
    public static boolean deleteStudent(Integer studentId) {
        // Check user is logged in
        if(!User.loggedIn())
            return false;
        
        // Check User Permissions
        if(User.hasPermission(Permissions.ADMIN_PANEL)) {
            PreparedStatement prepStatement = null;
            String query = "DELETE FROM `students` WHERE `id` = ?";
            
            try {
                prepStatement = Database._conn.prepareStatement(query);
                prepStatement.setInt(1, studentId);
                prepStatement.executeUpdate();
                return true;
            } catch(SQLException ex) {
                AccommodationSystem.debug(ex.getMessage());
            } finally {
                try {
                    if(prepStatement != null) prepStatement.close();
                }catch(Exception x) {}
            }
        }
        
        return false;
    }
}
