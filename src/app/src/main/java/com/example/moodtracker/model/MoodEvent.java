/*
 * MoodEvent
 *
 * Version 1.0
 *
 * 11/8/2019
 *
 * MIT License
 *
 * Copyright (c) 2019 CMPUT301F19T26
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
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

/**
 * Constructor for various mood event types with setters, and getters.
 */
// Todo: Implement Parcelable
public class MoodEvent implements Parcelable {
    // Need access to the db
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

    /**
     * Gets latitude
     * @return latitude
     */
    public Double getLat() {
        return lat;
    }

    /**
     * Setter for latitude
     * @param lat latitude to be set to mood events latitude
     */
    public void setLat(Double lat) {
        this.lat = lat;
    }

    /**
     * Gets longitude
     * @return longitude
     */
    public Double getLng() {
        return lng;
    }

    /**
     * Sets user name
     * @param user_name user name to be set
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /**
     * Sets longitude
     * @param lng longitude to be set
     */
    public void setLng(Double lng) {
        this.lng = lng;
    }
  
   /**
     * Constructor for mood event with mood and date input only
     * @param mood the mood input from user
     * @param user_id the user id of the current user
     * @param date the date input from user
     */
    public MoodEvent(@NonNull String mood, @NonNull String user_id, @NonNull String date) {
        this.mood = mood;
        this.user_id = user_id;
        this.date = date;
    }

    /**
     * Constructor for mood event with mood, date and a reason input
     * @param mood the mood input from user
     * @param user_id the user id of the current user
     * @param date the date input from user
     * @param reason the reason input from user
     */
    public MoodEvent(@NonNull String mood, @NonNull String user_id, @NonNull String date, @Nullable String reason) {
        this.mood = mood;
        this.user_id = user_id;
        this.date = date;
        this.reason = reason;
    }

    /**
     * The mood event with a map input
     * @param mood the users mood input
     * @param user_id the usr id of the current user
     * @param date the date input from user
     * @param lat the latitude of the map coordinate for the mood event
     * @param lng the longitude of the map coordinate for the mood event
     */
    public MoodEvent(@NonNull String mood, @NonNull String user_id, @NonNull String date, @Nullable Double lat, @Nullable Double lng) {
        this.mood = mood;
        this.user_id = user_id;
        this.date = date;
        this.lat = lat;
        this.lng = lng;
    }

    /**
     * The mood event with all possible inputs
     * @param mood the mood input of the user
     * @param user_id the user id of the current user
     * @param date the date input of the user
     * @param reason the reason input of the user
     * @param photo_url the url of the photo the user chooses to attach
     * @param lat the latitude of the map coordinate for the mood event
     * @param lng the longitude of the map coordinate for the mood event
     * @param social_sit the social situation input
     */
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

    /**
     * Gets Social Situation
     * @return Social situation of mood event
     */
    public String getSocialSituation() {
        return this.social_situation;
    }

    /**
     * Sets social situation
     * @param ss the social situation to be set as the mood event object's social situation
     */
    public void setSocial_situation(String ss) {
        this.social_situation = ss;
    }

    /**
     * Gets mood id
     * @return mood id of mood event
     */
    public String getMood_id() {
        return this.mood_id;
    }

    /**
     * Sets id of mood
     * @param id The mood id to be set
     */
    public void setMood_id(String id) {
        this.mood_id = id;
    }

    public void edit(MoodEvent e) {
        // Go into the db and edit a mood event
    }

    public void delete(MoodEvent e) {
        // Go into the db and delete a selected mood event
    }

    /**
     * Gets mood
     * @return Mood of of mood event
     */
    public String getMood() {
        return mood;
    }

    /**
     * Sets mood and returns new mood
     * @param mood the mood to be set
     * @return the newly set mood
     */
    public String setMood(String mood) {
        // Should hit the db
        this.mood = mood;
        return this.mood;
    }

    /**
     * Gets user id
     * @return user id of mood event
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * Gets date
     * @return date of mood event
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets reason
     * @return reason for mood event
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets reason for mood event
     * @param reason reason to be set
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Gets photo url
     * @return the url of the photo
     */
    public String getPhoto_url() {
        return photo_url;
    }

    /**
     * Sets the photo url
     * @param photo_url url of attached photo
     */
    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    /**
     * Set mood event elements from read parcel
     * @param in parcel
     */
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

    public String getUser_name() {
        return user_name;
    }

    /**
     * Methods for creating mood event from Parcel
     */
    public static final Creator<MoodEvent> CREATOR = new Creator<MoodEvent>() {
        /**
         * Creates mood event from Parcel
         * @param in Parcel
         * @return the new mood event object creating using the Parcel
         */
        @Override
        public MoodEvent createFromParcel(Parcel in) {
            return new MoodEvent(in);
        }

        /**
         *
         * @param size
         * @return
         */
        @Override
        public MoodEvent[] newArray(int size) {
            return new MoodEvent[size];
        }
    };

    // Parcelable methods
    /**
     *
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Writes parameters of mood event to parcel
     * @param parcel parcel to be written to
     * @param i
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mood_id);
        parcel.writeString(mood);
        parcel.writeString(user_name);
        parcel.writeString(user_id);
        parcel.writeString(date);
        parcel.writeString(reason);
        parcel.writeString(photo_url);
        parcel.writeValue(lat);
        parcel.writeValue(lng);
        parcel.writeString(social_situation);

    }
}
