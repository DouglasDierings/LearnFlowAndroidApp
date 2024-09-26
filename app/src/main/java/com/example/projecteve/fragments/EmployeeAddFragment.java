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
    EditText edtName;
    EditText edtSurname;
    EditText edtEmployeeNumber;
    CheckBox cbSite1;
    CheckBox cbSite2;
    CheckBox cbSite3;
    Button btnRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_employee, container, false);

        edtName = view.findViewById(R.id.edt_name);
        edtSurname = view.findViewById(R.id.edt_surname);
        edtEmployeeNumber = view.findViewById(R.id.edt_employee_number);
        cbSite1 = view.findViewById(R.id.cb_anpost);
        cbSite2 = view.findViewById(R.id.cb_cbre);
        cbSite3 = view.findViewById(R.id.cb_corgan);
        btnRegister = view.findViewById(R.id.btn_register);

        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // Handle the back arrow click in the toolbar
        toolbar.setNavigationOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
        });

        btnRegister.setOnClickListener(view -> {
            String firstName = edtName.getText().toString();
            String lastName = edtSurname.getText().toString();
            String employeeNumber = edtEmployeeNumber.getText().toString();

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
            if (cbSite1.isChecked()) {
                sites.add(new Site("An Post", "Docklands", new ArrayList<>(coursesList)));
            }
            if (cbSite2.isChecked()) {
                sites.add(new Site("CBRE", "Docklands", new ArrayList<>(coursesList)));
            }
            if (cbSite3.isChecked()) {
                sites.add(new Site("Corgan", "Docklands", new ArrayList<>(coursesList)));
            }

            Employee employee = new Employee(firstName, lastName, employeeNumber, sites);

            CheckAndSaveEmployee.checkAndSaveEmployee(employee, new CheckAndSaveEmployee.EmployeeCallback() {
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
        });

        return view;
    }
}
