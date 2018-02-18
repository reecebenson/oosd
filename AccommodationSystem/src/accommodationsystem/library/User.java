/*
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 */
package accommodationsystem.library;

import accommodationsystem.AccommodationSystem;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author simpl_000
 */
public class User {
    /**
     * Variables
     */
    private static boolean      _loggedIn = false;
    private static int          _userId = -1;
    private static String       _username = null;
    private static String       _password = null;
    private static int          _userhall = -1;
    private static List<String> _userperms = new ArrayList<>();
    
    public static boolean loggedIn() {
        return User._loggedIn;
    }
    
    public static void login(int userId, String username, String password, int allocatedHall) {
        // Update Data
        User._userId   = userId;
        User._username = username;
        User._password = password;
        User._userhall = allocatedHall;
        User._loggedIn = true;
        
        // Retrieve User Permissions
        try {
            User._userperms = Database.getUserPermissions(User._userId);
        } catch(Exception e) {
            AccommodationSystem.debug("Unable to get " + username + "'s Permissions from Database:\n" + e.getMessage());
            User.logout();
        }
    }
    
    public static void logout() {
        // Update Data
        User._username = null;
        User._password = null;
        User._userhall = -1;
        User._loggedIn = false;
        User._userperms = new ArrayList<>();
    }
    
    public static boolean hasPermission(String permission) {
        return User._userperms.contains(permission);
    }

    public static int getUserId() {
        return _userId;
    }
    
    public static String getUsername() {
        return User._username;
    }
    
    public static int getAllocatedHall() {
        return User._userhall;
    }
    
    public static String getPassword() {
        return User._password;
    }
    
    public static List<String> getPermissions() {
        return User._userperms;
    }
}
