package com.jairocuevas.controllers.employee;

import com.jairocuevas.App;
import com.jairocuevas.controllers.admin.AdminController;
import com.jairocuevas.models.TimeOffRequest;
import com.jairocuevas.utils.TimeOffRequestsDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EmployeeCalendarController implements Initializable {
    @FXML
    private Button homeButton;

    @FXML
    private TableView<TimeOffRequest> employeeCalendarTable = new TableView<TimeOffRequest>();
    @FXML
    TableColumn employeeIdColumn = new TableColumn("ID");
    @FXML
    TableColumn startDateColumn = new TableColumn("Start Date");
    @FXML
    TableColumn endDateColumn = new TableColumn("End Date");
    @FXML
    TableColumn requestType = new TableColumn("Type");
    @FXML
    TableColumn requestStatusColumn = new TableColumn("Status");
    @FXML
    TableColumn employeeColumn = new TableColumn("Employee");


    @SuppressWarnings("unchecked")
    @Override
    public void initialize(URL location, ResourceBundle resources){
        employeeIdColumn.setCellValueFactory(
                new PropertyValueFactory<TimeOffRequest, String>("id"));

        startDateColumn.setCellValueFactory(
                new PropertyValueFactory<TimeOffRequest, String>("startDate"));


        endDateColumn.setCellValueFactory(
                new PropertyValueFactory<TimeOffRequest, String>("endDate"));

        requestType.setCellValueFactory(
                new PropertyValueFactory<TimeOffRequest, String>("type"));

        requestStatusColumn.setCellValueFactory(
                new PropertyValueFactory<TimeOffRequest, String>("type"));
        
        requestStatusColumn.setCellFactory(column -> {
            return new TableCell<TimeOffRequest, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty); //This is mandatory
                    if (item == null || empty) { //If the cell is empty
                        setText(null);
                        setStyle("");
                    } else { //If the cell is not empty

                        //We get here all the info of the Person of this row
                        TimeOffRequest request = getTableView().getItems().get(getIndex());

                        if(request.getRequestStatus() == 0){
                            setText("Pending");
                        }else if(request.getRequestStatus() == 1){
                            setText("Approved");
                        }else if(request.getRequestStatus() == 2){
                            setText("Denied");
                        }
                    }
                }
            };
        });
        
        
        try {
        	ObservableList<TimeOffRequest> calendarList;
        	
        	if(App.currentEmployee.isAdmin()) {
        	 calendarList=TimeOffRequestsDAO.getAllTimeOffRequests();
        	}
        	else {
        	 calendarList=TimeOffRequestsDAO.getTimeOffRequestByEmployeeID(App.currentEmployee);
        	}
        	 employeeColumn.setCellValueFactory(
             		new PropertyValueFactory<TimeOffRequest, String>("employeeName"));

//             ObservableList<TimeOffRequest> filteredList= calendarList.stream().filter(t->t.getEmployee().getId() == App.currentEmployee.getId()).collect(Collectors.toCollection(FXCollections::observableArrayList));
             employeeCalendarTable.setItems(calendarList);
             System.out.println(calendarList.size());
             employeeCalendarTable.getColumns().addAll(employeeIdColumn, startDateColumn, endDateColumn,requestType, requestStatusColumn, employeeColumn);

        }
        catch(Exception e) {
        	
        }
        
//        employeeColumn.setCellValueFactory(
//        		new PropertyValueFactory<TimeOffRequest, String>("employeeName"));
//
//        ObservableList<TimeOffRequest> filteredList= AdminController.dayOffRequests.stream().filter(t->t.getEmployee().getId() == App.currentEmployee.getId()).collect(Collectors.toCollection(FXCollections::observableArrayList));
//        employeeCalendarTable.setItems(filteredList);
//        employeeCalendarTable.getColumns().addAll(employeeIdColumn, startDateColumn, endDateColumn,requestType, requestStatusColumn, employeeColumn);
    }

    @FXML
    public void goHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("employee.fxml"));
        Parent root = loader.load();

        Window window = homeButton.getScene().getWindow();

        if (window instanceof Stage) {
            Scene scene =  new Scene(root);
            Stage stage = (Stage) window ;
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }
}
