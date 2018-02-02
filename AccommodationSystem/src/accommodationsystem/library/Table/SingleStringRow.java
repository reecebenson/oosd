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
    private final StringProperty permission;
    public SingleStringRow(String perm) {
        this.permission = new SimpleStringProperty(perm);
    }

    public String getPermission() {
        return permission.getValue();
    }
}
