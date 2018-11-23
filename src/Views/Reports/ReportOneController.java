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

public class ReportOneController implements Initializable {
       
    @FXML private TableView reportOneTable;
    @FXML private TableColumn descriptionColumn;
    @FXML private TableColumn monthColumn;
    @FXML private TableColumn totalColumn;
    
    private static ObservableList<Report> reportOneList = FXCollections.observableArrayList();
    
    private static final String REPORT_ONE_QUERY = "SELECT description," +
        " MONTHNAME(start) as 'Month', COUNT(*) as 'Total' FROM" +
        " appointment GROUP BY description, MONTH(start)";
    
    private PreparedStatement reportQuery;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentMonth"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTotal"));
        
        try {
            reportQuery = DBConnection.getConnection().prepareStatement(REPORT_ONE_QUERY);
            ResultSet results = reportQuery.executeQuery();
            
            while(results.next()) {
                Report report = new Report();
                
                report.setAppointmentDescription(results.getString("description"));
                report.setAppointmentMonth(results.getString("Month"));
                report.setAppointmentTotal(results.getString("Total"));
                
                reportOneList.add(report);
                reportOneTable.setItems(reportOneList);
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
