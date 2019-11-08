/**
 * UserProfileActivity
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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodtracker.R;

public class UserProfileActivty extends AppCompatActivity {
    Button followButton;
    ImageView backButton;
    TextView userName;
    String userID;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Get's data pushed into this intent
        Intent intent = getIntent();
        userID = intent.getStringExtra("key");

        userName = findViewById(R.id.usernameTV);



        // Setting the buttons
        backButton = findViewById(R.id.backIV);
        followButton = findViewById(R.id.FollowFab);

            backButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This method is used to set an onClick listener on the back arrow button
             * @param view
             */
            @Override
            public void onClick(View view) {
                finish();
            }
        });


            followButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This method is used to set an onClick listener on follow button that follows
             * a user when clicked
             * @param view
             */
            @Override
            public void onClick(View view) {
//                Intent i = new Intent (getApplicationContext(), followActivity.class);
//                i.putExtra("key", userID);
//                startActivity(i);
            }
        });

}

}
