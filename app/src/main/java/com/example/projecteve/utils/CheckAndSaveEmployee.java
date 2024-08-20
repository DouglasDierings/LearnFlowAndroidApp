package com.example.projecteve.utils;

import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.projecteve.models.Employee;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CheckAndSaveEmployee {

    public interface EmployeeCallback {
        void onSuccess(String message);
        void onFailure(String error);
    }

    public static void checkAndSaveEmployee(Fragment fragment, final Employee employee, final EmployeeCallback callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("employees").child(employee.getEmployeeNumber()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    // Employee with the same number already exists
                    callback.onFailure("Employee number already exists.");
                } else {
                    // Save the new employee
                    employee.saveEmployee();
                    callback.onSuccess(employee.getFirstName() + " was added.");
                }
            } else {
                // Handle errors
                callback.onFailure("Error checking employee number.");
            }
        });
    }
}
