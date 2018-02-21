/*
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 */
package accommodationsystem.library.Table;

import accommodationsystem.library.Database;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Reece
 */
public class UserRow {
    /**
     * Variables
     */
    private final IntegerProperty userId;
    private final StringProperty userName;
    private final StringProperty userPass;
    private final StringProperty userAllocatedHall;
    private final StringProperty userPerms;

    /**
     * @name    UserRow
     * @desc    Default Constructor
     * @param   userId
     * @param   userName
     * @param   userPass
     * @param   userAllocatedHall 
     */
    public UserRow(Integer userId, String userName, String userPass, String userAllocatedHall) {
        this.userId = new SimpleIntegerProperty(userId);
        this.userName = new SimpleStringProperty(userName);
        this.userPass = new SimpleStringProperty(userPass);
        this.userAllocatedHall = new SimpleStringProperty(userAllocatedHall);
        
        // Get User Permissions
        this.userPerms = new SimpleStringProperty(String.join(",", Database.getUserPermissions(userId)));
    }

    /**
     * @name    getId
     * @desc    Retrieve ID
     * 
     * @return  Integer
     */
    public Integer getId() {
        return userId.intValue();
    }

    /**
     * @name    getUsername
     * @desc    Retrieve Username
     * 
     * @return  String
     */
    public String getUsername() {
        return userName.getValue();
    }

    /**
     * @name    getPassword
     * @desc    Retrieve Password
     * 
     * @return  String
     */
    public String getPassword() {
        return userPass.getValue();
    }

    /**
     * @name    getAllocatedHall
     * @desc    Retrieve AllocatedHall
     * 
     * @return  String
     */
    public String getAllocatedHall() {
        return userAllocatedHall.getValue();
    }

    /**
     * @name    getPerms
     * @desc    Retrieve Permissions as a concatenated string, separated by commas
     * 
     * @return  String
     */
    public String getPerms() {
        return userPerms.getValue();
    }
}
