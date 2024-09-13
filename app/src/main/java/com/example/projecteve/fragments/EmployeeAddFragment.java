package com.example.projecteve.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.projecteve.R;
import com.example.projecteve.models.Course;
import com.example.projecteve.models.Employee;
import com.example.projecteve.models.Site;
import com.example.projecteve.utils.CheckAndSaveEmployee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAddFragment extends Fragment {

    View view;
    EditText edt_name;
    EditText edt_surname;
    EditText edt_employee_number;
    CheckBox cb_site1;
    CheckBox cb_site2;
    CheckBox cb_site3;
    Button btn_register;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_employee, container, false);

        edt_name = view.findViewById(R.id.edt_name);
        edt_surname = view.findViewById(R.id.edt_surname);
        edt_employee_number = view.findViewById(R.id.edt_employee_number);
        cb_site1 = view.findViewById(R.id.cb_anpost);
        cb_site2 = view.findViewById(R.id.cb_cbre);
        cb_site3 = view.findViewById(R.id.cb_corgan);
        btn_register = view.findViewById(R.id.btn_register);

        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // Handle the back arrow click in the toolbar
        toolbar.setNavigationOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = edt_name.getText().toString();
                String lastName = edt_surname.getText().toString();
                String employeeNumber = edt_employee_number.getText().toString();

                if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(employeeNumber)) {
                    Toast.makeText(EmployeeAddFragment.this.getActivity(), "Please complete all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }


                List<Course> coursesList = new ArrayList<>();
                coursesList.add(new Course("Site folder sign off", false));
                coursesList.add(new Course("Employee form", false));
                coursesList.add(new Course("An post garda vetting", false));
                // For Toolbox talks, we use the static initializeMonthCompletion method to set month to false
                coursesList.add(new Course("Toolbox Talks", Course.initializeMonthCompletion()));


                List<Site> sites = new ArrayList<>();
                if (cb_site1.isChecked()) {
                    sites.add(new Site("An Post", "Docklands", new ArrayList<>(coursesList)));
                }
                if (cb_site2.isChecked()) {
                    sites.add(new Site("CBRE", "Docklands", new ArrayList<>(coursesList)));
                }
                if (cb_site3.isChecked()) {
                    sites.add(new Site("Corgan", "Docklands", new ArrayList<>(coursesList)));
                }

                Employee employee = new Employee(firstName, lastName, employeeNumber, sites);

                CheckAndSaveEmployee.checkAndSaveEmployee(EmployeeAddFragment.this, employee, new CheckAndSaveEmployee.EmployeeCallback() {
                    @Override
                    public void onSuccess(String message) {
                        Toast.makeText(EmployeeAddFragment.this.getActivity(), message, Toast.LENGTH_SHORT).show();
                        NavController navController = Navigation.findNavController(view);
                        navController.popBackStack();
                    }

                    @Override
                    public void onFailure(String error) {
                        Toast.makeText(EmployeeAddFragment.this.getActivity(), error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }
}