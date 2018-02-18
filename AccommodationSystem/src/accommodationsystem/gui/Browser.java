/*
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 */
package accommodationsystem.gui;

import accommodationsystem.AccommodationSystem;
import accommodationsystem.bases.GUI;
import accommodationsystem.library.User;
import accommodationsystem.library.LeaseData;
import accommodationsystem.library.Database;
import accommodationsystem.library.Lease.CleaningStatus;
import accommodationsystem.library.Permissions;
import com.sun.javafx.tk.Toolkit;
import com.sun.javafx.tk.FontMetrics;
import java.text.MessageFormat;
import java.util.Optional;
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

/**
 *
 * @author simpl_000
 */
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
            btnDeleteLease;
    ComboBox comboChangeCleanStatus;
    private int _currentHallView = -1;
    
    // Table Context Menu
    ContextMenu rightClickMenu;
    MenuItem miViewLease,
            miUpdateLease,
            miDeleteLease,
            miCheckLeaseDuration,
            miViewStudent,
            miHallLocation;
    
    // GUI Size (default: 800)
    private int _size_xy = 800;
    private boolean justSelectedNewItem = false;
    
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
        ComboBox cbHalls;
        Button btnAdminPanel = new Button();
        Button btnLogout = new Button();
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
        headerSpacer.setPrefWidth(50);
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
        cbHalls.setValue("All");
        cbHalls.setPrefWidth(150.0);
        cbHalls.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> cbHalls_Changed(cbHalls, options, oldValue, newValue));
        // Administrator Panel
        btnAdminPanel.getStyleClass().add("button");
        btnAdminPanel.setText("Administrator Panel");
        btnAdminPanel.setAlignment(Pos.CENTER);
        btnAdminPanel.setOnAction((ActionEvent e) -> btnAdminPanel_Click(e));
        // Logout Button
        btnLogout.setId("logoutBtn");
        btnLogout.setText("Logout");
        btnLogout.setVisible(true);
        btnLogout.setAlignment(Pos.CENTER);
        btnLogout.setOnAction((ActionEvent e) -> btnLogout_Click(e));
        
        /**
         * Compile Elements
         */
        // Logged In As Label
        loggedInAs.getChildren().addAll(lblLoggedInAs, lblLoggedInUsername, lblViewPermissions, lblSpacer);
        // Button Strip
        // ComboBox
        btnStrip.getChildren().add(cbHalls);
        if(User.hasPermission(Permissions.ADMIN_PANEL)) btnStrip.getChildren().add(btnAdminPanel);
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
     * @desc    Handles the Click event for the View Permissions button
     * @param   event 
     */
    private void btnViewPerms_Click(ActionEvent event) {
        // Open up View Permissions GUI
        try {
            new ViewPermissions().getStage().showAndWait();
        } catch(Exception e) { }
    }
    
    /**
     * @name    btnAdminPanel_Click
     * @desc    Handles the Click event for the Administrator Panel button
     * @param   event 
     */
    private void btnAdminPanel_Click(ActionEvent event) {
        // Open up Administrator Panel GUI
        try {
            new AdminPanel().getStage().showAndWait();
        } catch(Exception e) { }
    }
    
    /**
     * @name    btnLogout_Click
     * @desc    Handles the Click event for the logout button
     * @param   event 
     */
    private void btnLogout_Click(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to logout?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
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
         * Cast Objects to Strings
         */
        String oldVal = (String)oldValue;
        String newVal = (String)newValue;
        
        // Debug
        AccommodationSystem.debug("Old Value: " + oldVal + " | New Value: " + newVal + " ---> " + cb.getSelectionModel().getSelectedIndex());
        
        /**
         * Update TableView to match ComboBox value
         */
        this.buildTable(cb.getSelectionModel().getSelectedIndex());
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
        /*if(justSelectedNewItem) {
            justSelectedNewItem = false;
            return;
        }*/
        
        /**
         * Cast Objects to Strings
         */
        String oldVal = (String)oldValue;
        String newVal = (String)newValue;
        
        // Debug
        AccommodationSystem.debug("Old Value: " + oldVal + " | New Value: " + newVal + " ---> " + comboChangeCleanStatus.getSelectionModel().getSelectedIndex());
        
        // Check if we're trying to view a lease that does not exist (should not occur)
        if(tbl.getSelectionModel().isEmpty())
            return;
        
        // Get LeaseData
        LeaseData lease = tbl.getSelectionModel().getSelectedItem();
        
        /**
         * Check ComboBox Data
         */
        if(!User.hasPermission(Permissions.MANAGE_OFFLINE) && newVal.equals("Offline")) {
            justSelectedNewItem = true;
            new Alert(Alert.AlertType.ERROR, "You do not have permissions to set a lease status to Offline.", ButtonType.OK).showAndWait();
            Platform.runLater(() -> comboChangeCleanStatus.setValue(lease.getCleanStatusName()));
        } else {
            // Check if trying to set Lease to Offline when lease is present
            if(newVal.equals("Offline") && lease.getLeaseId() != null) {
                new Alert(Alert.AlertType.ERROR, "Cannot set lease status to Offline when a lease is present.", ButtonType.OK).showAndWait();
                Platform.runLater(() -> comboChangeCleanStatus.setValue(lease.getCleanStatusName()));
            } else {
                /**
                * Update Lease Data
                */
               if(!lease.getCleanStatusName().equals(newVal)) {
                   lease.setCleanStatus(new SimpleIntegerProperty((int)CleaningStatus.getId((String)newVal)));
                   Database.updateRoom(lease);
                   this.buildTable(_currentHallView);
               }
            }
        }
    }
    
    private void tbl_RightClicked(MouseEvent e) {
        
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
            miHallLocation = new MenuItem("Get Hall Details");
            rightClickMenu.getItems().addAll(miViewLease, miUpdateLease, miDeleteLease, miSeparator, miCheckLeaseDuration, miViewStudent, miHallLocation);
            
            /**
             * Menu Item Listeners
             */
            miViewLease.setOnAction((ActionEvent e) -> btnViewLease_Click(e));
            miUpdateLease.setOnAction((ActionEvent e) -> btnCreateLease_Click(e));
            miDeleteLease.setOnAction((ActionEvent e) -> btnDeleteLease_Click(e));
            miCheckLeaseDuration.setOnAction((ActionEvent e) -> mi_CheckLeaseDuration(e));
            miViewStudent.setOnAction((ActionEvent e) -> mi_ViewStudent(e));
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
    
    private void tbl_Click(MouseEvent event) {
        // Validate selected row
        if(tbl.getSelectionModel().isEmpty())
            return;
        
        // Check if we've double clicked
        if(event.getClickCount() >= 2) {
            btnViewLease_Click(null);
        }
        
        // Check if we've right clicked
        if(event.getButton() == MouseButton.SECONDARY) {
            rightClickMenu.show(tbl, event.getScreenX(), event.getScreenY());
        }
    }
    
    private void mi_CheckLeaseDuration(ActionEvent e) {
        // Get our selected item
        LeaseData lease = tbl.getSelectionModel().getSelectedItem();
        
        // Check Lease Status
        if(lease.getLeaseId() == null) {
            new Alert(Alert.AlertType.ERROR, "Cannot check lease duration for a lease that does not exist.", ButtonType.OK).showAndWait();
            return;
        }
        
        /**
         * Rollback to Commit #341dec8d88edba7de831ba911629c8d40669b06a for Lease Duration in seconds
         * Changed to Lease Duration for "Months"
         */
        
        // Show Alert (Message) - getLeaseId() is formatted to a String to avoid the Message Formatter adding commas
        String m = MessageFormat.format("The duration of Lease #{0} is {1} months.", lease.getLeaseId().toString(), lease.getDuration());
        Alert checkLeaseDur = new Alert(Alert.AlertType.INFORMATION, m, ButtonType.OK);
        checkLeaseDur.showAndWait();
    }
    
    private void mi_ViewStudent(ActionEvent e) {
        // Get our selected item
        LeaseData lease = tbl.getSelectionModel().getSelectedItem();
        
        // Check Lease Status
        if(lease.getLeaseId() == null) {
            new Alert(Alert.AlertType.ERROR, "Cannot view student for a lease that does not exist.", ButtonType.OK).showAndWait();
            return;
        }
        
        // Show Alert (Message)
        Alert viewStudentInfo = new Alert(Alert.AlertType.INFORMATION, "Student ID: {}\nStudent Name: {}\n\n{} lives in Room {}, Flat {}, {}.", ButtonType.OK);
        viewStudentInfo.showAndWait();
    }
    
    private void mi_HallLocation(ActionEvent e) {
        // Get our selected item
        LeaseData lease = tbl.getSelectionModel().getSelectedItem();
        
        // Show Alert (Message)
        String m = MessageFormat.format("This lease exists within {0}.\n\nName: {0}\nNumber: {1}\nAddress: {2}\nPostcode: {3}\nTelephone: {4}",
                lease.getHallName(), lease.getHall().getId(), lease.getHall().getAddress(), lease.getHall().getPostcode(), lease.getHall().getPhone());
        Alert hallDetails = new Alert(Alert.AlertType.INFORMATION, m, ButtonType.OK);
        hallDetails.showAndWait();
    }
    
    private void tbl_Select(Object obs, Object oldSelection, Object newSelection) {
        // Enable or Disable our Footer Buttons
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
        
        // Enable or Disable our Right Click Menu Items
        rightClickMenu.getItems().stream().forEach((MenuItem i) -> {
            i.setDisable((newSelection == null));
        });
        
        // Get our selected item
        LeaseData lease = tbl.getSelectionModel().getSelectedItem();
        
        // Check if lease is valid
        if(lease == null) return;
        
        // Enable or Disable our Right Click Menu Items
        miViewLease.setDisable(false);
        miUpdateLease.setDisable((lease.getLeaseId() == null && lease.getCleanStatusName().equals("Offline")));
        miDeleteLease.setDisable((lease.getLeaseId() == null));
        miCheckLeaseDuration.setDisable((lease.getLeaseId() == null));
        miViewStudent.setDisable((lease.getLeaseId() == null));
        miHallLocation.setDisable(false);
        
        // Debug
        AccommodationSystem.debug("Selected Lease: " + lease.getLeaseId());
        
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
        comboChangeCleanStatus.setDisable(lease.getCleanStatusName().equals("Offline") && !User.hasPermission(Permissions.MANAGE_OFFLINE));
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
        this.buildTable(0);
        
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
    
    private void btnCreateLease_Click(ActionEvent event) {
        // Check if we're trying to view a lease that does not exist (should not occur)
        if(tbl.getSelectionModel().isEmpty())
            return;
        
        // Get LeaseData
        LeaseData lease = tbl.getSelectionModel().getSelectedItem();
        
        // Open our View Lease GUI
        try {
            // Verify Lease Cleaning Status
            if(lease.getCleanStatusName().equals("Offline")) {
                new Alert(Alert.AlertType.ERROR, "Cannot create a lease when the room status is Offline.", ButtonType.OK).show();
                return;
            }
            
            Integer leaseId = lease.getLeaseId();
            AccommodationSystem.debug("Creating Lease: " + (leaseId == null ? 0 : leaseId));
            
            // Modal
            Stage viewLease = new ViewLease(this, lease, (btnCreateLease.textProperty().get().equals("Create Lease") ? "create" : "update")).getStage();
            viewLease.initOwner(this.getScene().getWindow());
            viewLease.initModality(Modality.APPLICATION_MODAL);
            viewLease.showAndWait();
            
        } catch(Exception e) { }
    }
    
    private void btnViewLease_Click(ActionEvent event) {
        // Check if we're trying to view a lease that does not exist (should not occur)
        if(tbl.getSelectionModel().isEmpty())
            return;
        
        // Get LeaseData
        LeaseData lease = tbl.getSelectionModel().getSelectedItem();
        
        // Open our View Lease GUI
        try {
            Integer leaseId = lease.getLeaseId();
            AccommodationSystem.debug("Viewing Lease: " + (leaseId == null ? 0 : leaseId));
            
            // Modal
            Stage viewLease = new ViewLease(this, lease, "view").getStage();
            viewLease.initOwner(this.getScene().getWindow());
            viewLease.initModality(Modality.APPLICATION_MODAL);
            viewLease.showAndWait();
            
        } catch(Exception e) { }
    }
    
    private void btnDeleteLease_Click(ActionEvent event) {
        // Check if we're trying to view a lease that does not exist (should not occur)
        if(tbl.getSelectionModel().isEmpty())
            return;
        
        // Get LeaseData
        LeaseData lease = tbl.getSelectionModel().getSelectedItem();
        
        // Check if our lease exists
        if(lease.getLeaseId() == null) {
            new Alert(Alert.AlertType.ERROR, "Unable to delete a lease that does not exist.", ButtonType.OK).show();
            return;
        }
            
        // Open our View Lease GUI
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
