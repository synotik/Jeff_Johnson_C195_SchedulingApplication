/*
 *
 * Created by Jeff Johnson - November 2018
 *
 */
package Views;

import Models.Appointment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainScreenController implements Initializable {
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
        if(Appointment.getAppointmentsIn15Minutes() != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment notification");
            alert.setHeaderText("Attention!");
            alert.setContentText("There is a scheduled appointment soon!");
            alert.showAndWait();
        }
        
    }
    
    public void handleCustomerButton(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("MainCustomerScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }
    
    public void handleAppointmentButton(ActionEvent event) throws IOException {
        
        Parent parent = FXMLLoader.load(getClass().getResource("MainAppointmentScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
        
    }
    
    public void handleReportsButton(ActionEvent event) throws IOException {
                    
        Parent parent = FXMLLoader.load(getClass().getResource("MainReports.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
            
    }
}
