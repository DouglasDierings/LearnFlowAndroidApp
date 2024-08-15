package com.example.projecteve.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projecteve.MainActivity;
import com.example.projecteve.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {

    private ImageView backArrow;
    private Button btn_send_email;

    private EditText edt_email_forgot_password;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);



        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(forgotPassword.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

        //callback
        getOnBackPressedDispatcher().addCallback(this, callback);



        backArrow = findViewById(R.id.backArrow);
        btn_send_email = findViewById(R.id.btn_send_email);
        edt_email_forgot_password = findViewById(R.id.edt_email_forgot_password);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(forgotPassword.this, login.class);
                startActivity(intent);
                finish();

            }
        });

        btn_send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt_email_forgot_password.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(forgotPassword.this, "Please enter your registered email", Toast.LENGTH_SHORT).show();
                    edt_email_forgot_password.setError("Email is required");
                    edt_email_forgot_password.requestFocus();


                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    Toast.makeText(forgotPassword.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                    edt_email_forgot_password.setError("Valid email is required");
                    edt_email_forgot_password.requestFocus();

                } else {
                    resetPassword(email);
                }


                Intent intent = new Intent(forgotPassword.this, login.class);
                startActivity(intent);
                finish();

            }

            private void resetPassword(String email) {

                mAuth = FirebaseAuth.getInstance();
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            Toast.makeText(forgotPassword.this, "Please check your inbox for password reset link", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(forgotPassword.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }


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