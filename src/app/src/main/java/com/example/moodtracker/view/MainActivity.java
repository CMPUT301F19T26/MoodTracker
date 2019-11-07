package com.example.moodtracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.moodtracker.R;
import com.google.firebase.auth.FirebaseAuth;
public class MainActivity extends AppCompatActivity {

    FirebaseAuth fb_auth;
    Button loginBtn;
    Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        loginBtn = findViewById(R.id.button_login);
        signupBtn = findViewById(R.id.button_signup);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(signupIntent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        fb_auth = FirebaseAuth.getInstance();
        if (fb_auth.getCurrentUser() != null) {
            Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(homeIntent);
        } else {
            Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_LONG).show();
        }
    }
}
