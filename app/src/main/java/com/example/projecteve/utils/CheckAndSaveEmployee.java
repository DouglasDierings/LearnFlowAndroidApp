package com.example.projecteve.utils;

import com.example.projecteve.models.Employee;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CheckAndSaveEmployee {

    public interface EmployeeCallback {
        void onSuccess(String message);
        void onFailure(String error);
    }

    // Add userId as a parameter to ensure data is saved in the correct user branch
    public static void checkAndSaveEmployee(final String userId, final Employee employee, final EmployeeCallback callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        // Check if the employee already exists under the given userId
        reference.child("users").child(userId).child("employees").child(employee.getEmployeeNumber()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    // Employee with the same number already exists for this user
                    callback.onFailure("Employee number already exists for this user.");
                } else {
                    // Save the new employee under the specific userId
                    employee.saveEmployee(userId);
                    callback.onSuccess(employee.getFirstName() + " was added.");
                }
            } else {
                // Handle errors
                callback.onFailure("Error checking employee number.");
            }
        });
    }
}
