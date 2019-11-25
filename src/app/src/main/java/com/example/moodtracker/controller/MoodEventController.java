/*
 * MoodEventController
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

package com.example.moodtracker.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.moodtracker.helpers.SocialSituation;
import com.example.moodtracker.model.Mood;
import com.example.moodtracker.model.MoodEvent;
import com.google.type.LatLng;

import java.util.Date;

/**
 * Modifies, Creates and Deletes mood events
 */
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
     * @return the newly created mood event
     */
    public static MoodEvent createMoodEvent(@NonNull String mood, @NonNull String user_id, @NonNull String date,
                                            @Nullable String reason, @Nullable String photo_url, @Nullable LatLng location,
                                            @Nullable SocialSituation social_situation) {
        MoodEvent new_event = new MoodEvent(mood, user_id, date);
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
