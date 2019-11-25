package com.example.moodtracker.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.moodtracker.R;
import com.example.moodtracker.controller.MoodHistoryController;
import com.example.moodtracker.model.MoodEvent;
import com.example.moodtracker.model.MoodHistory;
import com.example.moodtracker.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FeedAdapter extends ArrayAdapter<MoodEvent> {

    private ArrayList<MoodEvent> events;
    private Context context;

    public FeedAdapter(Context context, ArrayList<MoodEvent> events){
        super(context,0, events);
        this.events = events;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content, parent,false);
        }

        // Get a handle on the moodEvent and set up the view for it
        MoodEvent event_item = events.get(position);
        MoodHistoryAdapter.setUpMoodEvent(event_item, view);

        // Handle deletion for the given moodEvent
        Button delete_btn = view.findViewById(R.id.delete_item);
        if (!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(event_item.getUser_id())) {
            delete_btn.setVisibility(View.GONE);
        }
        return view;

    }

}

