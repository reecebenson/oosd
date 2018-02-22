/*
 * UWE Accommodation System
 * BSc Computer Science
 * 
 * Developers:
 * - Reece Benson
 * - Chris Tapply
 * - Jonas Arud
 */
package accommodationsystem.library.Lease;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CleaningStatus {
    /**
     * Variables
     */
    private static final int OFFLINE = 0;
    private static final int CLEAN = 1;
    private static final int DIRTY = 2;
    
    /**
     * @name    getStatus
     * @desc    Retrieve the status from the ID specified
     * 
     * @param   statusId
     * @return  String
     */
    public static String getStatus(int statusId) {
        switch(statusId) {
            case OFFLINE: return "Offline";
            case CLEAN:   return "Clean";
            case DIRTY:   return "Dirty";
            default:      return "Error";
        }
    }
    
    /**
     * @name    getId
     * @desc    Retrieve the ID from the status specified
     * @param   name
     * @return  int
     */
    public static int getId(String name) {
        switch(name) {
            default:
            case "Offline": return OFFLINE;
            case "Clean":   return CLEAN;
            case "Dirty":   return DIRTY;
        }
    }
    
    /**
     * @name    getStatuses
     * @desc    Builds an ObservableList with the available statuses
     * @return  ObservableList<String>
     */
    public static ObservableList<String> getStatuses() {
        ObservableList<String> statuses = FXCollections.observableArrayList();
        statuses.addAll("Offline", "Clean", "Dirty");
        return statuses;
    }
}
