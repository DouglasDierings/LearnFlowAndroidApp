package com.example.projecteve.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projecteve.MainActivity;
import com.example.projecteve.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Login extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth mAuth;
        ProgressBar loginProgressBar;
        EditText edtPassword;
        EditText edtEmail;
        Button btnRegister;
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        Button btnSignInButton = findViewById(R.id.btn_sign_in_button);
        loginProgressBar = findViewById(R.id.loginProgressBar);
        btnRegister = findViewById(R.id.btn_register);
        Button btnForgotPassword = findViewById(R.id.btn_forgot_password);

        //Do the Login

        btnSignInButton.setOnClickListener(v -> {
            String loginEmail = edtEmail.getText().toString();
            String loginPassword = edtPassword.getText().toString();

            if (!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPassword)) {
                loginProgressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(loginEmail, loginPassword)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                openMainScreen();
                            } else {
                                String error = Objects.requireNonNull(task.getException()).getMessage();
                                Toast.makeText(Login.this, error, Toast.LENGTH_SHORT).show();
                                loginProgressBar.setVisibility(View.INVISIBLE);
                            }
                        });
            }

        });

        btnRegister.setOnClickListener(v -> openRegisterScreen());

        btnForgotPassword.setOnClickListener(v -> openRecoveryScreen());

    }

    private void openMainScreen() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void openRecoveryScreen() {
        Intent intent = new Intent(Login.this, ForgotPassword.class);
        startActivity(intent);
        finish();
    }

    private void openRegisterScreen() {
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
        finish();
    }

}