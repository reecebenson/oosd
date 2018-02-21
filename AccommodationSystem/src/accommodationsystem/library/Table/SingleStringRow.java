/*
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 */
package accommodationsystem.library.Table;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Reece
 */
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
