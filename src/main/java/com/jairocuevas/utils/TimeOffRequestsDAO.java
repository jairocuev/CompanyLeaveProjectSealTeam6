package com.jairocuevas.utils;

import com.jairocuevas.models.Employee;
import com.jairocuevas.models.TimeOffRequest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TimeOffRequestsDAO extends SQL {
	public static String employeeIDColumnName="id";
	public static String employeeFirstNameColumnName="firstName";
	public static String employeeLastNameColumnName="lastName";
	public static String employeeIsAdminColumnName="isAdmin";
	public static String employeeAccruedTimeColumnName="accruedTime";
	public static String employeeTypeColumnName="employeeType";
	
	public static String employeeusername="username";
	public static String employeepassword="password";
//	INSERT INTO `timeOffRequest` (`id`, `employee_ID`, `startDate`, `endDate`, `type`, `requestStatus`) VALUES (NULL, '', '', '', '', '')
	public static String requestIDColumnName="id"; 
	public static String requestEmployeeIDColumnName="employee_ID";
	public static String requestStartDateColumnName="startDate";
	public static String requestEndDateColumnName="endDate";
	public static String requestTypeColumnName="type";
	public static String requestStatusColumnName="requestStatus";
	
	public static TimeOffRequest setTimeOffRequest(Employee currentEmployee, String startDate, String endDate, String type, int requestStatus) {
		
		
		try {
			ps=conn.prepareStatement("INSERT INTO timeOffRequest (employee_ID, startDate, endDate, type, requestStatus) VALUES (?, ?, ?, ?, ?)");
			ps.setLong(1,currentEmployee.getId());
			ps.setString(2, startDate);
			ps.setString(3, endDate);
			ps.setString(4, type);
			ps.setInt(5, 0);
//			rs=ps.executeQuery();
			
			ps.execute();
			
			
		}
		catch(Exception e1){
			e1.printStackTrace();
		}
		return null;
		
		
		
		}

	public static ObservableList<TimeOffRequest> getTimeOffRequestByEmployeeID(Employee e){
		long id = 0;
		int employeeID;
		String startDate = null;
	    String endDate = null;
	    String type;
	    int requestStatus;
	    ObservableList<TimeOffRequest> dayOffRequests =
	            FXCollections.observableArrayList();
	    
	    
	    
		try {
			
			ps=conn.prepareStatement("SELECT * FROM timeOffRequest WHERE employee_ID=?");
			ps.setLong(1, e.getId());
			rs=ps.executeQuery();
			System.out.println(e.getId());
			
			while (rs.next()) {               // Position the cursor                  4 
				
				 id = rs.getLong(requestIDColumnName);        // Retrieve the first column value
				 employeeID = rs.getInt(requestEmployeeIDColumnName);      // Retrieve the first column value
				 startDate=rs.getString(requestStartDateColumnName);
				 endDate=rs.getString(requestEndDateColumnName);
				 type=rs.getString(requestTypeColumnName);
				 requestStatus=rs.getInt(requestStatusColumnName);

				 
				 dayOffRequests.add(new TimeOffRequest(id, e,startDate,endDate,type,requestStatus));
//				 INSERT INTO `timeOffRequest` (`id`, `employee_ID`, `startDate`, `endDate`, `type`, `requestStatus`) VALUES (NULL, '', '', '', '', '')
				 
				}
			
			
		}
		catch(Exception e1){
			e1.printStackTrace();
		}
		
		
		return dayOffRequests;
		
		}
	public static ObservableList<TimeOffRequest> getAllTimeOffRequests(){
		
		long id = 0;
		int employeeID;
		String startDate = null;
	    String endDate = null;
	    String type;
	    int requestStatus;
	    ObservableList<TimeOffRequest> dayOffRequests =
	            FXCollections.observableArrayList();
	    long id1 = 0;
		String firstName = null;
	    String lastName = null;
	    boolean isAdmin = false;
	    long accruedTime = 0;
	    int employeeType = 0;
	    
	    
	    
		try {
			ps=conn.prepareStatement("SELECT * FROM timeOffRequest t JOIN employees e ON e.id=t.employee_ID");
			rs=ps.executeQuery();
			
			while (rs.next()) {               // Position the cursor                  4 
				
				 id = rs.getLong(1);        // Retrieve the first column value
				 employeeID = rs.getInt(requestEmployeeIDColumnName);      // Retrieve the first column value
				 startDate=rs.getString(requestStartDateColumnName);
				 endDate=rs.getString(requestEndDateColumnName);
				 type=rs.getString(requestTypeColumnName);
				 requestStatus=rs.getInt(requestStatusColumnName);
				 
				 
				 id1 = rs.getLong(7);        // Retrieve the first column value
				 firstName = rs.getString(employeeFirstNameColumnName);      // Retrieve the first column value
				 lastName=rs.getString(employeeLastNameColumnName);
				 isAdmin=rs.getBoolean(employeeIsAdminColumnName);
				 accruedTime=rs.getInt(employeeAccruedTimeColumnName);
				 employeeType=rs.getInt(employeeTypeColumnName);

				 
				 dayOffRequests.add(new TimeOffRequest(id, new Employee(id1, firstName, lastName, accruedTime, isAdmin, employeeType),startDate,endDate,type,requestStatus));
//				 INSERT INTO `timeOffRequest` (`id`, `employee_ID`, `startDate`, `endDate`, `type`, `requestStatus`) VALUES (NULL, '', '', '', '', '')
				 
				}
			
			
		}
		catch(Exception e1){
			e1.printStackTrace();
		}
		
		
		return dayOffRequests;
		
		
		}
	public static void setUpdateRequestStatus(TimeOffRequest req, int status){

	    
	    
		try {
			
			ps=conn.prepareStatement("UPDATE timeOffRequest SET requestStatus=? WHERE id=?");
			ps.setLong(2, req.getId());
			ps.setInt(1, status);
			ps.executeUpdate();		
			
			
			
		}
		catch(Exception e1){
			e1.printStackTrace();
		}
		
		

		
		}


}
