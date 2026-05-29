package com.example.findmystudyspot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmystudyspot.adapter.StudySpotAdapter;
import com.example.findmystudyspot.model.StudySpot;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SavedActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    StudySpotAdapter adapter;
    ArrayList<StudySpot> allSpotsList;
    ArrayList<StudySpot> favoriteSpotsList;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        userEmail = getIntent().getStringExtra("USER_EMAIL");

        // 1. Setup RecyclerView linking to your layout ID
        recyclerView = findViewById(R.id.recyclerViewSaved);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 2. Define standard spots database
        allSpotsList = new ArrayList<>();
        allSpotsList.add(new StudySpot("Blue Bottle Café", "Excellent WiFi • Quiet • Prishtinë", R.drawable.cafe, 42.6629, 21.1655));
        allSpotsList.add(new StudySpot("University Library", "Super quiet • Perfect for studying", R.drawable.library, 42.6489, 21.1622));
        allSpotsList.add(new StudySpot("Study Hub", "Modern space • Group friendly", R.drawable.studyhub, 42.6590, 21.1605));
        allSpotsList.add(new StudySpot("Coffee House", "Modern café • Great WiFi", R.drawable.cafe, 42.6629, 21.1655));
        allSpotsList.add(new StudySpot("City Library", "Quiet study area • Free seating", R.drawable.library, 42.6489, 21.1622));
        allSpotsList.add(new StudySpot("Study Hub", "Perfect for group work", R.drawable.studyhub, 42.6590, 21.1605));

        // 3. Scan storage and populate favorites
        favoriteSpotsList = new ArrayList<>();
        SharedPreferences prefs = getSharedPreferences("Favorites", Context.MODE_PRIVATE);

        for (StudySpot spot : allSpotsList) {
            if (prefs.getBoolean(spot.getName(), false)) {
                favoriteSpotsList.add(spot);
            }
        }

        // 4. Set the adapter
        adapter = new StudySpotAdapter(this, favoriteSpotsList);
        recyclerView.setAdapter(adapter);

        // 5. Setup Fixed Navigation rules
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.nav_saved);

        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_explore) {
                Intent intent = new Intent(SavedActivity.this, ExploreActivity.class);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
                return true;
            }
            if (id == R.id.nav_profile) {
                Intent intent = new Intent(SavedActivity.this, ProfileActivity.class);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }
}