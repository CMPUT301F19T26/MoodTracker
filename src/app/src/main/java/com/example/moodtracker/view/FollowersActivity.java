/**
 * Followers Activity
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
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
                                FollowersDataList.add(new User(followerId));
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }

                            FollowersAdapter.notifyDataSetChanged();

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

}
