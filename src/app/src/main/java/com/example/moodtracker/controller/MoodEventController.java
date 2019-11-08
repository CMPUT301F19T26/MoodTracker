/**
 * MoodEventController
 *
 * Version 1.0
 *
 * 11/8/2019
 *
 * Copyright (c) $today.year. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.moodtracker.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.moodtracker.helpers.SocialSituation;
import com.example.moodtracker.model.Mood;
import com.example.moodtracker.model.MoodEvent;
import com.google.type.LatLng;

import java.util.Date;

public class MoodEventController {

    public MoodEventController() {
    }

    /**
     * Creates the mood event
     * @param mood the mood input
     * @param user_id the id of user
     * @param date the date input
     * @param reason the reason input
     * @param photo_url the url of picture
     * @param location the location input
     * @param social_situation the social situation input
     * @return the mood event
     */
    public static MoodEvent createMoodEvent(@NonNull Mood mood, @NonNull String user_id, @NonNull Date date,
                                            @Nullable String reason, @Nullable String photo_url, @Nullable LatLng location,
                                            @Nullable SocialSituation social_situation) {
        MoodEvent new_event = new MoodEvent(mood, user_id, date, reason, photo_url, location, social_situation);
        // Todo: Add to db
        // MoodEvent.addToDB(new_event);
        return new_event;
    }

    /**
     * Calls mood event edit
     * @param e mood event
     */
    public void editMoodEvent(MoodEvent e) {
        e.edit(e);
    }

    /**
     * Calls mood event delete
     * @param e mood event
     */
    public void deleteMoodEvent(MoodEvent e) {
        e.delete(e);
    }
}
