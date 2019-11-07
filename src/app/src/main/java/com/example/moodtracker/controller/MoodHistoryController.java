package com.example.moodtracker.controller;

import com.example.moodtracker.model.MoodEvent;
import com.example.moodtracker.model.MoodHistory;
import com.google.firebase.firestore.DocumentReference;

public class MoodHistoryController {

    public MoodHistoryController() {
    }

    // Factory method
    public static MoodHistory getMoodHistory() {
        return new MoodHistory();
    }

    public void addEventToHistory(MoodEvent event, MoodHistory h) {
        // Should add handling for photos
        h.addMoodEvent(event);
    }

    public void deleteEventFromHistory(MoodEvent e, MoodHistory h) {
        // Should be able to delet
        h.deleteMoodEvent(e);
    }
}
