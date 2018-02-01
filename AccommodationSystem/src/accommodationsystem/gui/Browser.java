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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableRow;
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
import javafx.scene.text.TextFlow;

/**
 *
 * @author simpl_000
 */
public class Browser extends GUI {
    /**
     * Variables
     */
    TableView<LeaseData> tbl = new TableView<>();
    HBox headerBox = new HBox();
    ScrollPane middleBox = new ScrollPane();
    FlowPane footerBox = new FlowPane(Orientation.HORIZONTAL, 15, 15);
    
    private void buildHeader() {
        Image logoImg = new Image(this.getClass().getClassLoader().getResourceAsStream("accommodationsystem/resources/images/logo.png")) {};
        
        // Configure our Elements
        // --> HORIZ BOX
        headerBox.setPadding(new Insets(25, 25, 25, 25));
        headerBox.setId("topBox");
        
        // --> HORIZ BOX DIRECTIONS
        Region topMid = new Region();
        topMid.setPrefWidth(100);
        HBox.setHgrow(topMid, Priority.ALWAYS);
        
        VBox       topLeftBox = new VBox();
        VBox      topRightBox = new VBox();
        topLeftBox.setAlignment(Pos.CENTER_LEFT);
        topRightBox.setAlignment(Pos.TOP_RIGHT);
        
        // --> LEFT BOX
        ImageView logoImgV = new ImageView(logoImg);
        logoImgV.setPreserveRatio(true);
        logoImgV.setFitWidth(250);
        
        // --> RIGHT BOX
        TextFlow loggedInAsTF = new TextFlow();
        Text loggedInAs = new Text("Logged in as");
        Text loggedInUsername = new Text(" " + User.getUsername() + "\n");
        loggedInAs.setFill(Color.WHITE);
        loggedInUsername.setFill(Color.WHITE);
        loggedInUsername.setStyle("-fx-font-weight: bold");
        loggedInAsTF.getChildren().addAll(loggedInAs, loggedInUsername);
        
        // --> LOGOUT BUTTON
        Button logoutBtn = new Button();
        logoutBtn.setId("logoutBtn");
        logoutBtn.setText("Logout");
        logoutBtn.setVisible(true);
        logoutBtn.setAlignment(Pos.CENTER);
        logoutBtn.setOnAction((ActionEvent event) -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, User.getUsername() + ", you have been successfully logged out.", ButtonType.OK);
            alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> {
                // Open up Login GUI
                try {
                    User.logout();
                    new Login().show();
                    super.close();
                } catch(Exception e) { }
            });
        });
        
        // --> ADD ELEMENTS FOR TOP BOX
        topLeftBox.getChildren().add(logoImgV);
        topRightBox.getChildren().addAll(loggedInAsTF, logoutBtn);
        headerBox.getChildren().addAll(topLeftBox, topMid, topRightBox);
    }
    
    private void buildTable() {
        
        TableColumn leaseNumber = new TableColumn("Lease Number");
        TableColumn hallId = new TableColumn("Hall ID");
        TableColumn roomNumber = new TableColumn("Room Number");
        TableColumn studentName = new TableColumn("Student Name");
        TableColumn occupancyStatus = new TableColumn("Occupancy Status");
        TableColumn cleaningStatus = new TableColumn("Cleaning Status");
        
        // Set Cell Values
        leaseNumber.setCellValueFactory(new PropertyValueFactory<>("LeaseId"));
        hallId.setCellValueFactory(new PropertyValueFactory<>("HallId"));
        roomNumber.setCellValueFactory(new PropertyValueFactory<>("RoomNumber"));
        studentName.setCellValueFactory(new PropertyValueFactory<>("StudentName"));
        occupancyStatus.setCellValueFactory(new PropertyValueFactory<>("OccupiedStatus"));
        cleaningStatus.setCellValueFactory(new PropertyValueFactory<>("CleanStatusName"));
        tbl.getItems().setAll(Database.getLeases());
        
        tbl.getColumns().addAll(leaseNumber, hallId, roomNumber, studentName, occupancyStatus, cleaningStatus);
    }
    
    private void buildContent() {
        // --> GRID PANE
        middleBox.setId("contentBox");
        middleBox.setFitToWidth(true);
        middleBox.setFitToHeight(true);
        
        // --> TABLE VIEW
        //TableView<String> tbl = new TableView<>();
        //tbl.prefWidthProperty().bind(topBox.widthProperty());
        //tbl.setMinHeight(700);
        
        buildTable();
        
        middleBox.setContent(tbl);
    }
    
    private void buildFooter() {
        // Flow Pane for bottom of GUI
        footerBox.setPadding(new Insets(20, 20, 20, 20));
        footerBox.setMinHeight(175);
        footerBox.setId("actionPane");
        
        // Add Buttons to our Flow Pane
        for(int x = 0; x < 5; x++) {
            Button _tBtn = new Button();
            _tBtn.setText("Button " + String.valueOf(x + 1));
            _tBtn.setVisible(true);
            _tBtn.getStyleClass().add("button");
            footerBox.getChildren().add(_tBtn);
        }
    }
    
    public Browser() throws Exception {
        AccommodationSystem.debug("Loaded '" + this.getClass().getName() + "'");
        
        
        buildHeader();
        buildContent();
        buildFooter();
        
        // Add our GUI Elements (hierarchy)
        super.getPane().setTop(headerBox);
        super.getPane().setCenter(middleBox);
        super.getPane().setBottom(footerBox);
        
        // Resize TableColumn Width
        int totalLength = 0;
        FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(new Font("Arial", 14));
        for(int i = 0; i < tbl.getColumns().size(); i++) {
            String text = tbl.getColumns().get(i).getText();
            double textWidth = fontMetrics.computeStringWidth(text);
            tbl.getColumns().get(i).setPrefWidth(textWidth + 40);
            totalLength += (textWidth + 40);
            System.out.println("Updated Column '" + text + "', " + String.valueOf(i));
        }
        
        // Finalise our GUI
        super.setTitle("UWE Accommodation System");
        super.setSize(totalLength + 25, totalLength + 25);
        super.finalise(true);
    }
    
    @Override
    public String formId() {
        return this.getClass().getSimpleName();
    }
}
