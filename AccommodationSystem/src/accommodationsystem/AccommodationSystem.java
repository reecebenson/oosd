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
        AccommodationSystem.debug("-----------------------------------------------------------------");
        AccommodationSystem.debug("Accommodation System Application - loaded!");
        AccommodationSystem.debug("Application created by Reece Benson, Chris Tapply and Jonas Arud");
        AccommodationSystem.debug("Debugging set to " + (AccommodationSystem.debugging ? "ON" : "OFF"));
        AccommodationSystem.debug("-----------------------------------------------------------------");
        
        // Connect to Database
        AccommodationSystem.debug("Connecting to Database...");
        Database.Connect();
        
        launch(args);
    }
    
    public static void debug(String... print) {
        // Check if we're outputting debug information
        if(!AccommodationSystem.debugging)
            return;
            
        // Loop through the passed arguments and output them
        for(String s: print) {
            System.out.println("[DEBUG]: " + s);
        }
    }
}
