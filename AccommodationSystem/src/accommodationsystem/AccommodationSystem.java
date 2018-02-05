/*
 * UWE Accommodation System
 * BSc Computer Science
 * 
 * Developers:
 * - Reece Benson
 * - Chris Tapply
 * - Jonas Arud
 */
package accommodationsystem;

import accommodationsystem.gui.Login;
import accommodationsystem.library.Database;
import javafx.application.Application;
import javafx.stage.Stage;

public class AccommodationSystem extends Application {
    private static boolean debugging = true;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Show our Login GUI
        new Login(false).show();
    }

    /**
     * @name    main
     * @desc    Entry point of the Java Application
     * 
     * @param   args (the command line arguments)
     */
    public static void main(String[] args) {
        // Print out Initial Debugging Information
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Accommodation System Application - loaded!");
        System.out.println("Application created by Reece Benson, Chris Tapply and Jonas Arud");
        System.out.println("Debugging set to " + (AccommodationSystem.debugging ? "ON" : "OFF"));
        System.out.println("-----------------------------------------------------------------");
        
        // Connect to Database
        AccommodationSystem.debug("Connecting to Database...");
        Database.Connect();
        
        launch(args);
    }
    
    /**
     * @name    debug
     * @desc    Used instead of SOUT so that debug output is only displayed when debugging is set to true
     * 
     * @param print 
     */
    public static void debug(String... print) {
        // Check if we're outputting debug information
        if(!AccommodationSystem.isDebugging())
            return;
            
        // Loop through the passed arguments and output them
        for(String s: print) {
            System.out.println("[DEBUG]: " + s);
        }
    }
    
    /**
     * @name    isDebugging
     * @desc    Returns a boolean to whether the application is debugging or not
     * 
     * @return  boolean
     */
    public static boolean isDebugging() {
        return AccommodationSystem.debugging;
    }
}
