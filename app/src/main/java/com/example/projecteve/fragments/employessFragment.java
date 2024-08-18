package com.example.projecteve.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.projecteve.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link employessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class employessFragment extends Fragment {

    private View view;
    private Button btn_add_employee;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_employess, container, false);

        btn_add_employee = view.findViewById(R.id.btn_add_employee);

        // Set the click listener on the button
        btn_add_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the NavController from the view
                NavController navController = Navigation.findNavController(view);

                // Navigate to the addEmployee fragment using its action ID
                navController.navigate(R.id.action_menu_employees_to_addEmployee);
            }
        });


        return view;
    }
}
