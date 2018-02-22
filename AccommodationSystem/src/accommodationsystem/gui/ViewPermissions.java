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
import accommodationsystem.library.Table.SingleStringRow;
import accommodationsystem.library.User;
import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class ViewPermissions extends GUI {
    /**
     * Variables
     */
    // Panes
    HBox headerBox = new HBox();
    ScrollPane contentBox = new ScrollPane();
    // Table
    TableView<SingleStringRow> tbl = new TableView<>();
    
    /**
     * @name    buildHeader
     * @desc    Create, style and build the Header of the "ViewPermissions" GUI
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
     * @name    buildTable
     * @desc    Populate the permissions table
     */
    private void buildTable() {
        /**
         * Clean-up Table
         * Only call this if the form has previously been finalised
         */
        if(super.hasFinalised()) {
            tbl.getItems().clear();
            tbl.getColumns().clear();
        }
        
        /**
         * Declare & Initialise Elements
         */
        TableColumn permission = new TableColumn("Your Permissions");
        permission.setMinWidth(392.5);
        
        /**
         * Set Table Properties
         */
        ObservableList<SingleStringRow> perm = FXCollections.observableArrayList();
        User.getPermissions().stream().forEach((p) -> {
            perm.add(new SingleStringRow(p));
        });
        permission.setCellValueFactory(new PropertyValueFactory<>("Permission"));
        tbl.setItems(perm);
        
        /**
         * Compile Elements
         */
        tbl.getColumns().addAll(permission);
        tbl.getColumns().stream().forEach((TableColumn c) -> c.impl_setReorderable(false)); // TEMP -- DISABLES COLUMN REORDERING
        
        /**
         * Style Elements
         */
        // Update Column Widths
        FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(new Font("Arial", 14));
        for(int i = 0; i < tbl.getColumns().size(); i++) {
            // Compute Text Size and Set Column Width
            double textWidth = fontMetrics.computeStringWidth(tbl.getColumns().get(i).getText());
            tbl.getColumns().get(i).setPrefWidth(textWidth + 40);
        }
    }
    
    /**
     * @name    buildContent
     * @desc    Create, style and build the Content of the "ViewPermissions" GUI
     */
    private void buildContent() {
        /**
         * Style Elements
         */
        contentBox.setId("contentBox");
        contentBox.setFitToWidth(true);
        contentBox.setFitToHeight(true);
        
        /**
         * Build Table
         */
        this.buildTable();
        
        /**
         * Compile Elements
         */
        contentBox.setContent(tbl);
    }
    
    
    /**
     * @name    Default Constructor
     * @desc    Initialise the ViewPermissions GUI
     * @throws  Exception 
     */
    public ViewPermissions() throws Exception {
        /**
         * Debug
         */
        AccommodationSystem.debug("Loaded '" + this.getClass().getName() + "'");
        
        /**
         * Build "ViewPermissions" GUI
         */
        this.buildHeader();
        this.buildContent();
        
        /**
         * Add our GUI Elements (hierarchy)
         */
        super.getPane().setTop(headerBox);
        super.getPane().setCenter(contentBox);
        
        /**
         * Finalise our GUI
         */
        super.setTitle("Permissions: View");
        super.setSize(400, 400);
        super.finalise(false);
    }
    
    @Override
    public String formId() {
        return this.getClass().getSimpleName();
    }
}
