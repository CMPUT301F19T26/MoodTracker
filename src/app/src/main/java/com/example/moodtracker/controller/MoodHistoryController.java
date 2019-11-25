/**
 * MoodHistoryController
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
                System.out.println("Deleted Successfuly");
            }

            @Override
            public void onFailure(@NonNull Exception e) {
                // Say Failure
                System.out.println("Delete Failure");
            }
        });
        h.history.remove(index);
        adapter.notifyDataSetChanged();
    }
}
