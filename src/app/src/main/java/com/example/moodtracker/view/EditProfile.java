/*
 * EditProfile
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