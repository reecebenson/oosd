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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author simpl_000
 */
public class Login extends GUI {
    /**
     * Variables
     */
    // Panes
    HBox headerBox = new HBox();
    GridPane loginBox = new GridPane();
    
    /**
     * @name    buildHeader
     * @desc    Create, style and build the Header of the "Login" GUI
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
     * @name    buildContent
     * @desc    Create, style and build the Content of the "Login" GUI
     * @param   justLoggedOut
     */
    private void buildContent(boolean justLoggedOut) {
        /**
         * Declare Elements
         */
        // Buttons
        Button btnLogin = new Button();
        // Text Fields
        TextField tfUsername = new TextField();
        PasswordField tfPassword = new PasswordField();
        VBox outerUsername = new VBox();
        VBox outerPassword = new VBox();
        // Labels
        Label lblLoggedOut = new Label("You have been successfully logged out!\n\n\n");
        
        /**
         * Style Elements
         */
        // Panes
        loginBox.setPadding(new Insets(20, 20, 20, 20));
        loginBox.setHgap(5);
        loginBox.setVgap(5);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setId("loginBox");
        // Outer Username
        outerUsername.setId("hboxUsername");
        outerUsername.setPadding(new Insets(6, 0, 6, 0));
        // Outer Password
        outerPassword.setId("hboxPassword");
        outerPassword.setPadding(new Insets(6, 0, 6, 0));
        // Username TextField
        tfUsername.setId("tfUsername");
        tfUsername.setPromptText("Username");
        // Password TextField
        tfPassword.setId("tfPassword");
        tfPassword.setPromptText("Password");
        // Login Button
        btnLogin.setId("loginBtn");
        btnLogin.setText("Login");
        btnLogin.setDefaultButton(true);
        btnLogin.setMaxWidth(Double.MAX_VALUE);
        btnLogin.setAlignment(Pos.CENTER);
        // Just Logged Out Label
        lblLoggedOut.setTextFill(Color.LIGHTGREEN);
        lblLoggedOut.setStyle("-fx-font-weight: bold");
        
        /**
         * Debugging - Write username/password fields
         */
        if(AccommodationSystem.isDebugging()) {
            tfUsername.setText("admin");
            tfPassword.setText("1234");
        }
        
        /**
         * Configure Elements (Listeners)
         */
        // Username TextField
        tfUsername.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            outerUsername.setId(newPropertyValue ? "hboxUsernameFocused" : "hboxUsername");
        });
        // Password TextField
        tfPassword.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            outerPassword.setId(newPropertyValue ? "hboxPasswordFocused" : "hboxPassword");
        });
        // Login Button
        btnLogin.setOnAction((ActionEvent event) -> btnLogin_Click(event, tfUsername, tfPassword));
        
        /**
         * Compile Elements
         */
        outerUsername.getChildren().add(tfUsername);
        outerPassword.getChildren().add(tfPassword);
        if(justLoggedOut) loginBox.add(lblLoggedOut, 1, 0);
        loginBox.add(outerUsername, 1, 1);
        loginBox.add(outerPassword, 1, 2);
        loginBox.add(btnLogin, 1, 3);
    }
    
    /**
     * @name    btnLogin_Click
     * @desc    Handles the Click event for the login button
     * @param   event 
     * @param   tfUsername
     * @param   tfPassword
     */
    private void btnLogin_Click(ActionEvent event, TextField tfUsername, TextField tfPassword) {
        // Try/catch due for handling of SQLException
        try {
            // Debug
            AccommodationSystem.debug("Attempting to login with credentials -> [" + tfUsername.getText() + ":" + tfPassword.getText() + "]");

            // Variables
            String userName = tfUsername.getText();
            String passWord = tfPassword.getText();
            Alert alert;

            // Login
            if(Database.validateLogin(userName, passWord)) {
                // Check Login Permissions
                if(User.hasPermission(Permissions.LOGIN)) {
                    // Setup Alert
                    alert = new Alert(
                            AlertType.INFORMATION,
                            "You have been successfully logged in as '" + tfUsername.getText() + "'"
                    );

                    // Show Alert
                    alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> {
                        // Open up Main GUI
                        try {
                            new Browser().show();
                            super.close();
                        } catch(Exception e) { }
                    });
                }else{
                    // Setup Alert
                    alert = new Alert(
                            AlertType.ERROR,
                            "You do not have permission to Login!"
                    );

                    // Show Alert
                    alert.show();

                    // Reset Password Field
                    tfPassword.setText("");
                }
            } else {
                // Setup Alert
                alert = new Alert(
                        AlertType.ERROR,
                        "You have entered an incorrect username and/or password."
                );

                // Show Alert
                alert.show();

                // Reset Password Field
                tfPassword.setText("");
            }
        } catch(Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @name    Default Constructor
     * @desc    Initialise the Login GUI
     * @param   justLoggedOut
     * @throws  Exception 
     */
    public Login(boolean justLoggedOut) throws Exception {
        /**
         * Debug
         */
        AccommodationSystem.debug("Loaded '" + this.getClass().getName() + "'");
        
        /**
         * Build "Login" GUI
         */
        this.buildHeader();
        this.buildContent(justLoggedOut);
        
        /**
         * Add our GUI Elements (hierarchy)
         */
        super.getPane().setTop(headerBox);
        super.getPane().setCenter(loginBox);
        
        /**
         * Finalise our GUI
         */
        super.setTitle("Login");
        super.setSize(400, 400);
        super.finalise(false);
    }
    
    @Override
    public String formId() {
        return this.getClass().getSimpleName();
    }
}
