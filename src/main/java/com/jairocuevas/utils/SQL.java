package com.jairocuevas.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQL {

	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	static PreparedStatement ps=null;


	public SQL(){

		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			String connectionUrl = "jdbc:mysql://localhost:3306/LeaveManagement";
			String connectionUser = "root";
			String connectionPassword = "";

			conn = DriverManager.getConnection(connectionUrl, connectionUser,  connectionPassword);
			stmt = conn.createStatement();
			
	
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
}
