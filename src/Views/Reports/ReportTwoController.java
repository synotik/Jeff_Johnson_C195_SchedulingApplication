/*
 *
 * Created by Jeff Johnson - November 2018
 *
 */
package Views.Reports;

import Models.DBConnection;
import Models.Report;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ReportTwoController implements Initializable {
       
    @FXML private TableView reportTwoTable;
    @FXML private TableColumn contactColumn;
    @FXML private TableColumn descriptionColumn;
    @FXML private TableColumn customerNameColumn;
    @FXML private TableColumn startColumn;
    @FXML private TableColumn endColumn;
    
    private static ObservableList<Report> reportTwoList = FXCollections.observableArrayList();
    
    private static final String REPORT_TWO_QUERY = "SELECT appointment.contact," +
            " appointment.description, customer.customerName, start, end" +
            " FROM appointment JOIN customer ON customer.customerId = appointment.customerId" +
            " GROUP BY appointment.contact, MONTH(start), start";
    
    private PreparedStatement reportQuery;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentContact"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentCustomerName"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        
        try {
            reportQuery = DBConnection.getConnection().prepareStatement(REPORT_TWO_QUERY);
            ResultSet results = reportQuery.executeQuery();
            
            while(results.next()) {
                Report report = new Report();
                
                report.setAppointmentContact(results.getString("contact"));
                report.setAppointmentDescription(results.getString("description"));
                report.setAppointmentCustomerName(results.getString("customerName"));
                report.setAppointmentStart(results.getString("start"));
                report.setAppointmentEnd(results.getString("end"));
                
                reportTwoList.add(report);
                reportTwoTable.setItems(reportTwoList);
            }

        } catch (SQLException e) {
            System.out.println("Error running report query: " + e.getMessage());
        }
        
    }

    public void handleBackButton(ActionEvent event) {
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
        
    }
    
}
