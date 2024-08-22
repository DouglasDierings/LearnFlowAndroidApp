package com.example.projecteve.fragments;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.projecteve.R;
import com.example.projecteve.adapters.EmployeeAdapterTrainingCheck;
import com.example.projecteve.models.Employee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class employeesTrainingCheck extends Fragment {

    private View view;
    private ListView employeeListView;
    private List<Employee> employeeList;
    private EmployeeAdapterTrainingCheck employeeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_employees_training_check, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // Initialize the employee list and adapter
        employeeListView = view.findViewById(R.id.employee_list_view);
        employeeList = new ArrayList<>();
        employeeAdapter = new EmployeeAdapterTrainingCheck(getContext(), employeeList);
        employeeListView.setAdapter(employeeAdapter);

        // Fetch the site name from arguments and use it to fetch employees
        if (getArguments() != null) {
            String siteName = getArguments().getString("siteName");
            fetchEmployeesForSite(siteName);
        }

        // Handle the back arrow click in the toolbar
        toolbar.setNavigationOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();  // Navigates back when the back arrow is clicked
        });

        return view;
    }

    private void fetchEmployeesForSite(String siteName) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("employees");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                employeeList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Employee employee = snapshot.getValue(Employee.class);

                    if (employee != null) {
                        // Filter employees based on the selected site
                        if ("Site 1".equals(siteName) && employee.getSite1()) {
                            employeeList.add(employee);
                        } else if ("Site 2".equals(siteName) && employee.getSite2()) {
                            employeeList.add(employee);
                        } else if ("Site 3".equals(siteName) && employee.getSite3()) {
                            employeeList.add(employee);
                        }
                    }
                }
                employeeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }
}
