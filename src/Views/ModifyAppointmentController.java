/*
 *
 * Created by Jeff Johnson - November 2018
 *
 */
package Views;

import Models.Appointment;
import Models.Customer;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class ModifyAppointmentController implements Initializable {
    
    private Date date = new Date();
    
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
    SimpleDateFormat justDate = new SimpleDateFormat("yyyy-MM-dd");
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
        try {
            String dateString = MainAppointmentScreenController.selectedAppointment.getAppointmentStart();
            date = justDate.parse(dateString);           
        } catch (ParseException p) {
            System.out.println("Error when parsing date: " + p.getMessage());
        }
               
        customerBox.setItems(Customer.allCustomerNames);
        startBox.setItems(appointmentTimes);
        endBox.setItems(appointmentTimes);
        contactBox.setItems(contactNames);
        locationBox.setItems(locations);
        appointmentBox.setItems(meetingTitle);
        descriptionBox.setItems(meetingDescription);
        
        Customer customerToModify = Customer.getCustomer(MainAppointmentScreenController.myCustomerId);
        
        customerBox.valueProperty().set(customerToModify.getCustomerName());
        dateField.valueProperty().set(convertToLocalDateViaInstant(date));
        startBox.valueProperty().set(Appointment.getAppointmentStartTime(MainAppointmentScreenController.selectedAppointment.getAppointmentStart()));
        endBox.valueProperty().set(Appointment.getAppointmentEndTime(MainAppointmentScreenController.selectedAppointment.getAppointmentEnd()));
        contactBox.valueProperty().set(MainAppointmentScreenController.selectedAppointment.getAppointmentContact());
        locationBox.valueProperty().set(MainAppointmentScreenController.selectedAppointment.getAppointmentLocation());
        appointmentBox.valueProperty().set(MainAppointmentScreenController.selectedAppointment.getAppointmentTitle());
        descriptionBox.valueProperty().set(MainAppointmentScreenController.selectedAppointment.getAppointmentDescription());
        
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
    
    public Timestamp stripStartTime() throws ParseException {
        
        String startTime = MainAppointmentScreenController.selectedAppointment.getAppointmentStart();
        Date parsedStartTime = dateFormat.parse(startTime);
        Timestamp timestampStart = new java.sql.Timestamp(parsedStartTime.getTime());
        return timestampStart;
        
    }
    
    public Timestamp stripEndTime() throws ParseException {
        
        String endTime = MainAppointmentScreenController.selectedAppointment.getAppointmentEnd();
        Date parsedEndTime = dateFormat.parse(endTime);
        Timestamp timestampEnd = new java.sql.Timestamp(parsedEndTime.getTime());
        return timestampEnd;
        
    }
    
    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
    }
    
    public void handleCancelButton(ActionEvent event) {
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
        
    }
}
