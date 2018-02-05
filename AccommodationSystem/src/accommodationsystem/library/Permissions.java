/*
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 */
package accommodationsystem.library;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author simpl_000
 */
public class Permissions {
    /**
     * Ranks (currently useless due to permissions per user?)
     */
    public static final int WARDEN = 1;
    public static final int MANAGER = 2;
    public static final int ADMINISTRATOR = 3;
    
    /**
     * Permissions
     */
    public static final String LOGIN = "LOGIN";
    public static final String VIEW_LEASES = "VIEW_LEASES";
    public static final String EDIT_LEASE = "EDIT_LEASE";
    public static final String EDIT_CLEAN = "EDIT_CLEAN";
    public static final String ADMIN_PANEL = "ADMIN_PANEL";
    
    public static List<String> getPermissions() {
        List<String> perms = new ArrayList<>();
        for(Field p: Permissions.class.getDeclaredFields()) {
            if(p.getType() == "".getClass()) {
                perms.add(p.getName());
            }
        }
        return perms;
    }
}
