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
import accommodationsystem.library.User;
import accommodationsystem.library.LeaseData;
import accommodationsystem.library.Database;
import accommodationsystem.library.Lease.CleaningStatus;
import accommodationsystem.library.Lease.Occupancy;
import accommodationsystem.library.LeaseDuration;
import accommodationsystem.library.Permissions;
import com.sun.javafx.tk.Toolkit;
import com.sun.javafx.tk.FontMetrics;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Browser extends GUI {
    /**
     * Variables for "Browser" GUI
     */
    
    // Panes
    HBox headerBox = new HBox();
    ScrollPane middleBox = new ScrollPane();
    HBox footerBox = new HBox();
    
    // Table
    TableView<LeaseData> tbl = new TableView<>();
    Button btnCreateLease,
           btnViewLease,
           btnDeleteLease,
           btnSearch;
    ComboBox comboChangeCleanStatus,
             cbHalls;
    private int _currentHallView = -1;
    private boolean _isSearching = false;
    
    // Table Context Menu
    ContextMenu rightClickMenu;
    MenuItem miViewLease,
             miUpdateLease,
             miDeleteLease,
             miCheckLeaseDuration,
             miViewStudent,
             miRoomPrice,
             miRoomLocation,
             miHallLocation;
    
    // GUI Size (default: 800)
    private int _size_xy = 800;
    private boolean justSelectedNewItem = false,
                    justChangedHallFilter = false;
    
    /**
     * @name    buildHeader
     * @desc    Create, style and build the Header of the "Browser" GUI
     */
    private void buildHeader() {
        /**
         * Declare Elements
         */
        // Panes
        VBox headerLeft = new VBox();
        VBox headerRight = new VBox();
        Region headerSpacer = new Region();
        FlowPane btnStrip = new FlowPane(Orientation.HORIZONTAL, 10, 10);
        // Buttons
        Button btnAdminPanel = new Button();
        Button btnLogout = new Button();
        btnSearch = new Button();
        // Images
        Image logo = new Image(this.getClass().getClassLoader().getResourceAsStream("accommodationsystem/resources/images/logo.png"));
        ImageView logoView = new ImageView(logo);
        // Text
        TextFlow loggedInAs = new TextFlow();
        Text lblLoggedInAs, lblLoggedInUsername, lblSpacer;
        // Hyperlinks
        Hyperlink lblViewPermissions = new Hyperlink();
        
        /**
         * Style Elements
         */
        // Headers
        headerBox.setPadding(new Insets(25, 25, 25, 25));
        headerBox.setId("topBox");
        headerLeft.setAlignment(Pos.CENTER_LEFT);
        headerRight.setAlignment(Pos.TOP_RIGHT);
        headerRight.setMinWidth(500.0);
        headerSpacer.setPrefWidth(0);
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);
        btnStrip.setAlignment(Pos.TOP_RIGHT);
        // Logo
        logoView.setPreserveRatio(true);
        logoView.setFitWidth(250);
        // Logged In As/Username Flow
        loggedInAs.setTextAlignment(TextAlignment.RIGHT);
        // Logged In As
        lblLoggedInAs = new Text("Logged in as");
        lblLoggedInAs.setFill(Color.WHITE);
        // Logged In Username
        lblLoggedInUsername = new Text(" " + User.getUsername() + " | ");
        lblLoggedInUsername.setFill(Color.WHITE);
        lblLoggedInUsername.setStyle("-fx-font-weight: bold");
        // View Permissions
        lblViewPermissions.setText("View My Permissions");
        lblViewPermissions.setOnAction((ActionEvent e) -> btnViewPerms_Click(e));
        // Spacer Label
        lblSpacer = new Text("\n");
        // Hall Selector
        cbHalls = new ComboBox(Database.getHallNames(true));
        cbHalls.setStyle("-fx-text-fill: white");
        cbHalls.setPadding(new Insets(11, 5, 11, 5));
        cbHalls.setValue("0: All");
        cbHalls.setPrefWidth(150.0);
        cbHalls.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> cbHalls_Changed(cbHalls, options, oldValue, newValue));
        // Administrator Panel
        btnAdminPanel.getStyleClass().add("button");
        btnAdminPanel.setText("Administrator Panel");
        btnAdminPanel.setAlignment(Pos.CENTER);
        btnAdminPanel.setOnAction((ActionEvent e) -> btnAdminPanel_Click(e));
        // Search Button
        btnSearch.getStyleClass().addAll("button", "fontawesome");
        btnSearch.setText("🔍"); // Search Magnify Glass
        btnSearch.setOnAction((ActionEvent e) -> btnSearch_Click(e));
        // Logout Button
        btnLogout.getStyleClass().add("button");
        btnLogout.setText("Logout");
        btnLogout.setAlignment(Pos.CENTER);
        btnLogout.setOnAction((ActionEvent e) -> btnLogout_Click(e));
        
        /**
         * Compile Elements
         */
        // Logged In As Label
        loggedInAs.getChildren().addAll(lblLoggedInAs, lblLoggedInUsername, lblViewPermissions, lblSpacer);
        // Button Strip
        btnStrip.getChildren().add(cbHalls);
        if(User.hasPermission(Permissions.ADMIN_PANEL)) btnStrip.getChildren().add(btnAdminPanel);
        btnStrip.getChildren().add(btnSearch);
        btnStrip.getChildren().add(btnLogout);
        // Header Left
        headerLeft.getChildren().add(logoView);
        // Header Right
        headerRight.getChildren().addAll(loggedInAs, btnStrip);
        // Header Box
        headerBox.getChildren().addAll(headerLeft, headerSpacer, headerRight);
    }
    
    /**
     * @name    btnViewPerms_Click
     * @desc    Handles the Click event for the View Permissions button/label
     * @param   event 
     */
    private void btnViewPerms_Click(ActionEvent event) {
        /**
         * Open up View Permissions GUI
         */
        try {
            // Show GUI
            new ViewPermissions().getStage().showAndWait();
        } catch(Exception e) { }
    }
    
    /**
     * @name    btnAdminPanel_Click
     * @desc    Handles the Click event for the Administrator Panel button
     * @param   event 
     */
    private void btnAdminPanel_Click(ActionEvent event) {
        /**
         * Open up Administrator Panel GUI
         */
        try {
            // Show GUI
            new AdminPanel().getStage().showAndWait();
            
            // Rebuild our table
            this.buildTable(0);
            
            // Update the ComboBox for filtering by Hall Name
            this.cbHalls.getItems().clear();
            this.cbHalls.setValue("0: All"); 
            this.cbHalls.setItems(Database.getHallNames(true));
        } catch(Exception e) { }
    }
    
    /**
     * @name    btnSearch_Click
     * @desc    Handles the Click event for the search button
     * @param   event
     */
    private void btnSearch_Click(ActionEvent event) {
        /**
         * Check if we're searching already
         */
        if(_isSearching) {
            _isSearching = false;
            btnSearch.setText("🔍"); // Search Magnify Glass
            this.buildTable(User.getAllocatedHall());
            return;
        }
        
        /**
         * Initialise our options
         */
        List<String> options = new ArrayList<>();
        options.add("Student Name");
        if(User.getAllocatedHall() == 0) options.add("Hall Name");
        options.add("Flat Number");
        options.add("Room Number");
        options.add("Cleaning Status");
        options.add("Occupancy");

        /**
         * Initialise our Dialog
         */
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Student Name", options);
        dialog.setTitle("Search");
        dialog.setHeaderText("Please select a searching option");
        dialog.setContentText("Search by:");
        
        /**
         * Show Dialog and get our result
         */
        Optional<String> res = dialog.showAndWait();
        if(res.isPresent()) {
            // Cache our leases
            List<LeaseData> leases = Database.getLeases(0);
            
            // Filter through our result
            switch(res.get()) {
                case "Student Name": {
                    TextInputDialog tiDialog = new TextInputDialog();
                    tiDialog.setHeaderText("Please enter a name to search by");
                    tiDialog.setContentText("Please enter a name:");
                    Optional<String> snRes = tiDialog.showAndWait();
                    if(snRes.isPresent()) {
                        List<LeaseData> sortedLeases = new ArrayList<>();
                        for(LeaseData l: leases) {
                            // Check if the User has permission to view all halls
                            if(User.getAllocatedHall() == 0 || l.getHallId() == User.getAllocatedHall()) {
                                // Add lease to list
                                if(l.getStudentName().toLowerCase().contains(snRes.get().toLowerCase())) {
                                    sortedLeases.add(l);
                                }
                            }
                        }
                        
                        // Populate our table
                        this.populateTable(sortedLeases);
                    }
                }
                break;
                case "Hall Name": {
                    List<String> hallNames = Database.getHallNames(false).stream().collect(Collectors.toList());
                    
                    ChoiceDialog<String> cDialog = new ChoiceDialog<>("", hallNames);
                    cDialog.setHeaderText("Please select a Hall Name");
                    cDialog.setContentText("Please select a hall:");
                    Optional<String> cRes = cDialog.showAndWait();
                    if(cRes.isPresent()) {
                        List<LeaseData> sortedLeases = new ArrayList<>();
                        for(LeaseData l: leases) {
                            if(l.getHallId().equals(Database.getIdFromString(cRes.get()))) {
                                sortedLeases.add(l);
                            }
                        }
                        
                        // Populate our table
                        this.populateTable(sortedLeases);
                    }
                }
                break;
                case "Flat Number": {
                    List<String> flatNumbs = new ArrayList<>();
                    leases.stream().forEach((l) -> { if(!flatNumbs.contains(l.getFlatNumber().toString())) { flatNumbs.add(l.getFlatNumber().toString()); } });
                    
                    ChoiceDialog<String> cDialog = new ChoiceDialog<>("", flatNumbs);
                    cDialog.setHeaderText("Please select a Flat Number");
                    cDialog.setContentText("Please select a number:");
                    Optional<String> cRes = cDialog.showAndWait();
                    if(cRes.isPresent()) {
                        List<LeaseData> sortedLeases = new ArrayList<>();
                        for(LeaseData l: leases) {
                            // Check if the User has permission to view all halls
                            if(User.getAllocatedHall() == 0 || l.getHallId() == User.getAllocatedHall()) {
                                // Add lease to list
                                if(l.getFlatNumber().toString().equals(cRes.get())) {
                                    sortedLeases.add(l);
                                }
                            }
                        }
                        
                        // Populate our table
                        this.populateTable(sortedLeases);
                    }
                }
                break;
                case "Room Number": {
                    List<String> roomNumbs = new ArrayList<>();
                    leases.stream().forEach((l) -> { if(!roomNumbs.contains(l.getRoomNumber().toString())) { roomNumbs.add(l.getRoomNumber().toString()); } });
                    
                    ChoiceDialog<String> cDialog = new ChoiceDialog<>("", roomNumbs);
                    cDialog.setHeaderText("Please select a Room Number");
                    cDialog.setContentText("Please select a number:");
                    Optional<String> cRes = cDialog.showAndWait();
                    if(cRes.isPresent()) {
                        List<LeaseData> sortedLeases = new ArrayList<>();
                        for(LeaseData l: leases) {
                            // Check if the User has permission to view all halls
                            if(User.getAllocatedHall() == 0 || l.getHallId() == User.getAllocatedHall()) {
                                // Add lease to list
                                if(l.getRoomNumber().toString().equals(cRes.get())) {
                                    sortedLeases.add(l);
                                }
                            }
                        }
                        
                        // Populate our table
                        this.populateTable(sortedLeases);
                    }
                }
                break;
                case "Cleaning Status": {
                    List<String> cleaningStatuses = CleaningStatus.getStatuses().stream().collect(Collectors.toList());
                    ChoiceDialog<String> cDialog = new ChoiceDialog<>("", cleaningStatuses);
                    cDialog.setHeaderText("Please select a Cleaning Status");
                    cDialog.setContentText("Please select a status:");
                    Optional<String> cRes = cDialog.showAndWait();
                    if(cRes.isPresent()) {
                        List<LeaseData> sortedLeases = new ArrayList<>();
                        for(LeaseData l: leases) {
                            // Check if the User has permission to view all halls
                            if(User.getAllocatedHall() == 0 || l.getHallId() == User.getAllocatedHall()) {
                                // Add lease to list
                                if(l.getCleanStatusName().equals(cRes.get())) {
                                    sortedLeases.add(l);
                                }
                            }
                        }
                        
                        // Populate our table
                        this.populateTable(sortedLeases);
                    }
                }
                break;
                case "Occupancy": {
                    List<String> occupancyStatuses = Occupancy.getOccupancies().stream().collect(Collectors.toList());
                    ChoiceDialog<String> cDialog = new ChoiceDialog<>("", occupancyStatuses);
                    cDialog.setHeaderText("Please select a Occupancy Status");
                    cDialog.setContentText("Please select a status:");
                    Optional<String> cRes = cDialog.showAndWait();
                    if(cRes.isPresent()) {
                        List<LeaseData> sortedLeases = new ArrayList<>();
                        for(LeaseData l: leases) {
                            // Check if the User has permission to view all halls
                            if(User.getAllocatedHall() == 0 || l.getHallId() == User.getAllocatedHall()) {
                                // Add lease to list
                                if(l.getOccupiedStatus().equals(cRes.get())) {
                                    sortedLeases.add(l);
                                }
                            }
                        }
                        
                        // Populate our table
                        this.populateTable(sortedLeases);
                    }
                }
                break;
                default: {
                    new Alert(Alert.AlertType.ERROR, "Invalid option selected for search.", ButtonType.OK).show();
                }
                break;
            }
        }
    }
    
    /**
     * @name    btnLogout_Click
     * @desc    Handles the Click event for the logout button
     * @param   event 
     */
    private void btnLogout_Click(ActionEvent event) {
        new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to logout?", ButtonType.YES, ButtonType.NO)
        .showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
            // Open up Login GUI
            try {
                User.logout();
                new Login(true).show();
                super.close();
            } catch(Exception e) { }
        });
    }
    
    /**
     * @name    cbHalls_Changed
     * @desc    Handles when the ComboBox value changes
     * @param   cb
     * @param   options
     * @param   oldValue
     * @param   newValue 
     */
    private void cbHalls_Changed(ComboBox cb, Object options, Object oldValue, Object newValue) {
        /**
         * Returners
         */
        if(justChangedHallFilter || cb.getSelectionModel().getSelectedItem() == null) {
            justChangedHallFilter = false;
            return;
        }
        
        /**
         * Cast Objects to Strings
         */
        String oldVal = (String)oldValue;
        String newVal = (String)newValue;
        
        /**
         * Debug
         */
        AccommodationSystem.debug("Old Value: " + oldVal + " | New Value: " + newVal + " ---> " + cb.getSelectionModel().getSelectedIndex());
        
        /**
         * Update TableView to match ComboBox value
         */
        int hallId = Database.getIdFromString((String)cb.getSelectionModel().getSelectedItem());
        if(User.getAllocatedHall() == 0)
            this.buildTable(hallId);
        else {
            if(hallId == User.getAllocatedHall() || hallId == 0)
                this.buildTable(User.getAllocatedHall());
            else {
                new Alert(Alert.AlertType.ERROR, "Unable to display data about '" + newVal + "'. You are allocated to Hall ID " + User.getAllocatedHall(), ButtonType.OK).showAndWait();
                justChangedHallFilter = true;
                Platform.runLater(() -> cb.setValue(oldValue));
            }
        }
    }
    
    /**
     * @name    cbCleanStatus_Changed
     * @desc    Handles when the ComboBox value changes
     * @param   cb
     * @param   options
     * @param   oldValue
     * @param   newValue 
     */
    private void cbCleanStatus_Changed(Object options, Object oldValue, Object newValue) {
        /**
         * Check to see if we've just clicked another item
         */
        /**
         * Cast Objects to Strings
         */
        String oldVal = (String)oldValue;
        String newVal = (String)newValue;
        
        /**
         * Debug
         */
        AccommodationSystem.debug("Old Value: " + oldVal + " | New Value: " + newVal + " ---> " + comboChangeCleanStatus.getSelectionModel().getSelectedIndex());
        
        /**
         * Check if we're trying to view a lease that does not exist (should not occur)
         */
        if(tbl.getSelectionModel().isEmpty())
            return;
        
        /**
         * Get LeaseData
         */
        LeaseData lease = tbl.getSelectionModel().getSelectedItem();
        
        /**
         * Check ComboBox Data
         */
        if(!User.hasPermission(Permissions.MANAGE_OFFLINE) && newVal.equals("Offline") && !justSelectedNewItem) {
            new Alert(Alert.AlertType.ERROR, "You do not have permissions to set a lease status to Offline.", ButtonType.OK).showAndWait();
            Platform.runLater(() -> comboChangeCleanStatus.setValue(lease.getCleanStatusName()));
        } else {
            // Check if trying to set Lease to Offline when lease is present
            if(newVal.equals("Offline") && lease.getLeaseId() != null && !justSelectedNewItem) {
                new Alert(Alert.AlertType.ERROR, "Cannot set lease status to Offline when a lease is present.", ButtonType.OK).showAndWait();
                Platform.runLater(() -> comboChangeCleanStatus.setValue(lease.getCleanStatusName()));
            } else {
                // Update Lease Data
               if(!lease.getCleanStatusName().equals(newVal)) {
                   lease.setCleanStatus(new SimpleIntegerProperty((int)CleaningStatus.getId((String)newVal)));

                   // Update Room
                   Database.updateRoom(lease);

                   // Rebuild Table
                   this.buildTable(_currentHallView);
               }
            }
        }
        
        
        if(justSelectedNewItem) {
            justSelectedNewItem = false;
        }
    }
    
    /**
     * @name    populateTable
     * @desc    Create the Table of the "Browser" GUI
     * 
     * @param   leases
     */
    public void populateTable(List<LeaseData> leases) {
        /**
         * Clean-up Table
         */
        if(super.hasFinalised()) {
            tbl.getItems().clear();
        }
        
        /**
         * Populate Table
         */
        btnSearch.setText("×"); // Erase Text
        _isSearching = true;
        tbl.getItems().setAll(leases);
        _currentHallView = -1;
    }
    
    /**
     * @name    buildTable
     * @desc    Create the Table of the "Browser" GUI
     * 
     * @param  hallNumber
     */
    public void buildTable(int hallNumber) {
        /**
         * Clean-up Table
         * Only call this if the form has previously been finalised
         */
        if(super.hasFinalised()) {
            tbl.getItems().clear();
            tbl.getColumns().clear();
        } else {
            /**
             * Build Context Menu
             */
            SeparatorMenuItem miSeparator = new SeparatorMenuItem();
            
            /**
             * Initialise Context Menu
             */
            rightClickMenu = new ContextMenu();
            miViewLease = new MenuItem("View Lease");
            miUpdateLease = new MenuItem("Update Lease");
            miDeleteLease = new MenuItem("Delete Lease");
            miCheckLeaseDuration = new MenuItem("Check Lease Duration");
            miViewStudent = new MenuItem("View Student");
            miRoomPrice = new MenuItem("Get Monthly Price");
            miRoomLocation = new MenuItem("Get Room Details");
            miHallLocation = new MenuItem("Get Hall Details");
            rightClickMenu.getItems().addAll(miViewLease, miUpdateLease, miDeleteLease, miSeparator, miCheckLeaseDuration, miViewStudent, miRoomPrice, miRoomLocation, miHallLocation);
            
            /**
             * Menu Item Listeners
             */
            miViewLease.setOnAction((ActionEvent e) -> btnViewLease_Click(e));
            miUpdateLease.setOnAction((ActionEvent e) -> btnCreateLease_Click(e));
            miDeleteLease.setOnAction((ActionEvent e) -> btnDeleteLease_Click(e));
            miCheckLeaseDuration.setOnAction((ActionEvent e) -> mi_CheckLeaseDuration(e));
            miViewStudent.setOnAction((ActionEvent e) -> mi_ViewStudent(e));
            miRoomPrice.setOnAction((ActionEvent e) -> mi_RoomPrice(e));
            miRoomLocation.setOnAction((ActionEvent e) -> mi_RoomLocation(e));
            miHallLocation.setOnAction((ActionEvent e) -> mi_HallLocation(e));
            
            /**
             * Table Listeners
             */
            tbl.setOnMouseClicked((MouseEvent e) -> tbl_Click(e));
            tbl.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> tbl_Select(obs, oldSelection, newSelection));
        }
        
        /**
         * Declare Elements
         */
        TableColumn leaseNumber,
                hallName,
                flatNumber,
                roomNumber,
                studentName,
                occupancyStatus,
                cleaningStatus;
        
        /**
         * Initialise Elements
         */
        leaseNumber         = new TableColumn("Lease #");
        hallName            = new TableColumn("Hall Name");
        flatNumber          = new TableColumn("Flat #");
        roomNumber          = new TableColumn("Room #");
        studentName         = new TableColumn("Student Name");
        occupancyStatus     = new TableColumn("Occupancy Status");
        cleaningStatus      = new TableColumn("Cleaning Status");
        
        /**
         * Set Table Properties
         */
        // Cell Value Factory
        leaseNumber.setCellValueFactory(new PropertyValueFactory<>("LeaseId"));
        hallName.setCellValueFactory(new PropertyValueFactory<>("HallName"));
        flatNumber.setCellValueFactory(new PropertyValueFactory<>("FlatNumber"));
        roomNumber.setCellValueFactory(new PropertyValueFactory<>("RoomNumber"));
        studentName.setCellValueFactory(new PropertyValueFactory<>("StudentName"));
        occupancyStatus.setCellValueFactory(new PropertyValueFactory<>("OccupiedStatus"));
        cleaningStatus.setCellValueFactory(new PropertyValueFactory<>("CleanStatusName"));
        tbl.getItems().setAll(Database.getLeases(hallNumber));
        _currentHallView = hallNumber;
        // Sorting Order
        leaseNumber.setSortType(TableColumn.SortType.DESCENDING);
            
        /**
         * Compile Elements
         */
        tbl.getColumns().addAll(leaseNumber, hallName, flatNumber, roomNumber, studentName, occupancyStatus, cleaningStatus);
        tbl.getColumns().stream().forEach((TableColumn c) -> c.impl_setReorderable(false)); // TEMP -- DISABLES COLUMN REORDERING
        
        /**
         * Style Table Elements
         */
        // Set Sorting Order
        tbl.getSortOrder().add(leaseNumber);
        
        // Reset XY Size
        _size_xy = 0;
        
        // Update Column Widths
        FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(new Font("Arial", 14));
        for(int i = 0; i < tbl.getColumns().size(); i++) {
            // Compute Text Size and Set Column Width
            double textWidth = fontMetrics.computeStringWidth(tbl.getColumns().get(i).getText());
            tbl.getColumns().get(i).setPrefWidth(textWidth + 60);
            
            // Update XY Size
            _size_xy += (textWidth + 60);
        }
    }
    
    /**
     * @name    tbl_Click
     * @desc    Create the Table Handler for when a user clicks
     * 
     * @param   event
     */
    private void tbl_Click(MouseEvent event) {
        /**
         * Validate selected row
         */
        if(tbl.getSelectionModel().isEmpty())
            return;
        
        /**
         * Check if we've double clicked
         * Check if we have permission to View a Lease
         */
        if(event.getClickCount() >= 2 && User.hasPermission(Permissions.VIEW_LEASES)) {
            btnViewLease_Click(null);
        }
        
        /**
         * Check if we've right clicked
         */
        if(event.getButton() == MouseButton.SECONDARY) {
            rightClickMenu.show(tbl, event.getScreenX(), event.getScreenY());
        }
    }
    
    /**
     * @name    tbl_Select
     * @desc    Create the Table Handler for when a user selects a row
     * 
     * @param   obs
     * @param   oldSelection
     * @param   newSelection
     */
    private void tbl_Select(Object obs, Object oldSelection, Object newSelection) {
        /**
         * Enable or Disable our Footer Buttons
         */
        footerBox.getChildren().stream().forEach((Node o) -> {
            if(o instanceof HBox) {
                HBox pane = (HBox)o;
                
                pane.getChildren().stream().forEach((Node n) -> {
                    if(n instanceof Button) {
                        Button b = (Button)n;
                        b.setDisable((newSelection == null));
                    } else if(n instanceof ComboBox) {
                        ComboBox cb = (ComboBox)n;
                        cb.setDisable((newSelection == null));
                    }
                });
            }
        });
        
        /**
         * Enable or Disable our Right Click Menu Items
         */
        rightClickMenu.getItems().stream().forEach((MenuItem i) -> {
            i.setDisable((newSelection == null));
        });
        
        /**
         * Get our selected item and return if it's null
         */
        LeaseData lease = tbl.getSelectionModel().getSelectedItem();
        if(lease == null) return;
        
        /**
         * Enable or Disable our Right Click Menu Items
         */
        miViewLease.setDisable(false);
        miUpdateLease.setDisable((lease.getLeaseId() == null && lease.getCleanStatusName().equals("Offline")));
        miDeleteLease.setDisable((lease.getLeaseId() == null));
        miCheckLeaseDuration.setDisable((lease.getLeaseId() == null));
        miViewStudent.setDisable((lease.getLeaseId() == null));
        miHallLocation.setDisable(false);
        
        /**
         * Permission Check for Right Click Menu Items
         */
        if(!User.hasPermission(Permissions.EDIT_LEASE)) {
            miUpdateLease.setDisable(true);
            miDeleteLease.setDisable(true);
        }
        
        /**
         * Debug
         */
        AccommodationSystem.debug("Selected Lease: " + lease.getLeaseId());
        
        /**
         * Make changes to the values, text and disable states of
         * our GUI elements.
         */
        // Check if our lease exists
        btnCreateLease.setText((lease.getLeaseId() == null ? "Create Lease" : "Update Lease"));
        miUpdateLease.setText((lease.getLeaseId() == null ? "Create Lease" : "Update Lease"));
        
        // Update Clean Button
        btnCreateLease.setDisable((lease.getCleanStatusName().equals("Offline") && lease.getLeaseId() == null));
        btnDeleteLease.setDisable((lease.getCleanStatusName().equals("Offline") || lease.getLeaseId() == null));
        
        // Modify our flag
        justSelectedNewItem = true;
        
        // Update ComboBox
        comboChangeCleanStatus.setValue(lease.getCleanStatusName());
        comboChangeCleanStatus.setDisable(User.hasPermission(Permissions.EDIT_CLEAN) ? lease.getCleanStatusName().equals("Offline") && !User.hasPermission(Permissions.MANAGE_OFFLINE) : true);
    }
    
    /**
     * @name    mi_CheckLeaseDuration
     * @desc    [Menu Item] Handles when the menu item is clicked
     * 
     * @param   e
     */
    private void mi_CheckLeaseDuration(ActionEvent e) {
        /**
         * Get our selected item
         */
        LeaseData lease = tbl.getSelectionModel().getSelectedItem();
        
        /**
         * Check Lease Status
         */
        if(lease.getLeaseId() == null) {
            new Alert(Alert.AlertType.ERROR, "Cannot check lease duration for a lease that does not exist.", ButtonType.OK).showAndWait();
            return;
        }
        
        /**
         * Get Lease Information
         */
        // DateFormat converts the string parsed to it in dd-MM-yyyy format
        DateFormat convFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat showFormat = new SimpleDateFormat("EEEEE, dd 'of' MMMMM, Y");
        
        // Parse our lease's start/end dates to a Date Object
        Date msStartDate, msEndDate;
        try {
            msStartDate = convFormat.parse(lease.getStartDate());
            msEndDate = convFormat.parse(lease.getEndDate());
        } catch(ParseException ex) {
            // Something went wrong
            new Alert(Alert.AlertType.ERROR, "There was an issue trying to retrieve the lease duration.", ButtonType.OK).showAndWait();
            System.out.println("Parse Exception error: " + ex.getMessage());
            return;
        }
        
        /**
         * Conversions
         */
        int tsStart = (int)Math.floor(msStartDate.getTime() / 1000);
        int tsEnd = (int)Math.floor(msEndDate.getTime() / 1000);
        int tsNow = (int)Math.floor(System.currentTimeMillis() / 1000);
        int secsLeft = (tsEnd - tsNow);
        
        /**
         * Create our LeaseDuration Object which will convert the seconds into a readable format
         */
        LeaseDuration ld = new LeaseDuration(secsLeft);
        
        /**
         * Show Alert (Message)
         */
        String m = MessageFormat.format("Start Date: {0}\nEnd Date: {1}\n\nThis lease has {2} weeks, {3} days, {4} hours, {5} minutes and {6} seconds remaining.",
                showFormat.format(msStartDate), showFormat.format(msEndDate), ld.getWeeks(), ld.getDays(), ld.getHours(), ld.getMinutes(), ld.getSeconds());
        new Alert(Alert.AlertType.INFORMATION, m, ButtonType.OK).showAndWait();
    }
    
    /**
     * @name    mi_ViewStudent
     * @desc    [Menu Item] Handles when the menu item is clicked
     * 
     * @param   e
     */
    private void mi_ViewStudent(ActionEvent e) {
        /**
         * Get our selected item
         */
        LeaseData lease = tbl.getSelectionModel().getSelectedItem();
        
        /**
         * Check Lease Status
         */
        if(lease.getLeaseId() == null) {
            new Alert(Alert.AlertType.ERROR, "Cannot view student for a lease that does not exist.", ButtonType.OK).showAndWait();
            return;
        }
        
        /**
         * Show Alert (Message)
         */
        String m = MessageFormat.format("Student ID: {0}\nStudent Name: {1}\n\n{1} lives in Room {2}, Flat {3}, {4}.", lease.getStudentId(), lease.getStudentName(), lease.getRoomNumber(), lease.getFlatNumber(), lease.getHallName());
        new Alert(Alert.AlertType.INFORMATION, m, ButtonType.OK).showAndWait();
    }
    
    /**
     * @name    mi_RoomPrice
     * @desc    [Menu Item] Handles when the menu item is clicked
     * 
     * @param   e
     */
    private void mi_RoomPrice(ActionEvent e) {
        /**
         * Get our selected item
         */
        LeaseData lease = tbl.getSelectionModel().getSelectedItem();
        
        /**
         * Show Alert (Message)
         */
        String m = MessageFormat.format("This lease''s monthly price is set to: £{0}",
                lease.getRoom().getMonthlyPrice());
        new Alert(Alert.AlertType.INFORMATION, m, ButtonType.OK).showAndWait();
    }
    
    /**
     * @name    mi_RoomLocation
     * @desc    [Menu Item] Handles when the menu item is clicked
     * 
     * @param   e
     */
    private void mi_RoomLocation(ActionEvent e) {
        /**
         * Get our selected item
         */
        LeaseData lease = tbl.getSelectionModel().getSelectedItem();
        
        /**
         * Show Alert (Message)
         */
        String m = MessageFormat.format("This lease''s address is set to:\n\nRoom {0}, Flat {1}, {2}\n{3}\n{4}",
                lease.getRoomNumber(), lease.getFlatNumber(), lease.getHallName(), lease.getHall().getAddress(), lease.getHall().getPostcode());
        new Alert(Alert.AlertType.INFORMATION, m, ButtonType.OK).showAndWait();
    }
    
    /**
     * @name    mi_HallLocation
     * @desc    [Menu Item] Handles when the menu item is clicked
     * 
     * @param   e
     */
    private void mi_HallLocation(ActionEvent e) {
        /**
         * Get our selected item
         */
        LeaseData lease = tbl.getSelectionModel().getSelectedItem();
        
        /**
         * Show Alert (Message)
         */
        String m = MessageFormat.format("This lease exists within {0}.\n\nName: {0}\nNumber: {1}\nAddress: {2}\nPostcode: {3}\nTelephone: {4}",
                lease.getHallName(), lease.getHall().getId(), lease.getHall().getAddress(), lease.getHall().getPostcode(), lease.getHall().getPhone());
        new Alert(Alert.AlertType.INFORMATION, m, ButtonType.OK).showAndWait();
    }
    
    /**
     * @name    buildContent
     * @desc    Create, style and build the Content of the "Browser" GUI
     */
    private void buildContent() {
        /**
         * Style Elements
         */
        middleBox.setId("contentBox");
        middleBox.setFitToWidth(true);
        middleBox.setFitToHeight(true);
        
        /**
         * Build Table
         */
        this.buildTable(User.getAllocatedHall());
        
        /**
         * Compile Elements
         */
        middleBox.setContent(tbl);
    }
    
    /**
     * @name    buildFooter
     * @desc    Create, style and build the Footer of the "Browser" GUI
     */
    private void buildFooter() {
        /**
         * Declare Elements
         */
        HBox leftBox, rightBox;
        Label lblChangeCleanStatus;
        
        /**
         * Initialise Elements
         */
        leftBox = new HBox(15);
        rightBox = new HBox(15);
        btnCreateLease = new Button();
        btnViewLease = new Button();
        btnDeleteLease = new Button();
        comboChangeCleanStatus = new ComboBox(CleaningStatus.getStatuses());
        lblChangeCleanStatus = new Label();
        
        /**
         * Style Elements
         */
        // Headers
        footerBox.setPadding(new Insets(20, 20, 20, 20));
        footerBox.setMinHeight(100);
        footerBox.setId("actionPane");
        leftBox.setAlignment(Pos.CENTER_LEFT);
        rightBox.setAlignment(Pos.CENTER_RIGHT);
        // Buttons are disabled by default, waiting for user to click an element on the TableView
        // Create Lease Button
        btnCreateLease.setText("Create Lease");
        btnCreateLease.setDisable(true);
        btnCreateLease.setOnAction((ActionEvent e) -> btnCreateLease_Click(e));
        // View Lease Button
        btnViewLease.setText("View Lease");
        btnViewLease.setDisable(true);
        btnViewLease.setOnAction((ActionEvent e) -> btnViewLease_Click(e));
        // Delete Lease Button
        btnDeleteLease.setText("Delete Lease");
        btnDeleteLease.setDisable(true);
        btnDeleteLease.setOnAction((ActionEvent e) -> btnDeleteLease_Click(e));
        // Button Area
        leftBox.getChildren().add(btnViewLease);
        if(User.hasPermission(Permissions.EDIT_LEASE)) leftBox.getChildren().addAll(btnCreateLease, btnDeleteLease);
        // ComboBoxes
        comboChangeCleanStatus.setStyle("-fx-text-fill: white");
        comboChangeCleanStatus.setPadding(new Insets(11, 5, 11, 5));
        comboChangeCleanStatus.setValue("Clean");
        comboChangeCleanStatus.setPrefWidth(150.0);
        comboChangeCleanStatus.setDisable(true);
        comboChangeCleanStatus.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> cbCleanStatus_Changed(options, oldValue, newValue));
        // Change Clean Status Label
        lblChangeCleanStatus.setText("Clean Status");
        lblChangeCleanStatus.setId("lblCleanStatus");
        lblChangeCleanStatus.setPadding(new Insets(20, 20, 20, 20));
        // Change Clean Status Area
        rightBox.getChildren().addAll(lblChangeCleanStatus, comboChangeCleanStatus);
        // Spacer
        Region footerSpacer = new Region();
        footerSpacer.setPrefWidth(150);
        HBox.setHgrow(footerSpacer, Priority.ALWAYS);
        
        /**
         * Compile Elements
         */
        footerBox.getChildren().addAll(leftBox, footerSpacer, rightBox);
    }
    
    /**
     * @name    btnCreateLease_Click
     * @desc    Handles the Click event for the create lease button
     * @param   event 
     */
    private void btnCreateLease_Click(ActionEvent event) {
        /**
         * Check if we're trying to select a lease that does not exist (should not occur)
         */
        if(tbl.getSelectionModel().isEmpty())
            return;
        
        /**
         * Get LeaseData
         */
        LeaseData lease = tbl.getSelectionModel().getSelectedItem();
        
        /**
         * Open our View Lease GUI
         */
        try {
            /**
             * Verify Lease Cleaning Status as leases are not able to be made
             * when the cleaning status is set to Offline.
             */
            if(lease.getCleanStatusName().equals("Offline")) {
                new Alert(Alert.AlertType.ERROR, "Cannot create a lease when the room status is Offline.", ButtonType.OK).show();
                return;
            }
            
            /**
             * Get our leases ID and show debug (if available)
             */
            Integer leaseId = lease.getLeaseId();
            AccommodationSystem.debug("Creating/Updating Lease: " + (leaseId == null ? 0 : leaseId));
            
            /**
             * Show our Create/Update Lease GUI as a Modal
             */
            Stage updLease = new ViewLease(this, lease, (btnCreateLease.textProperty().get().equals("Create Lease") ? "create" : "update")).getStage();
            updLease.initOwner(this.getScene().getWindow());
            updLease.initModality(Modality.APPLICATION_MODAL);
            updLease.showAndWait();
            
        } catch(Exception e) { }
    }
    
    /**
     * @name    btnViewLease_Click
     * @desc    Handles the Click event for the view lease button
     * @param   event 
     */
    private void btnViewLease_Click(ActionEvent event) {
        /**
         * Check if we're trying to view a lease that does not exist (should not occur)
         */
        if(tbl.getSelectionModel().isEmpty())
            return;
        
        /**
         * Get LeaseData
         */
        LeaseData lease = tbl.getSelectionModel().getSelectedItem();
        
        /**
         * Open our View Lease GUI
         */
        try {
            /**
             * Get our leases ID and show debug (if available)
             */
            Integer leaseId = lease.getLeaseId();
            AccommodationSystem.debug("Viewing Lease: " + (leaseId == null ? 0 : leaseId));
            
            /**
             * Show our View Lease GUI as a Modal
             */
            Stage viewLease = new ViewLease(this, lease, "view").getStage();
            viewLease.initOwner(this.getScene().getWindow());
            viewLease.initModality(Modality.APPLICATION_MODAL);
            viewLease.showAndWait();
            
        } catch(Exception e) { }
    }
    
    /**
     * @name    btnDeleteLease_Click
     * @desc    Handles the Click event for the delete lease button
     * @param   event 
     */
    private void btnDeleteLease_Click(ActionEvent event) {
        /**
         * Check if we're trying to select a lease that does not exist (should not occur)
         */
        if(tbl.getSelectionModel().isEmpty())
            return;
        
        /**
         * Get LeaseData
         */
        LeaseData lease = tbl.getSelectionModel().getSelectedItem();
        
        /**
         * Check if our lease exists
         */
        if(lease.getLeaseId() == null) {
            new Alert(Alert.AlertType.ERROR, "Unable to delete a lease that does not exist.", ButtonType.OK).show();
            return;
        }
            
        /**
         * Display our Yes/No Confirmation Message Dialog
         */
        try {
            Alert confirm = new Alert(Alert.AlertType.WARNING, "Are you sure you would like to delete Lease " + lease.getLeaseId(), ButtonType.NO, ButtonType.YES);
            Optional<ButtonType> ok = confirm.showAndWait();
            if(ok.get() == ButtonType.YES) {
                Database.deleteLease(lease);
                this.buildTable(_currentHallView);
            }
        } catch(Exception e) { }
    }
    
    /**
     * @name    Default Constructor
     * @desc    Initialise the Browser GUI
     * @throws  Exception 
     */
    public Browser() throws Exception {
        /**
         * Debug
         */
        AccommodationSystem.debug("Loaded '" + this.getClass().getName() + "'");
        
        /**
         * Build "Browser" GUI
         */
        this.buildHeader();
        this.buildContent();
        this.buildFooter();
        
        /**
         * Add our GUI Elements (hierarchy)
         */
        super.getPane().setTop(headerBox);
        super.getPane().setCenter(middleBox);
        super.getPane().setBottom(footerBox);
        
        /**
         * Finalise our GUI
         */
        super.setTitle("UWE Accommodation System");
        super.setSize(_size_xy + 25, _size_xy + 25);
        super.finaliseAndGetScene(true).addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            // Credit: fabian - https://stackoverflow.com/a/41908580
            Node source = e.getPickResult().getIntersectedNode();
            
            // Move up through Node Hierachy until something other than a TableRow or Button is present
            while(source != null && !(source instanceof TableRow) && !(source instanceof Button) && !(source instanceof ComboBox))
                // Recursively get the parent of the Node until condition is met
                source = source.getParent();
            
            // Check if the selected Node is null, or if a TableRow is selected but however empty, clear the selection
            if(source == null || (source instanceof TableRow && ((TableRow) source).isEmpty()))
                tbl.getSelectionModel().clearSelection();
            
            // Close Right Click menu
            rightClickMenu.hide();
        });
    }
    
    @Override
    public String formId() {
        return this.getClass().getSimpleName();
    }
}
