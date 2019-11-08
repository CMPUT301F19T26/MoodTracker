package com.example.moodtracker.view.mood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moodtracker.R;
import com.example.moodtracker.adapter.MoodHistoryAdapter;
import com.example.moodtracker.constants;
import com.example.moodtracker.controller.MoodEventController;
import com.example.moodtracker.controller.MoodHistoryController;
import com.example.moodtracker.model.Mood;
import com.example.moodtracker.model.MoodEvent;
import com.example.moodtracker.model.MoodHistory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;

import java.util.ArrayList;
import java.util.Date;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class MoodHistoryActivity extends AppCompatActivity {

    private ListView moodHistoryList;
    private ArrayAdapter<MoodEvent> HistoryAdapter;
    private MoodHistory moodHistory;
    private String user_id;

    // Button handler
    FloatingActionButton addMoodEventBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_history);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("userID");


        addMoodEventBTN = findViewById(R.id.add_mood_event);

        moodHistory = MoodHistoryController.getMoodHistory(user_id);
        // Get the ListView to assign the new data to
        moodHistoryList = findViewById(R.id.mood_history);
        // Point the list towards the data
        HistoryAdapter = new MoodHistoryAdapter(this,  moodHistory);
        moodHistoryList.setAdapter(HistoryAdapter);

        Toast.makeText(MoodHistoryActivity.this, FirebaseAuth.getInstance().getCurrentUser().getEmail().toString(), Toast.LENGTH_LONG).show();

        // Handler for the add button
        addMoodEventBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add a Mock Activity for now
                String a = user_id;
                Date b = new Date();
                Mood m = new Mood(constants.HAPPY);
                MoodEvent mock_2 = new MoodEvent(m, a, b);
//                moodHistory.history.add(mock_2);
//                HistoryAdapter.notifyDataSetChanged();
                // Go to the add activity and recieve a result from the add activity
                // Item, History, Adapter
                MoodHistoryController.addEventToHistory(mock_2, moodHistory, HistoryAdapter);
            }
        });
    }
}
