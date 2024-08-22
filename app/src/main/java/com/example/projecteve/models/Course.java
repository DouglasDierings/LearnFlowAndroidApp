package com.example.projecteve.models;

public class Course {
    private String courseName;
    private Boolean isCompleted;

    public Course() {}

    public Course(String courseName, Boolean isCompleted) {
        this.courseName = courseName;
        this.isCompleted = isCompleted;
    }



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
}
