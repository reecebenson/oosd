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

public class Occupancy {
    /**
     * Variables
     */
    private static final int UNOCCUPIED = 0;
    private static final int OCCUPIED = 1;
    
    /**
     * @name    getOccupancy
     * @desc    Retrieve the occupancy from the ID specified
     * 
     * @param   occupancyId
     * @return  String
     */
    public static String getOccupancy(int occupancyId) {
        switch(occupancyId) {
            case UNOCCUPIED:
                return "Unoccupied";
            case OCCUPIED:
                return "Occupied";
            default:
                return "Error";
        }
    }
    
    /**
     * @name    getId
     * @desc    Retrieve the ID from the occupancy specified
     * @param   occupancy
     * @return  int
     */
    public static int getId(String occupancy) {
        switch(occupancy) {
            default:
            case "Unoccupied":
                return UNOCCUPIED;
            case "Occupied":
                return OCCUPIED;
        }
    }
    
    /**
     * @name    getOccupancies
     * @desc    Builds an ObservableList with the available occupancies
     * @return  ObservableList<String>
     */
    public static ObservableList<String> getOccupancies() {
        ObservableList<String> statuses = FXCollections.observableArrayList();
        statuses.addAll("Unoccupied", "Occupied");
        return statuses;
    }
}
