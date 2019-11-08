/**
 * MoodEvent
 *
 * Version 1.0
 *
 * 11/8/2019
 *
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.moodtracker.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

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

// Todo: Implement Parcelable
public class MoodEvent{
    // Need access to the db
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String mood_id = UUID.randomUUID().toString();
    private String mood;
    private String user_id;
    private Date date;
    private String reason = NULL;
    private String photo_url = NULL;
    private Double lat;
    private Double lng;
    private String social_situation = NULL;

   

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
  
   /**
     * Mood event init and set
     * @param mood the mood input
     * @param user_id the user_id
     * @param date the date input
     */
    public MoodEvent(@NonNull String mood, @NonNull String user_id, @NonNull Date date) {
        this.mood = mood;
        this.user_id = user_id;
        this.date = date;
    }


    public MoodEvent(@NonNull String mood, @NonNull String user_id, @NonNull Date date, @Nullable String reason) {
        this.mood = mood;
        this.user_id = user_id;
        this.date = date;
        this.reason = reason;
    }

    /**
     * The MoodEvent
     * @param mood the mood input
     * @param user_id the users id
     * @param date the date input
     * @param reason the reason input
     * @param photo_url the photo url
     * @param location the location input
     * @param social_sit the social situation input
     */

    public MoodEvent(@NonNull String mood, @NonNull String user_id, @NonNull Date date, @Nullable Double lat, @Nullable Double lng) {
        this.mood = mood;
        this.user_id = user_id;
        this.date = date;
        this.lat = lat;
        this.lng = lng;
    }

    public MoodEvent(@NonNull String mood, @NonNull String user_id, @NonNull Date date,
                     @Nullable String reason, @Nullable String photo_url, @Nullable Double lat, Double lng, @Nullable String social_sit) {
        this.mood = mood;
        this.user_id = user_id;
        this.date = date;
        this.reason = reason;
        this.photo_url = photo_url;
        this.lat = lat;
        this.lng = lng;
        this.social_situation = social_sit;
    }

    public String getSocialSituation() {
        return this.social_situation;
    }

    public void setSocial_situation(String ss) {
        this.social_situation = ss;
    }

    /**
     * get mood id
     * @return mood id
     */
    public String getMood_id() {
        return this.mood_id;
    }

    public void setMood_id(String id) {
        this.mood_id = id;
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

    public String getMood() {
        return mood;
    }

    public String setMood(String mood) {
        // Should hit the db
        this.mood = mood;
        return this.mood;
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
}
