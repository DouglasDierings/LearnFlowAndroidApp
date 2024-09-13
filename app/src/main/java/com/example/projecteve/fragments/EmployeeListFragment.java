package com.example.projecteve.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.projecteve.R;
import com.example.projecteve.adapters.EmployeeAdapter;
import com.example.projecteve.models.Employee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListFragment extends Fragment {

    private View view;
    private ListView employeeListView;
    private EmployeeAdapter employeeAdapter;
    private List<Employee> employeeList;
    private List<Employee> filteredEmployeeList; // List for filtering employees
    private DatabaseReference databaseReference;
    private ProgressBar progressBar;
    private Button btn_add_employee;
    private EditText searchEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_employess, container, false);

        btn_add_employee = view.findViewById(R.id.btn_add_employee);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        employeeListView = view.findViewById(R.id.employee_list);
        progressBar = view.findViewById(R.id.progress_bar);
        searchEditText = view.findViewById(R.id.search_bar); // Initialize the search EditText

        btn_add_employee.setVisibility(View.GONE);

        // Initialize Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("employees");

        // Initialize employee lists and adapter
        employeeList = new ArrayList<>();
        filteredEmployeeList = new ArrayList<>();
        employeeAdapter = new EmployeeAdapter(getContext(), filteredEmployeeList);
        employeeListView.setAdapter(employeeAdapter);

        // Show ProgressBar and hide ListView while loading data
        progressBar.setVisibility(View.VISIBLE);
        employeeListView.setVisibility(View.GONE);

        // Fetch employees from Firebase
        fetchEmployeesFromDatabase();

        // Set up the search functionality
        setupSearchFunctionality();

        // Navigate to addemployee fragment
        btn_add_employee.setOnClickListener(v -> {
            // Get the NavController from the view
            NavController navController = Navigation.findNavController(view);

            // Navigate to the EmployeeAddFragment fragment using its action ID
            navController.navigate(R.id.action_menu_employees_to_addEmployee);
        });

        // Handle the back arrow click in the toolbar
        toolbar.setNavigationOnClickListener(v -> {
            // Trigger back navigation
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();  // Navigates back when the back arrow is clicked
        });

        return view;
    }

    private void fetchEmployeesFromDatabase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear the previous employee list
                employeeList.clear();

                // Iterate through all children in the "employees" node
                for (DataSnapshot employeeSnapshot : snapshot.getChildren()) {
                    Employee employee = employeeSnapshot.getValue(Employee.class);
                    if (employee != null) {
                        employeeList.add(employee);
                    }
                }

                // Copy the full employee list to the filtered list initially
                filteredEmployeeList.clear();
                filteredEmployeeList.addAll(employeeList);

                // Notify the adapter about data changes to update the ListView
                employeeAdapter.notifyDataSetChanged();

                // Hide ProgressBar and show ListView after data is loaded
                progressBar.setVisibility(View.GONE);
                employeeListView.setVisibility(View.VISIBLE);
                btn_add_employee.setVisibility(view.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors
                Toast.makeText(getContext(), "Failed to load employees.", Toast.LENGTH_SHORT).show();

                // Hide ProgressBar even if loading failed
                progressBar.setVisibility(View.GONE);
                employeeListView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupSearchFunctionality() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No action needed before text is changed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterEmployees(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No action needed after text is changed
            }
        });
    }

    private void filterEmployees(String query) {
        filteredEmployeeList.clear();
        if (query.isEmpty()) {
            // If the search query is empty, show the full list
            filteredEmployeeList.addAll(employeeList);
        } else {
            // Filter the employees based on the query
            for (Employee employee : employeeList) {
                if (employee.getFirstName().toLowerCase().contains(query.toLowerCase()) ||
                        employee.getLastName().toLowerCase().contains(query.toLowerCase()) ||
                        employee.getEmployeeNumber().toLowerCase().contains(query.toLowerCase())) {
                    filteredEmployeeList.add(employee);
                }
            }
        }
        // Notify the adapter about the changes to update the ListView
        employeeAdapter.notifyDataSetChanged();
    }
}
