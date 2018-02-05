/**
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 *
 * TODO:
 * - Disable all buttons until a table item has been selected OR show an Error Alert when users click a button and a TableRow is not selected
 * - Allow Administrators to create/edit/delete users/rooms/halls/students
 */

package accommodationsystem.gui;

import accommodationsystem.AccommodationSystem;
import accommodationsystem.bases.GUI;
import accommodationsystem.library.Database;
import accommodationsystem.library.Table.HallRow;
import accommodationsystem.library.Table.RoomRow;
import accommodationsystem.library.Table.StudentRow;
import accommodationsystem.library.Table.UserRow;
import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

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
    Tab mainTab, userTab, hallsTab, roomsTab, studentsTab;
    
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
        tBottomBtnStrip.setPadding(new Insets(20, 20, 20, 20));
        // Buttons
        updateButton.setText("Update");
        // Anchors
        tBottomBtnStrip.setAlignment(Pos.CENTER);
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
        ScrollPane tCenter = new ScrollPane();
        AnchorPane tBottom = new AnchorPane();
        Button createButton = new Button();
        Button editButton = new Button();
        Button deleteButton = new Button();
        FlowPane tBottomBtnStrip = new FlowPane(Orientation.HORIZONTAL, 5, 5);
        TableView<UserRow> tbl = new TableView<>();
        TableColumn userId = new TableColumn("ID");
        TableColumn userName = new TableColumn("Username");
        TableColumn userPass = new TableColumn("Password");
        TableColumn userRank = new TableColumn("Rank");
        TableColumn userPerms = new TableColumn("Permissions");
        
        /**
         * Style Elements
         */
        // Content
        tContent.getStyleClass().add("dark-bg-content");
        tBottomBtnStrip.setPadding(new Insets(20, 20, 20, 20));
        tCenter.setFitToWidth(true);
        tCenter.setFitToHeight(true);
        // Buttons
        createButton.setText("Create User");
        editButton.setText("Edit User");
        deleteButton.setText("Delete User");
        // Anchors
        tBottomBtnStrip.setAlignment(Pos.CENTER);
        AnchorPane.setRightAnchor(tBottomBtnStrip, 0d);
        AnchorPane.setBottomAnchor(tBottomBtnStrip, 0d);
        
        /**
         * Set Table Properties
         */
        ObservableList<UserRow> users = FXCollections.observableArrayList();
        Database.getUsersAsRow().stream().forEach((user) -> {
            users.add(new UserRow(user.getId(), user.getUsername(), user.getPassword(), user.getRank()));
        });
        userId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        userName.setCellValueFactory(new PropertyValueFactory<>("Username"));
        userPass.setCellValueFactory(new PropertyValueFactory<>("Password"));
        userRank.setCellValueFactory(new PropertyValueFactory<>("Rank"));
        userPerms.setCellValueFactory(new PropertyValueFactory<>("Perms"));
        tbl.setItems(users);
        
        /**
         * Compile Elements
         */
        tbl.getColumns().addAll(userId, userName, userPass, userRank, userPerms);
        tbl.getColumns().stream().forEach((TableColumn c) -> c.impl_setReorderable(false)); // TEMP -- DISABLES COLUMN REORDERING
        
        /**
         * Style Table Elements
         */
        // Update Column Widths
        FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(new Font("Arial", 14));
        for(int i = 0; i < tbl.getColumns().size(); i++) {
            // Compute Text Size and Set Column Width
            double textWidth = fontMetrics.computeStringWidth(tbl.getColumns().get(i).getText());
            tbl.getColumns().get(i).setPrefWidth(textWidth + 40);
        }
        
        // Update Permissions Column Width
        userPerms.setPrefWidth(250);
        
        /**
         * Compile Elements
         */
        tCenter.setContent(tbl);
        tBottomBtnStrip.getChildren().addAll(createButton, editButton, deleteButton);
        tBottom.getChildren().addAll(tBottomBtnStrip);
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
        ScrollPane tCenter = new ScrollPane();
        AnchorPane tBottom = new AnchorPane();
        Button createButton = new Button();
        Button editButton = new Button();
        Button deleteButton = new Button();
        FlowPane tBottomBtnStrip = new FlowPane(Orientation.HORIZONTAL, 5, 5);
        TableView<HallRow> tbl = new TableView<>();
        TableColumn hallId = new TableColumn("ID");
        TableColumn hallName = new TableColumn("Name");
        TableColumn hallShortName = new TableColumn("Short Name");
        TableColumn hallAddress = new TableColumn("Address");
        TableColumn hallPostcode = new TableColumn("Post Code");
        TableColumn hallPhone = new TableColumn("Phone");
        TableColumn hallRoomCount = new TableColumn("Room Count");
        
        /**
         * Style Elements
         */
        // Content
        tContent.getStyleClass().add("dark-bg-content");
        tBottomBtnStrip.setPadding(new Insets(20, 20, 20, 20));
        tCenter.setFitToWidth(true);
        tCenter.setFitToHeight(true);
        // Buttons
        createButton.setText("Create Hall");
        editButton.setText("Edit Hall");
        deleteButton.setText("Delete Hall");
        // Anchors
        tBottomBtnStrip.setAlignment(Pos.CENTER);
        AnchorPane.setRightAnchor(tBottomBtnStrip, 0d);
        AnchorPane.setBottomAnchor(tBottomBtnStrip, 0d);
        
        /**
         * Set Table Properties
         */
        ObservableList<HallRow> halls = FXCollections.observableArrayList();
        Database.getHallsAsRow().stream().forEach((h) -> {
            halls.add(new HallRow(h.getHallId(), h.getHallName(), h.getHallShortName(), h.getHallAddress(), h.getHallPostcode(), h.getHallPhone(), h.getHallRoomCount()));
        });
        hallId.setCellValueFactory(new PropertyValueFactory<>("HallId"));
        hallName.setCellValueFactory(new PropertyValueFactory<>("HallName"));
        hallShortName.setCellValueFactory(new PropertyValueFactory<>("HallShortName"));
        hallAddress.setCellValueFactory(new PropertyValueFactory<>("HallAddress"));
        hallPostcode.setCellValueFactory(new PropertyValueFactory<>("HallPostcode"));
        hallPhone.setCellValueFactory(new PropertyValueFactory<>("HallPhone"));
        hallRoomCount.setCellValueFactory(new PropertyValueFactory<>("HallRoomCount"));
        tbl.setItems(halls);
        
        /**
         * Compile Elements
         */
        tbl.getColumns().addAll(hallId, hallName, hallShortName, hallAddress, hallPostcode, hallPhone, hallRoomCount);
        tbl.getColumns().stream().forEach((TableColumn c) -> c.impl_setReorderable(false)); // TEMP -- DISABLES COLUMN REORDERING
        
        /**
         * Style Table Elements
         */
        // Update Column Widths
        FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(new Font("Arial", 14));
        for(int i = 0; i < tbl.getColumns().size(); i++) {
            // Compute Text Size and Set Column Width
            double textWidth = fontMetrics.computeStringWidth(tbl.getColumns().get(i).getText());
            tbl.getColumns().get(i).setPrefWidth(textWidth + 40);
        }
        
        /**
         * Compile Elements
         */
        tCenter.setContent(tbl);
        tBottomBtnStrip.getChildren().addAll(createButton, editButton, deleteButton);
        tBottom.getChildren().addAll(tBottomBtnStrip);
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
        ScrollPane tCenter = new ScrollPane();
        AnchorPane tBottom = new AnchorPane();
        Button createButton = new Button();
        Button editButton = new Button();
        Button deleteButton = new Button();
        FlowPane tBottomBtnStrip = new FlowPane(Orientation.HORIZONTAL, 5, 5);
        TableView<RoomRow> tbl = new TableView<>();
        TableColumn roomId = new TableColumn("Room ID");
        TableColumn hallId = new TableColumn("Hall ID");
        TableColumn occupied = new TableColumn("Occupancy Status");
        TableColumn cleanStatus = new TableColumn("Clean Status");
        
        /**
         * Style Elements
         */
        // Content
        tContent.getStyleClass().add("dark-bg-content");
        tBottomBtnStrip.setPadding(new Insets(20, 20, 20, 20));
        tCenter.setFitToWidth(true);
        tCenter.setFitToHeight(true);
        // Buttons
        createButton.setText("Create Room");
        editButton.setText("Edit Room");
        deleteButton.setText("Delete Room");
        // Anchors
        tBottomBtnStrip.setAlignment(Pos.CENTER);
        AnchorPane.setRightAnchor(tBottomBtnStrip, 0d);
        AnchorPane.setBottomAnchor(tBottomBtnStrip, 0d);
        
        /**
         * Set Table Properties
         */
        ObservableList<RoomRow> rooms = FXCollections.observableArrayList();
        Database.getRoomsAsRow().stream().forEach((r) -> {
            rooms.add(new RoomRow(r.getRoomId(), r.getHallId(), r.getOccupied(), r.getCleanStatus()));
        });
        roomId.setCellValueFactory(new PropertyValueFactory<>("RoomId"));
        hallId.setCellValueFactory(new PropertyValueFactory<>("HallId"));
        occupied.setCellValueFactory(new PropertyValueFactory<>("Occupied"));
        cleanStatus.setCellValueFactory(new PropertyValueFactory<>("CleanStatus"));
        tbl.setItems(rooms);
        
        /**
         * Compile Elements
         */
        tbl.getColumns().addAll(roomId, hallId, occupied, cleanStatus);
        tbl.getColumns().stream().forEach((TableColumn c) -> c.impl_setReorderable(false)); // TEMP -- DISABLES COLUMN REORDERING
        
        /**
         * Style Table Elements
         */
        // Update Column Widths
        FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(new Font("Arial", 14));
        for(int i = 0; i < tbl.getColumns().size(); i++) {
            // Compute Text Size and Set Column Width
            double textWidth = fontMetrics.computeStringWidth(tbl.getColumns().get(i).getText());
            tbl.getColumns().get(i).setPrefWidth(textWidth + 40);
        }
        
        /**
         * Compile Elements
         */
        tCenter.setContent(tbl);
        tBottomBtnStrip.getChildren().addAll(createButton, editButton, deleteButton);
        tBottom.getChildren().addAll(tBottomBtnStrip);
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
        ScrollPane tCenter = new ScrollPane();
        AnchorPane tBottom = new AnchorPane();
        Button createButton = new Button();
        Button editButton = new Button();
        Button deleteButton = new Button();
        FlowPane tBottomBtnStrip = new FlowPane(Orientation.HORIZONTAL, 5, 5);
        TableView<StudentRow> tbl = new TableView<>();
        TableColumn studentId = new TableColumn("ID");
        TableColumn studentFName = new TableColumn("First Name");
        TableColumn studentLName = new TableColumn("Last Name");
        
        /**
         * Style Elements
         */
        // Content
        tContent.getStyleClass().add("dark-bg-content");
        tBottomBtnStrip.setPadding(new Insets(20, 20, 20, 20));
        tCenter.setFitToWidth(true);
        tCenter.setFitToHeight(true);
        // Buttons
        createButton.setText("Create Student");
        editButton.setText("Edit Student");
        deleteButton.setText("Delete Student");
        // Anchors
        tBottomBtnStrip.setAlignment(Pos.CENTER);
        AnchorPane.setRightAnchor(tBottomBtnStrip, 0d);
        AnchorPane.setBottomAnchor(tBottomBtnStrip, 0d);
        
        /**
         * Set Table Properties
         */
        ObservableList<StudentRow> students = FXCollections.observableArrayList();
        Database.getStudentsAsRow().stream().forEach((student) -> {
            students.add(new StudentRow(student.getId(), student.getFirstName(), student.getLastName()));
        });
        studentId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        studentFName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        studentLName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        tbl.setItems(students);
        
        /**
         * Compile Elements
         */
        tbl.getColumns().addAll(studentId, studentFName, studentLName);
        tbl.getColumns().stream().forEach((TableColumn c) -> c.impl_setReorderable(false)); // TEMP -- DISABLES COLUMN REORDERING
        
        /**
         * Style Table Elements
         */
        // Update Column Widths
        FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(new Font("Arial", 14));
        for(int i = 0; i < tbl.getColumns().size(); i++) {
            // Compute Text Size and Set Column Width
            double textWidth = fontMetrics.computeStringWidth(tbl.getColumns().get(i).getText());
            tbl.getColumns().get(i).setPrefWidth(textWidth + 40);
        }
        
        /**
         * Compile Elements
         */
        tCenter.setContent(tbl);
        tBottomBtnStrip.getChildren().addAll(createButton, editButton, deleteButton);
        tBottom.getChildren().addAll(tBottomBtnStrip);
        tContent.setCenter(tCenter);
        tContent.setBottom(tBottom);
        studentsTab.setContent(tContent);
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
        
        /**
         * Build Tabs
         */
        this.buildMainTab();
        this.buildUserTab();
        this.buildHallsTab();
        this.buildRoomsTab();
        this.buildStudentsTab();
        
        /**
         * Compile Elements
         */
        contentBox.getTabs().addAll(mainTab, userTab, hallsTab, roomsTab, studentsTab);
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
