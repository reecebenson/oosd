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
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GUI {
    /**
     * Variables used for classes that extend this Class
     */
    private     Stage       _stage;
    private     BorderPane  _bpane;
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
        this._bpane = new BorderPane();
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
     * @name    getPane
     * @desc    Returns the Border Pane
     * 
     * @return  BorderPane
     */
    public BorderPane getPane() {
        return this._bpane;
    }
    
    /**
     * @name    formId
     * @desc    Returns the Forms ID
     * 
     * @return  String
     */
    public String formId() {
        return null;
    }
    
    /**
     * @name    finalise
     * @desc    Finalises the Scene and Stage
     * 
     * @param   resizable
     * 
     * @throws  Exception
     */
    public void finalise(boolean resizable) throws Exception {
        // Finalise Border Pane
        this._bpane.setPadding(new Insets(0, 0, 0, 0));
        
        // Create our Scene
        Scene me = new Scene(this._bpane, this._sizeX, this._sizeY);
        
        // Import Scene CSS
        try {
            me.getStylesheets().add(this.getClass().getClassLoader().getResource("accommodationsystem/resources/css/" + this.formId() + ".css").toExternalForm());
        }
        catch(Exception x) {
            System.out.println("Error trying to import CSS file for '" + this.formId() + "'");
            System.out.println("'accommdationsystem/resources/css/" + this.formId() + ".css' does not exist for this GUI.");
        }
        
        // Modify Scene Flags
        this._stage.getIcons().add(new Image(this.getClass().getClassLoader().getResourceAsStream("accommodationsystem/resources/images/icon.png")));
        this._stage.setScene(me);
        this._stage.setResizable(resizable);
        this._stage.setMinHeight(this._sizeX);
        this._stage.setMinWidth(this._sizeY);
        
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
    
    /**
     * @name    hide
     * @desc    Hides the built Stage
     * 
     * @throws  Exception
     */
    public void hide() throws Exception {
        if(!this._hasFinalised)
            throw new Exception("GUI has not yet been initialised.");
        
        // Hide our GUI
        this._stage.hide();
    }
    
    /**
     * @name    close
     * @desc    Closes this GUI
     * 
     * @throws  Exception
     */
    public void close() throws Exception {
        if(!this._hasFinalised)
            throw new Exception("GUI has not yet been initialised.");
        
        // Close our GUI
        this._stage.close();
    }
}