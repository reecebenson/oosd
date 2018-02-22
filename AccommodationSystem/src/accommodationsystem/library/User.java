/*
 * UWE Accommodation System
 * BSc Computer Science
 * 
 * Developers:
 * - Reece Benson
 * - Chris Tapply
 * - Jonas Arud
 */
package accommodationsystem.library;

import accommodationsystem.AccommodationSystem;
import java.util.ArrayList;
import java.util.List;

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
    
    /**
     * @name    loggedIn
     * @desc    Return the login state of the current user
     * 
     * @return  boolean
     */
    public static boolean loggedIn() {
        return User._loggedIn;
    }
    
    /**
     * @name    login
     * @desc    Attempt to login a user using credentials
     * 
     * @param   userId
     * @param   username
     * @param   password
     * @param   allocatedHall 
     */
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
    
    /**
     * @name    logout
     * @desc    Log a user out
     */
    public static void logout() {
        // Update Data
        User._username = null;
        User._password = null;
        User._userhall = -1;
        User._loggedIn = false;
        User._userperms = new ArrayList<>();
    }
    
    /**
     * @name    hasPermission
     * @desc    Check against the users permission to see if the specified permission exists
     * 
     * @param   permission
     * @return  boolean
     */
    public static boolean hasPermission(String permission) {
        return User._userperms.contains(permission);
    }

    /**
     * @name    getUserId
     * @desc    Retrieve User ID
     * 
     * @return  int
     */
    public static int getUserId() {
        return _userId;
    }
    
    /**
     * @name    getUsername
     * @desc    Retrieve Username
     * 
     * @return  String
     */
    public static String getUsername() {
        return User._username;
    }
    
    /**
     * @name    getAllocatedHall
     * @desc    Retrieve Allocated Hal
     * 
     * @return  int
     */
    public static int getAllocatedHall() {
        return User._userhall;
    }
    
    /**
     * @name    getPassword
     * @desc    Retrieve Password
     * 
     * @return  String
     */
    public static String getPassword() {
        return User._password;
    }
    
    /**
     * @name    getPermissions
     * @desc    Retrieve Permissions as a List
     * @return  List<String>
     */
    public static List<String> getPermissions() {
        return User._userperms;
    }
}
