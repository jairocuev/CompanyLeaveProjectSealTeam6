package com.jairocuevas.models;

import java.util.*;

public class Employee {
    long id;
    String firstName;
    String lastName;
    boolean isAdmin;
    long accruedTime;
    int employeeType;

    public Employee(long id, String fname, String lname, long accruedTime, boolean isAdmin, int employeeType) {
        this.id = id;
        this.firstName = fname;
        this.lastName = lname;
        this.accruedTime = accruedTime;
        this.isAdmin = isAdmin;
        this.employeeType = employeeType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return firstName + " " + lastName;
    }
    // public void setName(String name) {
    // this. = name;
    // }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public long getAccruedTime() {
        return accruedTime;
    }

    public void setAccruedTime(long accruedTime) {
        this.accruedTime = accruedTime;
    }

    public int getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(int employeeType) {
        this.employeeType = employeeType;
    }
    // public void setRequestNumber(int number) {
    // this.requestNumber=number;
    // }
    // public int getRequestNumber() {
    // return requestNumber;
    // }
    // public void addTimeRequest(TimeOffRequest t) {
    // map.put(t.getId(), t);
    // }
    // public TimeOffRequest getTimeOffRequest(Long id) {
    // return map.get(id);
    // }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + firstName + " " + lastName + '\'' +
                '}';
    }
}
