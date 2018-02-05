/*
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 */
package accommodationsystem.gui;

/**
 * TODO:
 * 
 * - Import all available rooms into `rooms` table in database
 * - When updating Hall Name: Flat ID and Room ID should be set to empty, "Update" button should be disabled
 * - When updating Flat ID: Room ID should be set to empty, "Update" button should be disabled
 * - When updating values: check against database for clashes against leases existing for room etc
 * - When updating values: update values according to database
 * 
 * - Manager:
 *   > able to edit Lease Number and Student Name
 *   > able to edit the occupancy state
 *   > check Lease Number when updating lease to check that the Lease Number does not already exist
 * 
 * - Warden:
 *   > able to change the cleaning status (only)
 */

import accommodationsystem.AccommodationSystem;
import accommodationsystem.bases.GUI;
import accommodationsystem.library.Lease.CleaningStatus;
import accommodationsystem.library.Database;
import accommodationsystem.library.Lease.Hall;
import accommodationsystem.library.Lease.Occupancy;
import accommodationsystem.library.LeaseData;
import accommodationsystem.library.Permissions;
import accommodationsystem.library.User;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author simpl_000
 */
public class ViewLease extends GUI {
    /**
     * Variables
     */
    // Panes
    HBox headerBox = new HBox();
    GridPane contentBox = new GridPane();
    // Lease Data
    LeaseData leaseData;
    // Elements
    Button btnUpdate;
    ComboBox hallName,
            flatNumber,
            roomNumber,
            occupancy,
            cleanStatus;
    
    /**
     * @name    buildHeader
     * @desc    Create, style and build the Header of the "ViewPermissions" GUI
     */
    private void buildHeader() {
        /**
         * Declare Elements
         */
        // Images
        Image logo = new Image(this.getClass().getClassLoader().getResourceAsStream("accommodationsystem/resources/images/logo.png"));
        ImageView logoView = new ImageView(logo);
        
        /**
         * Style Elements
         */
        // Panes
        headerBox.setPadding(new Insets(25, 25, 25, 25));
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setId("topBox");
        // Images
        logoView.setPreserveRatio(true);
        logoView.setFitWidth(250);
        
        /**
         * Compile Elements
         */
        headerBox.getChildren().add(logoView);
    }
    
    /**
     * @name    buildContent
     * @desc    Create, style and build the Content of the "ViewPermissions" GUI
     */
    private void buildContent() {
        /**
         * Declare Elements
         */
        Label lblHallName,
                lblFlatNumber,
                lblRoomNumber,
                lblOccupancy,
                lblCleanStatus;
        
        /**
         * Initialise Elements
         */
        // Button
        btnUpdate = new Button();
        // Label
        lblHallName = new Label("Hall Name:");
        lblFlatNumber = new Label("Flat Number:");
        lblRoomNumber = new Label("Room Number:");
        lblOccupancy = new Label("Occupancy:");
        lblCleanStatus = new Label("Clean Status");
        // ComboBox
        hallName = new ComboBox(Database.getHallNames(false));
        flatNumber = new ComboBox(this.leaseData.getHall().getFlatsAsCollection());
        roomNumber = new ComboBox(this.leaseData.getHall().getRoomsAsCollection(this.leaseData.getFlatNumber()));
        occupancy = new ComboBox(Occupancy.getOccupancies());
        cleanStatus = new ComboBox(CleaningStatus.getStatuses());
        
        /**
         * Style Elements
         */
        // Headers
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(20, 20, 20, 20));
        contentBox.setVgap(10d);
        contentBox.setHgap(10d);
        contentBox.setId("contentBox");
        // Labels
        GridPane.setHalignment(lblHallName, HPos.RIGHT);
        GridPane.setHalignment(lblFlatNumber, HPos.RIGHT);
        GridPane.setHalignment(lblRoomNumber, HPos.RIGHT);
        GridPane.setHalignment(lblOccupancy, HPos.RIGHT);
        GridPane.setHalignment(lblCleanStatus, HPos.RIGHT);
        // hallName ComboBox
        hallName.setStyle("-fx-text-fill: white");
        hallName.setValue(this.leaseData.getHallName());
        hallName.setPrefWidth(225.0);
        hallName.setPadding(new Insets(11, 5, 11, 5));
        hallName.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> hallName_Changed(options, oldValue, newValue));
        // hallNumber ComboBox
        flatNumber.setStyle("-fx-text-fill: white");
        flatNumber.setValue(this.leaseData.getFlatNumber());
        flatNumber.setPrefWidth(225.0);
        flatNumber.setPadding(new Insets(11, 5, 11, 5));
        flatNumber.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> flatNumber_Changed(options, oldValue, newValue));
        // roomNumber ComboBox
        roomNumber.setStyle("-fx-text-fill: white");
        roomNumber.setValue(this.leaseData.getRoomNumber());
        roomNumber.setPrefWidth(225.0);
        roomNumber.setPadding(new Insets(11, 5, 11, 5));
        roomNumber.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> roomNumber_Changed(options, oldValue, newValue));
        // occupancy ComboBox
        occupancy.setStyle("-fx-text-fill: white");
        occupancy.setValue(this.leaseData.getOccupiedStatus());
        occupancy.setPrefWidth(225.0);
        occupancy.setPadding(new Insets(11, 5, 11, 5));
        // cleanStatus ComboBox
        cleanStatus.setStyle("-fx-text-fill: white");
        cleanStatus.setValue(this.leaseData.getCleanStatusName());
        cleanStatus.setPrefWidth(225.0);
        cleanStatus.setPadding(new Insets(11, 5, 11, 5));
        // Update Button
        btnUpdate.setText("Update Lease");
        btnUpdate.setDefaultButton(true);
        btnUpdate.setMaxWidth(Double.MAX_VALUE);
        btnUpdate.setAlignment(Pos.CENTER);
        btnUpdate.setOnAction((ActionEvent e) -> btnUpdate_Click(e));
        
        /**
         * Style Elements in accordance to Permissions
         */
        if(!User.hasPermission(Permissions.EDIT_LEASE)) {
            hallName.setDisable(true);
            flatNumber.setDisable(true);
            roomNumber.setDisable(true);
        }
        
        if(!User.hasPermission(Permissions.EDIT_CLEAN)) {
            occupancy.setDisable(true);
            cleanStatus.setDisable(true);
        }
        
        /**
         * Compile Elements
         */
        // Hall Name
        contentBox.add(lblHallName, 0, 0);
        contentBox.add(hallName, 1, 0);
        // Hall ID
        contentBox.add(lblFlatNumber, 0, 1);
        contentBox.add(flatNumber, 1, 1);
        // Room Number
        contentBox.add(lblRoomNumber, 0, 2);
        contentBox.add(roomNumber, 1, 2);
        // Occupancy
        contentBox.add(lblOccupancy, 0, 3);
        contentBox.add(occupancy, 1, 3);
        // Clean Status
        contentBox.add(lblCleanStatus, 0, 4);
        contentBox.add(cleanStatus, 1, 4);
        // Update Button
        contentBox.add(btnUpdate, 1, 5);
    }
    
    /**
     * @name    hallName_Changed
     * @desc    Handles when the ComboBox value changes
     * 
     * @param   options
     * @param   oldValue
     * @param   newValue 
     */
    private void hallName_Changed(Object options, Object oldValue, Object newValue) {
        /**
         * Check if values are null to avoid errors
         */
        if(newValue == null) return;
        
        /**
         * Cast Objects to Strings
         */
        String newVal = (String)newValue;
        
        /**
         * Debug
         */
        AccommodationSystem.debug("New Value: " + newVal + " ---> " + hallName.getSelectionModel().getSelectedIndex());
        
        // Disable Update Button
        btnUpdate.setDisable(true);
        flatNumber.setDisable(false);
        roomNumber.setDisable(true);
        
        // Clear items from our other ComboBoxes
        flatNumber.getItems().clear();
        
        // Set our combo box values to null
        flatNumber.setValue(null);
        
        // Populate ComboBoxes
        Hall tempHall = Database.getHall(hallName.getSelectionModel().getSelectedIndex() + 1);
        flatNumber.setItems(tempHall.getFlatsAsCollection());
        System.out.println("populated flats");
    }
    
    /**
     * @name    flatNumber_Changed
     * @desc    Handles when the ComboBox value changes
     * 
     * @param   options
     * @param   oldValue
     * @param   newValue 
     */
    private void flatNumber_Changed(Object options, Object oldValue, Object newValue) {
        /**
         * Check if values are null to avoid errors
         */
        if(newValue == null) return;
        
        /**
         * Cast Objects to Strings
         */
        int newVal = (int)newValue;
        
        /**
         * Debug
         */
        AccommodationSystem.debug("New Value: " + newVal + " ---> " + hallName.getSelectionModel().getSelectedIndex());
        
        // Disable Update Button
        btnUpdate.setDisable(true);
        roomNumber.setDisable(false);
        
        // Clear items from our other ComboBoxes
        roomNumber.getItems().clear();
        
        // Set our combo box values to null
        roomNumber.setValue(null);
        
        // Populate ComboBoxes
        Hall tempHall = Database.getHall(hallName.getSelectionModel().getSelectedIndex() + 1);
        roomNumber.setItems(tempHall.getRoomsAsCollection((int)flatNumber.getSelectionModel().getSelectedItem()));
        System.out.println("populated rooms");
    }
    
    /**
     * @name    roomNumber_Changed
     * @desc    Handles when the ComboBox value changes
     * 
     * @param   options
     * @param   oldValue
     * @param   newValue 
     */
    private void roomNumber_Changed(Object options, Object oldValue, Object newValue) {
        /**
         * Check if values are null to avoid errors
         */
        if(newValue == null) return;
        
        /**
         * Cast Objects to Strings
         */
        int newVal = (int)newValue;
        
        /**
         * Debug
         */
        AccommodationSystem.debug("New Value: " + newVal + " ---> " + hallName.getSelectionModel().getSelectedIndex());
        
        // Enable Update Lease button
        btnUpdate.setDisable(false);
    }
    
    private void btnUpdate_Click(ActionEvent event) {
        AccommodationSystem.debug("update button pressed!1!11!!1!1!11!!!");
    }
    
    /**
     * @name    Default Constructor
     * @desc    Initialise the ViewLease GUI
     * 
     * @param   lease
     * 
     * @throws  Exception 
     */
    public ViewLease(LeaseData lease) throws Exception {
        /**
         * Debug
         */
        AccommodationSystem.debug("Loaded '" + this.getClass().getName() + "'");
        
        /**
         * Set Variables
         */
        this.leaseData = lease;
        
        /**
         * Build "ViewPermissions" GUI
         */
        this.buildHeader();
        this.buildContent();
        
        /**
         * Add our GUI Elements (hierarchy)
         */
        super.getPane().setTop(headerBox);
        super.getPane().setCenter(contentBox);
        
        /**
         * Finalise our GUI
         */
        super.setTitle("View Lease: " + String.valueOf(this.leaseData.getLeaseId()));
        super.setSize(400, 600);
        super.finalise(false);
    }
    
    @Override
    public String formId() {
        return this.getClass().getSimpleName();
    }
}
