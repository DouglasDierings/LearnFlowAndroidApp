package com.example.projecteve.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.projecteve.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Register extends AppCompatActivity {


    private EditText edtFirstNameRegister;
    private EditText edtLastNameRegister;
    private EditText edtEmailRegister;
    private EditText edtEmployeeNumber;
    private EditText edtPasswordRegister;
    private EditText edtConfirmationPasswordRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

        //callback
        getOnBackPressedDispatcher().addCallback(this, callback);


        mAuth = FirebaseAuth.getInstance();

        Button btnLogIn = findViewById(R.id.btn_log_in);
        Button btnLoginRegister = findViewById(R.id.btn_login_register);
        ImageView backButton = findViewById(R.id.back_button);

        edtFirstNameRegister = findViewById(R.id.edt_firstName_register);
        edtLastNameRegister = findViewById(R.id.edt_lastName_register);
        edtEmailRegister = findViewById(R.id.edt_email_register);
        edtEmployeeNumber = findViewById(R.id.edt_employee_number);
        edtPasswordRegister = findViewById(R.id.edt_password_register);
        edtConfirmationPasswordRegister = findViewById(R.id.edt_confirmation_password_register);


        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
            finish();
        });

        btnLogIn.setOnClickListener(v -> {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
            finish();

        });


        btnLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserModel userModel = new UserModel();

                userModel.setFirstName(edtFirstNameRegister.getText().toString());
                userModel.setLastName(edtLastNameRegister.getText().toString());
                userModel.setEmail(edtEmailRegister.getText().toString());
                userModel.setEmployeeNumber(edtEmployeeNumber.getText().toString());
                String password = edtPasswordRegister.getText().toString();
                String passwordConfirmation = edtConfirmationPasswordRegister.getText().toString();


                if (!TextUtils.isEmpty(userModel.getFirstName()) && !TextUtils.isEmpty(userModel.getLastName()) && !TextUtils.isEmpty(userModel.getEmail()) && !TextUtils.isEmpty(userModel.getEmployeeNumber()) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(passwordConfirmation)) {
                    if (password.equals(passwordConfirmation)) {
                        mAuth.createUserWithEmailAndPassword(userModel.getEmail(), password).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                userModel.setId(mAuth.getUid());
                                userModel.save();
                                Toast.makeText(Register.this, "Register", Toast.LENGTH_SHORT).show();
                                openMainScreen();
                            } else {
                                String error = Objects.requireNonNull(task.getException()).getMessage();
                                Toast.makeText(Register.this, error, Toast.LENGTH_SHORT).show();

                            }
                        });
                    } else {
                        Toast.makeText(Register.this, "Password did not match!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Register.this, "Fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }


            private void openMainScreen() {
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

        });

    }
}