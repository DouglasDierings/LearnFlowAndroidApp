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

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class settingsFragment extends Fragment {

    View view;
    private Button btn_log_out;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        btn_log_out = view.findViewById(R.id.btn_log_out);
        mAuth = FirebaseAuth.getInstance();

        btn_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                openMainScreen();
            }
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