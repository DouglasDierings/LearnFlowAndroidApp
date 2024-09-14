package com.example.projecteve.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projecteve.MainActivity;
import com.example.projecteve.R;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText edtEmailForgotPassword;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);


        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(ForgotPassword.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

        //callback
        getOnBackPressedDispatcher().addCallback(this, callback);


        ImageView backArrow = findViewById(R.id.backArrow);
        Button btnSendEmail = findViewById(R.id.btn_send_email);
        edtEmailForgotPassword = findViewById(R.id.edt_email_forgot_password);

        backArrow.setOnClickListener(v -> {
            Intent intent = new Intent(ForgotPassword.this, Login.class);
            startActivity(intent);
            finish();

        });

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmailForgotPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(ForgotPassword.this, "Please enter your registered email", Toast.LENGTH_SHORT).show();
                    edtEmailForgotPassword.setError("Email is required");
                    edtEmailForgotPassword.requestFocus();


                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    Toast.makeText(ForgotPassword.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                    edtEmailForgotPassword.setError("Valid email is required");
                    edtEmailForgotPassword.requestFocus();

                } else {
                    resetPassword(email);
                }


                Intent intent = new Intent(ForgotPassword.this, Login.class);
                startActivity(intent);
                finish();

            }

            private void resetPassword(String email) {

                mAuth = FirebaseAuth.getInstance();
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        Toast.makeText(ForgotPassword.this, "Please check your inbox for password reset link", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(ForgotPassword.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }


                });

            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}