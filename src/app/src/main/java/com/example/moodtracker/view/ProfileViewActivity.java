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
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

//import com.example.moodtracker.view.AddMoodEvent;
import com.example.moodtracker.R;
import com.example.moodtracker.helpers.BottomNavigationViewHelper;
import com.example.moodtracker.model.User;
import com.example.moodtracker.view.fragment.ProfileViewFragment;
import com.example.moodtracker.view.mood.MoodHistoryActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

/**
 * @auhtor CMPUT301F19T26
 * ProfileViewActivity extends AppCompactActivity
 * it overwrites onCreateView
 */

public class ProfileViewActivity extends AppCompatActivity implements ProfileViewFragment.OnFragmentInteractionListener {

    private MaterialButton LogoutFab;
    private FirebaseAuth fAuth;


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
        // Logout Handler
        LogoutFab = findViewById(R.id.logoutFAB);
        LogoutFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent mainIntent = new Intent(ProfileViewActivity.this, LoginActivity.class);
                startActivity(mainIntent);
            }
        });


        // Displays all parts of the fragment in the view
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        if(username != null) {
            FirebaseFirestore.getInstance().collection("users").whereEqualTo("username", username).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot doc : task.getResult()) {
                        displayFragments(doc.getId(), username);
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
                }

                @Override
                public void onError() {
                }
            });

        }

        //add Navigation bar functionality
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        BottomNavigationViewHelper.enableNavigation(ProfileViewActivity.this, bottomNavigationView);


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


