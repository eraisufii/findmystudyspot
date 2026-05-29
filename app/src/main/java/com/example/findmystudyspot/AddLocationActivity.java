package com.example.findmystudyspot;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.findmystudyspot.database.DatabaseHelper;
import com.example.findmystudyspot.model.StudySpot;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddLocationActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private String selectedCategory = "";

    // Tracks the user's active cover image choice (defaulting to studyhub)
    private int chosenImageResource = R.drawable.studyhub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        dbHelper = new DatabaseHelper(this);

        TextInputEditText etName = findViewById(R.id.etSpotName);
        TextInputEditText etDescription = findViewById(R.id.etSpotDescription);
        AutoCompleteTextView menuCategory = findViewById(R.id.spinnerCategory);
        ImageView ivPreview = findViewById(R.id.ivSelectedPreview);
        MaterialButton btnSave = findViewById(R.id.btnSaveLocation);

        // Grid selection items setup
        ImageButton btnImgCafe = findViewById(R.id.imgBtnOptionCafe);
        ImageButton btnImgLibrary = findViewById(R.id.imgBtnOptionLibrary);
        ImageButton btnImgHub = findViewById(R.id.imgBtnOptionHub);

        // Bind items dropdown text options
        String[] categories = new String[]{"Café", "Library", "Study Hub"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categories);
        menuCategory.setAdapter(adapter);

        menuCategory.setOnItemClickListener((parent, view, position, id) -> {
            selectedCategory = parent.getItemAtPosition(position).toString();

            // Helpful automation: infer a matching image style placeholder on category swap
            if (selectedCategory.equals("Café")) {
                chosenImageResource = R.drawable.cafe;
            } else if (selectedCategory.equals("Library")) {
                chosenImageResource = R.drawable.library;
            } else {
                chosenImageResource = R.drawable.studyhub;
            }
            ivPreview.setImageResource(chosenImageResource);
        });

        // Manual Selection Listeners
        if (btnImgCafe != null) {
            btnImgCafe.setOnClickListener(v -> {
                chosenImageResource = R.drawable.cafe;
                ivPreview.setImageResource(chosenImageResource);
            });
        }

        if (btnImgLibrary != null) {
            btnImgLibrary.setOnClickListener(v -> {
                chosenImageResource = R.drawable.library;
                ivPreview.setImageResource(chosenImageResource);
            });
        }

        if (btnImgHub != null) {
            btnImgHub.setOnClickListener(v -> {
                chosenImageResource = R.drawable.studyhub;
                ivPreview.setImageResource(chosenImageResource);
            });
        }

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String desc = etDescription.getText().toString().trim();

            if (name.isEmpty() || desc.isEmpty() || selectedCategory.isEmpty()) {
                Toast.makeText(this, "Please populate all form sections first", Toast.LENGTH_SHORT).show();
                return;
            }

            // Central coordinates for Prishtinë map placement fallback
            double defaultLat = 42.6629;
            double defaultLng = 21.1655;

            // Instantiates the model using the custom image resource variable
            StudySpot newSpot = new StudySpot(name, desc, chosenImageResource, defaultLat, defaultLng);

            boolean success = dbHelper.addStudySpot(newSpot);
            if (success) {
                Toast.makeText(this, "Spot successfully added to repository!", Toast.LENGTH_SHORT).show();
                finish(); // Instantly returns to the refreshed map dashboard list screen
            } else {
                Toast.makeText(this, "Database write failure event occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }
}