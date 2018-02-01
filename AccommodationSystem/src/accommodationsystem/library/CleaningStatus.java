/*
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 */
package accommodationsystem.library;

/**
 *
 * @author simpl_000
 */
public class CleaningStatus {
    public static final int OFFLINE = 0;
    public static final int CLEAN = 1;
    public static final int DIRTY = 2;
    
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
}
