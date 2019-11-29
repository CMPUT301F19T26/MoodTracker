/*
 * HomeActivity
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
 */

package com.example.moodtracker.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.moodtracker.R;
import com.example.moodtracker.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Handles home activity
 */
public class HomeActivity extends AppCompatActivity {

    // move :
    // logout
    // follow that person
    // maps
    // mood history

    Button logoutBtn;
    Button mapsBtn;
//    Button searchBtn;

    TextView usernameText;
    EditText followUsernameText;
    Button gotoMoodHistory;
    Button followBtn;
    String toFollowId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logoutBtn = findViewById(R.id.button_logout);
        followBtn = findViewById(R.id.button_follow);
        mapsBtn = findViewById(R.id.button_maps);
//        searchBtn = findViewById(R.id.button_search);

        usernameText = findViewById(R.id.text_email);
        gotoMoodHistory = findViewById(R.id.button_moodhistory);
        followUsernameText = findViewById(R.id.input_username);

        usernameText.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent mainIntent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(mainIntent);

            }
        });

        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(HomeActivity.this, MapActivity.class);

                User Jared = new User("21");
                searchIntent.putExtra("USER", Jared);
                searchIntent.putExtra("MODE", 0);
                startActivity(searchIntent);
            }
        });

//        searchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent searchIntent = new Intent(HomeActivity.this, FindActivity.class);
//                startActivity(searchIntent);
//
//            }
//        });

        gotoMoodHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent myIntent = new Intent(HomeActivity.this, MoodHistoryActivity.class);
                Intent myIntent = new Intent(HomeActivity.this, ProfileViewActivity.class);
                startActivity(myIntent);
            }
        });

        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get username of myself
                String myId = FirebaseAuth.getInstance().getUid();

                // get username of the person trying to follow

                FirebaseFirestore.getInstance().collection("users").whereEqualTo("username", followUsernameText.getText().toString()).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                        if (doc.exists()) {
                                            Log.d("HOME", "DocumentSnapshot data: " + doc.getId());
                                            // put in a hashmap
                                            Map<String, Object> followMap = new HashMap<>();
                                            followMap.put("follower_id", myId);
                                            followMap.put("following_id", doc.getId());

                                            // store in db
                                            FirebaseFirestore.getInstance().collection("follow").document(UUID.randomUUID().toString())
                                                    .set(followMap)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d("Home", "DocumentSnapshot successfully written!");

                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.w("Home", "Error writing document", e);
                                                        }
                                                    });
                                        } else {
                                            Log.d("HOME", "No such document");
                                        }
                                    }
                                } else{
                                    Log.d("HOME", "get failed with ", task.getException());
                                }
                            }
                        });


            }
        });



    }
}
