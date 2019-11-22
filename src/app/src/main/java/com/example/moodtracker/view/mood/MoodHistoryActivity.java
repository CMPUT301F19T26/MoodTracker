package com.example.moodtracker.view.mood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moodtracker.R;
import com.example.moodtracker.adapter.MoodHistoryAdapter;
import com.example.moodtracker.constants;
import com.example.moodtracker.controller.MoodEventController;
import com.example.moodtracker.controller.MoodHistoryController;
import com.example.moodtracker.helpers.FirebaseHelper;
import com.example.moodtracker.model.Mood;
import com.example.moodtracker.model.MoodEvent;
import com.example.moodtracker.model.MoodHistory;
import com.example.moodtracker.view.fragment.MoodEventFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class MoodHistoryActivity extends AppCompatActivity implements MoodEventFragment.OnFragmentInteractionListener {
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
        // Get the user's ID
        Intent intent = getIntent();
        user_id = intent.getStringExtra("userID");
        // Buttons
        addMoodEventBTN = findViewById(R.id.add_mood_event);
        // Create a new MoodHistory class that gets the mood history for the given user
        moodHistory = new MoodHistory(user_id);
        // Get the ListView to assign the new data to
        moodHistoryList = findViewById(R.id.mood_history);
        // Point the list towards the data
        HistoryAdapter = new MoodHistoryAdapter(this,  moodHistory);
        moodHistoryList.setAdapter(HistoryAdapter);
        MoodHistory.getMoodHistory(HistoryAdapter, moodHistory);

        Toast.makeText(MoodHistoryActivity.this, FirebaseAuth.getInstance().getCurrentUser().getEmail().toString(), Toast.LENGTH_LONG).show();

        // Handler for view/edit of a Mood Event
        moodHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Will call open fragment and pass it a mood event
                MoodEvent clicked_event = moodHistory.history.get(position);
                openFragment(clicked_event, position);
            }
        });

        // Handler for the add button
        addMoodEventBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MoodHistoryActivity.this, AddMoodEventActivity.class);
                setResult(RESULT_OK, addIntent);
                startActivityForResult(addIntent, 1);
            }
        });

    }

    private void openFragment(MoodEvent moodEvent, int position) {
        MoodEventFragment fragment = MoodEventFragment.newInstance(moodEvent, position);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.addToBackStack(null);
        transaction.add(R.id.mood_event_frag_container, fragment, "MOOD_EVENT_FRAGMENT").commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        System.out.println("We back");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == 1) { // We wanted to add a Mood Event
//             Make sure the request was successful
            moodHistory.history.clear();
            HistoryAdapter.notifyDataSetChanged();
            MoodHistory.getMoodHistory(HistoryAdapter, moodHistory);
        }
    }
}
