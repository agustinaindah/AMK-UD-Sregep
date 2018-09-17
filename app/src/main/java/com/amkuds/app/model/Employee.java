package com.amkuds.app.model;

public class Employee {

    /*data dummy*/

    public String employeeImage;
    public String employeeName;
    public String employeePosition;
    public String employeeStatus;
    public String employeeEndContract;

    public Employee(String employeeName, String employeeImage, String employeePosition, String employeeStatus, String employeeEndContract) {
        this.employeeImage = employeeImage;
        this.employeeName = employeeName;
        this.employeePosition = employeePosition;
        this.employeeStatus = employeeStatus;
        this.employeeEndContract = employeeEndContract;
    }

    public String getEmployeeImage() {
        return employeeImage;
    }

    public void setEmployeeImage(String employeeImage) {
        this.employeeImage = employeeImage;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeePosition() {
        return employeePosition;
    }

    public void setEmployeePosition(String employeePosition) {
        this.employeePosition = employeePosition;
    }

    public String getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(String employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public String getEmployeeEndContract() {
        return employeeEndContract;
    }

    public void setEmployeeEndContract(String employeeEndContract) {
        this.employeeEndContract = employeeEndContract;
    }
}
