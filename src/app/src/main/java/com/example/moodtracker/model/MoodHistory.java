package com.example.moodtracker.model;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.ArrayList;

public class MoodHistory implements Serializable {
    private FirebaseFirestore db = FirebaseFirestore.getInstance(); // Access to the db
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String user_id;
    // Todo: Make this private
    public ArrayList<MoodEvent> history = new ArrayList<MoodEvent>();

    public MoodHistory() {
//        user_id = auth.getCurrentUser().getUid();
        // Get the Mood history for the user
    }

    public void addMoodEvent(MoodEvent e) {
//        db.collection("moodhistory").document(user_id);
    }

    public void deleteMoodEvent(MoodEvent e) {
        // Deleting a mood event
    }

}
