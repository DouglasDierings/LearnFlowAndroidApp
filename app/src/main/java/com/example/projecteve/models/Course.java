package com.example.projecteve.models;

import java.util.HashMap;
import java.util.Map;

public class Course {
    private String courseName;
    private Boolean isCompleted; // This remains for the original constructor
    private Map<String, Boolean> monthCompletion;

    public Course() {
    }

    // Original constructor that uses courseName and isCompleted
    public Course(String courseName, Boolean isCompleted) {
        this.courseName = courseName;
        this.isCompleted = isCompleted;
    }

    // New constructor to handle course with month-based completion status
    public Course(String courseName, Map<String, Boolean> monthCompletion) {
        this.courseName = courseName;
        this.monthCompletion = monthCompletion != null ? monthCompletion : initializeMonthCompletion();
    }

    // Static method to initialize month completion (all months set to false)
    public static Map<String, Boolean> initializeMonthCompletion() {
        Map<String, Boolean> monthCompletion = new HashMap<>();
        monthCompletion.put("January", false);
        monthCompletion.put("February", false);
        monthCompletion.put("March", false);
        monthCompletion.put("April", false);
        monthCompletion.put("May", false);
        monthCompletion.put("June", false);
        monthCompletion.put("July", false);
        monthCompletion.put("August", false);
        monthCompletion.put("September", false);
        monthCompletion.put("October", false);
        monthCompletion.put("November", false);
        monthCompletion.put("December", false);
        return monthCompletion;
    }

    // Getters and Setters

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Map<String, Boolean> getMonthCompletion() {
        return monthCompletion;
    }

    public void setMonthCompletion(Map<String, Boolean> monthCompletion) {
        this.monthCompletion = monthCompletion;
    }

    // To mark a course as completed for a specific month
    public void setCompletionForMonth(String month, Boolean isCompleted) {
        if (monthCompletion.containsKey(month)) {
            monthCompletion.put(month, isCompleted);
        }
    }

    public Boolean isCompletedForMonth(String month) {
        return monthCompletion.getOrDefault(month, false);
    }
}