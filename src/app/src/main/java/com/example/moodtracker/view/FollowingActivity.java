/**
 * Following Activity
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

package com.example.moodtracker.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.moodtracker.R;
import com.example.moodtracker.adapter.FollowAdapter;
import com.example.moodtracker.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FollowingActivity extends AppCompatActivity {

    // Declare the variables so that you will be able to reference it later.
    ListView followingListView;
    ArrayAdapter<User> followingAdapter;
    ArrayList<User> followingDataList;

    // new class vars
    String TAG = "Sample";
    Button addCityButton;
    EditText addCityEditText;
    EditText addProvinceEditText;
    FirebaseFirestore db;
    /**
     * On create of following activity
     *
     * @param savedInstanceState the instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);


        followingListView = findViewById(R.id.list_user_following);
        followingDataList = new ArrayList<>();
        followingAdapter = new FollowAdapter(this, followingDataList);
        followingListView.setAdapter(followingAdapter);

        db = FirebaseFirestore.getInstance();

        // query list of people the current user is following
        // by looking at all instances where the follower is the current person
        db.collection("follow")
                .whereEqualTo("follower_id", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            followingDataList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String followingId = document.get("following_id").toString();
                                followingDataList.add(new User(followingId));
//                                Log.d("FUCKU", document.get("following_id").toString());
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }

                            followingAdapter.notifyDataSetChanged();

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });




//        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                followingDataList.clear();
//                for(QueryDocumentSnapshot doc : queryDocumentSnapshots) {
//                    String city = doc.getId();
//                    followingDataList.add(new User(uid));
//                }
//                followingAdapter.notifyDataSetChanged();
//            }
//        });

    }


}
