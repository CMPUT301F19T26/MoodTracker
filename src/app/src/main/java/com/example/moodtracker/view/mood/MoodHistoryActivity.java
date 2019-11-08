package com.example.moodtracker.view.mood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
        moodHistory = new MoodHistory(user_id);
        // Get the ListView to assign the new data to
        moodHistoryList = findViewById(R.id.mood_history);
        // Point the list towards the data
        HistoryAdapter = new MoodHistoryAdapter(this,  moodHistory);
        moodHistoryList.setAdapter(HistoryAdapter);
        MoodHistory.getMoodHistory(HistoryAdapter, moodHistory);

        Toast.makeText(MoodHistoryActivity.this, FirebaseAuth.getInstance().getCurrentUser().getEmail().toString(), Toast.LENGTH_LONG).show();

        // Handler for the add button
        addMoodEventBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add a Mock Activity for now
//                String a = user_id;
//                Date b = new Date();
//                String mood_num = Integer.toString(constants.HAPPY);
//                MoodEvent mock_2 = new MoodEvent("1", a, b);
                // Go to the add activity and recieve a result from the add activity
                Intent addIntent = new Intent(MoodHistoryActivity.this, AddMoodEventActivity.class);
                setResult(RESULT_OK, addIntent);
                startActivityForResult(addIntent, 1);
                // Item, History, Adapter
//                MoodHistoryController.addEventToHistory(mock_2, moodHistory, HistoryAdapter);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) { // We wanted to add a Mood Event
            // Make sure the request was successful
            System.out.println("OUTPUT");
//            MoodEvent event = data.getSerializableExtra("new_mood_event");
        }
    }
}
