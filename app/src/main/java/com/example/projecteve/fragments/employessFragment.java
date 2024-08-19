package com.example.projecteve.fragments;

import android.os.Bundle;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.projecteve.R;

public class employessFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_employess, container, false);

        Button btn_add_employee = view.findViewById(R.id.btn_add_employee);
        Toolbar toolbar = view.findViewById(R.id.toolbar);


        // Navigate to addemployee fragment

        // Set the click listener on the button
        btn_add_employee.setOnClickListener(v -> {
            // Get the NavController from the view
            NavController navController = Navigation.findNavController(view);

            // Navigate to the addEmployee fragment using its action ID
            navController.navigate(R.id.action_menu_employees_to_addEmployee);
        });




        // Handle the back arrow click in the toolbar
        toolbar.setNavigationOnClickListener(v -> {
            // Instead of using the deprecated onBackPressed(), we'll directly trigger the back navigation
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();  // Navigates back when the back arrow is clicked
        });


        return view;
    }
}
