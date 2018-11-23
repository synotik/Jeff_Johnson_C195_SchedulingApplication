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
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCustomerController implements Initializable {
    
    private static final String CITY_QUERY = "SELECT cityid FROM city WHERE city = ?";
    
    private static PreparedStatement cityId;
    
    @FXML private TextField nameField;
    @FXML private TextField addressField;
    @FXML private TextField cityField;
    @FXML private TextField zipField;
    @FXML private TextField phoneNumberField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML private void handleSubmitButton(ActionEvent event) {
        
        int cityId = getCityId(cityField.getText());
        
        if(nameField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Input Error");
            alert.setContentText("Customer Name Field Cannot Be Blank!");
            alert.showAndWait();
        } else if(addressField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Input Error");
            alert.setContentText("Customer Address Field Cannot Be Blank!");
            alert.showAndWait();
        } else if(cityField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Input Error");
            alert.setContentText("Customer City Field Cannot Be Blank!");
            alert.showAndWait();
        } else if(zipField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Input Error");
            alert.setContentText("Customer Zip Field Cannot Be Blank!");
            alert.showAndWait();
        } else if(phoneNumberField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Input Error");
            alert.setContentText("Customer Phone Number Field Cannot Be Blank!");
            alert.showAndWait();
        } else if(getCityId(cityField.getText()) == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Input Error");
            alert.setContentText("Customer City Field Is Not Valid!");
            alert.showAndWait();
        } else {
            Customer.newCustomer(nameField.getText(), addressField.getText(), cityId,
                Integer.parseInt(zipField.getText()), phoneNumberField.getText());
        }
                               
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
    
    @FXML private void handleClearButton(ActionEvent event) {
        
        nameField.clear();
        addressField.clear();
        cityField.clear();
        zipField.clear();
        phoneNumberField.clear();
        
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
                return -1;
            }
        } catch (SQLException e) {
            System.out.println("Get city ID query failed: " +e.getMessage());
        }
        return -1;        
    }    
}
