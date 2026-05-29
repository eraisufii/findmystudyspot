package com.example.findmystudyspot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvProfileEmail;
    private MaterialButton logoutButton;
    private BottomNavigationView bottomNavigation;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // 1. Initialize UI elements
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        logoutButton = findViewById(R.id.logoutButton);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        // 2. Extract the logged-in user email from Intent
        userEmail = getIntent().getStringExtra("USER_EMAIL");

        // 3. Display the email in the profile layout
        if (userEmail != null && !userEmail.isEmpty()) {
            tvProfileEmail.setText(userEmail);
        } else {
            tvProfileEmail.setText("Guest User");
        }

        // 4. Handle Logout Button Click
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            // Clear activity stack so clicking back button won't re-login
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // 5. Handle Bottom Navigation Item Selection
        bottomNavigation.setSelectedItemId(R.id.nav_profile);
        bottomNavigation.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_explore) {
                Intent intent = new Intent(ProfileActivity.this, ExploreActivity.class);
                // Keep passing the user email down the intent chain
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
                return true;
            }

            if (item.getItemId() == R.id.nav_saved) {
                Intent intent = new Intent(ProfileActivity.this, SavedActivity.class);
                // Keep passing the user email down the intent chain
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
                return true;
            }

            return true;
        });
    }
}