/*
 * FollowAdapter
 *
 * Version 1.0
 *
 * 11/8/2019
 *
 * MIT License
 *
 * Copyright (c) 2019 CMPUT301F19T26
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
import com.example.moodtracker.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Adapter for follows list
 */
public class RequestAdapter extends ArrayAdapter<User> {

    private ArrayList<User> users;
    private Context context;

    /**
     * Constructor
     * @param context Context for Adapter
     * @param users users followed
     */
    public RequestAdapter(Context context, ArrayList<User> users){
        super(context,0, users);
        this.users = users;
        this.context = context;
    }


    /**
     * Gets view
     * @param position position in view
     * @param convertView convert view
     * @param parent parent
     * @return view
     */
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content_requests, parent,false);
        }

        User user = users.get(position);

        TextView usernameText = view.findViewById(R.id.list_username_text_request);
        TextView uidText = view.findViewById(R.id.list_uid_text_request);

        user.getUsername(new User.UsernameListener() {
            /**
             * set username text
             * @param username username to set
             */
            @Override
            public void onRetrieve(String username) {
                usernameText.setText(username);
            }

            /**
             * on error set empty
             */
            @Override
            public void onError() {
                usernameText.setText("");
            }
        });

        uidText.setText(user.getUid());


        FirebaseAuth fAuth = FirebaseAuth.getInstance();


        Button FollowAllButton = view.findViewById(R.id.follow_all_button);
        FollowAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> followMap = new HashMap<>();
                followMap.put("follower_id", user.getUid());
                followMap.put("following_id", fAuth.getUid());
                followMap.put("type", "all");

                // store a request for that person in db from authenticated person
                FirebaseFirestore.getInstance().collection("follow").document(UUID.randomUUID().toString())
                        .set(followMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("HOME", "DocumentSnapshot successfully written!");

                                FirebaseFirestore.getInstance().collection("requests")
                                        .whereEqualTo("follower_id", user.getUid())
                                        .whereEqualTo("following_id", fAuth.getUid())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for(DocumentSnapshot doc : task.getResult()) {
                                                    FirebaseFirestore.getInstance().collection("requests").document(doc.getId()).delete();
                                                }
                                                users.remove(position);
                                                notifyDataSetChanged();
                                            }
                                        });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("HOME", "Error writing document", e);
                            }
                        });

            }
        });


        Button FollowRecentButton = view.findViewById(R.id.follow_recent_button);
        FollowRecentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> followMap = new HashMap<>();
                followMap.put("follower_id", user.getUid());
                followMap.put("following_id", fAuth.getUid());
                followMap.put("type", "recent");

                FirebaseFirestore.getInstance().collection("follow").document(UUID.randomUUID().toString())
                        .set(followMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("HOME", "DocumentSnapshot successfully written!");

                                FirebaseFirestore.getInstance().collection("requests")
                                        .whereEqualTo("follower_id", user.getUid())
                                        .whereEqualTo("following_id", fAuth.getUid())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for(DocumentSnapshot doc : task.getResult()) {
                                                    FirebaseFirestore.getInstance().collection("requests").document(doc.getId()).delete();
                                                }
                                                users.remove(position);
                                                notifyDataSetChanged();
                                            }
                                        });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("HOME", "Error writing document", e);
                            }
                        });



            }
        });

        Button RejectButton = view.findViewById(R.id.reject_request_button);
        RejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseFirestore.getInstance().collection("requests")
                        .whereEqualTo("follower_id", user.getUid())
                        .whereEqualTo("following_id", fAuth.getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for(DocumentSnapshot doc : task.getResult()) {
                                    FirebaseFirestore.getInstance().collection("requests").document(doc.getId()).delete();
                                }
                                users.remove(position);
                                notifyDataSetChanged();
                            }
                        });


            }
        });



        return view;

    }

}
