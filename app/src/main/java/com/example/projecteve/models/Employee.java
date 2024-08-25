package com.example.projecteve.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Employee {

    private String firstName;
    private String lastName;
    private String employeeNumber;

    // Lista de sites associados a este funcionário
    private List<Site> sites;

    public Employee(String firstName, String lastName, String employeeNumber, List<Site> sites) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeNumber = employeeNumber;

        this.sites = sites;
    }

    public Employee() {}

    // Getters e Setters

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


    public List<Site> getSites() {
        return sites;
    }

    public void setSites(List<Site> sites) {
        this.sites = sites;
    }

    // Método para salvar no Firebase
    public void saveEmployee() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("employees").child(getEmployeeNumber()).setValue(this);
    }
}
