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
import com.example.moodtracker.model.MoodEvent;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MoodHistoryAdapter extends ArrayAdapter<MoodEvent> {

    private ArrayList<MoodEvent> history; // History
    private Context context;

    public MoodHistoryAdapter(Context context, ArrayList<MoodEvent> h) {
        super(context, 0, h);
        this.history = h;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content, parent,false);
        }

//        City city = cities.get(position);
//
//        final TextView cityName = view.findViewById(R.id.city_text);
//        TextView provinceName = view.findViewById(R.id.province_text);
//
//        cityName.setText(city.getCityName());
//        provinceName.setText(city.getProvinceName());
//
//        Button delete_btn = view.findViewById(R.id.delete_item);


//        delete_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public  void onClick(View v) {
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
//
//            }
//        });

        return view;


    }
}
