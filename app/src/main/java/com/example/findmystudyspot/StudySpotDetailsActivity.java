package com.example.findmystudyspot;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import com.google.android.material.button.MaterialButton;

public class StudySpotDetailsActivity extends AppCompatActivity {

    ImageView detailImage;
    TextView detailName, detailDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_spot_details);

        detailImage = findViewById(R.id.detailImage);
        detailName = findViewById(R.id.detailName);
        detailDescription = findViewById(R.id.detailDescription);

        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        int image = getIntent().getIntExtra("image", 0);

        detailName.setText(name);
        detailDescription.setText(description);
        detailImage.setImageResource(image);

        MaterialButton mapButton = findViewById(R.id.mapButton);

        double lat = getIntent().getDoubleExtra("lat", 0);
        double lng = getIntent().getDoubleExtra("lng", 0);

        mapButton.setOnClickListener(v -> {

            Uri gmmIntentUri = Uri.parse(
                    "google.navigation:q=" + lat + "," + lng);

            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

            mapIntent.setPackage("com.google.android.apps.maps");

            startActivity(mapIntent);
        });
    }
}