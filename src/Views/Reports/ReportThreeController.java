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

public class ReportThreeController implements Initializable {
       
    @FXML private TableView reportThreeTable;
    @FXML private TableColumn countColumn;
    @FXML private TableColumn cityColumn;

    private static ObservableList<Report> reportThreeList = FXCollections.observableArrayList();
    
    private static final String REPORT_THREE_QUERY = "SELECT COUNT(c.customerName) AS count," +
            " ci.city FROM customer c INNER JOIN address a ON c.addressId = a.addressId" +
            " INNER JOIN city ci ON a.cityId = ci.cityId GROUP BY city";
    
    private PreparedStatement reportQuery;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        countColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentCount"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentCity"));
        
        try {
            reportQuery = DBConnection.getConnection().prepareStatement(REPORT_THREE_QUERY);
            ResultSet results = reportQuery.executeQuery();
            
            while(results.next()) {
                Report report = new Report();
                
                report.setAppointmentCount(results.getString("count"));
                report.setAppointmentCity(results.getString("city"));
                
                reportThreeList.add(report);
                reportThreeTable.setItems(reportThreeList);
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
