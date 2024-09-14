package com.example.projecteve.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.projecteve.MainActivity;
import com.example.projecteve.R;
import com.google.firebase.auth.FirebaseAuth;


public class AppSettingsFragment extends Fragment {

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseAuth mAuth;
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        Button btnLogOut = view.findViewById(R.id.btn_log_out);
        mAuth = FirebaseAuth.getInstance();

        btnLogOut.setOnClickListener(view -> {
            mAuth.signOut();
            openMainScreen();
        });


        return view;
    }

    private void openMainScreen() {
        // Use getActivity() as the context
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        // Finish the activity to prevent going back to the settings screen after logout
        getActivity().finish();
    }


}