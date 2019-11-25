/**
 * EditProfile
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import com.example.moodtracker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


public class EditProfile extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private String uid;
    private String username;

    private Toolbar toolbar;
    private TextView usernameTV;
    private Button saveButton;
    private FirebaseAuth fAuth;
    private DatabaseReference fDatabase;
    private AlertDialog.Builder passAuthenticate;
    private FirebaseUser user;

    /**
     * On Create
     *
     * @param savedInstanceState the instance
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        setContentView(R.layout.activity_edit_profile);
        Intent intent = getIntent();
        uid = intent.getStringExtra("userID");
        username = intent.getStringExtra("username");

        toolbar = findViewById(R.id.toolbar);
        usernameTV = findViewById(R.id.userNameTV);
        saveButton = findViewById(R.id.saveButton);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        usernameTV.setText(username);

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

    }

    /**
     * return false on menu item click
     *
     * @param item the item
     * @return false
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }


    /**
     * @param v Initializes new PopupMenu.
     *          This popup menu pops out when the user clicks on the image icon to edit their profile picture.
     *          The menu displays 2 options: 1.Take Photo(using camera) 2.Choose from Gallery
     * @see PopupMenu
     */

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.photo_menu);
        popup.show();

    }
}