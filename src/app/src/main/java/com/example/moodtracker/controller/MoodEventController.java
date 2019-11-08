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

    public static MoodEvent createMoodEvent(@NonNull String mood, @NonNull String user_id, @NonNull Date date,
                                            @Nullable String reason, @Nullable String photo_url, @Nullable LatLng location,
                                            @Nullable SocialSituation social_situation) {
        MoodEvent new_event = new MoodEvent(mood, user_id, date, reason, photo_url, location, social_situation);
        // Todo: Add to db
        // MoodEvent.addToDB(new_event);
        return new_event;
    }

    public void editMoodEvent(MoodEvent e) {
        e.edit(e);
    }

    public void deleteMoodEvent(MoodEvent e) {
        e.delete(e);
    }
}
