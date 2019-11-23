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
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class MoodHistoryActivity extends AppCompatActivity implements MoodEventFragment.OnFragmentInteractionListener {
    private ListView moodHistoryList;
    private ArrayAdapter<MoodEvent> HistoryAdapter;
    private MoodHistory moodHistory;
    private ArrayList<MoodEvent> all_list;
    private String user_id;
    private androidx.appcompat.widget.Toolbar toolbar;
    private String previously_selected = "All";
    private Spinner mood_history_spinner;
    private ArrayAdapter<String> adapt;
    private HashMap<String, String> mood_name_to_num_mapper = constants.mood_name_to_num_mapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_history);
        // Get the user's ID
        Intent intent = getIntent();
        user_id = intent.getStringExtra("userID");
        // Buttons
        // Create a new MoodHistory class that gets the mood history for the given user
        moodHistory = new MoodHistory(user_id);
        // Get the ListView to assign the new data to
        moodHistoryList = findViewById(R.id.mood_history);
        // Point the list towards the data
        HistoryAdapter = new MoodHistoryAdapter(this,  moodHistory);
        moodHistoryList.setAdapter(HistoryAdapter);
        MoodHistory.getMoodHistory(HistoryAdapter, moodHistory);

        // Handler for view/edit of a Mood Event
        moodHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Will call open fragment and pass it a mood event
                MoodEvent clicked_event = moodHistory.history.get(position);
                openFragment(clicked_event, position);
            }
        });

        // Building the spinner filter for the mood history list
        mood_history_spinner = findViewById(R.id.mood_history_spinner);
        String[] mood_items = constants.mood_spinner_list;
        adapt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mood_items);
        mood_history_spinner.setAdapter(adapt);
        mood_history_spinner.setSelection(0);

        // Onclick handler for filtering
        mood_history_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(mood_history_spinner.getSelectedItem().toString()) {
                    case "All":
                        if (!previously_selected.equals( mood_history_spinner.getSelectedItem().toString())) {
                            previously_selected =  mood_history_spinner.getSelectedItem().toString();
                            moodHistory.history.clear();
                            HistoryAdapter.notifyDataSetChanged();
                            MoodHistory.getMoodHistory(HistoryAdapter, moodHistory);
                        }
                        break;
                     default:
                        if (!previously_selected.equals( mood_history_spinner.getSelectedItem().toString())) {
                            handleSpinner(mood_history_spinner.getSelectedItem().toString());
                        }
                        break;
                }
            }

            private void handleSpinner(String mood) {
                previously_selected =  mood_history_spinner.getSelectedItem().toString();
                moodHistory.history.clear();
                HistoryAdapter.notifyDataSetChanged();
                MoodHistory.getMoodHistoryWithFilter(HistoryAdapter, moodHistory, "mood", constants.mood_name_to_num_mapper.get(mood));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        toolbar = findViewById(R.id.mood_history_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
