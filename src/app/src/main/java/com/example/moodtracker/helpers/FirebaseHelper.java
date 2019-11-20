/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.moodtracker.helpers;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;

public class FirebaseHelper {
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
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static String getUserName(String user_id) {
        DocumentSnapshot user = db.collection("users").document(user_id).get().getResult();
        System.out.print(user.getData());
        return "BALH";
    }

    public static void uploadImage(Uri photo, String mood_id, final FirebaseCallback<Uri> cb) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storeRef = storage.getReference().child(String.format("moodEvents/%s.jpg", mood_id));
        storeRef.putFile(photo)
                .addOnSuccessListener(taskSnapshot -> storeRef.getDownloadUrl()
                .addOnSuccessListener(cb::onSuccess)
                .addOnFailureListener(cb::onFailure));
    }


}
