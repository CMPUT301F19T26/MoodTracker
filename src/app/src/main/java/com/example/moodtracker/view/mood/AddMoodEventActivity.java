package com.example.moodtracker.view.mood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.moodtracker.R;
import com.example.moodtracker.helpers.BottomNavigationViewHelper;
import com.example.moodtracker.view.FindActivity;
import com.example.moodtracker.view.MapActivity;
import com.example.moodtracker.view.ProfileFragment;
import com.example.moodtracker.view.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddMoodEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mood_event);


        //add Navigation bar functionality
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.ic_Profile:
                        Intent intent0 = new Intent(AddMoodEventActivity.this, ProfileFragment.class);
                        startActivity(intent0);
                        break;

                    case R.id.ic_Search:
                        Intent intent1 = new Intent(AddMoodEventActivity.this, FindActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_Add:

                        break;

                    case R.id.ic_Map:
//                        Intent intent3 = new Intent(AddMoodEventActivity.this, MapActivity.class);
//                        startActivity(intent3);
//                        break;

                    case R.id.ic_Feed:

                        break;

                }

                return false;
            }
        });
    }
}
