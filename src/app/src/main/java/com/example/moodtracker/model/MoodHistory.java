/*
 * MoodHistory
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

import android.net.Uri;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.moodtracker.constants;
import com.example.moodtracker.helpers.FirebaseHelper;
import com.example.moodtracker.helpers.MoodHistoryHelpers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;
import com.google.type.LatLng;

import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Creates and edits users mood history.
 */
public class MoodHistory implements Serializable {
    public interface FirebaseCallback<T> {
        /**
         * callback handler for handling success cases, returned the value is stored in `document` variable
         * @param document depending on the operation, this variable can hold different type of values
         */
        void onSuccess(T document);

        /**
         * callback handler for handling failure cases
         * @param e Exception thrown by the Firebase library
         */
        void onFailure(@NonNull Exception e);
    }

    private FirebaseFirestore db = FirebaseFirestore.getInstance(); // Access to the db

    private String user_id;
    // Todo: Make this private
    public ArrayList<MoodEvent> history = new ArrayList<>();
  
    /**
     * Get the mood history for the user
     */
    public MoodHistory(final String user_id) {
        // Get the Mood history for the user
        this.user_id = user_id;
    }

    /**
     * Gets Mood history
     * @param adapter the adapter for the mood history
     * @param h the mood history
     */
    public static void getMoodHistory(ArrayAdapter adapter, MoodHistory h) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        db.collection("users").document(h.user_id).collection("moodEvents")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            if (doc.exists()) {
                                MoodEvent me  = buildMoodEventFromDoc(doc, h.user_id);
                                h.history.add(me);
                            }
                        }
                        // Sort the moodEvents
                        Collections.sort(h.history, new MoodHistoryHelpers());
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

    }

    /**
     * Get the mood history with the specified filter
     * @param adapter the adapter for the mood history
     * @param h the mood history
     * @param filter the filter
     * @param filter_val the value for the filter
     */
    public static void getMoodHistoryWithFilter(ArrayAdapter adapter, MoodHistory h, String filter, String filter_val) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        db.collection("users").document(h.user_id).collection("moodEvents")
                .whereEqualTo(filter, filter_val)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            if (doc.exists()) {
                                MoodEvent me  = buildMoodEventFromDoc(doc, h.user_id);
                                h.history.add(me);
                            }
                        }
                        // Sort the moodEvents
                        Collections.sort(h.history, new MoodHistoryHelpers());
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

    }

    /**
     * Build mood event from document
     * @param doc the Document
     * @param user_id user id of current user
     * @return the mood event
     */
    public static MoodEvent buildMoodEventFromDoc(QueryDocumentSnapshot doc, String user_id) {
        String date = doc.get("date").toString();
        String mood = doc.get("mood").toString();
        String mood_id = doc.get("mood_id").toString();
        MoodEvent me = new MoodEvent(mood, user_id, date);
        me.setMood_id(mood_id);
        if (doc.get("reason") != null) {
            me.setReason(doc.get("reason").toString());
        }
        if (doc.get("photo_url") != null ) {
            me.setPhoto_url(doc.get("photo_url").toString());
        }
        if (doc.get("socialSituation") != null)  {
            me.setSocial_situation(doc.get("socialSituation").toString());
        }
        if (doc.get("lat") !=null) {
            me.setLat(Double.valueOf(doc.get("lat").toString()));
            me.setLng(Double.valueOf(doc.get("lng").toString()));
        }
        return me;
    }

    /**
     * Updates mood event
     * @param e the mood event
     * @param position the position in mood history
     * @param photo the photo provided
     * @param h the mood history
     * @param adapter adapter for mood history
     * @param cb firebase callback
     */
    public static void externalUpdateMoodEvent(MoodEvent e, int position, Uri photo,MoodHistory h, ArrayAdapter adapter, final MoodHistory.FirebaseCallback cb){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (photo != null) {
            // Upload first then do the the thing
            FirebaseHelper.uploadImage(photo, e.getMood_id(), new FirebaseHelper.FirebaseCallback<Uri>() {
                @Override
                public void onSuccess(Uri document) {
                    e.setPhoto_url(document.toString());
                    db.collection("users").document(auth.getCurrentUser().getUid()).collection("moodEvents").document(e.getMood_id())
                            .set(e)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    h.history.set(position, e);
                                    adapter.notifyDataSetChanged();
                                    cb.onSuccess(document);
                                }
                            });
                }

                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("Failed to upload image");
                }
            });

        } else {
            // Overwrite the document
            db.collection("users").document(auth.getCurrentUser().getUid()).collection("moodEvents").document(e.getMood_id())
                    .set(e, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            h.history.set(position, e);
                            adapter.notifyDataSetChanged();
                            cb.onSuccess(null);
                        }
                    });
        }
   }

    /**
     * Adding to the mood event
     * @param e the mood event
     * @param photo the photo provided
     * @param cb firebase callback
     */
    public static void externalAddMoodEvent(MoodEvent e, Uri photo, final FirebaseCallback<Void> cb) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (photo != null) {
            // Upload the image
            // Perform moodevent upload in the callback
            FirebaseHelper.uploadImage(photo, e.getMood_id(), new FirebaseHelper.FirebaseCallback<Uri>() {
                @Override
                public void onSuccess(Uri document) {
                    e.setPhoto_url(document.toString());
                    // Check if a location is needed to be added
                    db.collection("users").document(auth.getCurrentUser().getUid()).collection("moodEvents").document(e.getMood_id())
                            .set(e)
                            .addOnSuccessListener(cb::onSuccess)
                            .addOnFailureListener(cb::onFailure);
                }
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("Failed to upload image");
                    cb.onFailure(e);
                }
            });
        } else {
            // Check if a location must be added
            db.collection("users").document(auth.getCurrentUser().getUid()).collection("moodEvents").document(e.getMood_id())
                    .set(e)
                    .addOnSuccessListener(cb::onSuccess)
                    .addOnFailureListener(cb::onFailure);
        }
    }

    /**
     * Delete a mood event from mood history
     * @param e the mood event
     * @param cb firebase callback
     */
    public void deleteMoodEvent(MoodEvent e, final FirebaseCallback<Void> cb) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        // Deleting a mood event
        db.collection("users").document(auth.getCurrentUser().getUid()).collection("moodEvents").document(e.getMood_id())
                .delete()
                .addOnSuccessListener(cb::onSuccess)
                .addOnFailureListener(cb::onFailure);

    }

}
