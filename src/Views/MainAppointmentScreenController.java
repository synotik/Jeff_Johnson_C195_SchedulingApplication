/*
 *
 * Created by Jeff Johnson - November 2018
 *
 */
package Views;

import Models.Appointment;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainAppointmentScreenController implements Initializable {
    
    public static Appointment selectedAppointment;
    
    public static int myCustomerId;
    
    @FXML private TableView <Customer> customerTable;
    @FXML private TableColumn <Customer, Integer> customerId;
    @FXML private TableColumn <Customer, String> customerName;
    
    @FXML TableView <Appointment> weekTable;
    @FXML TableColumn <Appointment, String> weekDescription;
    @FXML TableColumn <Appointment, String> weekContact;
    @FXML TableColumn <Appointment, String> weekLocation;
    @FXML TableColumn <Appointment, String> weekStart;
    @FXML TableColumn <Appointment, String> weekEnd;
    
    @FXML TableView <Appointment> monthTable;
    @FXML TableColumn <Appointment, String> monthDescription;
    @FXML TableColumn <Appointment, String> monthContact;
    @FXML TableColumn <Appointment, String> monthLocation;
    @FXML TableColumn <Appointment, String> monthStart;
    @FXML TableColumn <Appointment, String> monthEnd;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerTable.setItems(Customer.getAllCustomers());
        
        weekDescription.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        weekContact.setCellValueFactory(new PropertyValueFactory<>("appointmentContact"));
        weekLocation.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        weekStart.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        weekEnd.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));

        monthDescription.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        monthContact.setCellValueFactory(new PropertyValueFactory<>("appointmentContact"));
        monthLocation.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        monthStart.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        monthEnd.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
                
    }

    @FXML public void handleMouseClick(MouseEvent event) {
        
        weekTable.getItems().clear();
        monthTable.getItems().clear();
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        myCustomerId = selectedCustomer.getCustomerID();
        
        reloadTables();
        
    }
    
    @FXML public void handleAddButton(ActionEvent event) throws IOException {
        
        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(getClass().getResource("AddAppointment.fxml"));
        
        Parent root1 = (Parent)fxmlLoader.load();
        
        AddAppointmentController addAppointmentController = fxmlLoader.getController();
        
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root1));
        stage.show();
        
        stage.setOnCloseRequest((WindowEvent event1) -> {
            reloadTables();
        });
        
    }
    
    @FXML public void handleModifyButton(ActionEvent event) throws IOException {
     
        if(weekTable.getSelectionModel().getSelectedItem() != null) {
            selectedAppointment = weekTable.getSelectionModel().getSelectedItem();
        } else if (monthTable.getSelectionModel().getSelectedItem() != null) {
            selectedAppointment = monthTable.getSelectionModel().getSelectedItem();
        } else {
            selectedAppointment = null;
        }
        
        Parent modifyAppointmentScreenParent = FXMLLoader.load(getClass().getResource("ModifyAppointment.fxml"));
        Scene modifyAppointmentScreenScene = new Scene(modifyAppointmentScreenParent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(modifyAppointmentScreenScene);
        stage.show();
        
    }
    
    @FXML public void handleDeleteButton(ActionEvent event) throws IOException {
        
        if(weekTable.getSelectionModel().getSelectedItem() != null) {
            selectedAppointment = weekTable.getSelectionModel().getSelectedItem();
        } else if (monthTable.getSelectionModel().getSelectedItem() != null) {
            selectedAppointment = monthTable.getSelectionModel().getSelectedItem();
        } else {
            selectedAppointment = null;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);        
        alert.setTitle("Delete Alert");        
        alert.setHeaderText("Delete?");        
        alert.setContentText("Are you sure you wish to delete the selected appointment?");        
        Optional<ButtonType> button = alert.showAndWait();                
        
        if(button.get() == ButtonType.OK) {  
            int appointmentId = selectedAppointment.getAppointmentId();
            Appointment.removeAppointment(appointmentId);
            Appointment.clearAppointmentLists();
            reloadTables();
        }
        
    }

    @FXML public void handleBackButton(ActionEvent event) {
        
        reloadTables();
        Appointment.clearAppointmentLists();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
        
    }
    
    @FXML public void reloadTables() {
        
        weekTable.setItems(Appointment.getAppointmentsByWeek(myCustomerId));
        monthTable.setItems(Appointment.getAppointmentsByMonth(myCustomerId));
        
    }  
}
