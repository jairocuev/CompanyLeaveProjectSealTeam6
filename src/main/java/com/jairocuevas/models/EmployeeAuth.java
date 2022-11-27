package com.jairocuevas.models;

public class EmployeeAuth {
	long id;
	String username;
	String password;
	int employeeID;
	

	public EmployeeAuth(long id, String username, String password, int employeeID) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.employeeID=employeeID;
	}
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	  public int getEmployeeID() {
			return employeeID;
		}

		public void setEmployeeID(int employeeID) {
			this.employeeID = employeeID;
		}
    
    

}
