/**
 * ProfileActivity
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

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

//import com.example.moodtracker.view.AddMoodEvent;
import com.example.moodtracker.R;
import com.example.moodtracker.helpers.BottomNavigationViewHelper;
import com.example.moodtracker.view.mood.MoodHistoryActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

/**
 * @auhtor CMPUT301F19T26
 * ProfileViewActivity extends AppCompactActivity
 * it overwrites onCreateView
 */

public class ProfileViewActivity extends AppCompatActivity {
    private FloatingActionButton EditFab;
    private MaterialButton LogoutFab;
    private FloatingActionButton AddMoodFab;
    private FirebaseAuth fAuth;
    private Button FollowersButton;
    private Button FollowingButton;
    private Button MoodEventButton;
    private Button MoodHistoryButton;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain); //here toolbar is your id in xml
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MoodTracker"); //string is custom name you want
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Gets Current User
        fAuth = FirebaseAuth.getInstance();
//        final View view = inflater.inflate(R.layout.activity_profile_view, container, false);
//        FirebaseStorage storage = FirebaseStorage.getInstance();

        // displays username
        TextView ProfileName = (TextView) findViewById(R.id.userNameFragmentProfile);
        ProfileName.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        EditFab = findViewById(R.id.editFAB);
        EditFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent editActivity = new Intent(ProfileViewActivity.this, EditProfile.class);
                startActivity(editActivity);

//
//                Intent editActivity = new Intent(ProfileViewActivity.this, EditProfile.class);
//                editActivity.putExtra("userID", fAuth.getCurrentUser().getUid());
//                editActivity.putExtra("username", ProfileName.getText());
//                startActivity(editActivity);

            }
        });

        LogoutFab = findViewById(R.id.logoutFAB);
        LogoutFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent mainIntent = new Intent(ProfileViewActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });



//        AddMoodFab = findViewById(R.id.AddMoodFab);
//        AddMoodFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent addActivity = new Intent(ProfileViewActivity.this, AddMoodEvent.class);
//                startActivity(addActivity);
//            }
//        });

        FollowersButton = findViewById(R.id.FollowersButton);
        FollowersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent followersActivity = new Intent(getActivity(), Followers.class);
//                getActivity().startActivity(followersActivity);
            }
        });

        FollowingButton = findViewById(R.id.FollowingButton);
        FollowingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent followersActivity = new Intent(getActivity(), Following.class);
//                getActivity().startActivity(followersActivity);
            }
        });

        MoodHistoryButton = findViewById(R.id.MoodHistoryButton);
        MoodHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moodHistoryIntent = new Intent(ProfileViewActivity.this, MoodHistoryActivity.class);
                moodHistoryIntent.putExtra("userID", fAuth.getCurrentUser().getUid());
                startActivity(moodHistoryIntent);
            }
        });
//        MoodEventButton = findViewById(R.id.MoodEventButton);
//        MoodEventButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent moodHistoryIntent = new Intent(ProfileViewActivity.this, MoodHistoryActivity.class);
//                moodHistoryIntent.putExtra("userID", fAuth.getCurrentUser().getUid());
//                startActivity(moodHistoryIntent);
//            }
//        });

        //add Navigation bar functionality
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        BottomNavigationViewHelper.enableNavigation(ProfileViewActivity.this, bottomNavigationView);


    }
}





        //Sets the name
//        ProfileName.setText(fAuth.getCurrentUser().getDisplayName());

//        //Gets the user's profile picture
//        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Log.e("Tuts+", "uri: " + uri.toString());
//                DownloadLink = uri.toString();
//                CircleImageView iv = (CircleImageView) view.findViewById(R.id.profilePictureEditFragment);
//                Picasso.with(getContext()).load(uri.toString()).placeholder(R.drawable.ic_launcher3slanted).error(R.drawable.ic_launcher3slanted).into(iv);
//                //Handle whatever you're going to do with the URL here
//            }
//        });

//    }


