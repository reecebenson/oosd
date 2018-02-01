/*
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 */
package accommodationsystem.gui;

import accommodationsystem.AccommodationSystem;
import accommodationsystem.bases.GUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author simpl_000
 */
public class AdminPanel extends GUI {
    /**
     * Variables
     */
    // Panes
    HBox headerBox = new HBox();
    ScrollPane contentOuterBox = new ScrollPane();
    TabPane contentBox = new TabPane();
    // Tabs
    Tab mainTab, userTab, hallsTab, roomsTab, studentsTab, permissionsTab;
    
    /**
     * @name    buildHeader
     * @desc    Create, style and build the Header of the "AdminPanel" GUI
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
     * @name    buildMainTab
     * @desc    Builds the "Main" tab
     */
    private void buildMainTab() {
        /**
         * Declare and Initialise Elements
         */
        BorderPane tContent = new BorderPane();
        GridPane tCenter = new GridPane();
        AnchorPane tBottom = new AnchorPane();
        Button updateButton = new Button();
        HBox tBottomBtnStrip = new HBox();
        
        /**
         * Style Elements
         */
        // Content
        tContent.setPadding(new Insets(20, 20, 20, 20));
        tContent.getStyleClass().add("dark-bg-content");
        tCenter.setStyle("-fx-background-color: black");
        // Buttons
        updateButton.setText("Update");
        // Anchors
        AnchorPane.setRightAnchor(tBottomBtnStrip, 0d);
        AnchorPane.setBottomAnchor(tBottomBtnStrip, 0d);
        
        /**
         * Compile Elements
         */
        tBottomBtnStrip.getChildren().addAll(updateButton);
        tBottom.getChildren().addAll(tBottomBtnStrip);
        tContent.setCenter(tCenter);
        tContent.setBottom(tBottom);
        mainTab.setContent(tContent);
    }
    
    /**
     * @name    buildUserTab
     * @desc    Builds the "User" tab
     */
    private void buildUserTab() {
        /**
         * Declare and Initialise Elements
         */
        BorderPane tContent = new BorderPane();
        GridPane tCenter = new GridPane();
        AnchorPane tBottom = new AnchorPane();
        Button testButton = new Button();
        
        /**
         * Style Elements
         */
        tContent.setPadding(new Insets(20, 20, 20, 20));
        tContent.getStyleClass().add("dark-bg-content");
        testButton.setText("Test!");
        
        /**
         * Compile Elements
         */
        tCenter.add(testButton, 0, 0);
        tContent.setCenter(tCenter);
        tContent.setBottom(tBottom);
        userTab.setContent(tContent);
    }
    
    /**
     * @name    buildHallsTab
     * @desc    Builds the "Halls" tab
     */
    private void buildHallsTab() {
        /**
         * Declare and Initialise Elements
         */
        BorderPane tContent = new BorderPane();
        GridPane tCenter = new GridPane();
        AnchorPane tBottom = new AnchorPane();
        Button testButton = new Button();
        
        /**
         * Style Elements
         */
        tContent.setPadding(new Insets(20, 20, 20, 20));
        tContent.getStyleClass().add("dark-bg-content");
        testButton.setText("Test!");
        
        /**
         * Compile Elements
         */
        tCenter.add(testButton, 0, 0);
        tContent.setCenter(tCenter);
        tContent.setBottom(tBottom);
        hallsTab.setContent(tContent);
    }
    
    /**
     * @name    buildRoomsTab
     * @desc    Builds the "Rooms" tab
     */
    private void buildRoomsTab() {
        /**
         * Declare and Initialise Elements
         */
        BorderPane tContent = new BorderPane();
        GridPane tCenter = new GridPane();
        AnchorPane tBottom = new AnchorPane();
        Button testButton = new Button();
        
        /**
         * Style Elements
         */
        tContent.setPadding(new Insets(20, 20, 20, 20));
        tContent.getStyleClass().add("dark-bg-content");
        testButton.setText("Test!");
        
        /**
         * Compile Elements
         */
        tCenter.add(testButton, 0, 0);
        tContent.setCenter(tCenter);
        tContent.setBottom(tBottom);
        roomsTab.setContent(tContent);
    }
    
    /**
     * @name    buildStudentsTab
     * @desc    Builds the "Students" tab
     */
    private void buildStudentsTab() {
        /**
         * Declare and Initialise Elements
         */
        BorderPane tContent = new BorderPane();
        GridPane tCenter = new GridPane();
        AnchorPane tBottom = new AnchorPane();
        Button testButton = new Button();
        
        /**
         * Style Elements
         */
        tContent.setPadding(new Insets(20, 20, 20, 20));
        tContent.getStyleClass().add("dark-bg-content");
        testButton.setText("Test!");
        
        /**
         * Compile Elements
         */
        tCenter.add(testButton, 0, 0);
        tContent.setCenter(tCenter);
        tContent.setBottom(tBottom);
        studentsTab.setContent(tContent);
    }
    
    /**
     * @name    buildPermissionsTab
     * @desc    Builds the "Permissions" tab
     */
    private void buildPermissionsTab() {
        /**
         * Declare and Initialise Elements
         */
        BorderPane tContent = new BorderPane();
        GridPane tCenter = new GridPane();
        AnchorPane tBottom = new AnchorPane();
        Button testButton = new Button();
        
        /**
         * Style Elements
         */
        tContent.setPadding(new Insets(20, 20, 20, 20));
        tContent.getStyleClass().add("dark-bg-content");
        testButton.setText("Test!");
        
        /**
         * Compile Elements
         */
        tCenter.add(testButton, 0, 0);
        tContent.setCenter(tCenter);
        tContent.setBottom(tBottom);
        permissionsTab.setContent(tContent);
    }
    
    /**
     * @name    buildContent
     * @desc    Create, style and build the Content of the "AdminPanel" GUI
     */
    private void buildContent() {
        /**
         * Initialise Elements
         */
        mainTab = new Tab();
        userTab = new Tab();
        hallsTab = new Tab();
        roomsTab = new Tab();
        studentsTab = new Tab();
        permissionsTab = new Tab();
        
        /**
         * Style Elements
         */
        // Panes
        contentOuterBox.setId("contentBox");
        contentOuterBox.setFitToWidth(true);
        contentOuterBox.setFitToHeight(true);
        // TabPane (Disable users access from 'Closing' tabs)
        contentBox.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        // Tabs
        mainTab.setText("Main");
        userTab.setText("Users");
        hallsTab.setText("Halls");
        roomsTab.setText("Rooms");
        studentsTab.setText("Students");
        permissionsTab.setText("Permissions");
        
        /**
         * Build Tabs
         */
        this.buildMainTab();
        this.buildUserTab();
        this.buildHallsTab();
        this.buildRoomsTab();
        this.buildStudentsTab();
        this.buildPermissionsTab();
        
        /**
         * Compile Elements
         */
        contentBox.getTabs().addAll(mainTab, userTab, hallsTab, roomsTab, studentsTab, permissionsTab);
        contentOuterBox.setContent(contentBox);
    }
    
    
    /**
     * @name    Default Constructor
     * @desc    Initialise the AdminPanel GUI
     * @throws  Exception 
     */
    public AdminPanel() throws Exception {
        /**
         * Debug
         */
        AccommodationSystem.debug("Loaded '" + this.getClass().getName() + "'");
        
        /**
         * Build "AdminPanel" GUI
         */
        this.buildHeader();
        this.buildContent();
        
        /**
         * Add our GUI Elements (hierarchy)
         */
        super.getPane().setTop(headerBox);
        super.getPane().setCenter(contentOuterBox);
        
        /**
         * Finalise our GUI
         */
        super.setTitle("Administrator Panel");
        super.setSize(750, 750);
        super.finalise(true);
    }
    
    @Override
    public String formId() {
        return this.getClass().getSimpleName();
    }
}
