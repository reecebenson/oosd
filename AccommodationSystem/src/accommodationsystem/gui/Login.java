/*
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 */
package accommodationsystem.gui;

import accommodationsystem.AccommodationSystem;
import accommodationsystem.bases.GUI;
import accommodationsystem.library.Database;
import accommodationsystem.library.User;
import accommodationsystem.library.Permissions;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author simpl_000
 */
public class Login extends GUI {
    public Login() throws Exception {
        AccommodationSystem.debug("Loaded '" + this.getClass().getName() + "'");
        
        // Elements Used for this GUI
        GridPane     gridPane = new GridPane();
        HBox         horizBox = new HBox();
        Image         logoImg = new Image(this.getClass().getClassLoader().getResourceAsStream("accommodationsystem/resources/images/logo.png")) {};
        Button       loginBtn = new Button();
        
        // Configure our Elements
        // --> HORIZ BOX
        horizBox.setPadding(new Insets(25, 25, 25, 25));
        horizBox.setAlignment(Pos.CENTER);
        horizBox.setId("topBox");
        
        // --> UWE LOGO IMAGE
        ImageView logoImgV = new ImageView(logoImg);
        logoImgV.setPreserveRatio(true);
        logoImgV.setFitWidth(250);
        
        // --> GRID PANE
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setId("loginBox");
        
        // --> LOGIN FORM
        // LF Element Declaration
        VBox vboxUsername = new VBox();
        VBox vboxPassword = new VBox();
        TextField tfUsername = new TextField();
        PasswordField tfPassword = new PasswordField();
        
        // LF Elements Styling
        // - HBox
        vboxUsername.setId("hboxUsername");
        vboxPassword.setId("hboxPassword");
        vboxUsername.setPadding(new Insets(6, 0, 6, 0));
        vboxPassword.setPadding(new Insets(6, 0, 6, 0));
        // - Text Field
        tfUsername.setId("tfUsername");
        tfPassword.setId("tfPassword");
        tfUsername.setPromptText("Username");
        tfPassword.setPromptText("Password");
        
        tfUsername.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if(newPropertyValue)
                vboxUsername.setId("hboxUsernameFocused");
            else
                vboxUsername.setId("hboxUsername");
        });
        
        tfPassword.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if(newPropertyValue)
                vboxPassword.setId("hboxPasswordFocused");
            else
                vboxPassword.setId("hboxPassword");
        });
        
        // - Login Buttons
        loginBtn.setId("loginBtn");
        loginBtn.setText("Login");
        loginBtn.setVisible(true);
        loginBtn.setDefaultButton(true);
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.setAlignment(Pos.CENTER);
        loginBtn.setOnAction((ActionEvent event) -> {
            try {
                AccommodationSystem.debug("Clicked >> " + event.getSource().toString() + " <<");
                System.out.println("Logging in with credentials: [" + tfUsername.getText() + ":" + tfPassword.getText() + "]");
                if(Database.validateLogin(tfUsername.getText(), tfPassword.getText())) {
                    // Check Login Permissions
                    if(User.hasPermission(Permissions.LOGIN)) {
                        Alert alert = new Alert(AlertType.INFORMATION, "You have been successfully logged in as '" + tfUsername.getText() + "'");
                        alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> {
                            // Open up Main GUI
                            try {
                                new Browser().show();
                                super.close();
                            } catch(Exception e) { }
                        });
                    }else{
                        Alert alert2 = new Alert(AlertType.ERROR, "You do not have permission to Login!");
                        alert2.show();
                        tfPassword.setText("");
                    }
                } else {
                    Alert alert = new Alert(AlertType.ERROR, "You have entered an incorrect username and/or password.");
                    alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> {
                        // Clear Users Password Field
                        tfPassword.setText("");
                    });
                }
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        // LF Elements
        vboxUsername.getChildren().add(tfUsername);
        gridPane.add(vboxUsername, 1, 0);
        
        vboxPassword.getChildren().add(tfPassword);
        gridPane.add(vboxPassword, 1, 1);
        
        gridPane.add(loginBtn, 1, 3);
        
        // Add our GUI Elements (hierarchy)
        horizBox.getChildren().add(logoImgV);
        super.getPane().setTop(horizBox);
        super.getPane().setCenter(gridPane);
        
        // Finalise our GUI
        super.setTitle("Login");
        super.setSize(400, 400);
        super.finalise(false);
    }
    
    @Override
    public String formId() {
        return this.getClass().getSimpleName();
    }
}
