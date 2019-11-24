/**
 * MoodHistory
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

import android.net.Uri;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.moodtracker.constants;
import com.example.moodtracker.helpers.FirebaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.type.LatLng;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class MoodHistory implements Serializable {
    public interface FirebaseCallback<T> {
        /**
         * callback handler for handling success cases, returned the value is stored in `document` variable
         *
         * @param document depending on the operation, this variable can hold different type of values
         */
        void onSuccess(T document);

        /**
         * callback handler for handling failure cases
         *
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

    public static void getMoodHistory(ArrayAdapter adapter, MoodHistory h) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("moodEvents")
                .whereEqualTo("user_id", h.user_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            if (doc.exists()) {
                                MoodEvent me  = buildMoodEventFromDoc(doc, h.user_id);
                                h.history.add(me);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

    }

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
     * add the mood event
     *
     * @param e the mood event
     * @param cb the call back to the firebase db
     */
    public void addMoodEvent(MoodEvent e, final FirebaseCallback<Void> cb) {
        db.collection("moodEvents").document(e.getMood_id())
                .set(e)
                .addOnSuccessListener(cb::onSuccess)
                .addOnFailureListener(cb::onFailure);
    }

    /**
     * Delete a mood event
     *
     * @param e the mood event
     * @param cb the call back to the firebase db
     */

    public static void externalAddMoodEvent(MoodEvent e, Uri photo, final FirebaseCallback<Void> cb) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (photo != null) {
            // Upload the image
            // Perform moodevent upload in the callback
            FirebaseHelper.uploadImage(photo, e.getMood_id(), new FirebaseHelper.FirebaseCallback<Uri>() {
                @Override
                public void onSuccess(Uri document) {
                    e.setPhoto_url(document.toString());
                    // Check if a location is needed to be added
                    db.collection("moodEvents").document(e.getMood_id())
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
            db.collection("moodEvents").document(e.getMood_id())
                    .set(e)
                    .addOnSuccessListener(cb::onSuccess)
                    .addOnFailureListener(cb::onFailure);
        }
    }

    public void deleteMoodEvent(MoodEvent e, final FirebaseCallback<Void> cb) {
        // Deleting a mood event
        db.collection("moodEvents").document(e.getMood_id())
                .delete()
                .addOnSuccessListener(cb::onSuccess)
                .addOnFailureListener(cb::onFailure);

    }

}
