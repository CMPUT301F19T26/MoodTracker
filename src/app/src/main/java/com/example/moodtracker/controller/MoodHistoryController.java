/**
 * MoodHistoryController
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

    /**
     * Get the mood history
     * @return MoodHistory
     */
    public static MoodHistory getMoodHistory() {
        return new MoodHistory();
    }

    /**
     * Adds mood event to history.
     * @param event the event to add
     * @param h the mood history
     * @param adapter the adapter for mood history
     */
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

    /**
     * Delete an event from mood history
     * @param e the mood event
     * @param h the mood history
     * @param index the index being deleted
     * @param adapter the adapter for the array
     */
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
