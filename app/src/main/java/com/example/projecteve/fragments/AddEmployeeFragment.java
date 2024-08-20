package com.example.projecteve.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projecteve.R;
import com.example.projecteve.models.Employee;


public class AddEmployeeFragment extends Fragment {

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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_employee, container, false);

        edt_name = view.findViewById(R.id.edt_name);
        edt_surname = view.findViewById(R.id.edt_surname);
        edt_employee_number = view.findViewById(R.id.edt_employee_number);
        cb_site1 = view.findViewById(R.id.cb_site1);
        cb_site2 = view.findViewById(R.id.cb_site2);
        cb_site3 = view.findViewById(R.id.cb_site3);
        btn_register = view.findViewById(R.id.btn_register);



        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // Handle the back arrow click in the toolbar
        toolbar.setNavigationOnClickListener(v -> {
            // Instead of using the deprecated onBackPressed(), we'll directly trigger the back navigation
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();  // Navigates back when the back arrow is clicked
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Employee employee = new Employee();

                employee.setFirstName(edt_name.getText().toString());
                employee.setLastName(edt_surname.getText().toString());
                employee.setEmployeeNumber(edt_employee_number.getText().toString());
                employee.setSite1(cb_site1.isChecked());
                employee.setSite2(cb_site2.isChecked());
                employee.setSite3(cb_site3.isChecked());

                if (!TextUtils.isEmpty(employee.getFirstName()) && !TextUtils.isEmpty(employee.getLastName()) && !TextUtils.isEmpty(employee.getEmployeeNumber())) {
                    employee.saveEmployee();

                    Toast.makeText(AddEmployeeFragment.this.getActivity(), employee.getFirstName() + " Was added", Toast.LENGTH_SHORT).show();
                    NavController navController = Navigation.findNavController(view);
                    navController.popBackStack();

                }else {

                    Toast.makeText(AddEmployeeFragment.this.getActivity(), "Insert complete all fields", Toast.LENGTH_SHORT).show();

                }
            }
        });




        return view;
    }
}