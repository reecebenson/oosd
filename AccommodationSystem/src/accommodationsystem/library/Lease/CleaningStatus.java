/*
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 */
package accommodationsystem.library.Lease;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author simpl_000
 */
public class CleaningStatus {
    private static final int OFFLINE = 0;
    private static final int CLEAN = 1;
    private static final int DIRTY = 2;
    
    public static String getStatus(int statusId) {
        switch(statusId) {
            case OFFLINE:
                return "Offline";
            case CLEAN:
                return "Clean";
            case DIRTY:
                return "Dirty";
            default:
                return "Error";
        }
    }
    
    public static ObservableList<String> getStatuses() {
        ObservableList<String> statuses = FXCollections.observableArrayList();
        statuses.addAll("Offline", "Clean", "Dirty");
        return statuses;
    }
}
