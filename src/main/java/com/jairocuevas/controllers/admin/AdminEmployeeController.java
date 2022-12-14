package com.jairocuevas.controllers.admin;

import com.jairocuevas.App;
import com.jairocuevas.models.TimeOffRequest;
import com.jairocuevas.utils.DateHelper;
import com.jairocuevas.utils.EmployeeDAO;
import com.jairocuevas.utils.TimeOffRequestsDAO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class AdminEmployeeController {
    @FXML
    private AnchorPane adminEmployeePane;
    @FXML
    private Button backButton;
    @FXML
    private Button acceptButton;
    @FXML
    private Button denyButton;
    @FXML
    private TextField lastName;
    @FXML
    private Label responseLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label endDateLabel;
    @FXML
    private Label typeLabel;

    TimeOffRequest reqe;

    public void init(TimeOffRequest req) {
        reqe = req;
        setRequestData(req);
    }

    public void setRequestData(TimeOffRequest request) {
        nameLabel.setText(request.getEmployee().getName());
        startDateLabel.setText(request.getStartDate());
        endDateLabel.setText(request.getEndDate());
        typeLabel.setText(request.getType());
    }

    @FXML
    public void accept() throws IOException {
        responseLabel.setText("Request " + reqe.getEmployee().getId() + " has been approved");
        TimeOffRequestsDAO.setUpdateRequestStatus(reqe, 1);
    }

    @FXML
    public void deny() throws IOException {
        responseLabel.setText("Request " + reqe.getEmployee().getId() + " has been denied");
        TimeOffRequestsDAO.setUpdateRequestStatus(reqe, 2);
        LocalDate startDate = LocalDate.parse(reqe.getStartDate());
        LocalDate endDate = LocalDate.parse(reqe.getEndDate());
        ZoneId defaultZoneId = ZoneId.systemDefault();

        var days = DateHelper.getWorkingDaysBetweenTwoDates(
                Date.from(startDate.atStartOfDay(defaultZoneId).toInstant()),
                Date.from(endDate.atStartOfDay(defaultZoneId).toInstant()));
        var hours = days * 8;

        EmployeeDAO.updateEmployeeAccruedTime(reqe.getEmployee(), (int) reqe.getEmployee().getAccruedTime() + hours);
    }

    @FXML
    public void back() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("admin.fxml"));
        Parent root = loader.load();

        Window window = backButton.getScene().getWindow();

        if (window instanceof Stage) {
            Scene scene = new Scene(root);
            Stage stage = (Stage) window;
            stage.setTitle("Admin Panel");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }

    }
}
