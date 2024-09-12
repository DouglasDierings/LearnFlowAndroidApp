package com.example.projecteve.fragments;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projecteve.R;
import com.example.projecteve.adapters.EmployeeAdapterTrainingCheck;
import com.example.projecteve.models.Employee;
import com.example.projecteve.models.Site;
import com.example.projecteve.models.Course;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployeesTrainingCheck extends Fragment {

    private View view;
    private ListView employeeListView;
    private List<Employee> employeeList;
    private EmployeeAdapterTrainingCheck employeeAdapter;
    private int siteIndex;
    private int courseIndex;
    private String siteName;
    private String courseName;
    private TextView tvCourseName;
    private Toolbar toolbar;
    private Button saveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_employees_training_check, container, false);

        // Initialize UI components
        toolbar = view.findViewById(R.id.toolbar);
        tvCourseName = view.findViewById(R.id.tv_course_name);
        employeeListView = view.findViewById(R.id.employee_list_view);
        saveButton = view.findViewById(R.id.btn_save_training);

        // Initialize the employee list
        employeeList = new ArrayList<>();

        // Handle fragment arguments and initialization in onViewCreated
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                return;
            }

            tvCourseName.setText(courseName);
            fetchEmployeesForSite(siteName);

            toolbar.setNavigationOnClickListener(v -> {
                NavController navController = Navigation.findNavController(view);
                navController.popBackStack();
            });

            saveButton.setOnClickListener(v -> saveTrainingStatus());
        } else {
            Toast.makeText(getContext(), "No data passed", Toast.LENGTH_SHORT).show();
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
        }
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
                                siteIndex = i; // Atualiza o siteIndex para o site encontrado
                                employeeList.add(employee);
                                break;
                            }
                        }
                    }
                }

                employeeAdapter = new EmployeeAdapterTrainingCheck(getContext(), employeeList, siteIndex, courseIndex);
                employeeListView.setAdapter(employeeAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to fetch employees", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveTrainingStatus() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("employees");

        for (Employee employee : employeeList) {
            if (employee.getSites() != null && siteIndex < employee.getSites().size()) {
                Site site = employee.getSites().get(siteIndex);

                if (site.getCoursesList() != null && courseIndex < site.getCoursesList().size()) {
                    Course course = site.getCoursesList().get(courseIndex);
                    Boolean isCompleted = course.getIsCompleted();


                    databaseReference.child(employee.getEmployeeNumber())
                            .child("sites")
                            .child(String.valueOf(siteIndex))
                            .child("coursesList")
                            .child(String.valueOf(courseIndex))
                            .child("isCompleted")
                            .setValue(isCompleted);
                }
            }
        }

        Toast.makeText(getContext(), "Training status saved successfully", Toast.LENGTH_SHORT).show();
    }
}