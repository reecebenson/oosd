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
import accommodationsystem.library.Permissions;
import com.sun.javafx.tk.Toolkit;
import com.sun.javafx.tk.FontMetrics;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    FlowPane footerBox = new FlowPane(Orientation.HORIZONTAL, 15, 15);
    
    // Table
    TableView<LeaseData> tbl = new TableView<>();
    
    // GUI Size (default: 800)
    private int _size_xy = 800;
    
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
        // Table Listeners
        if(!super.hasFinalised()) {
            tbl.setOnMouseClicked((MouseEvent e) -> tbl_Click(e));
            tbl.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> tbl_Select(obs, oldSelection, newSelection));
        }
        // Cell Value Factory
        leaseNumber.setCellValueFactory(new PropertyValueFactory<>("LeaseId"));
        hallName.setCellValueFactory(new PropertyValueFactory<>("HallName"));
        flatNumber.setCellValueFactory(new PropertyValueFactory<>("FlatNumber"));
        roomNumber.setCellValueFactory(new PropertyValueFactory<>("RoomNumber"));
        studentName.setCellValueFactory(new PropertyValueFactory<>("StudentName"));
        occupancyStatus.setCellValueFactory(new PropertyValueFactory<>("OccupiedStatus"));
        cleaningStatus.setCellValueFactory(new PropertyValueFactory<>("CleanStatusName"));
        tbl.getItems().setAll(Database.getLeases(hallNumber));
        
        /**
         * Compile Elements
         */
        tbl.getColumns().addAll(leaseNumber, hallName, flatNumber, roomNumber, studentName, occupancyStatus, cleaningStatus);
        tbl.getColumns().stream().forEach((TableColumn c) -> c.impl_setReorderable(false)); // TEMP -- DISABLES COLUMN REORDERING
        
        /**
         * Style Table Elements
         */
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
    }
    
    private void tbl_Select(Object obs, Object oldSelection, Object newSelection) {
        // Enable or Disable our Footer Buttons
        footerBox.getChildren().stream().forEach((Node n) -> {
            if(n instanceof Button) {
                Button b = (Button)n;
                b.setDisable((newSelection == null));
            }
        });
        
        // Stop execution of code if we have nothing selected
        if(newSelection == null)
            return;
        
        // Get our selected item
        LeaseData lease = tbl.getSelectionModel().getSelectedItem();
        AccommodationSystem.debug("Selected Lease: " + lease.getLeaseId());
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
        Button btnViewLease;
        
        /**
         * Initialise Elements
         */
        btnViewLease = new Button();
        
        /**
         * Style Elements
         */
        // Headers
        footerBox.setPadding(new Insets(20, 20, 20, 20));
        footerBox.setMinHeight(100);
        footerBox.setId("actionPane");
        // Buttons
        btnViewLease.setText("View Lease");
        btnViewLease.setDisable(true); // Disabled by default, waiting for user to click an element on the TableView
        btnViewLease.setOnAction((ActionEvent e) -> btnViewLease_Click(e));
        
        /**
         * Compile Elements
         */
        footerBox.getChildren().add(btnViewLease);
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
            Stage viewLease = new ViewLease(this, lease).getStage();
            viewLease.initOwner(this.getScene().getWindow());
            viewLease.initModality(Modality.APPLICATION_MODAL);
            viewLease.showAndWait();
            
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
            while(source != null && !(source instanceof TableRow) && !(source instanceof Button))
                // Recursively get the parent of the Node until condition is met
                source = source.getParent();
            
            // Check if the selected Node is null, or if a TableRow is selected but however empty, clear the selection
            if(source == null || (source instanceof TableRow && ((TableRow) source).isEmpty()))
                tbl.getSelectionModel().clearSelection();
        });
    }
    
    @Override
    public String formId() {
        return this.getClass().getSimpleName();
    }
}
