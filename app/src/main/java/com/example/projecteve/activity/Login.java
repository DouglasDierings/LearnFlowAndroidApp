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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projecteve.MainActivity;
import com.example.projecteve.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Login extends AppCompatActivity {


    //Buttons
    private EditText edt_email;
    private EditText edt_password;
    private Button btn_sign_in_button;
    private Button btn_register;
    private FirebaseAuth mAuth;
    private ProgressBar loginProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        btn_sign_in_button = findViewById(R.id.btn_sign_in_button);
        loginProgressBar = findViewById(R.id.loginProgressBar);
        btn_register = findViewById(R.id.btn_register);

        //Do the login

        btn_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginEmail = edt_email.getText().toString();
                String loginPassword = edt_password.getText().toString();

                if (!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPassword)) {
                    loginProgressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(loginEmail, loginPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        openMainScreen();
                                    } else {
                                        String error = Objects.requireNonNull(task.getException()).getMessage();
                                        Toast.makeText(Login.this, "" + error, Toast.LENGTH_SHORT).show();
                                        loginProgressBar.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                }

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void openMainScreen() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}