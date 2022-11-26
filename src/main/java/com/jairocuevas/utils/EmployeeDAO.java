package com.jairocuevas.utils;

import com.jairocuevas.models.Employee;
import com.jairocuevas.models.EmployeeAuth;

public class EmployeeDAO extends SQL {
	
	public static String employeeIDColumnName="id";
	public static String employeeFirstNameColumnName="firstName";
	public static String employeeLastNameColumnName="lastName";
	public static String employeeIsAdminColumnName="isAdmin";
	public static String employeeAccruedTimeColumnName="accruedTime";
	public static String employeeTypeColumnName="employeeType";
	
	public static String employeeusername="username";
	public static String employeepassword="password";

	
	public static Employee getEmployeeByUsername(String username) {
		
		long id = 0;
		String firstName = null;
	    String lastName = null;
	    boolean isAdmin = false;
	    long accruedTime = 0;
	    int employeeType = 0;
	    
	    
		try {
			ps=conn.prepareStatement("SELECT * FROM employeeAuth WHERE username=? LIMIT 1");
			ps.setString(1, username);
			rs=ps.executeQuery();
			
			while (rs.next()) {               // Position the cursor                  4 
				 id = rs.getLong(employeeIDColumnName);        // Retrieve the first column value
				 firstName = rs.getString(employeeFirstNameColumnName);      // Retrieve the first column value
				 lastName=rs.getString(employeeLastNameColumnName);
				 isAdmin=rs.getBoolean(employeeIsAdminColumnName);
				 accruedTime=rs.getInt(employeeAccruedTimeColumnName);
				 employeeType=rs.getInt(employeeTypeColumnName);
				}
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		return new Employee(id, firstName, lastName, accruedTime, isAdmin, employeeType );
	
	}
	
public static Employee getEmployeeByID(long ID) {
		
		long id = 0;
		String firstName = null;
	    String lastName = null;
	    boolean isAdmin = false;
	    long accruedTime = 0;
	    int employeeType = 0;
	    
	    
		try {
			ps=conn.prepareStatement("SELECT * FROM employees WHERE id=? LIMIT 1");
			ps.setLong(1, ID);
			rs=ps.executeQuery();
			
			while (rs.next()) {               // Position the cursor                  4 
				 id = rs.getLong(employeeIDColumnName);        // Retrieve the first column value
				 firstName = rs.getString(employeeFirstNameColumnName);      // Retrieve the first column value
				 lastName=rs.getString(employeeLastNameColumnName);
				 isAdmin=rs.getBoolean(employeeIsAdminColumnName);
				 accruedTime=rs.getInt(employeeAccruedTimeColumnName);
				 employeeType=rs.getInt(employeeTypeColumnName);
				}
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		return new Employee(id, firstName, lastName, accruedTime, isAdmin, employeeType );
	
	}

public static EmployeeAuth getEmployeeAuthByUsername(String user) {
		
		long id = 0;
		String username = null;
	    String password = null;
	    
	    
	    
		try {
			ps=conn.prepareStatement("SELECT * FROM employeeAuth WHERE username=? LIMIT 1");
			ps.setString(1, user);
			rs=ps.executeQuery();
			
			while (rs.next()) {               // Position the cursor                  4 
				 id = rs.getLong(employeeIDColumnName);        // Retrieve the first column value
				 username = rs.getString(employeeusername);      // Retrieve the first column value
				 password=rs.getString(employeepassword);
				}
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		return new EmployeeAuth(id, username, password) ;
	
	}

}
