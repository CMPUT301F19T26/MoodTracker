package com.example.moodtracker.view.mood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.moodtracker.R;

public class AddMoodEventActivity extends AppCompatActivity {
    ArrayAdapter<String> adapt;
    Spinner mood_dropdown;
    Button submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mood_event);

        submit_btn = findViewById(R.id.add_mood_event_submit);
        // Neutral happy surprised
        //get the spinner from the xml.
        mood_dropdown = findViewById(R.id.mood_type_selector);
        //create a list of items for the spinner.
        String[] items = new String[]{"Happy", "Neutral", "Surprised"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        adapt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        mood_dropdown.setAdapter(adapt);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
