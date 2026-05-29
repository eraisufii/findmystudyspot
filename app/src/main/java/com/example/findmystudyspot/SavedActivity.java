package com.example.findmystudyspot;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SavedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        BottomNavigationView bottomNavigation =
                findViewById(R.id.bottomNavigation);

        bottomNavigation.setSelectedItemId(R.id.nav_saved);

        bottomNavigation.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_explore) {

                startActivity(new Intent(
                        SavedActivity.this,
                        ExploreActivity.class));

                return true;
            }
            if (item.getItemId() == R.id.nav_profile) {

                startActivity(new Intent(
                        SavedActivity.this,
                        ProfileActivity.class));

                return true;
            }

            return true;
        });
    }
}