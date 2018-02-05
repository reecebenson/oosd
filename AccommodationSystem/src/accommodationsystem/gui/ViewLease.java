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
 */

import accommodationsystem.AccommodationSystem;
import accommodationsystem.bases.GUI;
import accommodationsystem.library.Lease.CleaningStatus;
import accommodationsystem.library.Database;
import accommodationsystem.library.Lease.Occupancy;
import accommodationsystem.library.LeaseData;
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
        Button btnUpdate;
        Label lblHallName,
                lblFlatNumber,
                lblRoomNumber,
                lblOccupancy,
                lblCleanStatus;
        ComboBox hallName,
                flatNumber,
                roomNumber,
                occupancy,
                cleanStatus;
        
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
        roomNumber = new ComboBox(this.leaseData.getHall().getRoomsAsCollection());
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
        // hallNumber ComboBox
        flatNumber.setStyle("-fx-text-fill: white");
        flatNumber.setValue(this.leaseData.getFlatNumber());
        flatNumber.setPrefWidth(225.0);
        flatNumber.setPadding(new Insets(11, 5, 11, 5));
        // roomNumber ComboBox
        roomNumber.setStyle("-fx-text-fill: white");
        roomNumber.setValue(this.leaseData.getRoomNumber());
        roomNumber.setPrefWidth(225.0);
        roomNumber.setPadding(new Insets(11, 5, 11, 5));
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
