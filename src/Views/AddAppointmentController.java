/*
 *
 * Created by Jeff Johnson - November 2018
 *
 */
package Views;

import Models.Appointment;
import Models.Customer;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class AddAppointmentController implements Initializable {
    
    @FXML ChoiceBox customerBox;
    @FXML DatePicker dateField;
    @FXML ChoiceBox startBox;
    @FXML ChoiceBox endBox;
    @FXML ChoiceBox contactBox;
    @FXML ChoiceBox locationBox;
    @FXML ChoiceBox appointmentBox;
    @FXML ChoiceBox descriptionBox;
    
    private final ObservableList<String> appointmentTimes = FXCollections.observableArrayList(
            "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", 
            "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00", "04:30",
            "05:00");
    private final ObservableList<String> contactNames = FXCollections.observableArrayList(
            "wflick", "user1", "user2");
    private final ObservableList<String> locations = FXCollections.observableArrayList(
            "Online", "New York, New York", "London, England", "Phoenix, Arizona");
    private final ObservableList<String> meetingTitle = FXCollections.observableArrayList(
            "Meeting", "Consulting");
    private final ObservableList<String> meetingDescription = FXCollections.observableArrayList(
            "First Meeting", "First Consultation", "Follow-up");
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm"); 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        customerBox.setItems(Customer.allCustomerNames);
        startBox.setItems(appointmentTimes);
        endBox.setItems(appointmentTimes);
        contactBox.setItems(contactNames);
        locationBox.setItems(locations);
        appointmentBox.setItems(meetingTitle);
        descriptionBox.setItems(meetingDescription);
        
    }   
    
    public void handleSubmitButton(ActionEvent event) throws ParseException, SQLException {

        if(Appointment.checkForDuplicateAppointment(startBox.getValue().toString(), endBox.getValue().toString()) > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error creating new appointment");
            alert.setHeaderText("Error!");
            alert.setContentText("Appointment start or end date/time overlaps an existing appointment.");
            alert.showAndWait();
        } else {
            Appointment.newAppointment(customerBox.getValue().toString(), appointmentBox.getValue().toString(), 
                descriptionBox.getValue().toString(), contactBox.getValue().toString(), 
                locationBox.getValue().toString(), convertStartTimestamp(), convertEndTimestamp());
        }
        
        customerBox.valueProperty().set(null);
        dateField.valueProperty().set(null);
        startBox.valueProperty().set(null);
        endBox.valueProperty().set(null);
        contactBox.valueProperty().set(null);
        locationBox.valueProperty().set(null);
        appointmentBox.valueProperty().set(null);
        descriptionBox.valueProperty().set(null);
                
    }
    
    public Timestamp convertStartTimestamp() throws ParseException {
        
        String date = dateField.getValue().toString();
        String start = startBox.getValue().toString();
        String combinedStart = date + " " + start;
        
        Date parsedStartDate = dateFormat.parse(combinedStart);
        Timestamp timestampStart = new java.sql.Timestamp(parsedStartDate.getTime());
        
        return timestampStart;
        
    }
    
    public Timestamp convertEndTimestamp() throws ParseException {
        
        String date = dateField.getValue().toString();
        String end = endBox.getValue().toString();
        String combinedEnd = date + " " + end;
        
        Date parsedEndDate = dateFormat.parse(combinedEnd);
        Timestamp timestampEnd = new java.sql.Timestamp(parsedEndDate.getTime());
        
        return timestampEnd;
        
    }
    
    public void handleCancelButton(ActionEvent event) {
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
        
    }
}
