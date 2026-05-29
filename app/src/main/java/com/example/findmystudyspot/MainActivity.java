package com.example.findmystudyspot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.findmystudyspot.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    TextView tvSignup;

    EditText etEmail, etPassword;
    Button btnLogin;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSignup = findViewById(R.id.tvSignup);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        databaseHelper = new DatabaseHelper(this);

        // OPEN SIGNUP PAGE
        tvSignup.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        // LOGIN BUTTON
        btnLogin.setOnClickListener(v -> {

            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if(email.isEmpty() || password.isEmpty()) {

                Toast.makeText(MainActivity.this,
                        "Please fill all fields",
                        Toast.LENGTH_SHORT).show();

            } else {

                boolean exists = databaseHelper.checkUser(email, password);

                if (exists) {
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, ExploreActivity.class);
                    // Pass the logged-in email forward
                    intent.putExtra("USER_EMAIL", email);
                    startActivity(intent);
                } else {

                    Toast.makeText(MainActivity.this,
                            "Invalid Email or Password",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
