package com.jairocuevas.controllers.admin;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.jairocuevas.App;
import com.jairocuevas.controllers.employee.EmployeeController;
import com.jairocuevas.models.Employee;
import com.jairocuevas.models.TimeOffRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

public class AdminController implements Initializable {

    @FXML
    private VBox secondaryVBox = new VBox();
    @FXML
    private TableView<TimeOffRequest> employeeTable = new TableView<TimeOffRequest>();
    @FXML
    TableColumn employeeIdColumn = new TableColumn("ID");
    @FXML
    TableColumn employeeNameColumn = new TableColumn("Name");
    @FXML
    TableColumn employeeTypeColumn = new TableColumn("Employee Type");
    @FXML
    TableColumn requestTypeColumn = new TableColumn("Leave Type");

    @FXML private Button homeButton;
    public static ObservableList<TimeOffRequest> dayOffRequests =
            FXCollections.observableArrayList(
                    new TimeOffRequest(1, new Employee(1, "Marisol" ,null, 0, false, 0), "11/25/2022", "11/25/2022", "Sick Leave",2),
                    new TimeOffRequest(2, new Employee(121, "Jairo", null, 0,false, 0), "11/26/2022", "11/26/2022", "Sick Leave",0),
                    new TimeOffRequest(3, new Employee(121, "Jair", null, 0,false, 0), "11/28/2022", "11/29/2022", "Sick Leave",0),
                    new TimeOffRequest(4, new Employee(3, "Jose", null, 50,false, 0), "11/29/2022", "11/30/2022", "Maternity Leave",1),
                    new TimeOffRequest(5, new Employee(5, "Angie", null, 67,false, 0), "11/30/2022", "11/30/2022", "PTO",2)
            );

    @SuppressWarnings("unchecked")
	@Override
    public void initialize(URL url, ResourceBundle rb){

        employeeIdColumn.setCellValueFactory(
                new PropertyValueFactory<TimeOffRequest, String>("id"));

        employeeNameColumn.setCellValueFactory(
                new PropertyValueFactory<TimeOffRequest, String>("employeeName"));


        employeeTypeColumn.setCellValueFactory(
                new PropertyValueFactory<TimeOffRequest, String>("employeeType"));

        employeeTypeColumn.setCellFactory(column -> {
            return new TableCell<TimeOffRequest, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty); //This is mandatory
                    if (item == null || empty) { //If the cell is empty
                        setText(null);
                        setStyle("");
                    } else { //If the cell is not empty

                        //We get here all the info of the Person of this row
                        Employee employee = getTableView().getItems().get(getIndex()).getEmployee();

                        if(employee.getEmployeeType() == 0){
                            setText("Part Time");
                        }else if(employee.getEmployeeType() == 1){
                            setText("Full Time");
                        }
                    }
                }
            };
        });


        requestTypeColumn.setCellValueFactory(
                new PropertyValueFactory<TimeOffRequest, String>("type"));
        ObservableList<TimeOffRequest> filteredList= dayOffRequests.stream().filter(t->t.getRequestStatus()==0).collect(Collectors.toCollection(FXCollections::observableArrayList));
        employeeTable.setItems(filteredList);
        employeeTable.getColumns().addAll(employeeIdColumn, employeeNameColumn, employeeTypeColumn, requestTypeColumn);

        employeeTable.setRowFactory( tv -> {
            TableRow<TimeOffRequest> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    TimeOffRequest rowData = row.getItem();
                    try {
                        buttonClicked(rowData);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return row ;
        });
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("admin");
    }

    @FXML
    private void logSelectedRow(ActionEvent event){
        System.out.println(employeeTable.getSelectionModel().getSelectedItem());
    }

    //time request info
    public void buttonClicked(TimeOffRequest req) throws IOException{

        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("adminemployee.fxml"));
            Parent root = loader.load();

            //The following both lines are the only addition we need to pass the arguments
            AdminEmployeeController controller2 = loader.getController();
            controller2.init(req);
            controller2.setEmployeeName(req.getEmployee().getName(),"AutoFill for now");

            Window window = employeeTable.getScene().getWindow();
            if (window instanceof Stage) {
                Scene scene =  new Scene(root);
                Stage stage = (Stage) window ;
                stage.setScene(scene);
                stage.setTitle(req.getEmployee().getName() + "'s Page");
                stage.setResizable(false);
                stage.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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