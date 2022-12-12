package com.jairocuevas.controllers.employee;

import com.jairocuevas.App;
import com.jairocuevas.utils.DateHelper;
import com.jairocuevas.utils.EmployeeDAO;
import com.jairocuevas.utils.TimeOffRequestsDAO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {
    @FXML
    private Label employeeNameLabel;
    @FXML
    private Label employeePtoBalanceLabel;

    @FXML
    private Label errorLabel;
    @FXML
    private Button manageEmployeeRequestButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button requestTimeOffButton;
    @FXML
    private Button showCalendarButton;

    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ChoiceBox ptoChoiceBox;

    private Object timeOffType;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // this is just to set the login in page buttons and name of user to show up and
        // days off. looks in the app.java for the current employee

        employeeNameLabel.setText(App.currentEmployee.getName());
        employeePtoBalanceLabel.setText(String.valueOf(App.currentEmployee.getAccruedTime()));
        manageEmployeeRequestButton.setVisible(App.currentEmployee.isAdmin());
        if (App.currentEmployee.getEmployeeType() == 0)
            ptoChoiceBox.getItems().add("Sick Leave");
        else if (App.currentEmployee.getEmployeeType() == 1) {
            ptoChoiceBox.getItems().add("Sick Leave");
            ptoChoiceBox.getItems().add("Maternity Leave");
            ptoChoiceBox.getItems().add("PTO");
            ptoChoiceBox.getItems().add("Emergency Leave");
        }
        ptoChoiceBox.setOnAction((event) -> {
            Object selectedItem = ptoChoiceBox.getSelectionModel().getSelectedItem();

            timeOffType = selectedItem;
        });
    }

    @FXML
    public void logOut() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("login.fxml"));
        Parent root = loader.load();

        Window window = logoutButton.getScene().getWindow();
        App.currentEmployee = null;

        if (window instanceof Stage) {
            Scene scene = new Scene(root);
            Stage stage = (Stage) window;
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }

    // code will be use if button is is visible, line 41 (if user is admin)

    @FXML
    public void manageEmployeeRequests() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("admin.fxml"));
        Parent root = loader.load();

        Window window = logoutButton.getScene().getWindow();

        if (window instanceof Stage) {
            Scene scene = new Scene(root);
            Stage stage = (Stage) window;
            stage.setTitle("Admin Panel");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }

    @FXML
    public void showCalendar() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("employeecalendar.fxml"));
        Parent root = loader.load();

        Window window = logoutButton.getScene().getWindow();

        if (window instanceof Stage) {
            Scene scene = new Scene(root);
            Stage stage = (Stage) window;
            stage.setTitle("Calendar");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }

    @FXML
    public void requestTimeOff() throws IOException {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        // later will add to database
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (startDate == null || endDate == null || timeOffType == null) {
            errorLabel.setStyle("-fx-text-fill: red");
            errorLabel.setText("Please fill out all fields.");
            return;
        }
        if (endDate.isBefore(startDate)) {
            errorLabel.setStyle("-fx-text-fill: red");
            errorLabel.setText("Start date must be before end date.");
            return;
        }
        var days = DateHelper.getWorkingDaysBetweenTwoDates(
                Date.from(startDate.atStartOfDay(defaultZoneId).toInstant()),
                Date.from(endDate.atStartOfDay(defaultZoneId).toInstant()));
        var hours = days * 8;
        if (hours > App.currentEmployee.getAccruedTime()) {
            errorLabel.setStyle("-fx-text-fill: red");
            errorLabel.setText("You do not have enough accrued hours for the time requested.");
        } else {

            TimeOffRequestsDAO.setTimeOffRequest(App.currentEmployee,
                    startDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    endDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    timeOffType.toString(),
                    0);

            errorLabel.setStyle("-fx-text-fill: black");
            errorLabel.setText("Request Submitted..");
            startDatePicker.setValue(null);
            endDatePicker.setValue(null);
            ptoChoiceBox.setValue(null);

            EmployeeDAO.updateEmployeeAccruedTime(App.currentEmployee,
                    (int) App.currentEmployee.getAccruedTime() - hours);

            App.currentEmployee.setAccruedTime(App.currentEmployee.getAccruedTime() - hours);
            employeePtoBalanceLabel.setText(String.valueOf(App.currentEmployee.getAccruedTime()));
        }
    }

}
