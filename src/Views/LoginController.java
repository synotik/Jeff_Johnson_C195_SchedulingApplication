/*
 * 
 * Created by Jeff Johnson - November 2018
 * 
 */
package Views;

import Models.DBConnection;
import Models.LogUtility;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class LoginController implements Initializable {
    
    @FXML Label usernameLabel;
    @FXML Label passwordLabel;
    @FXML TextField txtFieldUsername;
    @FXML PasswordField txtFieldPassword;
    @FXML Label mainLabel;
    @FXML Button loginButton;
    @FXML Button cancelButton;
    
    public static String currentUser;
    private String errorTitle;
    private String errorHeader;
    private String errorText;

    public static final String LOGIN_QUERY = "SELECT * FROM user WHERE userName = ? AND password = ?";
    
    private PreparedStatement queryUsers;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Locale locale = Locale.getDefault();
        rb = ResourceBundle.getBundle("languages/login", locale);
        
        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        mainLabel.setText(rb.getString("maintitle"));
        loginButton.setText(rb.getString("loginbutton"));
        cancelButton.setText(rb.getString("cancelbutton"));
        
        errorTitle = rb.getString("title");
        errorHeader = rb.getString("header");
        errorText = rb.getString("text");
        
    }  
    
    public void handleLogin(ActionEvent event) throws IOException, ClassNotFoundException {
        
        DBConnection.connect();
        String username = txtFieldUsername.getText();
        currentUser = txtFieldUsername.getText();
        String password = txtFieldPassword.getText();
        
        if(authenticate(username, password)) {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Scheduling Application - Main");
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(errorTitle);
            alert.setHeaderText(errorHeader);
            alert.setContentText(errorText);
            alert.showAndWait();
        }
        
    }
    
    public boolean authenticate(String username, String password) {
        
        try {
            queryUsers = DBConnection.getConnection().prepareStatement(LOGIN_QUERY);
            queryUsers.setString(1, username);
            queryUsers.setString(2, password);
            
            ResultSet results = queryUsers.executeQuery();
            
            if(results.next()) {
                LogUtility.log(username, true);
                return true;
            } else {
                LogUtility.log(username, false);
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("Login attempt failed: " + e.getMessage());
            return false;
        }
        
    }
    
    public void handleCancelButton(ActionEvent event) {
        
        System.exit(0);
        
    }
}
