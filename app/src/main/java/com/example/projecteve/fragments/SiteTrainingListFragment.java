package com.example.projecteve.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.projecteve.R;
import com.example.projecteve.utils.ResetMonthlyCourses;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SiteTrainingListFragment extends Fragment {

    private View view;
    private TextView txtSiteName;
    private Button btnAnPostGardaVetting;
    private Button btnMcrEmployeeForm;
    private Button btnToolboxTalks;
    private Button btnSiteFolderSignOff;
    private Button btnResetMonthlyCourses;
    private int siteIndex;
    private FirebaseAuth firebaseAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sites_tranings, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        txtSiteName = view.findViewById(R.id.txt_site_name);
        btnAnPostGardaVetting = view.findViewById(R.id.btn_an_post_garda_vetting);
        btnMcrEmployeeForm = view.findViewById(R.id.btn_mcr_employee_form);
        btnToolboxTalks = view.findViewById(R.id.btn_toolbox_talks);
        btnSiteFolderSignOff = view.findViewById(R.id.btn_site_folder_sign_off);
        btnResetMonthlyCourses = view.findViewById(R.id.btn_reset_monthly_courses);

        // Initialize Firebase Auth and get the current user
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            String currentUserId = currentUser.getUid();
        } else {
            // Handle the case where the user is not authenticated
            return view;
        }

        // Get the Site name and index from the Bundle
        if (getArguments() != null) {
            String siteName = getArguments().getString("siteName");
            txtSiteName.setText(siteName);

            // Use switch to determine siteIndex
            switch (siteName) {
                case "An Post":
                    siteIndex = 0;
                    break;
                case "CBRE":
                    siteIndex = 1;
                    break;
                case "Corgan":
                    siteIndex = 2;
                    break;
                default:
                    siteIndex = -1; // Default or error case
                    break;
            }
        }

        if(txtSiteName.getText().toString().equals("An Post")){
            btnAnPostGardaVetting.setVisibility(View.VISIBLE);
        }


        // Set up click listeners for buttons
        btnSiteFolderSignOff.setOnClickListener(v -> navigateToEmployeesTrainingCheck("Site Folder Sign-Off"));
        btnMcrEmployeeForm.setOnClickListener(v -> navigateToEmployeesTrainingCheck("Employee Form"));
        btnAnPostGardaVetting.setOnClickListener(v -> navigateToEmployeesTrainingCheck("An Post Garda Vetting"));
        btnToolboxTalks.setOnClickListener(v -> navigateToEmployeesTrainingCheckToolboxTalks("Toolbox Talks"));

        // Handle the back arrow click in the toolbar
        toolbar.setNavigationOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();  // Navigates back when the back arrow is clicked
        });

        btnResetMonthlyCourses.setOnClickListener(view -> {
            // Create an AlertDialog to confirm the action
            new AlertDialog.Builder(requireContext())
                    .setTitle("Reset Monthly Courses")
                    .setMessage("Are you sure you want to reset all monthly courses ?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // If user confirms, call the reset method
                        ResetMonthlyCourses.resetMonthlyCourses();
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        // If user cancels, just dismiss the dialog
                        dialog.dismiss();
                    })
                    .show();
        });




        return view;
    }

    private void navigateToEmployeesTrainingCheck(String courseName) {
        // Determine the courseIndex based on the courseName
        int courseIndex = getCourseIndex(courseName);

        Bundle bundle = new Bundle();
        bundle.putString("siteName", txtSiteName.getText().toString());
        bundle.putString("courseName", courseName);
        bundle.putInt("siteIndex", siteIndex);
        bundle.putInt("courseIndex", courseIndex);


        NavController navController = Navigation.findNavController(view);
        navController.navigate(R.id.action_sitesTranings_to_employeesTraningCheck, bundle);
    }

    private void navigateToEmployeesTrainingCheckToolboxTalks(String courseName) {
        // Determine the courseIndex based on the courseName
        int courseIndex = getCourseIndex(courseName);

        Bundle bundle = new Bundle();
        bundle.putString("siteName", txtSiteName.getText().toString());
        bundle.putString("courseName", courseName);
        bundle.putInt("siteIndex", siteIndex);
        bundle.putInt("courseIndex", courseIndex);



        NavController navController = Navigation.findNavController(view);
        navController.navigate(R.id.action_sitesTranings_to_fragment_employees_training_check_toolbox_talks, bundle);
    }

    private int getCourseIndex(String courseName) {
        switch (courseName) {
            case "Site Folder Sign-Off":
                return 0;
            case "Employee Form":
                return 1;
            case "An Post Garda Vetting":
                return 2;
            case "Toolbox Talks":
                return 3;
            default:
                return -1; // Default or error case
        }
    }



}
