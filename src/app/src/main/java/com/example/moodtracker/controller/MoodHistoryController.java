package com.example.moodtracker.controller;

import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.moodtracker.model.MoodEvent;
import com.example.moodtracker.model.MoodHistory;
import com.example.moodtracker.view.mood.MoodHistoryActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MoodHistoryController {

    public MoodHistoryController() {
    }

    // Factory method
    public static MoodHistory getMoodHistory() {
        return new MoodHistory();
    }

    public static void addEventToHistory(final MoodEvent event, MoodHistory h, ArrayAdapter adapter) {
        // Todo:Should add handling for photos
        h.addMoodEvent(event, new MoodHistory.FirebaseCallback<Void>() {
            @Override
            public void onSuccess(Void document) {
                // Say success
            }

            @Override
            public void onFailure(@NonNull Exception e) {
                // Say Failed
            }
        });
        h.history.add(event);
        adapter.notifyDataSetChanged();

    }

    public static void deleteEventFromHistory(MoodEvent e, MoodHistory h, int index, ArrayAdapter adapter) {
        // Should be able to delet
        h.deleteMoodEvent(e, new MoodHistory.FirebaseCallback<Void>() {
            @Override
            public void onSuccess(Void document) {
                // Say success
            }

            @Override
            public void onFailure(@NonNull Exception e) {
                // Say Failure
            }
        });
        h.history.remove(index);
        adapter.notifyDataSetChanged();
    }
}
