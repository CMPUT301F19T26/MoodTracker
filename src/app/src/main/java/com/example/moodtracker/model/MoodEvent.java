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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.moodtracker.constants;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.UUID;


// Todo: Implement Parcelable
public class MoodEvent implements Parcelable {
    // Need access to the db
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String mood_id = UUID.randomUUID().toString();
    private String mood;
    private String user_name;
    private String user_id;
    private String date;
    private String reason = null;
    private String photo_url = null;
    private Double lat;
    private Double lng;
    private String social_situation = null;

   

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    private void setUser_name(String user_name) {
        this.user_name = user_name;
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
    public MoodEvent(@NonNull String mood, @NonNull String user_id, @NonNull String date) {
        this.mood = mood;
        this.user_id = user_id;
        this.date = date;
    }


    public MoodEvent(@NonNull String mood, @NonNull String user_id, @NonNull String date, @Nullable String reason) {
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

    public MoodEvent(@NonNull String mood, @NonNull String user_id, @NonNull String date, @Nullable Double lat, @Nullable Double lng) {
        this.mood = mood;
        this.user_id = user_id;
        this.date = date;
        this.lat = lat;
        this.lng = lng;
    }

    public MoodEvent(@NonNull String mood, @NonNull String user_id, @NonNull String date,
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

    public String getDate() {
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

    protected MoodEvent(Parcel in) {
        mood_id = in.readString();
        mood = in.readString();
        user_name = in.readString();
        user_id = in.readString();
        date = in.readString();
        reason = in.readString();
        photo_url = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        social_situation = in.readString();
    }

    public static final Creator<MoodEvent> CREATOR = new Creator<MoodEvent>() {
        @Override
        public MoodEvent createFromParcel(Parcel in) {
            return new MoodEvent(in);
        }

        @Override
        public MoodEvent[] newArray(int size) {
            return new MoodEvent[size];
        }
    };

    // Parcelable methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mood_id);
        parcel.writeString(mood);
        parcel.writeString(user_name);
        parcel.writeString(user_id);
        parcel.writeString(date);
        parcel.writeString(reason);
        parcel.writeString(photo_url);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
        parcel.writeString(social_situation);

    }
}
