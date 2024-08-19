package com.example.projecteve.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projecteve.R;


public class employeesTrainingCheck extends Fragment {

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_employees_traning_check, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);







        // Handle the back arrow click in the toolbar
        toolbar.setNavigationOnClickListener(v -> {
            // Instead of using the deprecated onBackPressed(), we'll directly trigger the back navigation
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();  // Navigates back when the back arrow is clicked
        });


        return view;
    }
}