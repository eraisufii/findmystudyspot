package com.example.findmystudyspot;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.findmystudyspot.database.DatabaseHelper;

public class SignUpActivity extends AppCompatActivity {

    EditText etUsername, etEmail, etPassword;
    Button btnSignup;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etSignupEmail);
        etPassword = findViewById(R.id.etSignupPassword);
        btnSignup = findViewById(R.id.btnSignup);

        databaseHelper = new DatabaseHelper(this);

        btnSignup.setOnClickListener(v -> {

            String username = etUsername.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if(username.isEmpty() || email.isEmpty() || password.isEmpty()) {

                Toast.makeText(SignUpActivity.this,
                        "Please fill all fields",
                        Toast.LENGTH_SHORT).show();

            } else {

                boolean inserted = databaseHelper.insertUser(
                        username,
                        email,
                        password
                );

                if(inserted) {

                    Toast.makeText(SignUpActivity.this,
                            "Signup Successful",
                            Toast.LENGTH_SHORT).show();

                    finish();

                } else {

                    Toast.makeText(SignUpActivity.this,
                            "Signup Failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
