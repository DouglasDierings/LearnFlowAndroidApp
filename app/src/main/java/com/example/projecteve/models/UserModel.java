package com.example.projecteve.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

public class UserModel {

    private String id;
    private String firstName;
    private String lastName;
    private String employeeNumber;
    private String email;

    public UserModel() {
    }

    public UserModel(String id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployeeNumber() {return employeeNumber;}

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;}

    public void save(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("users").child(getId()).setValue(this);
    }

}
