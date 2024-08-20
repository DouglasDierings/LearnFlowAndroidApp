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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sites_tranings, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        txtSiteName = view.findViewById(R.id.txt_site_name);
        btn_an_post_garda_vetting = view.findViewById(R.id.btn_an_post_garda_vetting);


        // Obter o nome do site do Bundle
        if (getArguments() != null) {
            String siteName = getArguments().getString("siteName");
            if ((siteName.equals("Site 1"))){
                btn_an_post_garda_vetting.setVisibility(View.VISIBLE);
            }
            txtSiteName.setText(siteName);
        }




        // Handle the back arrow click in the toolbar
        toolbar.setNavigationOnClickListener(v -> {
            // Instead of using the deprecated onBackPressed(), we'll directly trigger the back navigation
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();  // Navigates back when the back arrow is clicked
        });

        return view;
    }
}