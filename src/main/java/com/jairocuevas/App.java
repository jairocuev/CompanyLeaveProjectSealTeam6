package com.jairocuevas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.*;
import com.jairocuevas.models.Employee;
import com.jairocuevas.models.TimeOffRequest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.crypto.*;
import java.util.*;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    public static Employee currentEmployee;
    //after launch this method is called 
    @Override
    public void start(Stage stage) throws IOException {
    	//this is admin example
   
    	
        stage.setTitle("Company Leave MFFF");
        //auto login
        var loggedIn = false;
        if(!loggedIn) {
        	//if failed log on reload login screen and loginContoller is listening to events
            scene = new Scene(loadFXML("login"), 640, 480);
        }else{
        	
            scene = new Scene(loadFXML("employee"), 640, 480);

        }
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }


//launches the login GUI 
    public static void main(String[] args) {
        launch();
    }

}