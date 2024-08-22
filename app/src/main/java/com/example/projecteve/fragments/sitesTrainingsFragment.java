package com.example.projecteve.fragments;

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

public class sitesTrainingsFragment extends Fragment {

    private View view;
    private TextView txtSiteName;
    private Button btn_an_post_garda_vetting;
    private Button btn_mcr_employee_form;
    private Button btn_toolbox_talks;
    private Button btn_site_folder_sign_off;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sites_tranings, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        txtSiteName = view.findViewById(R.id.txt_site_name);
        btn_an_post_garda_vetting = view.findViewById(R.id.btn_an_post_garda_vetting);
        btn_mcr_employee_form = view.findViewById(R.id.btn_mcr_employee_form);
        btn_toolbox_talks = view.findViewById(R.id.btn_toolbox_talks);
        btn_site_folder_sign_off = view.findViewById(R.id.btn_site_folder_sign_off);

        // Get the site name from the Bundle
        if (getArguments() != null) {
            String siteName = getArguments().getString("siteName");
            txtSiteName.setText(siteName);

            // Show or hide buttons based on site name
            if ("Site 1".equals(siteName)){
                btn_an_post_garda_vetting.setVisibility(View.VISIBLE);
                // Hide other buttons or set visibility based on your logic
            } else {
                btn_an_post_garda_vetting.setVisibility(View.GONE);
                // Handle visibility of other buttons if needed
            }
        }

        // Set up click listeners for buttons
        btn_an_post_garda_vetting.setOnClickListener(v -> navigateToEmployeesTrainingCheck("AN Post Garda Vetting"));
        btn_mcr_employee_form.setOnClickListener(v -> navigateToEmployeesTrainingCheck("MCR Employee Form"));
        btn_toolbox_talks.setOnClickListener(v -> navigateToEmployeesTrainingCheck("Toolbox Talks"));
        btn_site_folder_sign_off.setOnClickListener(v -> navigateToEmployeesTrainingCheck("Site Folder Sign Off"));

        // Handle the back arrow click in the toolbar
        toolbar.setNavigationOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();  // Navigates back when the back arrow is clicked
        });

        return view;
    }

    private void navigateToEmployeesTrainingCheck(String buttonType) {
        Bundle bundle = new Bundle();
        bundle.putString("siteName", txtSiteName.getText().toString());
        bundle.putString("buttonType", buttonType);  // Optional: Pass the type of button clicked

        NavController navController = Navigation.findNavController(view);
        navController.navigate(R.id.action_sitesTranings_to_employeesTraningCheck, bundle);
    }
}
