package com.example.moodtracker;

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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}