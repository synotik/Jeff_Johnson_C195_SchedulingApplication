/*
 *
 * Created by Jeff Johnson - November 2018
 *
 */
package Views;

import Models.Customer;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainCustomerScreenController implements Initializable {
    
    public static Customer selectedCustomer;
    
    @FXML public TableView <Customer> customersTable;
    @FXML private TableColumn <Customer, Integer> idColumn;
    @FXML private TableColumn <Customer, String> nameColumn;
    @FXML private TableColumn <Customer, String> addressColumn;
    @FXML private TableColumn <Customer, String> cityColumn;
    @FXML private TableColumn <Customer, Integer> zipColumn;
    @FXML private TableColumn <Customer, String> phoneColumn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        idColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("customerCity"));
        zipColumn.setCellValueFactory(new PropertyValueFactory<>("customerZip"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        customersTable.setItems(Customer.getAllCustomers());
        
    }
    
    @FXML private void handleAddCustomerButton(ActionEvent event) throws IOException {

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Scheduling Application - Add Customer");
        stage.show();
        Stage oldStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        oldStage.close();

    }

    @FXML private void handleModifyCustomerButton(ActionEvent event) throws IOException {
        
        selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ModifyCustomer.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Scheduling Application - Modify Customer");
        stage.show();
        Stage oldStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        oldStage.close();
        
    }

    
    @FXML private void handleDeleteCustomerButton() {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);        
        alert.setTitle("Delete Alert");        
        alert.setHeaderText("Delete?");        
        alert.setContentText("Are you sure you wish to delete this customer?");        
        Optional<ButtonType> button = alert.showAndWait();                
        
        if(button.get() == ButtonType.OK) {            
            Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();            
            Customer.removeCustomer(selectedCustomer.getCustomerID());
            customersTable.setItems(Customer.getAllCustomers());
        }
        
    }

    public void handleBackButton(ActionEvent event) throws IOException {
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
        
    }
}
