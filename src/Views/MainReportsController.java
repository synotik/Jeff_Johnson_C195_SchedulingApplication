/*
 *
 * Created by Jeff Johnson - November 2018
 *
 */
package Views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainReportsController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }   
    
    public void handleReportOneButton(ActionEvent event) throws IOException {
        
        Parent parent = FXMLLoader.load(getClass().getResource("Reports/ReportOne.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
        
    }
    
    public void handleReportTwoButton(ActionEvent event) throws IOException {
        
        Parent parent = FXMLLoader.load(getClass().getResource("Reports/ReportTwo.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
        
    }
    
    public void handleReportThreeButton(ActionEvent event) throws IOException {
        
        Parent parent = FXMLLoader.load(getClass().getResource("Reports/ReportThree.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
        
    }
    
    public void handleBackButton(ActionEvent event) {
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
        
    }
}
