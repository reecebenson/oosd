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
public class Row {
    private StringProperty permission;
    public Row(String perm) {
        this.permission = new SimpleStringProperty(perm);
    }

    public String getPermission() {
        return permission.getValue();
    }
}
