package com.example.moodtracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.moodtracker.R;
import com.example.moodtracker.model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, MapActivity.class);

        User jared = new User("234");
        intent.putExtra("USER", jared);
        intent.putExtra("MODE", 1);

        startActivity(intent);
    }
}
