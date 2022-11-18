module com.jairocuevas {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.security.crypto;
    requires com.calendarfx.view;

    opens com.jairocuevas to javafx.fxml;
    exports com.jairocuevas;
    exports com.jairocuevas.controllers;
    opens com.jairocuevas.controllers to javafx.fxml;

    exports com.jairocuevas.models;
    exports com.jairocuevas.controllers.admin;
    opens com.jairocuevas.controllers.admin to javafx.fxml;

    exports com.jairocuevas.controllers.employee;
    opens com.jairocuevas.controllers.employee to javafx.fxml;
    
    exports com.calendarfx.app to javafx.graphics;
    
    
}
