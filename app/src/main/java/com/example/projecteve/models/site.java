package com.example.projecteve.models;

public class site {
    private String name;
    private String location;
    private int numEmployees;

    public site(String name, String location, int numEmployees) {
        this.name = name;
        this.location = location;
        this.numEmployees = numEmployees;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getNumEmployees() {
        return numEmployees;
    }
}

