package com.example.moodtracker.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.moodtracker.R;
import com.example.moodtracker.constants;
import com.example.moodtracker.controller.MoodEventController;
import com.example.moodtracker.helpers.SocialSituation;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.LatLng;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class MoodEvent implements Serializable {
    // Need access to the db
    private String mood_id = UUID.randomUUID().toString();
    private Mood mood;
    private String user_id;
    private Date date;
    private String reason = NULL;
    private String photo_url = NULL;
    private LatLng location;
    private String social_situation = NULL;

    public MoodEvent(@NonNull Mood mood, @NonNull String user_id, @NonNull Date date) {
        this.mood = mood;
        this.user_id = user_id;
        this.date = date;
    }

    public MoodEvent(@NonNull Mood mood, @NonNull String user_id, @NonNull Date date, @Nullable String reason) {
        this.mood = mood;
        this.user_id = user_id;
        this.date = date;
        this.reason = reason;
    }

    public MoodEvent(@NonNull Mood mood, @NonNull String user_id, @NonNull Date date,
                     @Nullable String reason, @Nullable String photo_url, @Nullable LatLng location, @Nullable SocialSituation social_sit) {
        this.mood = mood;
        this.user_id = user_id;
        this.date = date;
        this.reason = reason;
        this.photo_url = photo_url;
        this.location = location;
        this.social_situation = social_sit.getSocialType();
    }

    public String getMood_id() {
        return this.mood_id;
    }

    public static void addToDB(MoodEvent e) {
        // Handle it in the db
    }

    public void edit(MoodEvent e) {
        // Go into the db and edit a mood event
    }

    public void delete(MoodEvent e) {
        // Go into the db and delete a selected mood event
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        // Should hit the db
        this.mood = mood;
    }

    public String getUser_id() {
        return user_id;
    }

    public Date getDate() {
        return date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
