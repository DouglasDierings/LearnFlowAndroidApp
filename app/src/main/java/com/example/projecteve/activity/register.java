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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projecteve.MainActivity;
import com.example.projecteve.R;
import com.example.projecteve.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {


    private EditText edt_firstName_register;
    private EditText edt_lastName_register;
    private EditText edt_email_register;
    private EditText edt_employee_number;
    private EditText edt_password_register;
    private EditText edt_confirmation_password_register;
    private Button btn_log_in;
    private Button btn_login_register;
    private ImageView back_button;

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




        mAuth = FirebaseAuth.getInstance();

        btn_log_in = findViewById(R.id.btn_log_in);
        btn_login_register = findViewById(R.id.btn_login_register);
        back_button = findViewById(R.id.back_button);

        edt_firstName_register = findViewById(R.id.edt_firstName_register);
        edt_lastName_register = findViewById(R.id.edt_lastName_register);
        edt_email_register = findViewById(R.id.edt_email_register);
        edt_employee_number = findViewById(R.id.edt_employee_number);
        edt_password_register = findViewById(R.id.edt_password_register);
        edt_confirmation_password_register = findViewById(R.id.edt_confirmation_password_register);


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
                finish();
            }
        });

        btn_log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
                finish();
            }
        });




        btn_login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserModel userModel = new UserModel();

                userModel.setFirstName(edt_firstName_register.getText().toString());
                userModel.setLastName(edt_lastName_register.getText().toString());
                userModel.setEmail(edt_email_register.getText().toString());
                userModel.setEmployeeNumber(edt_employee_number.getText().toString());
                String password = edt_password_register.getText().toString();
                String passwordConfirmation = edt_confirmation_password_register.getText().toString();


                if (!TextUtils.isEmpty(userModel.getFirstName()) && !TextUtils.isEmpty(userModel.getLastName()) && !TextUtils.isEmpty(userModel.getEmail()) && !TextUtils.isEmpty(userModel.getEmployeeNumber()) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(passwordConfirmation)) {
                    if (password.equals(passwordConfirmation)) {
                        mAuth.createUserWithEmailAndPassword(userModel.getEmail(), password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    userModel.setId(mAuth.getUid());
                                    userModel.save();
                                    Toast.makeText(register.this, "register", Toast.LENGTH_SHORT).show();
                                    openMainScreen();
                                } else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(register.this, "" + error, Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    } else {
                        Toast.makeText(register.this, "Password did not match!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(register.this, "Fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }


            private void openMainScreen() {
                Intent intent = new Intent(register.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

        });

    }
}