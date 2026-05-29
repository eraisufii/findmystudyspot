package com.example.findmystudyspot.model;

public class StudySpot {

    private String name;
    private String description;
    private int image;

    private double latitude;
    private double longitude;

    public StudySpot(String name, String description, int image,
                     double latitude, double longitude) {

        this.name = name;
        this.description = description;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}