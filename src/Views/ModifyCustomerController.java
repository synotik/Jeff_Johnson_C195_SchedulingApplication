/*
 *
 * Created by Jeff Johnson - November 2018
 *
 */
package Views;

import Models.Customer;
import Models.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifyCustomerController implements Initializable {
    
    private static final String CITY_QUERY = "SELECT cityid FROM city WHERE city = ?";
    
    private static PreparedStatement cityId;
    
    @FXML private TextField nameField;
    @FXML private TextField addressField;
    @FXML private TextField cityField;
    @FXML private TextField zipField;
    @FXML private TextField phoneNumberField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        nameField.setText(MainCustomerScreenController.selectedCustomer.getCustomerName());
        addressField.setText(MainCustomerScreenController.selectedCustomer.getCustomerAddress());
        cityField.setText(MainCustomerScreenController.selectedCustomer.getCustomerCity());
        zipField.setText(Integer.toString(MainCustomerScreenController.selectedCustomer.getCustomerZip()));
        phoneNumberField.setText(MainCustomerScreenController.selectedCustomer.getCustomerPhone());
        
    }

    @FXML private void handleSubmitButton(ActionEvent event) throws IOException {
        
        Customer.updateCustomer(MainCustomerScreenController.selectedCustomer.getCustomerID(), 
                nameField.getText(),
                addressField.getText(),
                getCityId(cityField.getText()), 
                Integer.parseInt(zipField.getText()), 
                phoneNumberField.getText());
        
    }
    
    @FXML private void handleCancelButton(ActionEvent event) throws IOException {
              
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MainCustomerScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Scheduling Application - Main Customer Screen");
        stage.show();
        Stage oldStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        oldStage.close();
        
    }
    
    private static int getCityId(String cityName) {
        
        try {
            cityId = DBConnection.getConnection().prepareStatement(CITY_QUERY);
            cityId.setString(1, cityName);
                        
            ResultSet results = cityId.executeQuery();
            
            if(results.next()) {
                int newCityId;
                newCityId = results.getInt("cityid");
                return newCityId;
            } else {
                System.out.println("No city ID found!");
            }
        } catch (SQLException e) {
            System.out.println("Get city ID query failed: " +e.getMessage());
        }
        return -1;
        
    }
}
