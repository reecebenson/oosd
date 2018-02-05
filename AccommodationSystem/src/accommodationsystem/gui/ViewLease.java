/*
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 */
package accommodationsystem.gui;

import accommodationsystem.AccommodationSystem;
import accommodationsystem.bases.GUI;
import accommodationsystem.library.Database;
import accommodationsystem.library.LeaseData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
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
        ComboBox hallName,
                hallId,
                roomNumber,
                occupancy,
                cleanStatus;
        
        /**
         * Initialise Elements
         */
        hallName = new ComboBox(Database.getHallNames(true));
        hallId = new ComboBox();
        roomNumber = new ComboBox();
        occupancy = new ComboBox();
        cleanStatus = new ComboBox();
        
        /**
         * Style Elements
         */
        // Headers
        contentBox.setPadding(new Insets(20, 20, 20, 20));
        contentBox.setVgap(10d);
        contentBox.setHgap(10d);
        contentBox.setId("contentBox");
        // hallName ComboBox
        hallName.setStyle("-fx-text-fill: white");
        hallName.setValue(this.leaseData.getHallName());
        hallName.setPrefWidth(150.0);
        hallName.setPadding(new Insets(11, 5, 11, 5));
        // hallNumber ComboBox
        hallId.setStyle("-fx-text-fill: white");
        hallId.setValue(this.leaseData.getHallId());
        hallId.setPrefWidth(150.0);
        hallId.setPadding(new Insets(11, 5, 11, 5));
        // roomNumber ComboBox
        roomNumber.setStyle("-fx-text-fill: white");
        roomNumber.setValue(this.leaseData.getRoomNumber());
        roomNumber.setPrefWidth(150.0);
        roomNumber.setPadding(new Insets(11, 5, 11, 5));
        // occupancy ComboBox
        occupancy.setStyle("-fx-text-fill: white");
        occupancy.setValue(this.leaseData.getOccupiedStatus());
        occupancy.setPrefWidth(150.0);
        occupancy.setPadding(new Insets(11, 5, 11, 5));
        // cleanStatus ComboBox
        cleanStatus.setStyle("-fx-text-fill: white");
        cleanStatus.setValue(this.leaseData.getCleanStatusName());
        cleanStatus.setPrefWidth(150.0);
        cleanStatus.setPadding(new Insets(11, 5, 11, 5));
        
        /**
         * Compile Elements
         */
        contentBox.add(hallId, 0, 0);
        contentBox.add(hallName, 0, 1);
        contentBox.add(roomNumber, 0, 2);
        contentBox.add(occupancy, 0, 3);
        contentBox.add(cleanStatus, 0, 4);
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
