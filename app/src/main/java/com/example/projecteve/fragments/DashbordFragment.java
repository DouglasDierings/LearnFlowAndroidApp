package com.example.projecteve.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.projecteve.R;
import com.example.projecteve.activity.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class DashbordFragment extends Fragment {

    View view;
    FirebaseUser currentUser;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    private FirebaseAuth mAuth;
    private TextView txt_name;
    private String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dashbord, container, false);

        txt_name = view.findViewById(R.id.txt_name);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            uid = currentUser.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String firstName = snapshot.child("firstName").getValue(String.class);
                    String lastName = snapshot.child("lastName").getValue(String.class);
                    String fullName = firstName + " " + lastName;

                    txt_name.setText(fullName);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(DashbordFragment.this.getActivity(), "User not found", Toast.LENGTH_SHORT).show();
                }
            };
            databaseReference.addValueEventListener(valueEventListener);
        } else {
            startActivity(new Intent(getActivity(), Login.class));
            getActivity().finish(); // Optional: finish the current activity
        }

        return view;
    }
}
