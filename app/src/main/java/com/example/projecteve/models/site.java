package com.example.projecteve.models;

import java.util.List;

public class site {
    private String name;
    private String location;
    private int numEmployees;

    private List<Course> coursesList ;

    public site() {
    }

    public site(String name, String location, int numEmployees) {
        this.name = name;
        this.location = location;
        this.numEmployees = numEmployees;
    }

    public List<Course> getCoursesList() {
        return coursesList;
    }

    public void setCoursesList(List<Course> coursesList) {
        this.coursesList = coursesList;
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

