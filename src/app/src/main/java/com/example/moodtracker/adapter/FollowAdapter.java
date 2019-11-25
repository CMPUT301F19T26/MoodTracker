package com.example.moodtracker.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.moodtracker.R;
import com.example.moodtracker.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FollowAdapter extends ArrayAdapter<User> {

    private ArrayList<User> users;
    private Context context;

    public FollowAdapter(Context context, ArrayList<User> users){
        super(context,0, users);
        this.users = users;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content_user, parent,false);
        }

        User user = users.get(position);

        final TextView usernameText = view.findViewById(R.id.list_username_text);
        TextView uidText = view.findViewById(R.id.list_uid_text);

        user.getUsername(new User.UsernameListener() {
            @Override
            public void onRetrieve(String username) {
                usernameText.setText(username);
            }

            @Override
            public void onError() {
            }
        });

        uidText.setText(user.getUid());


        return view;

    }

}
