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
    private final IntegerProperty userId;
    private final StringProperty userName;
    private final StringProperty userPass;
    private final StringProperty userRank;
    private final StringProperty userPerms;

    public UserRow(Integer userId, String userName, String userPass, String userRank) {
        this.userId = new SimpleIntegerProperty(userId);
        this.userName = new SimpleStringProperty(userName);
        this.userPass = new SimpleStringProperty(userPass);
        this.userRank = new SimpleStringProperty(userRank);
        
        // Get User Permissions
        this.userPerms = new SimpleStringProperty(String.join(", ", Database.getUserPermissions(userId)));
    }

    public Integer getId() {
        return userId.intValue();
    }

    public String getUsername() {
        return userName.getValue();
    }

    public String getPassword() {
        return userPass.getValue();
    }

    public String getRank() {
        return userRank.getValue();
    }

    public String getPerms() {
        return userPerms.getValue();
    }
}
