package com.example.projecteve.fragments;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.projecteve.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class sitesTrainingsFragment extends Fragment {

    private View view;
    private TextView txtSiteName;
    private Button btnAnPostGardaVetting;
    private Button btnMcrEmployeeForm;
    private Button btnToolboxTalks;
    private Button btnSiteFolderSignOff;
    private int siteIndex;
    private FirebaseAuth firebaseAuth;
    private String currentUserId;


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

        // Initialize Firebase Auth and get the current user
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            currentUserId = currentUser.getUid();
        } else {
            // Handle the case where the user is not authenticated (e.g., navigate to login screen)
            // You can implement the necessary logic here
            return view;
        }

        // Get the Site name and index from the Bundle
        if (getArguments() != null) {
            String siteName = getArguments().getString("siteName");
            txtSiteName.setText(siteName);

            // Use switch to determine siteIndex
            switch (siteName) {
                case "Site 1":
                    siteIndex = 0;
                    break;
                case "Site 2":
                    siteIndex = 1;
                    break;
                case "Site 3":
                    siteIndex = 2;
                    break;
                case "Site 4":
                    siteIndex = 3;
                    break;
                default:
                    siteIndex = -1; // Default or error case
                    break;
            }
        }

        if(txtSiteName.getText().toString().equals("Site 1")){
            btnAnPostGardaVetting.setVisibility(View.VISIBLE);
        }

        // Set up click listeners for buttons
        btnAnPostGardaVetting.setOnClickListener(v -> navigateToEmployeesTrainingCheck("An Post Garda Vetting"));
        btnMcrEmployeeForm.setOnClickListener(v -> navigateToEmployeesTrainingCheck("Employee Form"));
        btnToolboxTalks.setOnClickListener(v -> navigateToEmployeesTrainingCheck("Toolbox Talks"));
        btnSiteFolderSignOff.setOnClickListener(v -> navigateToEmployeesTrainingCheck("Site Folder Sign-Off"));

        // Handle the back arrow click in the toolbar
        toolbar.setNavigationOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();  // Navigates back when the back arrow is clicked
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

        // Log the values to verify
        Log.d("sitesTrainingsFragment", "siteName: " + txtSiteName.getText().toString());
        Log.d("sitesTrainingsFragment", "courseName: " + courseName);
        Log.d("sitesTrainingsFragment", "siteIndex: " + siteIndex);
        Log.d("sitesTrainingsFragment", "courseIndex: " + courseIndex);

        NavController navController = Navigation.findNavController(view);
        navController.navigate(R.id.action_sitesTranings_to_employeesTraningCheck, bundle);
    }

    private int getCourseIndex(String courseName) {
        switch (courseName) {
            case "Site Folder Sign-Off":
                return 0;
            case "Toolbox Talks":
                return 1;
            case "Employee Form":
                return 2;
            case "An Post Garda Vetting":
                return 3;
            default:
                return -1; // Default or error case
        }
    }
}
