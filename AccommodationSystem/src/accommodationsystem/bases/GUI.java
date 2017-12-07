/*
 * UWE Accommodation System
 * BSc Computer Science
 * 
 * Developers:
 * - Reece Benson
 * - Chris Tapply
 * - Jonas Arud
 */
package accommodationsystem.bases;

import accommodationsystem.AccommodationSystem;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GUI {
    /**
     * Variables used for classes that extend this Class
     */
    private     Stage       _stage;
    private     StackPane   _stack;
    private     String      _title;
    private     int         _sizeX;
    private     int         _sizeY;
    private     boolean     _hasFinalised = false;
    
    /**
     * @name    GUI
     * @desc    Initialises the variables used within this Class
     */
    public GUI() {
        this._stage = new Stage();
        this._stack = new StackPane();
    }
    
    /**
     * @name    addItem
     * @desc    Add an item to the Scene
     * 
     * @param item
     * @throws Exception 
     */
    public void addItem(Control item) throws Exception {
        // Verify that our Stack Pane has been initialised
        if(_stack == null)
            throw new Exception("Unable to add item to GUI for " + this.getClass().getName());
        
        // Add our item to the Stack Pane
        this._stack.getChildren().add(item);
    }
    
    /**
     * @name    setTitle
     * @desc    Sets the title of the Stage
     * 
     * @param   title
     */
    public void setTitle(String title) {
        // Set our variables
        this._title = title;
        this._stage.setTitle(this._title);
        
        // Debug
        AccommodationSystem.debug("Set Title: '" + title + "'");
    }
    
    /**
     * @name    setSize
     * @desc    Set the size of the Stage
     * 
     * @param   x
     * @param   y 
     */
    public void setSize(int x, int y) {
        // Set our variables
        this._sizeX = x;
        this._sizeY = y;
        
        // Debug
        AccommodationSystem.debug("Set Size: 'X: " + String.valueOf(x) + "', 'Y: " + String.valueOf(y) + "'");
    }
    
    /**
     * @name    finalise
     * @desc    Finalises the Scene and Stage
     * 
     * @throws  Exception
     */
    public void finalise() throws Exception {
        // Create our Scene
        Scene me = new Scene(this._stack, this._sizeX, this._sizeY);
        this._stage.setScene(me);
        
        // Finalise our GUI
        this._hasFinalised = true;
    }
    
    /**
     * @name    show
     * @desc    Shows the built Stage
     * 
     * @throws  Exception
     */
    public void show() throws Exception {
        // Check that we have finalised our GUI
        if(!this._hasFinalised)
            throw new Exception("GUI has not yet been finalised and has tried to be shown [" + this.getClass().getName() + "]");
        
        // Show our GUI
        this._stage.show();
    }
}