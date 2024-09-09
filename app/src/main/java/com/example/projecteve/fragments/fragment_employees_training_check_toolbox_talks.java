package com.example.projecteve.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.projecteve.R;
import com.example.projecteve.adapters.EmployeeAdapterMonthlyCourseCheck;
import com.example.projecteve.adapters.EmployeeAdapterTrainingCheck;
import com.example.projecteve.models.Course;
import com.example.projecteve.models.Employee;
import com.example.projecteve.models.Site;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class fragment_employees_training_check_toolbox_talks extends Fragment {
    View view;
    private Toolbar toolbar;
    private int siteIndex;
    private int courseIndex;
    private String siteName;
    private String courseName;
    private Button btn_save_training;
    private Spinner spinner_months;
    private TextView tvCourseName;
    private ListView employeeListView;
    private List<Employee> employeeList;
    private EmployeeAdapterMonthlyCourseCheck employeeAdapter;
    private String selectedMonth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_employees_training_check_toolbox_talks, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        employeeListView = view.findViewById(R.id.employee_list_view);
        // Initialize the employee list
        employeeList = new ArrayList<>();
        btn_save_training = view.findViewById(R.id.btn_save_training);
        spinner_months = view.findViewById(R.id.spinner_months);
        tvCourseName = view.findViewById(R.id.tv_course_name);

        toolbar.setNavigationOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
        });

        // Retrieve arguments passed from the previous fragment
        if (getArguments() != null) {
            siteName = getArguments().getString("siteName");
            courseName = getArguments().getString("courseName");
            siteIndex = getArguments().getInt("siteIndex", -1);
            courseIndex = getArguments().getInt("courseIndex", -1);

            if (siteName == null || courseName == null || siteIndex == -1 || courseIndex == -1) {
                Toast.makeText(getContext(), "Invalid data passed", Toast.LENGTH_SHORT).show();
                NavController navController = Navigation.findNavController(view);
                navController.popBackStack();
            }

            tvCourseName.setText(courseName);
            fetchEmployeesForSite(siteName);

            toolbar.setNavigationOnClickListener(v -> {
                NavController navController = Navigation.findNavController(view);
                navController.popBackStack();
            });


        } else {
            Toast.makeText(getContext(), "No data passed", Toast.LENGTH_SHORT).show();
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
        }


        btn_save_training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < employeeList.size(); i++) {
                    Employee employee = employeeList.get(i);

                    if (employee.getSites() != null && siteIndex != -1) {
                        Site site = employee.getSites().get(siteIndex);

                        if (site != null && site.getCoursesList() != null && courseIndex != -1) {
                            Course course = site.getCoursesList().get(courseIndex);

                            if (course != null && course.getCourseName().equals(courseName)) {
                                if (course.getMonthCompletion() != null && selectedMonth != null) {
                                    // Use the course's isCompleted field to determine if it's checked
                                    boolean isChecked = course.getIsCompleted() != null && course.getIsCompleted();

                                    // Update the selected month to true or false based on checkbox state
                                    course.getMonthCompletion().put(selectedMonth, isChecked);

                                    // Now update the Firebase database with the modified course
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                                            .getReference("employees")
                                            .child(employee.getEmployeeNumber())
                                            .child("sites")
                                            .child(String.valueOf(siteIndex))
                                            .child("coursesList")
                                            .child(String.valueOf(courseIndex))
                                            .child("monthCompletion")
                                            .child(selectedMonth);

                                    // Update the monthCompletion value for the selected month
                                    databaseReference.setValue(isChecked)
                                            .addOnCompleteListener(task -> {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getContext(), "Training updated successfully for " + selectedMonth, Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(getContext(), "Failed to update training", Toast.LENGTH_SHORT).show();
                                                }
                                                Log.d("Saving","Hello from here");
                                            });
                                }
                            }
                        }
                    }
                }
            }
        });



        // Set up the Spinner with an adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.months_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_months.setAdapter(adapter);

        // Set up the Spinner listener
        spinner_months.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected item as a String
                selectedMonth = parentView.getItemAtPosition(position).toString();
                // Optional: Do something with the selected month, e.g., log or update UI
                Toast.makeText(getContext(), "Selected Month: " + selectedMonth, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle case where no selection is made, if necessary
            }
        });

        return view;

    }
    private void fetchEmployeesForSite(String siteName) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("employees");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (employeeList != null) {
                    employeeList.clear();
                }
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Employee employee = snapshot.getValue(Employee.class);

                    if (employee != null && employee.getSites() != null) {
                        for (int i = 0; i < employee.getSites().size(); i++) {
                            Site site = employee.getSites().get(i);
                            if (site.getName().equals(siteName)) {
                                siteIndex = i; // Update siteIndex to the found site
                                employeeList.add(employee);
                                break;
                            }
                        }
                    }
                }

                // Pass selectedMonth to the adapter
                employeeAdapter = new EmployeeAdapterMonthlyCourseCheck(getContext(), employeeList, siteIndex, courseIndex, selectedMonth);
                employeeListView.setAdapter(employeeAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to fetch employees", Toast.LENGTH_SHORT).show();
            }
        });
    }


}