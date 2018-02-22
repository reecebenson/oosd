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
import accommodationsystem.library.Database;
import accommodationsystem.library.Lease.Hall;
import accommodationsystem.library.Lease.Room;
import accommodationsystem.library.Permissions;
import accommodationsystem.library.Table.HallRow;
import accommodationsystem.library.Table.RoomRow;
import accommodationsystem.library.Table.StudentRow;
import accommodationsystem.library.Table.UserRow;
import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

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
        // Panes
        BorderPane tContent = new BorderPane();
        GridPane tCenter = new GridPane();
        AnchorPane tBottom = new AnchorPane();
        HBox tBottomBtnStrip = new HBox();
        // Buttons
        Button roomSchedule = new Button();
        Button futureBookings = new Button();
        Button studentCount = new Button();
        Button roomCount = new Button();
        Button monthlyIncome = new Button();
        Button oRoomCount = new Button();
        Button uRoomCount = new Button();
        
        /**
         * Style Elements
         */
        // Content
        tCenter.setHgap(15.0);
        tCenter.setVgap(15.0);
        tCenter.setAlignment(Pos.TOP_CENTER);
        tContent.setPadding(new Insets(20, 20, 20, 20));
        tContent.getStyleClass().add("dark-bg-content");
        tBottomBtnStrip.setPadding(new Insets(20, 20, 20, 20));
        // Buttons
        roomSchedule.setText("Room Schedule");
        roomSchedule.setDisable(true);
        roomSchedule.setPrefWidth(250.0);
        futureBookings.setText("Future Bookings");
        futureBookings.setDisable(true);
        futureBookings.setPrefWidth(250.0);
        studentCount.setText("Student Count");
        studentCount.setPrefWidth(250.0);
        roomCount.setText("Room Count");
        roomCount.setPrefWidth(250.0);
        oRoomCount.setText("Occupied Room Count");
        oRoomCount.setPrefWidth(250.0);
        uRoomCount.setText("Unoccupied Room Count");
        uRoomCount.setPrefWidth(250.0);
        monthlyIncome.setText("Monthly Income");
        monthlyIncome.setPrefWidth(250.0);
        // Anchors
        tBottomBtnStrip.setAlignment(Pos.CENTER);
        AnchorPane.setRightAnchor(tBottomBtnStrip, 0d);
        AnchorPane.setBottomAnchor(tBottomBtnStrip, 0d);
        
        /**
         * Having to use a bit of a silly way to store the Integer as lambda
         * functions have to refer to a 'final' when using outer scope
         * variables.
         */
        // Variables
        final int totalMonthlyIncome;
        int monthlyIncomeTotal = 0;
        
        /**
         * Work out monthly income
         * This is done by evaluating each Room which has an occupied
         * state of 1, it will then increase the variable
         * 'monthlyIncomeTotal' by the values returned by the map
         */
        monthlyIncomeTotal = Database.getRooms().stream().map((r) -> (r.getOccupied() == 1 ? r.getMonthlyPrice() : 0)).reduce(monthlyIncomeTotal, Integer::sum);
        totalMonthlyIncome = monthlyIncomeTotal;
        
        /**
         * Add Button Functionality
         */
        studentCount.setOnAction((e) -> new Alert(Alert.AlertType.INFORMATION, "There are " + Database.getStudentsAsRow().size() + " students.", ButtonType.OK).showAndWait());
        roomCount.setOnAction((e) -> new Alert(Alert.AlertType.INFORMATION, "There are a total of " + Database.getRooms().size() + " rooms.", ButtonType.OK).showAndWait());
        oRoomCount.setOnAction((e) -> new Alert(Alert.AlertType.INFORMATION, "There are a total of " + Database.getRooms().stream().filter((Room r) -> r.getOccupied() == 1).collect(Collectors.toList()).size() + " occupied rooms.", ButtonType.OK).showAndWait());
        uRoomCount.setOnAction((e) -> new Alert(Alert.AlertType.INFORMATION, "There are a total of " + Database.getRooms().stream().filter((Room r) -> r.getOccupied() == 0).collect(Collectors.toList()).size() + " unoccupied rooms.", ButtonType.OK).showAndWait());
        monthlyIncome.setOnAction((e) -> new Alert(Alert.AlertType.INFORMATION, "The monthly income from occupied rooms is £" + totalMonthlyIncome + ".", ButtonType.OK).showAndWait());
        
        /**
         * Compile Elements
         */
        tCenter.add(roomSchedule, 0, 0);
        tCenter.add(futureBookings, 1, 0);
        tCenter.add(studentCount, 2, 0);
        tCenter.add(roomCount, 0, 1);
        tCenter.add(oRoomCount, 1, 1);
        tCenter.add(uRoomCount, 2, 1);
        tCenter.add(monthlyIncome, 1, 2);
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
        TableColumn userHall = new TableColumn("Allocated Hall");
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
        // Update Permissions Column Width
        userPerms.setPrefWidth(250);
        
        /**
         * Set Table Properties
         */
        ObservableList<UserRow> users = FXCollections.observableArrayList();
        Database.getUsersAsRow().stream().forEach((user) -> {
            users.add(new UserRow(user.getId(), user.getUsername(), user.getPassword(), user.getAllocatedHall()));
        });
        userId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        userName.setCellValueFactory(new PropertyValueFactory<>("Username"));
        userPass.setCellValueFactory(new PropertyValueFactory<>("Password"));
        userHall.setCellValueFactory(new PropertyValueFactory<>("AllocatedHall"));
        userPerms.setCellValueFactory(new PropertyValueFactory<>("Perms"));
        tbl.setItems(users);
        
        /**
         * Compile Elements
         */
        tbl.getColumns().addAll(userId, userName, userPass, userHall, userPerms);
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
         * Add functionality to buttons
         */
        
        // Setup Dialog
        Dialog<Boolean> userDialog = new Dialog<>();
        
        // Create the grid
        GridPane userGrid = new GridPane();
        userGrid.setHgap(10);
        userGrid.setVgap(10);
        userGrid.setPadding(new Insets(20, 150, 10, 10));
            
        // Set fields
        TextField udUsername = new TextField();
        udUsername.setPromptText("Username");
        PasswordField udPassword = new PasswordField();
        udPassword.setPromptText("Password");
        ComboBox udAllocatedHall = new ComboBox(Database.getHallNames(true));
        udAllocatedHall.setValue("0: All");
        
        // Add to User Grid
        userGrid.add(new Label("Username:"), 0, 0);
        userGrid.add(udUsername, 1, 0);
        userGrid.add(new Label("Password:"), 0, 1);
        userGrid.add(udPassword, 1, 1);
        userGrid.add(new Label("Allocated Hall:"), 0, 2);
        userGrid.add(udAllocatedHall, 1, 2);
        userGrid.add(new Label("Permissions:"), 0, 3);
        
        // Generate Checkboxes for permissions
        int curCount = 3;
        for(String p: Permissions.getPermissions()) {
            CheckBox permBox = new CheckBox(p);
            permBox.setSelected(false);
            permBox.setMnemonicParsing(false);
            userGrid.add(permBox, 1, ++curCount);
        }
        
        // Setup Grid
        userDialog.getDialogPane().setContent(userGrid);
        
        // Create Button
        createButton.setOnAction((e) -> {
            // Create the custom dialog
            userDialog.setTitle("Create User");
            userDialog.setHeaderText("Create User");
            
            // Initialise elements
            ButtonType userOkButton = new ButtonType("Create User", ButtonData.OK_DONE);
            userDialog.getDialogPane().getButtonTypes().clear();
            userDialog.getDialogPane().getButtonTypes().addAll(userOkButton, ButtonType.CANCEL);

            // Button True/False Sets
            userDialog.setResultConverter(dialogButton -> {
                if(dialogButton == userOkButton) {
                    return true;
                } else if(dialogButton == ButtonType.CANCEL) {
                    return false;
                }
                return null;
            });
            
            // Reset Elements
            udUsername.setText("");
            udPassword.setText("");
            udAllocatedHall.setValue("0: All");
            udAllocatedHall.setItems(Database.getHallNames(true));
            
            // Generate Checkboxes for permissions
            userGrid.getChildren().stream().forEach((Node n) -> {
                // Check that we have a CheckBox Node
                if(n instanceof CheckBox) {
                    CheckBox cb = (CheckBox)n;
                    cb.setSelected(false);
                }
            });
            
            // Handle Result
            Optional<Boolean> result = userDialog.showAndWait();
            if(result.isPresent()) {
                // Make sure we've pressed the 'Create User' button
                if(result.get()) {
                    // Validate inputs
                    if(!(udUsername.getText().isEmpty() && udPassword.getText().isEmpty() && udAllocatedHall.getValue() != null)) {
                        // Permissions
                        List<String> permissions = new ArrayList<>();
                        
                        // Validate CheckBoxes
                        userGrid.getChildren().stream().forEach((Node n) -> {
                            // Check that we have a CheckBox Node
                            if(n instanceof CheckBox) {
                                CheckBox cb = (CheckBox)n;
                                if(cb.isSelected()) {
                                    permissions.add(cb.getText());
                                }
                            }
                        });
                        
                        // Create User
                        Database.createUser(udUsername.getText(), udPassword.getText(), Database.getIdFromString((String)udAllocatedHall.getSelectionModel().getSelectedItem()), permissions);

                        // Rebuild Table
                        ObservableList<UserRow> newUsers = FXCollections.observableArrayList();
                        Database.getUsersAsRow().stream().forEach((user) -> {
                            newUsers.add(new UserRow(user.getId(), user.getUsername(), user.getPassword(), user.getAllocatedHall()));
                        });
                        tbl.setItems(newUsers);
                        
                        // Show Alert
                        new Alert(Alert.AlertType.CONFIRMATION, udUsername.getText() + " has been successfully created.", ButtonType.OK).showAndWait();
                    }
                }
            }
        });
        
        // Edit Button
        editButton.setOnAction((ActionEvent e) -> {
            // Get Selected Item
            UserRow urSelected = tbl.getSelectionModel().getSelectedItem();
            
            // Check if we have something selected
            if(urSelected == null) {
                new Alert(Alert.AlertType.ERROR, "Please select a User to delete!", ButtonType.OK).showAndWait();
                return;
            }
            
            // Create the custom dialog
            userDialog.setTitle("Edit User");
            userDialog.setHeaderText("Edit User");
            
            // Initialise elements
            ButtonType userOkButton = new ButtonType("Edit User", ButtonData.OK_DONE);
            userDialog.getDialogPane().getButtonTypes().clear();
            userDialog.getDialogPane().getButtonTypes().addAll(userOkButton, ButtonType.CANCEL);

            // Button True/False Sets
            userDialog.setResultConverter(dialogButton -> {
                if(dialogButton == userOkButton) {
                    return true;
                } else if(dialogButton == ButtonType.CANCEL) {
                    return false;
                }
                return null;
            });
            
            // Set Values
            udUsername.setText(urSelected.getUsername());
            udPassword.setText(urSelected.getPassword());
            udAllocatedHall.setItems(Database.getHallNames(true));
            udAllocatedHall.setValue(Database.getHallNames(true).stream().filter((String s) -> s.contains(urSelected.getAllocatedHall())).collect(Collectors.toList()).get(0));
            
            // Set Permissions
            List<String> urPermissions = new ArrayList<>(Arrays.asList(urSelected.getPerms().split(",")));
            urPermissions.removeAll(Collections.singleton(""));

            userGrid.getChildren().stream().forEach((Node n) -> {
                if(n instanceof CheckBox) {
                    CheckBox cb = (CheckBox)n;
                    cb.setSelected(urPermissions.contains(cb.getText()));
                }
            });
            
            // Handle Result
            Optional<Boolean> result = userDialog.showAndWait();
            if(result.isPresent()) {
                // Make sure we've pressed the 'Create User' button
                if(result.get()) {
                    // Validate inputs
                    if(!(udUsername.getText().isEmpty() && udPassword.getText().isEmpty() && udAllocatedHall.getValue() != null)) {
                        // Permissions
                        List<String> permissions = new ArrayList<>();
                        
                        // Validate CheckBoxes
                        userGrid.getChildren().stream().forEach((Node n) -> {
                            // Check that we have a CheckBox Node
                            if(n instanceof CheckBox) {
                                CheckBox cb = (CheckBox)n;
                                if(cb.isSelected()) {
                                    permissions.add(cb.getText());
                                }
                            }
                        });
                        
                        // Update User
                        Database.updateUser(urSelected.getId(), "username", udUsername.getText());
                        Database.updateUser(urSelected.getId(), "password", udPassword.getText());
                        Database.updateUser(urSelected.getId(), "allocated_hall", String.valueOf(Database.getIdFromString((String)udAllocatedHall.getSelectionModel().getSelectedItem())));
                        Database.updateUserPermissions(urSelected.getId(), permissions);

                        // Rebuild Table
                        ObservableList<UserRow> newUsers = FXCollections.observableArrayList();
                        Database.getUsersAsRow().stream().forEach((user) -> {
                            newUsers.add(new UserRow(user.getId(), user.getUsername(), user.getPassword(), user.getAllocatedHall()));
                        });
                        tbl.setItems(newUsers);
                        
                        // Show Alert
                        new Alert(Alert.AlertType.CONFIRMATION, urSelected.getUsername() + " has been successfully updated.", ButtonType.OK).showAndWait();
                    }
                }
            }
        });
        
        deleteButton.setOnAction((e) -> {
            // Get Selected Item
            UserRow urSelected = tbl.getSelectionModel().getSelectedItem();
            
            // Check if we have something selected
            if(urSelected == null) {
                new Alert(Alert.AlertType.ERROR, "Please select a User to delete!", ButtonType.OK).showAndWait();
                return;
            }
            
            // Confirm Deletion
            Alert confirmDeletion = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + urSelected.getUsername() + "?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> r = confirmDeletion.showAndWait();
            if(r.get() == ButtonType.YES) {
                // Deletion Confirmed
                if(Database.deleteUser(urSelected.getId(), false)) {
                    // Clear Table
                    tbl.getItems().clear();
                    
                    // Rebuild Table
                    ObservableList<UserRow> newUsers = FXCollections.observableArrayList();
                    Database.getUsersAsRow().stream().forEach((user) -> {
                        newUsers.add(new UserRow(user.getId(), user.getUsername(), user.getPassword(), user.getAllocatedHall()));
                    });
                    tbl.setItems(newUsers);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Unable to delete user!", ButtonType.OK).showAndWait();
                }
            }
        });
        
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
         * Add functionality to buttons
         */
        
        // Setup Dialog
        Dialog<Boolean> hallDialog = new Dialog<>();
        
        // Create the grid
        GridPane hallGrid = new GridPane();
        hallGrid.setHgap(10);
        hallGrid.setVgap(10);
        hallGrid.setPadding(new Insets(20, 150, 10, 10));
            
        // Set fields
        TextField hdName = new TextField();
        hdName.setPromptText("Hall Name");
        TextField hdShortName = new TextField();
        hdShortName.setPromptText("Hall Short Name");
        TextField hdAddress = new TextField();
        hdAddress.setPromptText("Hall Full Address");
        TextField hdPostcode = new TextField();
        hdPostcode.setPromptText("Hall Post Code");
        TextField hdPhone = new TextField();
        hdPhone.setPromptText("Hall Phone Number");

        // Setup Grid
        hallGrid.add(new Label("Name:"), 0, 0);
        hallGrid.add(hdName, 1, 0);
        hallGrid.add(new Label("Short Name:"), 0, 1);
        hallGrid.add(hdShortName, 1, 1);
        hallGrid.add(new Label("Full Address:"), 0, 2);
        hallGrid.add(hdAddress, 1, 2);
        hallGrid.add(new Label("Postcode:"), 0, 3);
        hallGrid.add(hdPostcode, 1, 3);
        hallGrid.add(new Label("Phone Number:"), 0, 4);
        hallGrid.add(hdPhone, 1, 4);
        hallDialog.getDialogPane().setContent(hallGrid);
        
        // Create Button
        createButton.setOnAction((e) -> {
            // Create the custom dialog
            hallDialog.setTitle("Create Hall");
            hallDialog.setHeaderText("Create Hall");
            
            // Initialise elements
            ButtonType hallOkButton = new ButtonType("Create Hall", ButtonData.OK_DONE);
            hallDialog.getDialogPane().getButtonTypes().clear();
            hallDialog.getDialogPane().getButtonTypes().addAll(hallOkButton, ButtonType.CANCEL);

            // Button True/False Sets
            hallDialog.setResultConverter(dialogButton -> {
                if(dialogButton == hallOkButton) {
                    return true;
                } else if(dialogButton == ButtonType.CANCEL) {
                    return false;
                }
                return null;
            });
            
            // Handle Result
            Optional<Boolean> result = hallDialog.showAndWait();
            if(result.isPresent()) {
                if(result.get()) {
                    // Validate Hall Stuff
                    if(!(hdName.getText().isEmpty() && hdShortName.getText().isEmpty() && hdAddress.getText().isEmpty() && hdPostcode.getText().isEmpty() && hdPhone.getText().isEmpty())) {
                        Database.createHall(new Hall(-1, hdName.getText(), hdShortName.getText(), hdAddress.getText(), hdPostcode.getText(), hdPhone.getText(), -1));
                        
                        // Clear Table
                        tbl.getItems().clear();

                        // Rebuild Table
                        ObservableList<HallRow> newHalls = FXCollections.observableArrayList();
                        Database.getHallsAsRow().stream().forEach((h) -> {
                            newHalls.add(new HallRow(h.getHallId(), h.getHallName(), h.getHallShortName(), h.getHallAddress(), h.getHallPostcode(), h.getHallPhone(), h.getHallRoomCount()));
                        });
                        tbl.setItems(newHalls);
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Unable to create Hall!", ButtonType.OK).showAndWait();
                    }
                }
            }
        });
        
        // Edit Button
        editButton.setOnAction((e) -> {
            // Get Selected Item
            HallRow hrSelected = tbl.getSelectionModel().getSelectedItem();
            
            // Check if we have something selected
            if(hrSelected == null) {
                new Alert(Alert.AlertType.ERROR, "Please select a Hall to edit!", ButtonType.OK).showAndWait();
                return;
            }
            
            // Create the custom dialog
            hallDialog.setTitle("Create Hall");
            hallDialog.setHeaderText("Create Hall");
            
            // Initialise elements
            ButtonType hallOkButton = new ButtonType("Update Hall", ButtonData.OK_DONE);
            hallDialog.getDialogPane().getButtonTypes().clear();
            hallDialog.getDialogPane().getButtonTypes().addAll(hallOkButton, ButtonType.CANCEL);
            
            // Set Input Values
            hdName.setText(hrSelected.getHallName());
            hdShortName.setText(hrSelected.getHallShortName());
            hdAddress.setText(hrSelected.getHallAddress());
            hdPostcode.setText(hrSelected.getHallPostcode());
            hdPhone.setText(hrSelected.getHallPhone());

            // Button True/False Sets
            hallDialog.setResultConverter(dialogButton -> {
                if(dialogButton == hallOkButton) {
                    return true;
                } else if(dialogButton == ButtonType.CANCEL) {
                    return false;
                }
                return null;
            });
            
            // Handle Result
            Optional<Boolean> result = hallDialog.showAndWait();
            if(result.isPresent()) {
                if(result.get()) {
                    // Validate Hall Stuff
                    if(!(hdName.getText().isEmpty() && hdShortName.getText().isEmpty() && hdAddress.getText().isEmpty() && hdPostcode.getText().isEmpty() && hdPhone.getText().isEmpty())) {
                        Database.updateHall(new Hall(hrSelected.getHallId(), hdName.getText(), hdShortName.getText(), hdAddress.getText(), hdPostcode.getText(), hdPhone.getText(), -1));
                        
                        // Clear Table
                        tbl.getItems().clear();
                        
                        // Rebuild Table
                        ObservableList<HallRow> newHalls = FXCollections.observableArrayList();
                        Database.getHallsAsRow().stream().forEach((h) -> {
                            newHalls.add(new HallRow(h.getHallId(), h.getHallName(), h.getHallShortName(), h.getHallAddress(), h.getHallPostcode(), h.getHallPhone(), h.getHallRoomCount()));
                        });
                        tbl.setItems(newHalls);
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Unable to create Hall!", ButtonType.OK).showAndWait();
                    }
                }
            }
        });
        
        // Delete Button
        deleteButton.setOnAction((e) -> {
            // Get Selected Item
            HallRow hrSelected = tbl.getSelectionModel().getSelectedItem();
            
            // Check if we have something selected
            if(hrSelected == null) {
                new Alert(Alert.AlertType.ERROR, "Please select a Hall to delete!", ButtonType.OK).showAndWait();
                return;
            }
            
            // Confirm Deletion
            Alert confirmDeletion = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + hrSelected.getHallName() + "?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> r = confirmDeletion.showAndWait();
            if(r.get() == ButtonType.YES) {
                // Deletion Confirmed
                if(Database.deleteHall(hrSelected.getHallId())) {
                    // Clear Table
                    tbl.getItems().clear();
                    
                    // Rebuild Table
                    ObservableList<HallRow> newHalls = FXCollections.observableArrayList();
                    Database.getHallsAsRow().stream().forEach((h) -> {
                        newHalls.add(new HallRow(h.getHallId(), h.getHallName(), h.getHallShortName(), h.getHallAddress(), h.getHallPostcode(), h.getHallPhone(), h.getHallRoomCount()));
                    });
                    tbl.setItems(newHalls);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Unable to delete hall!", ButtonType.OK).showAndWait();
                }
            }
        });
        
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
        TableColumn hallId = new TableColumn("Hall ID");
        TableColumn flatId = new TableColumn("Flat ID");
        TableColumn roomId = new TableColumn("Room ID");
        TableColumn occupied = new TableColumn("Occupancy Status");
        TableColumn cleanStatus = new TableColumn("Clean Status");
        TableColumn monthlyPrice = new TableColumn("Monthly Price (£)");
        
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
            rooms.add(new RoomRow(r.getRoomId(), r.getFlatId(), r.getHallId(), r.getOccupied(), r.getCleanStatus(), r.getMonthlyPrice()));
        });
        hallId.setCellValueFactory(new PropertyValueFactory<>("HallId"));
        flatId.setCellValueFactory(new PropertyValueFactory<>("FlatId"));
        roomId.setCellValueFactory(new PropertyValueFactory<>("RoomId"));
        occupied.setCellValueFactory(new PropertyValueFactory<>("Occupied"));
        cleanStatus.setCellValueFactory(new PropertyValueFactory<>("CleanStatus"));
        monthlyPrice.setCellValueFactory(new PropertyValueFactory<>("MonthlyPrice"));
        tbl.setItems(rooms);
        
        /**
         * Compile Elements
         */
        tbl.getColumns().addAll(hallId, flatId, roomId, occupied, cleanStatus, monthlyPrice);
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
         * Add functionality to buttons
         */
        
        // Setup Dialog
        Dialog<Boolean> roomDialog = new Dialog<>();
        
        // Create the username and password labels and fields
        GridPane roomGrid = new GridPane();
        roomGrid.setHgap(10);
        roomGrid.setVgap(10);
        roomGrid.setPadding(new Insets(20, 150, 10, 10));
            
        // Set fields
        TextField rdHallId = new TextField();
        rdHallId.setPromptText("Hall ID");
        TextField rdFlatId = new TextField();
        rdFlatId.setPromptText("Flat ID");
        TextField rdRoomId = new TextField();
        rdRoomId.setPromptText("Room ID");
        TextField rdMonthlyPrice = new TextField();
        rdMonthlyPrice.setPromptText("Monthly Price (£)");

        // Validate Fields
        rdHallId.textProperty().addListener((options, oldValue, newValue) -> tfValidateNumber_Changed(rdHallId, options, oldValue, newValue));
        rdFlatId.textProperty().addListener((options, oldValue, newValue) -> tfValidateNumber_Changed(rdFlatId, options, oldValue, newValue));
        rdRoomId.textProperty().addListener((options, oldValue, newValue) -> tfValidateNumber_Changed(rdRoomId, options, oldValue, newValue));
        rdMonthlyPrice.textProperty().addListener((options, oldValue, newValue) -> tfValidateNumber_Changed(rdMonthlyPrice, options, oldValue, newValue));
        
        // Setup Grid
        roomGrid.add(new Label("Hall ID:"), 0, 0);
        roomGrid.add(rdHallId, 1, 0);
        roomGrid.add(new Label("Flat ID:"), 0, 1);
        roomGrid.add(rdFlatId, 1, 1);
        roomGrid.add(new Label("Room ID:"), 0, 2);
        roomGrid.add(rdRoomId, 1, 2);
        roomGrid.add(new Label("Monthly Price:"), 0, 3);
        roomGrid.add(rdMonthlyPrice, 1, 3);
        roomDialog.getDialogPane().setContent(roomGrid);
        
        // Create Button
        createButton.setOnAction((e) -> {
            // Create the custom dialog
            roomDialog.setTitle("Create Room");
            roomDialog.setHeaderText("Create Room");
            
            // Initialise elements
            ButtonType roomOkButton = new ButtonType("Create Room", ButtonData.OK_DONE);
            roomDialog.getDialogPane().getButtonTypes().clear();
            roomDialog.getDialogPane().getButtonTypes().addAll(roomOkButton, ButtonType.CANCEL);

            // Button True/False Sets
            roomDialog.setResultConverter(dialogButton -> {
                if(dialogButton == roomOkButton) {
                    return true;
                } else if(dialogButton == ButtonType.CANCEL) {
                    return false;
                }
                return null;
            });
            
            // Handle Result
            Optional<Boolean> result = roomDialog.showAndWait();
            if(result.isPresent()) {
                if(result.get()) {
                    // Validate Room Stuff
                    if(!(rdHallId.getText().isEmpty() && rdFlatId.getText().isEmpty() && rdRoomId.getText().isEmpty() && rdMonthlyPrice.getText().isEmpty())) {
                        // Validate Hall ID
                        if(Database.getHallIds(false).contains(Integer.valueOf(rdHallId.getText()))) {
                            // Validate HFR IDs
                            if(Database.getLeaseByHFR(Integer.valueOf(rdHallId.getText()), Integer.valueOf(rdFlatId.getText()), Integer.valueOf(rdRoomId.getText())) == null) {
                                // Create Room & Lease
                                Database.createRoom(new Room(Integer.valueOf(rdRoomId.getText()), Integer.valueOf(rdFlatId.getText()), Integer.valueOf(rdHallId.getText()), 0, 0, Integer.valueOf(rdMonthlyPrice.getText())));

                                // Rebuild Table
                                tbl.getItems().clear();
                                ObservableList<RoomRow> rbRooms = FXCollections.observableArrayList();
                                Database.getRoomsAsRow().stream().forEach((r) -> {
                                    rbRooms.add(new RoomRow(r.getRoomId(), r.getFlatId(), r.getHallId(), r.getOccupied(), r.getCleanStatus(), r.getMonthlyPrice()));
                                });
                                tbl.setItems(rbRooms);
                            } else {
                                new Alert(Alert.AlertType.ERROR, "Unable to create Room! A room already exists with the inputted Hall Id, Flat Id and Room Id.", ButtonType.OK).showAndWait();
                            }
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Unable to create Room! Please select a valid Hall ID.", ButtonType.OK).showAndWait();
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Unable to create Room!", ButtonType.OK).showAndWait();
                    }
                }
            }
        });
        
        // Edit Button
        editButton.setOnAction((e) -> {
            // Get Selected Item
            RoomRow rrSelected = tbl.getSelectionModel().getSelectedItem();
            
            // Check if we have something selected
            if(rrSelected == null) {
                new Alert(Alert.AlertType.ERROR, "Please select a Room to edit!", ButtonType.OK).showAndWait();
                return;
            }
            
            // Create the custom dialog
            roomDialog.setTitle("Create Room");
            roomDialog.setHeaderText("Create Room");
            
            // Initialise elements
            ButtonType roomOkButton = new ButtonType("Update Room", ButtonData.OK_DONE);
            roomDialog.getDialogPane().getButtonTypes().clear();
            roomDialog.getDialogPane().getButtonTypes().addAll(roomOkButton, ButtonType.CANCEL);
            
            // Set Input Values
            rdHallId.setText(rrSelected.getHallId().toString());
            rdFlatId.setText(rrSelected.getFlatId().toString());
            rdRoomId.setText(rrSelected.getRoomId().toString());
            rdMonthlyPrice.setText(rrSelected.getMonthlyPrice().toString());

            // Button True/False Sets
            roomDialog.setResultConverter(dialogButton -> {
                if(dialogButton == roomOkButton) {
                    return true;
                } else if(dialogButton == ButtonType.CANCEL) {
                    return false;
                }
                return null;
            });
            
            // Handle Result
            Optional<Boolean> result = roomDialog.showAndWait();
            if(result.isPresent()) {
                if(result.get()) {
                    // Validate Room Stuff
                    if(!(rdHallId.getText().isEmpty() && rdFlatId.getText().isEmpty() && rdRoomId.getText().isEmpty() && rdMonthlyPrice.getText().isEmpty())) {
                        //Database.updateHall(new Hall(rrSelected.getHallId(), rdHallId.getText(), rdFlatId.getText(), rdRoomId.getText(), rdMonthlyPrice.getText(), cbHallPhone.getText(), -1));
                        
                        // clear table
                        // update table
                        System.out.println("edit room clicked");
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Unable to create Room!", ButtonType.OK).showAndWait();
                    }
                }
            }
        });
        
        // Delete Button
        deleteButton.setOnAction((e) -> {
            // Get Selected Item
            RoomRow rrSelected = tbl.getSelectionModel().getSelectedItem();
            
            // Check if we have something selected
            if(rrSelected == null) {
                new Alert(Alert.AlertType.ERROR, "Please select a Room to delete!", ButtonType.OK).showAndWait();
                return;
            }
            
            // Confirm Deletion
            Alert confirmDeletion = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected room?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = confirmDeletion.showAndWait();
            if(result.get() == ButtonType.YES) {
                // Deletion Confirmed
                if(Database.deleteRoom(rrSelected.getHallId(), rrSelected.getFlatId(), rrSelected.getRoomId())) {
                    // Clear Table
                    tbl.getItems().clear();
                    
                    // Rebuild Table
                    ObservableList<RoomRow> rbRooms = FXCollections.observableArrayList();
                    Database.getRoomsAsRow().stream().forEach((r) -> {
                        rbRooms.add(new RoomRow(r.getRoomId(), r.getFlatId(), r.getHallId(), r.getOccupied(), r.getCleanStatus(), r.getMonthlyPrice()));
                    });
                    tbl.setItems(rbRooms);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Unable to delete room!", ButtonType.OK).showAndWait();
                }
            }
        });
        
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
         * Add functionality to buttons
         */
        createButton.setOnAction((e) -> {
            String firstName = null, lastName = null;
            
            /**
             * First Name
             */
            TextInputDialog tidFirst = new TextInputDialog();
            tidFirst.setTitle("Enter First Name");
            tidFirst.setHeaderText("Please enter the First Name");
            tidFirst.setContentText("First Name:");
            Optional<String> rFirst = tidFirst.showAndWait();
            if(rFirst.isPresent() && !rFirst.get().isEmpty())
                firstName = rFirst.get();
            
            /**
             * Last Name
             */
            TextInputDialog tidLast = new TextInputDialog();
            tidLast.setTitle("Enter Last Name");
            tidLast.setHeaderText("Please enter the Last Name");
            tidLast.setContentText("Last Name:");
            Optional<String> rLast = tidLast.showAndWait();
            if(rLast.isPresent() && !rLast.get().isEmpty())
                lastName = rLast.get();
            
            /**
             * Create Student
             */
            if(firstName != null && lastName != null) {
                if(Database.createStudent(firstName, lastName)) {
                    new Alert(Alert.AlertType.CONFIRMATION, firstName + " " + lastName + " was created as a student.", ButtonType.OK).showAndWait();
                    
                    // Clear Table
                    tbl.getItems().clear();
                    
                    // Rebuild Table
                    ObservableList<StudentRow> sList = FXCollections.observableArrayList();
                    Database.getStudentsAsRow().stream().forEach((student) -> {
                        sList.add(new StudentRow(student.getId(), student.getFirstName(), student.getLastName()));
                    });
                    
                    // Set Table Items
                    tbl.setItems(sList);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Unable to create student!", ButtonType.OK).showAndWait();
                }
            }
        });
        
        deleteButton.setOnAction((e) -> {
            // Get Selected Item
            StudentRow srSelected = tbl.getSelectionModel().getSelectedItem();
            
            // Check if we have something selected
            if(srSelected == null) {
                new Alert(Alert.AlertType.ERROR, "Please select a Student to remove!", ButtonType.OK).showAndWait();
                return;
            }
            
            // Confirm Deletion
            Alert confirmDeletion = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + srSelected.getFirstName() + " " + srSelected.getLastName() + "?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> r = confirmDeletion.showAndWait();
            if(r.get() == ButtonType.YES) {
                // Deletion Confirmed
                if(Database.deleteStudent(srSelected.getId())) {
                    // Clear Table
                    tbl.getItems().clear();
                    
                    // Rebuild Table
                    ObservableList<StudentRow> sList = FXCollections.observableArrayList();
                    Database.getStudentsAsRow().stream().forEach((student) -> {
                        sList.add(new StudentRow(student.getId(), student.getFirstName(), student.getLastName()));
                    });
                    
                    // Set Table Items
                    tbl.setItems(sList);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Unable to delete student!", ButtonType.OK).showAndWait();
                }
            }
        });
        
        /**
         * Compile Elements
         */
        tCenter.setContent(tbl);
        tBottomBtnStrip.getChildren().addAll(createButton, deleteButton);
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
    
    /**
     * @name    tfValidateNumber_Changed
     * @desc    This is used by multiple text fields which are parsed through as the
     *          `tf` parameter. It ensures that the inputed value is an integer.
     * 
     * @param   tf
     * @param   options
     * @param   oldValue
     * @param   newValue 
     */
    private void tfValidateNumber_Changed(TextField tf, Object options, Object oldValue, Object newValue) {
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
            new Alert(Alert.AlertType.ERROR, "This text field is expecting numbers only.", ButtonType.OK).show();
            tf.setText(oldVal.matches("[0-9]+") ? oldVal : "");
        }
    }
    
    @Override
    public String formId() {
        return this.getClass().getSimpleName();
    }
}
