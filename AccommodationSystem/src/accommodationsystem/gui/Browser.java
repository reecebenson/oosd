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
import com.sun.javafx.tk.Toolkit;
import com.sun.javafx.tk.FontMetrics;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        Button btnViewPerms = new Button();
        Button btnLogout = new Button();
        // Images
        Image logo = new Image(this.getClass().getClassLoader().getResourceAsStream("accommodationsystem/resources/images/logo.png"));
        ImageView logoView = new ImageView(logo);
        // Text
        TextFlow loggedInAs = new TextFlow();
        Text lblLoggedInAs, lblLoggedInUsername;
        
        /**
         * Style Elements
         */
        // Headers
        headerBox.setPadding(new Insets(25, 25, 25, 25));
        headerBox.setId("topBox");
        headerLeft.setAlignment(Pos.CENTER_LEFT);
        headerRight.setAlignment(Pos.TOP_RIGHT);
        headerSpacer.setPrefWidth(100);
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
        lblLoggedInUsername = new Text(" " + User.getUsername() + "\n");
        lblLoggedInUsername.setFill(Color.WHITE);
        lblLoggedInUsername.setStyle("-fx-font-weight: bold");
        // Hall Selector
        cbHalls = new ComboBox(Database.getHallNames(true));
        cbHalls.setId("hallSelector");
        cbHalls.setStyle("-fx-text-fill: white");
        cbHalls.setPadding(new Insets(11, 5, 11, 5));
        cbHalls.setValue("All");
        cbHalls.setPrefWidth(150.0);
        cbHalls.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> cbHalls_Changed(cbHalls, options, oldValue, newValue));
        // View Permissions
        btnViewPerms.getStyleClass().add("button");
        btnViewPerms.setText("View Permissions");
        btnViewPerms.setAlignment(Pos.CENTER);
        // Logout Button
        btnLogout.setId("logoutBtn");
        btnLogout.setText("Logout");
        btnLogout.setVisible(true);
        btnLogout.setAlignment(Pos.CENTER);
        btnLogout.setOnAction((ActionEvent e) -> btnLogout_Click(e));
        
        /**
         * Compile Elements
         */
        loggedInAs.getChildren().addAll(lblLoggedInAs, lblLoggedInUsername);
        btnStrip.getChildren().addAll(cbHalls, btnViewPerms, btnLogout);
        headerLeft.getChildren().add(logoView);
        headerRight.getChildren().addAll(loggedInAs, btnStrip);
        headerBox.getChildren().addAll(headerLeft, headerSpacer, headerRight);
    }
    
    /**
     * @name    btnLogout_Click
     * @desc    Handles the Click event for the logout button
     * @param   event 
     */
    private void btnLogout_Click(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, User.getUsername() + ", you have been successfully logged out.", ButtonType.OK);
        alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> {
            // Open up Login GUI
            try {
                User.logout();
                new Login().show();
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
        // Cast Objects to Strings
        String oldVal = (String)oldValue;
        String newVal = (String)newValue;
        
        // Debug
        AccommodationSystem.debug("Old Value: " + oldVal + " | New Value: " + newVal + " ---> " + cb.getSelectionModel().getSelectedIndex());
    }
    
    /**
     * @name    buildTable
     * @desc    Create the Table of the "Browser" GUI
     */
    private void buildTable() {
        /**
         * Declare Elements
         */
        TableColumn leaseNumber,
                hallId,
                roomNumber,
                studentName,
                occupancyStatus,
                cleaningStatus;
        
        /**
         * Initialise Elements
         */
        leaseNumber         = new TableColumn("Lease Number");
        hallId              = new TableColumn("Hall ID");
        roomNumber          = new TableColumn("Room Number");
        studentName         = new TableColumn("Student Name");
        occupancyStatus     = new TableColumn("Occupancy Status");
        cleaningStatus      = new TableColumn("Cleaning Status");
        
        /**
         * Set Table Properties
         */
        leaseNumber.setCellValueFactory(new PropertyValueFactory<>("LeaseId"));
        hallId.setCellValueFactory(new PropertyValueFactory<>("HallId"));
        roomNumber.setCellValueFactory(new PropertyValueFactory<>("RoomNumber"));
        studentName.setCellValueFactory(new PropertyValueFactory<>("StudentName"));
        occupancyStatus.setCellValueFactory(new PropertyValueFactory<>("OccupiedStatus"));
        cleaningStatus.setCellValueFactory(new PropertyValueFactory<>("CleanStatusName"));
        tbl.getItems().setAll(Database.getLeases());
        
        /**
         * Compile Elements
         */
        tbl.getColumns().addAll(leaseNumber, hallId, roomNumber, studentName, occupancyStatus, cleaningStatus);
        
        /**
         * Style Elements
         */
        // Reset XY Size
        _size_xy = 0;
        
        // Update Column Widths
        FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(new Font("Arial", 14));
        for(int i = 0; i < tbl.getColumns().size(); i++) {
            // Compute Text Size and Set Column Width
            double textWidth = fontMetrics.computeStringWidth(tbl.getColumns().get(i).getText());
            tbl.getColumns().get(i).setPrefWidth(textWidth + 40);
            
            // Update XY Size
            _size_xy += (textWidth + 40);
        }
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
        this.buildTable();
        
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
         * Style Elements
         */
        footerBox.setPadding(new Insets(20, 20, 20, 20));
        footerBox.setMinHeight(175);
        footerBox.setId("actionPane");
        
        /**
         * Initialise Elements (temp)
         */
        for(int x = 0; x < 5; x++) {
            Button _tBtn = new Button();
            _tBtn.setText("Button " + String.valueOf(x + 1));
            _tBtn.setVisible(true);
            _tBtn.getStyleClass().add("button");
            
            // Compile Elements
            footerBox.getChildren().add(_tBtn);
        }
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
        super.finalise(true);
    }
    
    @Override
    public String formId() {
        return this.getClass().getSimpleName();
    }
}
