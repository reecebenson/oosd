/*
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 */
package accommodationsystem.gui;

import accommodationsystem.AccommodationSystem;
import accommodationsystem.bases.GUI;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

/**
 *
 * @author simpl_000
 */
public class Main extends GUI {
    public Main() throws Exception {
        AccommodationSystem.debug("Loaded '" + this.getClass().getName() + "'");
        
        // Elements Used for this GUI
        Button helloWorld   = new Button();
        Button otherButton  = new Button();
        
        // Configure our Elements
        // --> HELLO WORLD
        helloWorld.setText("Hello World");
        helloWorld.setOnAction((ActionEvent event) -> {
            AccommodationSystem.debug("Clicked >> " + event.getSource().toString() + " <<");
            otherButton.setVisible(!otherButton.visibleProperty().getValue());
        });
        
        // --> OTHER BUTTON
        otherButton.setText("Other Button");
        otherButton.setVisible(true);
        otherButton.setOnAction((ActionEvent event) -> {
            AccommodationSystem.debug("Clicked >> " + event.getSource().toString() + " <<");
        });
        
        // Add our GUI Elements (hierarchy)
        super.addItem(helloWorld);
        super.addItem(otherButton);
        
        // Finalise our GUI
        super.setTitle("Main GUI");
        super.setSize(300, 250);
        super.finalise();
    }
}
