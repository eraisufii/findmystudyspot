package com.example.findmystudyspot;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmystudyspot.adapter.StudySpotAdapter;
import com.example.findmystudyspot.database.DatabaseHelper;
import com.example.findmystudyspot.model.StudySpot;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ExploreActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StudySpotAdapter adapter;
    private ArrayList<StudySpot> studySpotList;
    private DatabaseHelper dbHelper;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        // Track user lifecycle context
        userEmail = getIntent().getStringExtra("USER_EMAIL");

        // Setup local database reference layer
        dbHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TextInputEditText searchBar = findViewById(R.id.searchBar);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        FloatingActionButton fabAddLocation = findViewById(R.id.fabAddLocation);

        bottomNavigation.setSelectedItemId(R.id.nav_explore);

        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_saved) {
                Intent intent = new Intent(ExploreActivity.this, SavedActivity.class);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
                return true;
            }
            if (id == R.id.nav_profile) {
                Intent intent = new Intent(ExploreActivity.this, ProfileActivity.class);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
                return true;
            }
            return false;
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapter != null) {
                    adapter.getFilter().filter(s);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        // FAB short link listener routing to our new form layout page
        if (fabAddLocation != null) {
            fabAddLocation.setOnClickListener(v -> {
                Intent intent = new Intent(ExploreActivity.this, AddLocationActivity.class);
                startActivity(intent);
            });
        }

        // CHIP SELECTOR BINDING MODULES
        Chip chipAll = findViewById(R.id.chipAll);
        Chip chipCafes = findViewById(R.id.chipCafes);
        Chip chipLibraries = findViewById(R.id.chipLibraries);
        Chip chipHubs = findViewById(R.id.chipHubs);

        if (chipAll != null) chipAll.setOnClickListener(v -> adapter.getFilter().filter(""));
        if (chipCafes != null) chipCafes.setOnClickListener(v -> adapter.getFilter().filter("café"));
        if (chipLibraries != null) chipLibraries.setOnClickListener(v -> adapter.getFilter().filter("library"));
        if (chipHubs != null) chipHubs.setOnClickListener(v -> adapter.getFilter().filter("hub"));
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Query the database to fetch the up-to-date entries list
        studySpotList = dbHelper.getAllStudySpots();

        // Re-attach or refresh the layout adapter dataset
        adapter = new StudySpotAdapter(this, studySpotList);
        recyclerView.setAdapter(adapter);
    }
}