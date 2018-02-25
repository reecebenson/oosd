/*
 * UWE Accommodation System
 * BSc Computer Science
 * 
 * Developers:
 * - Reece Benson
 * - Chris Tapply
 * - Jonas Arud
 */
package accommodationsystem.gui;

import accommodationsystem.AccommodationSystem;
import accommodationsystem.bases.GUI;
import accommodationsystem.library.Lease.CleaningStatus;
import accommodationsystem.library.Database;
import accommodationsystem.library.Lease.Hall;
import accommodationsystem.library.Lease.Occupancy;
import accommodationsystem.library.LeaseData;
import accommodationsystem.library.Permissions;
import accommodationsystem.library.Student;
import accommodationsystem.library.User;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ViewLease extends GUI {
    /**
     * Variables
     */
    // Parent
    Browser parent;
    // Panes
    HBox headerBox = new HBox();
    GridPane contentBox = new GridPane();
    // Lease Data
    LeaseData leaseData;
    // Elements
    Button btnUpdate;
    TextField leaseId,
              startDate,
              endDate;
    ComboBox hallName,
             flatNumber,
             roomNumber,
             studentName,
             occupancy,
             cleanStatus;
    SimpleIntegerProperty prevHallId,
                          prevFlatNumber,
                          prevRoomNumber;
    // Flags
    boolean hasChangedRoom = false;
    
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
    private void buildContent(String showType) {
        /**
         * Declare Elements
         */
        Label lblLeaseId,
              lblHallName,
              lblFlatNumber,
              lblRoomNumber,
              lblStudentName,
              lblStartDate,
              lblEndDate,
              lblOccupancy,
              lblCleanStatus;
        
        /**
         * Initialise Elements
         */
        // Button
        btnUpdate = new Button();
        // Label
        lblLeaseId = new Label("Lease ID:");
        lblHallName = new Label("Hall Name:");
        lblFlatNumber = new Label("Flat Number:");
        lblRoomNumber = new Label("Room Number:");
        lblStudentName = new Label("Student Name:");
        lblStartDate = new Label("Start Date:");
        lblEndDate = new Label("End Date:");
        lblOccupancy = new Label("Occupancy:");
        lblCleanStatus = new Label("Clean Status");
        // ComboBox
        leaseId = new TextField();
        hallName = new ComboBox(Database.getHallNames(false));
        flatNumber = new ComboBox(this.leaseData.getHall().getFlatsAsCollection());
        roomNumber = new ComboBox(this.leaseData.getHall().getRoomsAsCollection(this.leaseData.getFlatNumber()));
        if(this.leaseData.getStudent() != null)
            studentName = new ComboBox(Database.getStudentsAsCollection(false, this.leaseData.getStudentId()));
        else
            studentName = new ComboBox(Database.getStudentsAsCollection(false));
        startDate = new TextField();
        endDate = new TextField();
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
        GridPane.setHalignment(lblLeaseId, HPos.RIGHT);
        GridPane.setHalignment(lblHallName, HPos.RIGHT);
        GridPane.setHalignment(lblFlatNumber, HPos.RIGHT);
        GridPane.setHalignment(lblRoomNumber, HPos.RIGHT);
        GridPane.setHalignment(lblOccupancy, HPos.RIGHT);
        GridPane.setHalignment(lblCleanStatus, HPos.RIGHT);
        // hallName ComboBox
        leaseId.setStyle("-fx-text-fill: white");
        leaseId.setText(this.leaseData.getLeaseId() != null ? String.valueOf(this.leaseData.getLeaseId()) : "");
        leaseId.setPrefWidth(225.0);
        leaseId.setPadding(new Insets(11, 7, 11, 7));
        leaseId.textProperty().addListener((options, oldValue, newValue) -> leaseId_Changed(options, oldValue, newValue));
        // hallName ComboBox
        hallName.setStyle("-fx-text-fill: white");
        hallName.setValue(this.leaseData.getHallId() + ": " + this.leaseData.getHallName());
        hallName.setPrefWidth(225.0);
        hallName.setPadding(new Insets(11, 5, 11, 5));
        hallName.setDisable(true);
        hallName.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> hallName_Changed(options, oldValue, newValue));
        // hallNumber ComboBox
        flatNumber.setStyle("-fx-text-fill: white");
        flatNumber.setValue(this.leaseData.getFlatNumber());
        flatNumber.setPrefWidth(225.0);
        flatNumber.setPadding(new Insets(11, 5, 11, 5));
        flatNumber.setDisable(true);
        flatNumber.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> flatNumber_Changed(options, oldValue, newValue));
        // roomNumber ComboBox
        roomNumber.setStyle("-fx-text-fill: white");
        roomNumber.setValue(this.leaseData.getRoomNumber());
        roomNumber.setPrefWidth(225.0);
        roomNumber.setPadding(new Insets(11, 5, 11, 5));
        roomNumber.setDisable(true);
        roomNumber.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> roomNumber_Changed(options, oldValue, newValue));
        // studentName ComboBox
        studentName.setStyle("-fx-text-fill: white");
        studentName.setValue(this.leaseData.getStudent() != null ? this.leaseData.getStudent().getStudentId() + ": " + this.leaseData.getStudentName() : "");
        studentName.setPrefWidth(225.0);
        studentName.setPadding(new Insets(11, 5, 11, 5));
        studentName.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> studentName_Changed(options, oldValue, newValue));
        // Work Out Current Year [!]
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.YEAR, 1);
        Date nextYear = cal.getTime();
        String yearToUse = (
                (Integer.valueOf(new SimpleDateFormat("MM").format(today)) > 8) ?   // Check if the Month digit is above August (08)
                    new SimpleDateFormat("yyyy").format(nextYear) :                 // If the current month is above 08, use next year (i.e. 2019)
                    new SimpleDateFormat("yyyy").format(today)                      // If the current month is below 08, use the current year (i.e. 2018)
                );
        // startDate TextField
        startDate.setStyle("-fx-text-fill: white");
        startDate.setText(this.leaseData.getStartDate());   // Displays Lease Date as 01-01-1970 for example
        startDate.setDisable(true);
        startDate.setPrefWidth(225.0);
        startDate.setPadding(new Insets(11, 5, 11, 5));
        // endDate TextField
        endDate.setStyle("-fx-text-fill: white");
        endDate.setText("31-08-" + yearToUse);
        endDate.setDisable(true);
        endDate.setPrefWidth(225.0);
        endDate.setPadding(new Insets(11, 5, 11, 5));
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
        switch(showType) {
            case "create":
                btnUpdate.setText("Create Lease");
                break;
            case "update":
                btnUpdate.setText("Update Lease");
                break;
            default:
            case "view":
                btnUpdate.setText("Close");
                break;
        }
        btnUpdate.setDefaultButton(true);
        btnUpdate.setMaxWidth(Double.MAX_VALUE);
        btnUpdate.setAlignment(Pos.CENTER);
        btnUpdate.setOnAction((ActionEvent e) -> btnUpdate_Click(e, showType));
        
        /**
         * Override Style Elements in accordance to showType
         */
        switch(showType) {
            case "create": {
                /**
                 * Style Elements in accordance to Permissions
                 */
                if(!User.hasPermission(Permissions.EDIT_LEASE)) {
                    leaseId.setDisable(true);
                    occupancy.setDisable(true);
                    studentName.setDisable(true);
                }

                if(!User.hasPermission(Permissions.EDIT_CLEAN)) {
                    cleanStatus.setDisable(true);
                }
                
                btnUpdate.setText("Create Lease");
            }
            break;
                
            case "view": {
                /**
                 * Disable All Elements
                 */
                leaseId.setDisable(true);
                hallName.setDisable(true);
                flatNumber.setDisable(true);
                roomNumber.setDisable(true);
                occupancy.setDisable(true);
                studentName.setDisable(true);
                cleanStatus.setDisable(true);
                btnUpdate.setText("Close");
            }
            break;
        }
        
        /**
         * Compile Elements
         */
        // Hall Name
        contentBox.add(lblLeaseId, 0, 0);
        contentBox.add(leaseId, 1, 0);
        // Hall Name
        contentBox.add(lblHallName, 0, 1);
        contentBox.add(hallName, 1, 1);
        // Hall ID
        contentBox.add(lblFlatNumber, 0, 2);
        contentBox.add(flatNumber, 1, 2);
        // Room Number
        contentBox.add(lblRoomNumber, 0, 3);
        contentBox.add(roomNumber, 1, 3);
        // Student Name
        contentBox.add(lblStudentName, 0, 4);
        contentBox.add(studentName, 1, 4);
        // Start Date
        contentBox.add(lblStartDate, 0, 5);
        contentBox.add(startDate, 1, 5);
        // End Date
        contentBox.add(lblEndDate, 0, 6);
        contentBox.add(endDate, 1, 6);
        // Occupancy
        contentBox.add(lblOccupancy, 0, 7);
        contentBox.add(occupancy, 1, 7);
        // Clean Status
        contentBox.add(lblCleanStatus, 0, 8);
        contentBox.add(cleanStatus, 1, 8);
        // Update Button
        contentBox.add(btnUpdate, 1, 9);
    }
    
    /**
     * @name    leaseId_Changed
     * @desc    Handles when the TextField value changes
     * 
     * @param   options
     * @param   oldValue
     * @param   newValue 
     */
    private void leaseId_Changed(Object options, Object oldValue, Object newValue) {
        /**
         * Check if values are null to avoid errors
         */
        if(newValue == null) return;
        
        /**
         * Cast Objects to Strings
         */
        String oldVal = (String)oldValue;
        String newVal = (String)newValue;
        
        /**
         * Debug
         */
        AccommodationSystem.debug("Old Value: " + oldVal + " | New Value: " + newVal);
        
        /**
         * Check if the value added was of character format
        */
        if (!newVal.matches("[0-9]+")) {
            new Alert(Alert.AlertType.ERROR, "Lease IDs can only contain numbers!", ButtonType.OK).show();
            leaseId.setText(oldVal.matches("[0-9]+") ? oldVal : "");
        }
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
        
        /**
         * Disable Update Button
         */
        btnUpdate.setDisable(true);
        flatNumber.setDisable(false);
        roomNumber.setDisable(true);
        
        /**
         * Clear items from our other ComboBoxes
         */
        flatNumber.getItems().clear();
        
        /**
         * Set our combo box values to null
         */
        flatNumber.setValue(null);
        roomNumber.setValue(null);
        
        /**
         * Populate ComboBoxes
         */
        Hall tempHall = Database.getHall(Database.getIdFromString((String)hallName.getSelectionModel().getSelectedItem()));
        flatNumber.setItems(tempHall.getFlatsAsCollection());
        
        /**
         * Update Flag
         */
        hasChangedRoom = true;
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
        
        /**
         * Disable Update Button
         */
        btnUpdate.setDisable(true);
        roomNumber.setDisable(false);
        
        /**
         * Clear items from our other ComboBoxes
         */
        roomNumber.getItems().clear();
        
        /**
         * Set our combo box values to null
         */
        roomNumber.setValue(null);
        
        /**
         * Populate ComboBoxes
         */
        Hall tempHall = Database.getHall(Database.getIdFromString((String)hallName.getSelectionModel().getSelectedItem()));
        roomNumber.setItems(tempHall.getRoomsAsCollection((int)flatNumber.getSelectionModel().getSelectedItem()));
        
        /**
         * Update Flag
         */
        hasChangedRoom = true;
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
        
        /**
         * Enable Update Lease button
         */
        btnUpdate.setDisable(false);
        
        /**
         * Update Flag
         */
        hasChangedRoom = true;
    }
    
    /**
     * @name    studentName_Changed
     * @desc    Handles when the ComboBox value changes
     * 
     * @param   options
     * @param   oldValue
     * @param   newValue 
     */
    private void studentName_Changed(Object options, Object oldValue, Object newValue) {
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
        
        /**
         * Update Occupancy State
         */
        occupancy.setValue("Occupied");
    }
    
    private void btnUpdate_Click(ActionEvent event, String showType) {
        /**
         * Check our show type: this will make it so if we're "viewing" the Lease,
         * clicking this button will close the GUI and return us back to the Browser.
         */
        if(showType.equals("view")) {
            try {
                this.close();
            } catch(Exception ex) { }
            return;
        }
        
        /**
         * Get Student Data
         */
        String studentNameData = (String)studentName.getSelectionModel().getSelectedItem();
        Integer studentId = Integer.valueOf(studentNameData.split(":")[0]);
        Student newStudent = Database.getStudentFromId(studentId);
        
        /**
         * Variables
         */
        Integer previousLeaseId = (this.leaseData.getLeaseId() != null ? this.leaseData.getLeaseId() : null);
        
        /**
         * Validate and Sanitise inputs
         */
        SimpleIntegerProperty tLeaseId, tHallId, tFlatNumber, tRoomNumber, tOccupiedState, tCleanStatus;
        tHallId = tFlatNumber = tRoomNumber = tOccupiedState = null;
        boolean dataIsValid = true;
        
        /**
         * Check User Permissions
         */
        if(User.hasPermission(Permissions.EDIT_LEASE)) {
            // Check Lease ID
            if(leaseId.getText().matches("[0-9]+") && !leaseId.getText().isEmpty()) {
                tLeaseId = new SimpleIntegerProperty(Integer.valueOf(leaseId.getText()));
                if(this.leaseData.getLeaseId() != null)
                    previousLeaseId = this.leaseData.getLeaseId();
                this.leaseData.setLeaseId(tLeaseId);
            } else dataIsValid = false;

            // Check Hall ID
            if(Database.getHallIds(false).contains(Database.getIdFromString((String)hallName.getSelectionModel().getSelectedItem()))) {
                tHallId = new SimpleIntegerProperty(Database.getIdFromString((String)hallName.getSelectionModel().getSelectedItem()));
                this.leaseData.setHallId(tHallId);
            } else dataIsValid = false;

            // Check Flat ID
            if(this.leaseData.getHall().getFlatsAsCollection().contains((int)flatNumber.getSelectionModel().getSelectedItem())) {
                tFlatNumber = new SimpleIntegerProperty((int)flatNumber.getSelectionModel().getSelectedItem());
                this.leaseData.setFlatNumber(tFlatNumber);
            } else dataIsValid = false;

            // Check Room ID
            if(this.leaseData.getHall().getRoomsAsCollection(this.leaseData.getFlatNumber()).contains((int)roomNumber.getSelectionModel().getSelectedItem())) {
                tRoomNumber = new SimpleIntegerProperty((int)roomNumber.getSelectionModel().getSelectedItem());
                this.leaseData.setRoomNumber(tRoomNumber);
            } else dataIsValid = false;
            
            // Check Occupancy of Hall>Flat>Room
            if(dataIsValid) {
                // Make sure our values are not null
                if(tHallId == null || tFlatNumber == null || tRoomNumber == null)
                    dataIsValid = false;
                else {
                    // Check for an existing lease
                    LeaseData existantLease = Database.getLeaseByHFR(tHallId.intValue(), tFlatNumber.intValue(), tRoomNumber.intValue());
                    
                    // If our lease exists, double check our values
                    if(existantLease != null) {
                        // Check if we're trying to allocate our new values to an already existing lease
                        if(prevHallId.intValue() != this.leaseData.getHallId() ||
                                prevFlatNumber.intValue() != this.leaseData.getFlatNumber() ||
                                prevRoomNumber.intValue() != this.leaseData.getRoomNumber()) {
                            new Alert(Alert.AlertType.ERROR, "You cannot create or update a lease when another lease exists with the same hall, flat and room values.", ButtonType.OK).showAndWait();
                            return;
                        }
                        
                        // Check Occupied State
                        if(existantLease.getOccupied() && existantLease.getStudentId() != this.leaseData.getStudentId().intValue()) {
                            new Alert(Alert.AlertType.ERROR, "The selected Hall, Flat and Room Number already contains a tenant. Please use another room.", ButtonType.OK).showAndWait();
                            return;
                        }
                        
                        // Check Clean Status
                        if(existantLease.getCleanStatusName().equals("Offline")) {
                            new Alert(Alert.AlertType.ERROR, "You cannot create a lease which has a Cleaning Status of Offline.", ButtonType.OK).showAndWait();
                            return;
                        }
                    }
                }
            }

            // Check Occupancy
            if(Occupancy.getOccupancies().contains((String)occupancy.getSelectionModel().getSelectedItem())) {
                tOccupiedState = new SimpleIntegerProperty((int)Occupancy.getId((String)occupancy.getSelectionModel().getSelectedItem()));
                this.leaseData.setOccupied(tOccupiedState);
            } else dataIsValid = false;

            // Set Student
            if(newStudent != null) {
                this.leaseData.setStudentId(new SimpleIntegerProperty(newStudent.getStudentId()));
                
                // Check occupancy
                if(tOccupiedState != null && tOccupiedState.intValue() == Occupancy.getId("Unoccupied")) {
                    new Alert(Alert.AlertType.ERROR, "Your occupancy state is invalid.", ButtonType.OK).showAndWait();
                    return;
                }
            } else dataIsValid = false;
        }
        
        /**
         * Check User Permissions
         */
        if(User.hasPermission(Permissions.EDIT_CLEAN)) {
            // Check Cleaning State
            if(CleaningStatus.getStatuses().contains((String)cleanStatus.getSelectionModel().getSelectedItem())) {
                tCleanStatus = new SimpleIntegerProperty((int)CleaningStatus.getId((String)cleanStatus.getSelectionModel().getSelectedItem()));
                
                // Verify Cleaning State
                if(this.leaseData.getLeaseId() != null && tCleanStatus.intValue() == CleaningStatus.getId("Offline")) {
                    new Alert(Alert.AlertType.ERROR, "You cannot set the cleaning status to Offline when a lease is active.", ButtonType.OK).showAndWait();
                    return;
                }
                
                this.leaseData.setCleanStatus(tCleanStatus);
            } else dataIsValid = false;
        }
        
        /**
         * Set our Start and End Dates
         * We do this without validation as they're static values
         */
        this.leaseData.setStartDate(startDate.getText());
        this.leaseData.setEndDate(endDate.getText());
        
        /**
         * Check our flag and attempt to update the lease
         */
        if(dataIsValid) {
            // Validate Lease ID
            if(Database.checkValidLeaseNumber(this.leaseData.getLeaseId()) || (Objects.equals(this.leaseData.getLeaseId(), previousLeaseId))) {
                if(Database.updateLease(this.leaseData)) {
                    try {
                        parent.buildTable(User.getAllocatedHall());
                        this.close();
                    } catch(Exception ex) { }
                } else new Alert(Alert.AlertType.ERROR, "Unable to update lease.", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Lease ID is already in use.", ButtonType.OK).show();
                if(previousLeaseId != null)
                    this.leaseData.setLeaseId(new SimpleIntegerProperty(previousLeaseId));
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "The data you have supplied is invalid.", ButtonType.OK).show();
        }
    }
    
    /**
     * @name    Default Constructor
     * @desc    Initialise the ViewLease GUI
     * 
     * @param   browser
     * @param   lease
     * @param   showType
     * 
     * @throws  Exception 
     */
    public ViewLease(Browser browser, LeaseData lease, String showType) throws Exception {
        /**
         * Debug
         */
        AccommodationSystem.debug("Loaded '" + this.getClass().getName() + "'");
        
        /**
         * Set Variables
         */
        this.parent = browser;
        this.leaseData = lease;
        
        /**
         * Cache Previous Data for use of comparison
         */
        prevHallId = new SimpleIntegerProperty(this.leaseData.getHallId());
        prevFlatNumber = new SimpleIntegerProperty(this.leaseData.getFlatNumber());
        prevRoomNumber = new SimpleIntegerProperty(this.leaseData.getRoomNumber());
        
        /**
         * Build "ViewPermissions" GUI
         */
        this.buildHeader();
        this.buildContent(showType);
        
        /**
         * Add our GUI Elements (hierarchy)
         */
        super.getPane().setTop(headerBox);
        super.getPane().setCenter(contentBox);
        
        /**
         * Finalise our GUI
         */
        Integer _leaseId = this.leaseData.getLeaseId();
        super.setTitle((showType.substring(0,1).toUpperCase() + showType.substring(1).toLowerCase()) + " Lease: " + (leaseId == null ? "Unoccupied" : String.valueOf(_leaseId)));
        super.setSize(225, 800);
        super.finalise(true);
    }
    
    @Override
    public String formId() {
        return this.getClass().getSimpleName();
    }
}