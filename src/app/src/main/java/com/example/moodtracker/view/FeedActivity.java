/*
 * FeedActivity
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.net.Uri;
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
import com.example.moodtracker.adapter.FeedAdapter;
import com.example.moodtracker.adapter.FollowAdapter;
import com.example.moodtracker.helpers.BottomNavigationViewHelper;
import com.example.moodtracker.helpers.FirebaseHelper;
import com.example.moodtracker.helpers.MoodHistoryHelpers;
import com.example.moodtracker.model.MoodEvent;
import com.example.moodtracker.model.MoodHistory;
import com.example.moodtracker.model.User;
import com.example.moodtracker.view.fragment.MoodEventFragment;
import com.example.moodtracker.view.mood.MoodHistoryActivity;
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
import java.util.Collection;
import java.util.Collections;

public class FeedActivity extends AppCompatActivity implements MoodEventFragment.OnFragmentInteractionListener {

    // Declare the variables so that you will be able to reference it later.
    ListView feedListView;
    FeedAdapter feedAdapter;
    ArrayList<MoodEvent> feedDataList;

    // new class vars
    String TAG = "Sample";
    Button addCityButton;
    EditText addCityEditText;
    EditText addProvinceEditText;
    FirebaseFirestore db;
    /**
     * On create of following activity
     * @param savedInstanceState the instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        //add Navigation bar functionality
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        BottomNavigationViewHelper.enableNavigation(FeedActivity.this, bottomNavigationView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Feed");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });


        feedListView = findViewById(R.id.list_user_following2);
        feedDataList = new ArrayList<>();
        feedAdapter = new FeedAdapter(this, feedDataList);
        feedListView.setAdapter(feedAdapter);

        db = FirebaseFirestore.getInstance();
        FirebaseHelper.updateFeed(db, FirebaseAuth.getInstance().getUid(), feedDataList, feedAdapter, new FirebaseHelper.FirebaseCallback() {
            @Override
            public void onSuccess(Object document) {
                System.out.println("Done getting feed");
            }

            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        // query list of people the current user is following
        // by looking at all instances where the follower is the current person

        feedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                System.out.println("CLICK ON ME:" + position);
                MoodEvent clicked_user = feedDataList.get(position);
                openFragment(clicked_user, position);
            }
        });

         SwipeRefreshLayout pulltoRefresh= findViewById(R.id.pullToRefresh);
         pulltoRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
             @Override
             public void onRefresh() {
                 FirebaseHelper.updateFeed(db, FirebaseAuth.getInstance().getUid(), feedDataList, feedAdapter, new FirebaseHelper.FirebaseCallback() {
                     @Override
                     public void onSuccess(Object document) {
                         pulltoRefresh.setRefreshing(false);
                     }

                     @Override
                     public void onFailure(@NonNull Exception e) {
                         pulltoRefresh.setRefreshing(false);
                     }
                 });
             }
         });


    }

    private void openFragment(MoodEvent moodEvent, int position) {
        boolean location_changed = false;
        MoodEventFragment fragment = MoodEventFragment.newInstance(moodEvent, position, location_changed);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.addToBackStack(null);
        transaction.add(R.id.mood_event_frag_container, fragment, "MOOD_EVENT_FRAGMENT").commit();
    }


    @Override
    public void onDeleteFragmentInteraction(int position) {

    }

    @Override
    public void onUpdateFragmentInteraction(MoodEvent e, int position, Uri photo, MoodHistory.FirebaseCallback cb) {

    }
}
