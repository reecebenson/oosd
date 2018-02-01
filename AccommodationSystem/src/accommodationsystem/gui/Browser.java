/*
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 */
package accommodationsystem.gui;

import accommodationsystem.AccommodationSystem;
import accommodationsystem.bases.GUI;
import accommodationsystem.library.User;
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
import javafx.scene.control.TableColumn.*;
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
    public Browser() throws Exception {
        AccommodationSystem.debug("Loaded '" + this.getClass().getName() + "'");
        
        // Elements Used for this GUI
        ScrollPane     gridPane = new ScrollPane();
        HBox           topBox = new HBox();
        Image         logoImg = new Image(this.getClass().getClassLoader().getResourceAsStream("accommodationsystem/resources/images/logo.png")) {};
        
        // Configure our Elements
        // --> HORIZ BOX
        topBox.setPadding(new Insets(25, 25, 25, 25));
        topBox.setId("topBox");
        
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
        topBox.getChildren().addAll(topLeftBox, topMid, topRightBox);
        super.getPane().setTop(topBox);
        
        // --> GRID PANE
        gridPane.setId("contentBox");
        gridPane.setFitToWidth(true);
        gridPane.setFitToHeight(true);
        
        // --> TABLE VIEW
        TableView<String> tbl = new TableView<>();
        //tbl.prefWidthProperty().bind(topBox.widthProperty());
        //tbl.setMinHeight(700);
        
        TableColumn<String,String> leaseNumber = new TableColumn<>("Lease Number");
        TableColumn<String,String> hallName = new TableColumn<>("Hall Name");
        TableColumn<String,String> hallNumber = new TableColumn<>("Hall Number");
        TableColumn<String,String> roomNumber = new TableColumn<>("Room Number");
        TableColumn<String,String> studentName = new TableColumn<>("Student Name");
        TableColumn<String,String> occupancyStatus = new TableColumn<>("Occupancy Status");
        TableColumn<String,String> cleaningStatus = new TableColumn<>("Cleaning Status");
        tbl.getColumns().addAll(leaseNumber, hallName, hallNumber, roomNumber, studentName, occupancyStatus, cleaningStatus);
        
        // Flow Pane for bottom of GUI
        FlowPane actionPane = new FlowPane(Orientation.HORIZONTAL, 15, 15);
        actionPane.setPadding(new Insets(20, 20, 20, 20));
        actionPane.setMinHeight(175);
        actionPane.setId("actionPane");
        
        // Add Buttons to our Flow Pane
        for(int x = 0; x < 5; x++) {
            Button _tBtn = new Button();
            _tBtn.setText("Button " + String.valueOf(x + 1));
            _tBtn.setVisible(true);
            _tBtn.getStyleClass().add("button");
            actionPane.getChildren().add(_tBtn);
        }
        
        // Add our GUI Elements (hierarchy)
        gridPane.setContent(tbl);
        super.getPane().setCenter(gridPane);
        super.getPane().setBottom(actionPane);
        
        // Resize TableColumn Width
        int totalLength = 0;
        FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(new Font("Arial", 14));
        for(int i = 0; i < tbl.getColumns().size(); i++) {
            String text = tbl.getColumns().get(i).getText();
            double textWidth = fontMetrics.computeStringWidth(text);
            tbl.getColumns().get(i).setPrefWidth(textWidth + 30);
            totalLength += (textWidth + 30);
            System.out.println("Updated Column '" + text + "', " + String.valueOf(i));
        }
        
        // Finalise our GUI
        super.setTitle("UWE Accommodation System");
        super.setSize(totalLength + 25, 600);
        super.finalise(true);
        
    }
    
    @Override
    public String formId() {
        return this.getClass().getSimpleName();
    }
}
