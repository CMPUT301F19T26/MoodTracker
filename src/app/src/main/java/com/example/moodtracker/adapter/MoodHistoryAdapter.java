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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MoodHistoryAdapter extends ArrayAdapter<MoodEvent> {

    private ArrayList<MoodEvent> history; // History
    private MoodHistory h;
    private Context context;
    private MoodHistoryAdapter adapter;


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
//        return super.getView(position, convertView, parent);
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content, parent,false);
        }

        MoodEvent event_item = history.get(position);
        TextView mood = view.findViewById(R.id.event_mood);
        TextView date = view.findViewById(R.id.event_date);
        mood.setText(event_item.getMood().getMoodName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date.setText((sdf.format(event_item.getDate())));
//        City city = cities.get(position);
//
//        final TextView cityName = view.findViewById(R.id.city_text);
//        TextView provinceName = view.findViewById(R.id.province_text);
//
//        cityName.setText(city.getCityName());
//        provinceName.setText(city.getProvinceName());
//
        Button delete_btn = view.findViewById(R.id.delete_item);


        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
//                history.remove(position);
//                notifyDataSetChanged();
                MoodHistoryController.deleteEventFromHistory(history.get(position), h, position, adapter);
//                FirebaseFirestore db = MainActivity.getInstance().firebaseInstance();
//                db.collection("Cities").document(cityName.getText().toString())
//                        .delete()
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Log.d("Success", "DocumentSnapshot successfully deleted!");
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.w("FAIL", "Error deleting document", e);
//                            }
//                        });

            }
        });

        return view;


    }
}
