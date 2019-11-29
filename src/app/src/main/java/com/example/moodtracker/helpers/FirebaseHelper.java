/*
 * FireBaseHelper
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
package com.example.moodtracker.helpers;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.util.Log;

import com.example.moodtracker.adapter.FeedAdapter;
import com.example.moodtracker.model.MoodEvent;
import com.example.moodtracker.model.MoodHistory;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;

/**
 * Class for Handling Firebase accesses
 */
public class FirebaseHelper {
    public interface FirebaseCallback<T> {
        /**
         * callback handler for handling success cases, returned value is stored in `document` variable
         * @param document depending on the operation, this variable can hold different type of values
         */
        void onSuccess(T document);

        /**
         * callback handler for handling failure cases
         * @param e Exception thrown by the Firebase library
         */
        void onFailure(@NonNull Exception e);
    }
    private static FirebaseAuth auth = FirebaseAuth.getInstance();
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();


    /**
     * Getter for user name
     * @param user_id the users id
     * @return user name
     */
    public static String getUserName(String user_id) {
        DocumentSnapshot user = db.collection("users").document(user_id).get().getResult();
        System.out.print(user.getData());
        return "BALH";
    }

    /**
     * Getter for user id
     * @return user id
     */
    public static String getUid() {
        return auth.getCurrentUser().getUid();
    }

    /**
     * Upload images to firebase
     * @param photo the uri of the photo
     * @param mood_id the id of the mood
     * @param cb the firebase callback
     */
    public static void uploadImage(Uri photo, String mood_id, final FirebaseCallback<Uri> cb) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storeRef = storage.getReference().child(String.format("moodEvents/%s.jpg", mood_id));
        storeRef.putFile(photo)
                .addOnSuccessListener(taskSnapshot -> storeRef.getDownloadUrl()
                .addOnSuccessListener(cb::onSuccess)
                .addOnFailureListener(cb::onFailure));
    }

    public static void updateFeed(FirebaseFirestore db,String user_id, ArrayList<MoodEvent> feed, FeedAdapter feedadapter, final FirebaseCallback cb) {
        String TAG = "Sample";
        db.collection("follow")
                .whereEqualTo("follower_id",user_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            feed.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if(document.get("type") != null && document.get("type").equals("all")) {
                                    db.collection("users").document(document.get("following_id").toString()).collection("moodEvents").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                                    MoodEvent me  = MoodHistory.buildMoodEventFromDoc(doc, document.get("following_id").toString());
                                                    feed.add(me);
                                                }
                                                Collections.sort(feed, new MoodHistoryHelpers());
                                                feedadapter.notifyDataSetChanged();
                                            }
                                        }
                                    });
                                } else {
                                    db.collection("users").document(document.get("following_id").toString()).collection("moodEvents").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                ArrayList<MoodEvent> moodEventsTemp = new ArrayList<>();

                                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                                    MoodEvent me  = MoodHistory.buildMoodEventFromDoc(doc, document.get("following_id").toString());
                                                    moodEventsTemp.add(me);
                                                }
                                                Collections.sort(moodEventsTemp, new MoodHistoryHelpers());
                                                MoodEvent firstOne;
                                                if(!moodEventsTemp.isEmpty()) {
                                                     firstOne = moodEventsTemp.get(0);
                                                     moodEventsTemp.clear();
                                                    feed.add(firstOne);
                                                }
                                                Collections.sort(feed, new MoodHistoryHelpers());
                                                feedadapter.notifyDataSetChanged();
                                            }
                                        }
                                    });
                                }



                            }
                            cb.onSuccess(null);
//                            Collections.sort(feedDataList, new MoodHistoryHelpers());
//                            feedAdapter.notifyDataSetChanged();

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            cb.onFailure(null);
                        }
                    }
                });
    }


}
