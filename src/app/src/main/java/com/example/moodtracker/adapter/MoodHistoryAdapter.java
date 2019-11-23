/**
 * MoodHistoryAdapter
 *
 * Version 1.0
 *
 * 11/8/2019
 *
 * Copyright (c) $today.year. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 *
 */

package com.example.moodtracker.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.moodtracker.R;
import com.example.moodtracker.constants;
import com.example.moodtracker.controller.MoodHistoryController;
import com.example.moodtracker.helpers.MoodHistoryHelpers;
import com.example.moodtracker.model.Mood;
import com.example.moodtracker.model.MoodEvent;
import com.example.moodtracker.model.MoodHistory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MoodHistoryAdapter extends ArrayAdapter<MoodEvent> {
    private ArrayList<MoodEvent> history;
    private MoodHistory h;
    private Context context;
    private MoodHistoryAdapter adapter;
    private HashMap<String, Mood> mood_num_to_mood_obj_map = constants.mood_num_to_mood_obj_mapper;

    /**
     * Create adapter for Mood History
     * @param context the context of mood history
     * @param h the mood history
     */
    public MoodHistoryAdapter(Context context, MoodHistory h) {
        super(context, 0, h.history);
        this.history = h.history;
        this.h = h;
        this.context = context;
        this.adapter = this;
    }

    /**
     * Obtain the view of Mood History
     * @param position position in array
     * @param convertView converted view
     * @param parent parent of view
     * @return view
     */
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content, parent,false);
        }
        // Get a handle on the moodEvent and set up the view for it
        MoodEvent event_item = history.get(position);
        setUpMoodEvent(event_item, view);

        // Handle deletion for the given moodEvent
        Button delete_btn = view.findViewById(R.id.delete_item);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                MoodHistoryController.deleteEventFromHistory(history.get(position), h, position, adapter);
            }
        });

        return view;


    }

    private void setUpMoodEvent(MoodEvent event_item, View view) {
        Mood mood_obj = mood_num_to_mood_obj_map.get(event_item.getMood()); // The Mood Object this event refers to

        // MoodEvent Views
        CardView mood_event_item = view.findViewById(R.id.mood_event_item);
        TextView mood = view.findViewById(R.id.event_mood);
        TextView date = view.findViewById(R.id.event_date);
        ImageView icon = view.findViewById(R.id.icon_image);
        ImageView photo = view.findViewById(R.id.me_photo);
        TextView reason = view.findViewById(R.id.reason);
        TextView social = view.findViewById(R.id.social_situation);
        handlePhotos(photo, event_item);

        if (event_item.getReason()!= null) {
            reason.setText(event_item.getReason());
        }
        if (event_item.getSocialSituation()!= null) {
            social.setText(event_item.getSocialSituation());
        }
        mood_event_item.setCardBackgroundColor(Color.parseColor(mood_obj.getColor()));
        mood.setText(mood_obj.getMoodName());
        icon.setImageResource(mood_obj.getIcon());

        String formatted_date = MoodHistoryHelpers.formatDate(event_item.getDate());
        date.setText(formatted_date);
    }

    private void handlePhotos(ImageView photoView, MoodEvent event_item) {
        photoView.setImageResource(0);
        photoView.setVisibility(View.GONE);
        if (event_item.getPhoto_url()!= null) {
            // get the image and put it into the view
            Picasso.get().load(event_item.getPhoto_url()).into(photoView);
            photoView.setVisibility(View.VISIBLE);
        }
    }
}
