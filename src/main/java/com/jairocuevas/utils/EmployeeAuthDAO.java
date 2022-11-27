package com.jairocuevas.utils;

import java.sql.ResultSet;
import java.sql.Statement;

import com.jairocuevas.models.Employee;
import com.jairocuevas.models.TimeOffRequest;

public class EmployeeAuthDAO extends SQL {

		
		public static String employeeusername="username";
		public static String employeepassword="password";
//		
		
		public static long insertEmployee(String fname, String lname, int employeeType) {
			long pk = 0;
			
			try {
				ps=conn.prepareStatement("INSERT INTO employees ( firstName, lastName, isAdmin, employeeType, accruedTime) VALUES ( ?, ?, ?, ?,?)", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1,fname);
				ps.setString(2, lname);
				ps.setInt(3, 0);
				ps.setInt(4, employeeType);
				ps.setInt(5, 0);
//				rs=ps.executeQuery();
				
				ps.execute();
				
				rs = ps.getGeneratedKeys();
				rs.next();
				pk = rs.getLong(1);
				
				
			}
			catch(Exception e1){
				e1.printStackTrace();
			}
			return pk;
			
			
			}
		
		public static void insertEmployeeAuth(String user, String pass, long key) {
//			"INSERT INTO employeeAuth (`id`, `employee_ID`, `username`, `password`) VALUES (NULL, '', '', '')"
			
			try {
				ps=conn.prepareStatement("INSERT INTO employeeAuth  (username, password, employee_ID) VALUES (?, ?,?)");
				ps.setString(1,user);
				ps.setString(2, pass);
				ps.setLong(3, key);

//				rs=ps.executeQuery();
				
				ps.execute();
				
				
			}
			catch(Exception e1){
				e1.printStackTrace();
			}

			
		}

		

}
