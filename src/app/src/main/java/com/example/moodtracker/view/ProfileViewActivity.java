/*
 * ProfileViewActivity
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

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

//import com.example.moodtracker.view.AddMoodEvent;
import com.example.moodtracker.R;
import com.example.moodtracker.adapter.MoodHistoryAdapter;
import com.example.moodtracker.constants;
import com.example.moodtracker.controller.MoodHistoryController;
import com.example.moodtracker.helpers.BottomNavigationViewHelper;
import com.example.moodtracker.model.MoodEvent;
import com.example.moodtracker.model.MoodHistory;
import com.example.moodtracker.model.User;
import com.example.moodtracker.view.fragment.MoodEventFragment;
import com.example.moodtracker.view.fragment.ProfileViewFragment;
import com.example.moodtracker.view.mood.MoodHistoryActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;

/**
 * @auhtor CMPUT301F19T26
 * ProfileViewActivity extends AppCompactActivity
 * it overwrites onCreateView
 */
public class ProfileViewActivity extends AppCompatActivity implements ProfileViewFragment.OnFragmentInteractionListener, MoodEventFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {

    private MaterialButton LogoutFab;
    private FirebaseAuth fAuth;
    private ListView moodHistoryList;
    private ArrayAdapter<MoodEvent> HistoryAdapter;
    private MoodHistory moodHistory;
    private String previously_selected = "All";
    private Spinner mood_history_spinner;
    private ArrayAdapter<String> adapt;
    private DrawerLayout drawer;
    private ImageButton toggler;
    private ImageView lockImage;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        Context context = this;
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Gets Current User
        fAuth = FirebaseAuth.getInstance();
//        final View view = inflater.inflate(R.layout.activity_profile_view, container, false);
//        FirebaseStorage storage = FirebaseStorage.getInstance();

        moodHistoryList = findViewById(R.id.mood_history);
        lockImage = findViewById(R.id.profile_lock_image);

        // Displays all parts of the fragment in the view
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain); //here toolbar is your id in xml
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MoodTracker"); //string is custom name you want


        drawer = findViewById(R.id.drawer_layout);
        toggler = findViewById(R.id.toggler);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(username != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        if (username != null) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toggler.setVisibility(View.GONE);
            FirebaseFirestore.getInstance().collection("users").whereEqualTo("username", username).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot doc : task.getResult()) {
                        displayFragments(doc.getId(), username);

                        User myUser = new User(fAuth.getUid());
                        myUser.getFollowingUsernames(new User.UsernamesListener() {
                            @Override
                            public void onRetrieve(ArrayList<String> usernames) {
                                if(usernames.contains(doc.getId())) {
                                    moodHistory = new MoodHistory(doc.getId());
                                    HistoryAdapter = new MoodHistoryAdapter(context,  moodHistory);
                                    moodHistoryList.setAdapter(HistoryAdapter);
                                    MoodHistory.getMoodHistory(HistoryAdapter, moodHistory);
                                } else {
                                    lockImage.setVisibility(View.VISIBLE);
                                }
                            }
                            @Override
                            public void onError() {

                            }
                        });
                    }
                }
            });
        } else {

            String uid = fAuth.getUid();
            User displayUser = new User(uid);

            displayUser.getUsername(new User.UsernameListener() {
                @Override
                public void onRetrieve(String username) {
                    displayFragments(uid, username);
                    moodHistory = new MoodHistory(uid);
                    HistoryAdapter = new MoodHistoryAdapter(context, moodHistory);
                    moodHistoryList.setAdapter(HistoryAdapter);
                    MoodHistory.getMoodHistory(HistoryAdapter, moodHistory);
                }

                @Override
                public void onError() {

                }
            });

        }

        // Handler for view/edit of a Mood Event
        moodHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Will call open fragment and pass it a mood event
                MoodEvent clicked_event = moodHistory.history.get(position);
                openFragment(clicked_event, position);
            }
        });

        toggler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.END);
            }
        });

        mood_history_spinner = findViewById(R.id.mood_history_spinner);
        String[] mood_items = constants.mood_spinner_list;
        adapt = new ArrayAdapter<>(this, R.layout.centered_spinner_item, mood_items);
        mood_history_spinner.setAdapter(adapt);
        mood_history_spinner.setSelection(0);

        // Onclick handler for filtering
        mood_history_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(mood_history_spinner.getSelectedItem().toString()) {
                    case "All":
                        if (!previously_selected.equals( mood_history_spinner.getSelectedItem().toString())) {
                            previously_selected =  mood_history_spinner.getSelectedItem().toString();
                            moodHistory.history.clear();
                            HistoryAdapter.notifyDataSetChanged();
                            MoodHistory.getMoodHistory(HistoryAdapter, moodHistory);
                        }
                        break;
                    default:
                        if (!previously_selected.equals( mood_history_spinner.getSelectedItem().toString())) {
                            handleSpinner(mood_history_spinner.getSelectedItem().toString());
                        }
                        break;
                }
            }

            private void handleSpinner(String mood) {
                previously_selected =  mood_history_spinner.getSelectedItem().toString();
                moodHistory.history.clear();
                HistoryAdapter.notifyDataSetChanged();
                MoodHistory.getMoodHistoryWithFilter(HistoryAdapter, moodHistory, "mood", constants.mood_name_to_num_mapper.get(mood));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //add Navigation bar functionality
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        BottomNavigationViewHelper.enableNavigation(ProfileViewActivity.this, bottomNavigationView);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (moodHistory != null) {
            moodHistory.history.clear();
            HistoryAdapter.notifyDataSetChanged();
            MoodHistory.getMoodHistory(HistoryAdapter, moodHistory);
        }
    }

    public void openFragment(MoodEvent moodEvent, int position) {
        boolean location_changed = false;
        MoodEventFragment fragment = MoodEventFragment.newInstance(moodEvent, position, location_changed);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.addToBackStack(null);
        transaction.add(R.id.mood_event_frag_container, fragment, "MOOD_EVENT_FRAGMENT").commit();
    }

    private void displayFragments(String user_id, String username) {
        ProfileViewFragment profile_frag = new ProfileViewFragment(user_id, username);
        FragmentManager manager = getSupportFragmentManager();//create an instance of fragment manager
        FragmentTransaction transaction = manager.beginTransaction();//create an instance of Fragment-transaction
        transaction.add(R.id.profile_container, profile_frag, "PROFILE_FRAG");
        transaction.commit();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onDeleteFragmentInteraction(int position) {
        MoodHistoryController.deleteEventFromHistory(moodHistory.history.get(position), moodHistory, position, HistoryAdapter);
    }

    @Override
    public void onUpdateFragmentInteraction(MoodEvent e, int position, Uri photo, final MoodHistory.FirebaseCallback cb) {
        // Send this new MoodEvent to the db and update the fields
        MoodHistory.externalUpdateMoodEvent(e, position, photo, moodHistory, HistoryAdapter, cb);
    }

    @Override
    public void onBackPressed() {
        final MoodEventFragment fragment = (MoodEventFragment) getSupportFragmentManager().findFragmentByTag("MOOD_EVENT_FRAGMENT");
        if (fragment!= null && fragment.allowBackPress()) { // and then you define a method allowBackPressed with the logic to allow back pressed or not
            super.onBackPressed();
        } else if(drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else if (fragment == null) {
            finish();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_logout:
                // Do some shit
                FirebaseAuth.getInstance().signOut();
                Intent mainIntent = new Intent(ProfileViewActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                break;
            case R.id.nav_preferences:
                break;
            case R.id.nav_requests:
                Intent requestsActivity = new Intent(ProfileViewActivity.this, RequestsActivity.class);
                startActivity(requestsActivity);
                break;
            default:
                break;
        }
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }
}


