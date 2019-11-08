package com.example.moodtracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.moodtracker.R;
import com.example.moodtracker.model.User;
import com.example.moodtracker.view.mood.MoodHistoryActivity;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    Button logoutBtn;
    Button mapsBtn;

    TextView usernameText;
    Button gotoMoodHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logoutBtn = findViewById(R.id.button_logout);
        mapsBtn = findViewById(R.id.button_maps);

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

        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(HomeActivity.this, MapActivity.class);

                User Jared = new User("21");
                searchIntent.putExtra("USER", Jared);
                searchIntent.putExtra("MODE", 0);
                startActivity(searchIntent);

            }
        });

        gotoMoodHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent myIntent = new Intent(HomeActivity.this, MoodHistoryActivity.class);
            Intent myIntent = new Intent(HomeActivity.this, ProfileFragment.class);

                startActivity(myIntent);
            }
        });

    }
}
