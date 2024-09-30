package com.example.projecteve.utils;

import com.example.projecteve.models.Employee;
import com.example.projecteve.models.Site;
import com.example.projecteve.models.Course;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ResetMonthlyCourses {

    private static final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public static void resetMonthlyCourses(final String userId) {
        // Fetch all employees for the specific user from Firebase
        databaseReference.child("users").child(userId).child("employees").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot employeeSnapshot : dataSnapshot.getChildren()) {
                    Employee employee = employeeSnapshot.getValue(Employee.class);
                    if (employee != null && employee.getSites() != null) {
                        // Iterate over each site the employee is associated with
                        for (Site site : employee.getSites()) {
                            if (site.getCoursesList() != null) {
                                // Iterate over the courses for the site
                                for (Course course : site.getCoursesList()) {
                                    Map<String, Boolean> monthCompletion = course.getMonthCompletion();
                                    if (monthCompletion != null) {
                                        // Reset all months to false
                                        for (String month : monthCompletion.keySet()) {
                                            monthCompletion.put(month, false);
                                        }
                                        // Update the course's monthCompletion
                                        course.setMonthCompletion(monthCompletion);
                                    }
                                }
                            }
                        }
                        // Save the updated employee data back to Firebase under the user's branch
                        employee.saveEmployee(userId);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
                System.out.println("Database error: " + databaseError.getMessage());
            }
        });
    }
}
