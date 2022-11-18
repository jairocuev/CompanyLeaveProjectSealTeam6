package com.jairocuevas.models;

public class TimeOffRequest {
    long id;
    Employee employee;
    String startDate;
    String endDate;
    String type;
    int requestStatus;

    public TimeOffRequest(long id,Employee e, String startDate, String endDate, String type, int requestStatus){
         this.id = id;
         this.employee = e;
         this.startDate = startDate;
         this.endDate = endDate;
         this.type = type;
         this.requestStatus= requestStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getEmployeeName(){
        return employee.getName();
    }

    public Integer getEmployeeType(){
        return employee.getEmployeeType();
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void setRequestStatus(int status) {
    	this.requestStatus=status;
    }
    public int getRequestStatus() {
    	return requestStatus;
    }
}
