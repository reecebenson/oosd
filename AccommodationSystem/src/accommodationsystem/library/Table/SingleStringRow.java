/*
 * UWE Accommodation System
 * BSc Computer Science
 * 
 * Developers:
 * - Reece Benson
 * - Chris Tapply
 * - Jonas Arud
 */
package accommodationsystem.library.Table;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SingleStringRow {
    /**
     * Variables
     */
    private final StringProperty permission;
    
    /**
     * @name    SingleStringRow
     * @desc    Default Constructor
     * @param   perm 
     */
    public SingleStringRow(String perm) {
        this.permission = new SimpleStringProperty(perm);
    }

    /**
     * @name    getPermission
     * @desc    Retrieve Permission Value
     * 
     * @return  String
     */
    public String getPermission() {
        return permission.getValue();
    }
}
