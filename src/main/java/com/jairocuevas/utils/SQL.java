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
	static PreparedStatement ps = null;

	public SQL() {

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// TODO: Update connection string to launch app
			String connectionUrl = "jdbc:mysql://root:goRDYhi5HnyIfAt4t1y0@containers-us-west-119.railway.app:5638/railway";

			conn = DriverManager.getConnection(connectionUrl);
			stmt = conn.createStatement();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
