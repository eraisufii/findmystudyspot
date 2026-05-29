package com.example.findmystudyspot;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        MaterialButton logoutButton = findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    ProfileActivity.this,
                    MainActivity.class);

            startActivity(intent);
            finish();
        });

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.setSelectedItemId(R.id.nav_profile);

        bottomNavigation.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_explore) {

                startActivity(new Intent(
                        ProfileActivity.this,
                        ExploreActivity.class));

                return true;
            }
            if (item.getItemId() == R.id.nav_saved) {

                startActivity(new Intent(
                        ProfileActivity.this,
                        SavedActivity.class));

                return true;
            }

            return true;
        });
    }
}