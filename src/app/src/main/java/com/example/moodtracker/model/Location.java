package com.example.moodtracker.model;

public class Location {
    //declare attributes
    double latitude;
    double longitude;
    String mood;

    //constructor
    public Location (double lat, double lon, String mood_event) {
        latitude = lat;
        longitude = lon;
        mood = mood_event;

    }

    //implement getters
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getMood() {
        return mood;
    }
}
