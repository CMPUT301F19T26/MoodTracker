package com.example.moodtracker.view.mood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.moodtracker.R;
import com.example.moodtracker.constants;
import com.example.moodtracker.model.MoodEvent;
import com.example.moodtracker.model.MoodHistory;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import android.view.Menu;
import android.view.MenuItem;

import com.example.moodtracker.R;
import com.example.moodtracker.helpers.BottomNavigationViewHelper;
import com.example.moodtracker.view.FindActivity;
import com.example.moodtracker.view.MapActivity;
import com.example.moodtracker.view.ProfileFragment;
import com.example.moodtracker.view.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AddMoodEventActivity extends AppCompatActivity {
    private String user_id = FirebaseAuth.getInstance().getUid();

    // Logic Mappers
    HashMap<String, String> mood_name_to_num_mapper = constants.mood_name_to_num_mapper;

    // Front end Views
    ArrayAdapter<String> adapt;
    ArrayAdapter<String> social_adapt;
    Spinner mood_dropdown;
    Spinner social_situation_dropdown;
    Button submit_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mood_event);

        mood_dropdown = findViewById(R.id.mood_type_selector);
        // Dynamically create the moods list
        String[] mood_items = Arrays.copyOf(mood_name_to_num_mapper.keySet().toArray(), mood_name_to_num_mapper.keySet().toArray().length, String[].class);
        adapt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mood_items);
        mood_dropdown.setAdapter(adapt);

        social_situation_dropdown = findViewById(R.id.social_sitation_selector);
        final String[] social_items = new String[]{"None","Alone", "One Other", "Two Others", "Several", "Crowd"};
        social_adapt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, social_items);
        social_situation_dropdown.setAdapter(social_adapt);

        // Submission handling
        submit_btn = findViewById(R.id.add_mood_event_submit);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText reason_view = findViewById(R.id.reason_edit);
                EditText latitude = findViewById(R.id.latitude);
                EditText longitude = findViewById(R.id.longitude);

                SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat(constants.date_format);
                String now = ISO_8601_FORMAT.format(new Date());
                String mood = mood_name_to_num_mapper.get(mood_dropdown.getSelectedItem().toString());
                String reason = reason_view.getText().toString();
                String situation = social_situation_dropdown.getSelectedItem().toString();
                String lat = latitude.getText().toString();
                String lng = longitude.getText().toString();
                // Build the mood event item
                MoodEvent new_item = buildMoodEventfromUserInput(mood, user_id, now, lat, lng, reason, situation);
                MoodHistory.externalAddMoodEvent(new_item, new MoodHistory.FirebaseCallback<Void>() {
                    @Override
                    public void onSuccess(Void document) {
                        finish();
                    }

                    @Override
                    public void onFailure(@NonNull Exception e) {}
                });
            }
        });

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

    private static  MoodEvent buildMoodEventfromUserInput(String mood, String user_id, String now, String lat, String lng, String reason, String social_situation) {
        // TODO fix this logic, needs reason rn
        MoodEvent new_item = new MoodEvent(mood, user_id, now);
        if (!(reason.equals(""))) {
            new_item.setReason(reason);
        }
        if (!(social_situation.equals("None"))) {
            new_item.setSocial_situation(social_situation);
        }
        if (!(lat.equals("") && !lng.equals(""))) {
            Double lat_value = Double.parseDouble(lat);
            Double lng_value = Double.parseDouble(lng);
            new_item.setLat(lat_value);
            new_item.setLng(lng_value);
        }
        return new_item;
    }
}
