package com.example.findmystudyspot;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmystudyspot.adapter.StudySpotAdapter;
import com.example.findmystudyspot.model.StudySpot;

import java.util.ArrayList;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputEditText;
import android.content.Intent;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ExploreActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    StudySpotAdapter adapter;
    ArrayList<StudySpot> studySpotList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TextInputEditText searchBar = findViewById(R.id.searchBar);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.setSelectedItemId(R.id.nav_explore);

        bottomNavigation.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_saved) {

                startActivity(new Intent(
                        ExploreActivity.this,
                        SavedActivity.class));

                return true;
            }

            if (item.getItemId() == R.id.nav_profile) {

                startActivity(new Intent(
                        ExploreActivity.this,
                        ProfileActivity.class));

                return true;
            }

            return true;
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s,
                                          int start,
                                          int count,
                                          int after) {

            }

            @Override
            public void onTextChanged(CharSequence s,
                                      int start,
                                      int before,
                                      int count) {

                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        studySpotList = new ArrayList<>();

        studySpotList.add(new StudySpot(
                "Blue Bottle Café",
                "Excellent WiFi • Quiet • Prishtinë",
                R.drawable.cafe,
                42.6629,
                21.1655
        ));

        studySpotList.add(new StudySpot(
                "University Library",
                "Super quiet • Perfect for studying",
                R.drawable.library,
                42.6489,
                21.1622
        ));

        studySpotList.add(new StudySpot(
                "Study Hub",
                "Modern space • Group friendly",
                R.drawable.studyhub,
                42.6590,
                21.1605
        ));

        studySpotList.add(new StudySpot(
                "Coffee House",
                "Modern café • Great WiFi",
                R.drawable.cafe,
                42.6629,
                21.1655
        ));

        studySpotList.add(new StudySpot(
                "City Library",
                "Quiet study area • Free seating",
                R.drawable.library,
                42.6489,
                21.1622
        ));

        studySpotList.add(new StudySpot(
                "Study Hub",
                "Perfect for group work",
                R.drawable.studyhub,
                42.6590,
                21.1605
        ));

        adapter = new StudySpotAdapter(this, studySpotList);

        recyclerView.setAdapter(adapter);
    }
}