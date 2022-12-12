package com.jairocuevas.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import com.jairocuevas.App;
import com.jairocuevas.utils.EmployeeAuthDAO;
import com.jairocuevas.utils.EmployeeDAO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class RegisterController implements Initializable {
	@FXML
	private ChoiceBox employeeType;
	@FXML
	private PasswordField reEnterPasswordText;
	@FXML
	private TextField usernameText;
	@FXML
	private TextField firstNameText;
	@FXML
	private TextField lastNameText;
	@FXML
	private PasswordField passwordText;
	@FXML
	private Label registerResponseLabel;
	@FXML
	private Button registerButton;
	@FXML
	private Button homeButton;

	private int eType = 99;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		employeeType.getItems().add("Part-Time");
		employeeType.getItems().add("Full-Time");
		employeeType.setOnAction((event) -> {
			String selectedItem = employeeType.getSelectionModel().getSelectedItem().toString();

			if (selectedItem.equals("Part-Time")) {
				eType = 0;
			} else if (selectedItem.equals("Full-Time")) {
				eType = 1;
			}
		});
	}

	@FXML
	public void register() throws IOException {

		var username = usernameText.getText();
		var fName = firstNameText.getText();
		var lName = lastNameText.getText();
		var pass = passwordText.getText();
		var reEnterPass = reEnterPasswordText.getText();

		if (username.trim().isEmpty() || fName.trim().isEmpty() || lName.equals("") || pass.equals("") || reEnterPass.equals("")
				|| eType == 99) {
			registerResponseLabel.setStyle("-fx-text-fill: red");
			registerResponseLabel.setText("Please Fill out all fields");
			return;
		}

		if (!pass.equals(reEnterPass)) {
			registerResponseLabel.setStyle("-fx-text-fill: red");

			registerResponseLabel.setText("Password does not match!");
			return;
		}

		var exists = EmployeeDAO.getUserExists(username);

		if (exists > 0) {
			registerResponseLabel.setStyle("-fx-text-fill: red");
			registerResponseLabel.setText("Employee with that username already exists");
			return;
		}

		Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32, 64, 1, 15 * 1024, 2);
		var hashedpassword = encoder.encode(pass);

		var key = EmployeeAuthDAO.insertEmployee(fName, lName, eType);
		EmployeeAuthDAO.insertEmployeeAuth(username, hashedpassword, key);

		goLogin();
	}

	@FXML
	public void goLogin() throws IOException {
		FXMLLoader loader = new FXMLLoader(App.class.getResource("login.fxml"));
		Parent root = loader.load();

		Window window = homeButton.getScene().getWindow();

		if (window instanceof Stage) {
			Scene scene = new Scene(root);
			Stage stage = (Stage) window;
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		}
	}

}
