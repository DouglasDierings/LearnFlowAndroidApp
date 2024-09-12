package com.example.projecteve.models;

import java.util.List;

public class Site {
    private int siteCode;
    private String name;
    private String location;
    private int numEmployees;

    private List<Course> coursesList;

    public Site() {
    }

    public Site(String name, String location, List<Course> coursesList) {
        this.name = name;
        this.location = location;
        this.coursesList = coursesList;
    }

    public Site(int siteCode, String name, String location, int numEmployees, List<Course> coursesList) {
        this.name = name;
        this.location = location;
        this.numEmployees = numEmployees;
        this.coursesList = coursesList;
        this.siteCode = siteCode;
    }

    // Getters e Setters


    public int getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(int siteCode) {
        this.siteCode = siteCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNumEmployees() {
        return numEmployees;
    }

    public void setNumEmployees(int numEmployees) {
        this.numEmployees = numEmployees;
    }

    public List<Course> getCoursesList() {
        return coursesList;
    }

    public void setCoursesList(List<Course> coursesList) {
        this.coursesList = coursesList;
    }
}
