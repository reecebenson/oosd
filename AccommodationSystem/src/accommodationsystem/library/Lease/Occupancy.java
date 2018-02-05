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
public class Occupancy {
    private static final int UNOCCUPIED = 0;
    private static final int OCCUPIED = 1;
    
    public static String getOccupancy(int statusId) {
        switch(statusId) {
            case UNOCCUPIED:
                return "Unoccupied";
            case OCCUPIED:
                return "Occupied";
            default:
                return "Error";
        }
    }
    
    public static int getId(String occupancy) {
        switch(occupancy) {
            default:
            case "Unoccupied":
                return UNOCCUPIED;
            case "Occupied":
                return OCCUPIED;
        }
    }
    
    public static ObservableList<String> getOccupancies() {
        ObservableList<String> statuses = FXCollections.observableArrayList();
        statuses.addAll("Unoccupied", "Occupied");
        return statuses;
    }
}
