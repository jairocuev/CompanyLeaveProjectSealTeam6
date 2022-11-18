package com.jairocuevas.controllers.employee;

import com.jairocuevas.App;
import com.jairocuevas.controllers.admin.AdminController;
import com.jairocuevas.models.Employee;
import com.jairocuevas.models.TimeOffRequest;

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
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.DAYS;

public class EmployeeController implements Initializable{
    @FXML private Label employeeNameLabel;
    @FXML private Label employeePtoBalanceLabel;

    @FXML private Label errorLabel;
    @FXML private Button manageEmployeeRequestButton;
    @FXML private Button logoutButton;
    @FXML private Button requestTimeOffButton;
    @FXML private Button showCalendarButton;

    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private ChoiceBox ptoChoiceBox;

    private Object timeOffType;

    @SuppressWarnings("unchecked")
	@Override
    public void initialize(URL url, ResourceBundle rb){
    	//this is just to set the login in page buttons and name of user to show up and days off. looks in the app.java for the current employee
    	
    	 employeeNameLabel.setText(App.currentEmployee.getName());
         employeePtoBalanceLabel.setText(String.valueOf(App.currentEmployee.getAccruedTime()));
         manageEmployeeRequestButton.setVisible(App.currentEmployee.isAdmin());
         if(App.currentEmployee.getEmployeeType() == 0) ptoChoiceBox.getItems().add("Sick Leave");
         else if(App.currentEmployee.getEmployeeType() == 1){
             ptoChoiceBox.getItems().add("Sick Leave");
             ptoChoiceBox.getItems().add("Maternity Leave");
             ptoChoiceBox.getItems().add("PTO");
             ptoChoiceBox.getItems().add("Emergency Leave");
         }
         ptoChoiceBox.setOnAction((event) -> {
             int selectedIndex = ptoChoiceBox.getSelectionModel().getSelectedIndex();
             Object selectedItem = ptoChoiceBox.getSelectionModel().getSelectedItem();

             timeOffType = selectedItem;
             });
    }

//    @SuppressWarnings("unchecked")
//	public void init(Employee e){
//    	System.out.println("we in the option box");
//        if(App.currentEmployee.getEmployeeType() == 0) ptoChoiceBox.getItems().add("Sick Leave");
//        else if(App.currentEmployee.getEmployeeType() == 1){
//        	System.out.println("we in the option box123");
//            ptoChoiceBox.getItems().add("Sick Leave");
//            ptoChoiceBox.getItems().add("Maternity Leave");
//            ptoChoiceBox.getItems().add("PTO");
//            ptoChoiceBox.getItems().add("Emergency Leave");
//        }
//
//
//        ptoChoiceBox.setOnAction((event) -> {
//            int selectedIndex = ptoChoiceBox.getSelectionModel().getSelectedIndex();
//            Object selectedItem = ptoChoiceBox.getSelectionModel().getSelectedItem();
//
//            timeOffType = selectedItem;
//        });
//    }

    @FXML
    public void logOut() throws IOException{
            FXMLLoader loader = new FXMLLoader(App.class.getResource("login.fxml"));
            Parent root = loader.load();

            Window window = logoutButton.getScene().getWindow();
            App.currentEmployee = null;

            if (window instanceof Stage) {
                Scene scene =  new Scene(root);
                Stage stage = (Stage) window ;
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }
    }
    
    // code will be use if button is is visible, line 41 (if user is admin)

    @FXML
    public void manageEmployeeRequests() throws IOException{
        FXMLLoader loader = new FXMLLoader(App.class.getResource("admin.fxml"));
        Parent root = loader.load();

        Window window = logoutButton.getScene().getWindow();

        if (window instanceof Stage) {
            Scene scene =  new Scene(root);
            Stage stage = (Stage) window ;
            stage.setTitle("Admin Panel");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }

    @FXML
    public void showCalendar() throws IOException{
        FXMLLoader loader = new FXMLLoader(App.class.getResource("employeecalendar.fxml"));
        Parent root = loader.load();

        Window window = logoutButton.getScene().getWindow();

        if (window instanceof Stage) {
            Scene scene =  new Scene(root);
            Stage stage = (Stage) window ;
            stage.setTitle("Calendar");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }

    @FXML
    public void requestTimeOff() throws IOException{
        ZoneId defaultZoneId = ZoneId.systemDefault();
    	//later will add to database
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        var days = getWorkingDaysBetweenTwoDates(Date.from(startDate.atStartOfDay(defaultZoneId).toInstant()), Date.from(endDate.atStartOfDay(defaultZoneId).toInstant()));
        var hours = days * 8;
        if(hours > App.currentEmployee.getAccruedTime()){
            errorLabel.setStyle("-fx-text-fill: red");
            errorLabel.setText("You do not have enough accrued hours for the time requested.");
            System.out.println("Not enough time");
        }else{
            AdminController.dayOffRequests.add(new TimeOffRequest(7,App.currentEmployee,startDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    endDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),timeOffType.toString(),0));
            errorLabel.setStyle("-fx-text-fill: black");
            errorLabel.setText("Request Submitted..");
            startDatePicker.setValue(null);
            endDatePicker.setValue(null);
            ptoChoiceBox.setValue(null);
            App.currentEmployee.setAccruedTime(App.currentEmployee.getAccruedTime() - hours);
            employeePtoBalanceLabel.setText(String.valueOf(App.currentEmployee.getAccruedTime()));
        }


    	
//        System.out.println("Start: "+ startDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        System.out.println("End: "+ endDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        System.out.println("Type: " + timeOffType);
    }

    public static int getWorkingDaysBetweenTwoDates(Date startDate, Date endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        int workDays = 0;

        //Return 0 if start and end are the same
        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
            return 0;
        }

        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
            startCal.setTime(endDate);
            endCal.setTime(startDate);
        }

        do {
            //excluding start date
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                ++workDays;
            }
        } while (startCal.getTimeInMillis() < endCal.getTimeInMillis()); //excluding end date

        return workDays;
    }

}
