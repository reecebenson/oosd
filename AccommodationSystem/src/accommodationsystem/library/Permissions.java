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
     * Permissions
     */
    public static final String LOGIN = "LOGIN";
    public static final String MANAGE_OFFLINE = "MANAGE_OFFLINE";
    public static final String MANAGE_LEASES = "MANAGE_LEASES";
    public static final String VIEW_LEASES = "VIEW_LEASES";
    public static final String UPDATE_LEASES = "UPDATE_LEASES";
    public static final String DELETE_LEASE = "DELETE_LEASE";
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
