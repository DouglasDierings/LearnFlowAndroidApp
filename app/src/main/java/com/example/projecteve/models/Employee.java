package com.example.projecteve.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Employee {


    private String firstName;
    private String lastName;
    private String employeeNumber;
    private Boolean courseCompleted; // Add this line

    private Boolean site1;
    private Boolean site2;
    private Boolean site3;

    public Employee(String firstName, String lastName, String employeeNumber, Boolean site1, Boolean site2, Boolean site3, Boolean courseCompleted) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeNumber = employeeNumber;
        this.site1 = site1;
        this.site2 = site2;
        this.site3 = site3;
        this.courseCompleted = courseCompleted;
    }

    public Boolean getCourseCompleted() {
        return courseCompleted;
    }

    public void setCourseCompleted(Boolean courseCompleted) {
        this.courseCompleted = courseCompleted;
    }

    public Employee() {
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public Boolean getSite1() {
        return site1;
    }

    public void setSite1(Boolean site1) {
        this.site1 = site1;
    }

    public Boolean getSite2() {
        return site2;
    }

    public void setSite2(Boolean site2) {
        this.site2 = site2;
    }

    public Boolean getSite3() {
        return site3;
    }

    public void setSite3(Boolean site3) {
        this.site3 = site3;
    }



    public void saveEmployee() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("employees").child(getEmployeeNumber()).setValue(this);
    }



}
