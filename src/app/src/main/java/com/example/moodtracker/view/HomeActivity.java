package com.example.moodtracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.moodtracker.R;
import com.example.moodtracker.view.mood.MoodHistoryActivity;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    Button logoutBtn;
    TextView usernameText;
    Button gotoMoodHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logoutBtn = findViewById(R.id.button_logout);
        usernameText = findViewById(R.id.text_email);
        gotoMoodHistory = findViewById(R.id.button_moodhistory);

        usernameText.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent mainIntent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(mainIntent);

            }
        });

        gotoMoodHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(HomeActivity.this, MoodHistoryActivity.class);
                startActivity(myIntent);
            }
        });

    }
}
