package com.jairocuevas.controllers;

import java.io.IOException;

import com.jairocuevas.App;
import com.jairocuevas.controllers.admin.AdminEmployeeController;
import com.jairocuevas.controllers.employee.EmployeeController;
import com.jairocuevas.models.Employee;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class LoginController {

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField usernamePasswordField;
    @FXML
    private Label errorLabel;

    @FXML
    private void handleKeyPressed(KeyEvent ke){
        if(ke.getCode().equals(KeyCode.ENTER)){
            try {
                this.login();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void login() throws IOException {
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32,64,1,15*1024,2);
        var myPassword = usernamePasswordField.getText();
        var encodedPassword = encoder.encode(myPassword);
        var validPassword = encoder.matches("admin", encodedPassword);

        if(validPassword) {
            LoginRedirect(new Employee(1, usernameTextField.getText(), 10,true, 1));
        } else{
            errorLabel.setText("Login Failed...");
        }

    }
    @FXML
    private void registerScreenNav() throws IOException{
        App.setRoot("register");
    }

    @FXML
    private void LoginRedirect(Employee emp) throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("employee.fxml"));
            Parent root = loader.load();

            //The following both lines are the only addition we need to pass the arguments
            App.currentEmployee = emp;

            Window window = usernameTextField.getScene().getWindow();
            if (window instanceof Stage) {
                Scene scene =  new Scene(root);
                Stage stage = (Stage) window ;
                stage.setScene(scene);
                stage.setTitle(emp.getName() + "'s Page");
                stage.setResizable(false);
                stage.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
