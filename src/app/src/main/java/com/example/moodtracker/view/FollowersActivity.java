/*
 * FollowersActivity
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.example.moodtracker.R;
import com.example.moodtracker.adapter.FollowAdapter;
import com.example.moodtracker.helpers.BottomNavigationViewHelper;
import com.example.moodtracker.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Activity handling for followers
 */
public class FollowersActivity extends AppCompatActivity {

    // Declare the variables so that you will be able to reference it later.
    ListView FollowersListView;
    ArrayAdapter<User> FollowersAdapter;
    ArrayList<User> FollowersDataList;

    // new class vars
    String TAG = "Sample";
    Button addCityButton;
    EditText addCityEditText;
    EditText addProvinceEditText;
    FirebaseFirestore db;

    /**
     * On create of Followers activity
     * @param savedInstanceState the instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Followers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //add Navigation bar functionality
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        BottomNavigationViewHelper.enableNavigation(FollowersActivity.this, bottomNavigationView);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FollowersListView = findViewById(R.id.list_user_followers);
        FollowersDataList = new ArrayList<>();
        FollowersAdapter = new FollowAdapter(this, FollowersDataList);
        FollowersListView.setAdapter(FollowersAdapter);

        db = FirebaseFirestore.getInstance();

        // query list of people the current user is Followers
        // by looking at all instances where the follower is the current person
        db.collection("follow")
                .whereEqualTo("following_id", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            FollowersDataList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String followerId = document.get("follower_id").toString();
                                String followerName= document.get("follower_user").toString();
                                User u = new User(followerId);
                                u.setUserName(followerName);
                                FollowersDataList.add(u);
                                FollowersAdapter.notifyDataSetChanged();
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }

                            FollowersAdapter.notifyDataSetChanged();

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });



        FollowersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // returns the user that the item is clicked on
                // this will later be passed on to a profile view activity

                Intent feedIntent = new Intent(FollowersActivity.this, ProfileViewActivity.class);
                feedIntent.putExtra("username", FollowersDataList.get(position).regulargetUserName());
                startActivity(feedIntent);

//                                            Toast.makeText(FindActivity.this,list.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
