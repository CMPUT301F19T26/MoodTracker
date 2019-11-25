/**
 * FindActivity
 *
 * Version 1.0
 *
 * 11/8/2019
 *
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.moodtracker.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.nfc.Tag;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.example.moodtracker.R;
import com.example.moodtracker.helpers.BottomNavigationViewHelper;
import com.example.moodtracker.model.User;
import com.example.moodtracker.view.mood.AddMoodEventActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.moodtracker.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FindActivity extends AppCompatActivity {

    ListView userListView;
//    ArrayAdapter<User> userArrayAdapter;
//    ArrayList<User> userDataList;


    /**
     * On Create
     *
     * @param savedInstanceState the instance
     */

    EditText searchText;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //add Navigation bar functionality
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        BottomNavigationViewHelper.enableNavigation(FindActivity.this, bottomNavigationView);



        searchText = findViewById(R.id.search_bar);
        searchButton = findViewById(R.id.search_button);
        userListView = findViewById(R.id.result_list);
        // front end apiKey for searching
        Client client = new Client("GZMZW0XPIB", "c1b5e252ea14c337a01be1f3d9d1085e");
        Index index = client.getIndex("dev_USERNAMES");
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Query query = new Query(s.toString())
                                .setAttributesToRetrieve("username")
                                .setHitsPerPage(50);
                        index.searchAsync(query, new CompletionHandler() {
                            @Override
                            public void requestCompleted(@Nullable JSONObject jsonObject, @Nullable AlgoliaException e) {
                                try {
                                    JSONArray hits = jsonObject.getJSONArray("hits");
                                    List<String> list = new ArrayList<>();
                                    for(int i = 0; i < hits.length(); i++){
                                        JSONObject jsonObject1 = hits.getJSONObject(i);
                                        String username = jsonObject1.getString("username");
                                        list.add(username);
                                    }
                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(FindActivity.this, android.R.layout.simple_list_item_1,list);
                                    userListView.setAdapter(arrayAdapter);
                                    userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // returns the user that the item is clicked on
                                            // this will later be passed on to a profile view activity
                                            Toast.makeText(FindActivity.this,list.get(position), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }catch (JSONException e1){
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });

            }
        });

//        Button followBtn = findViewById(R.id.af_follow_button);
//        EditText followUsernameText = findViewById(R.id.af_username_follow);
//
//        followBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                // get username of myself
//                String myId = FirebaseAuth.getInstance().getUid();
//                // get username of the person trying to follow
//                FirebaseFirestore.getInstance().collection("users").whereEqualTo("username", followUsernameText.getText().toString()).get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (QueryDocumentSnapshot doc : task.getResult()) {
//                                        if (doc.exists()) {
//                                            Log.d("HOME", "DocumentSnapshot data: " + doc.getId());
//                                            // put in a hashmap
//                                            Map<String, Object> followMap = new HashMap<>();
//                                            followMap.put("follower_id", myId);
//                                            followMap.put("following_id", doc.getId());
//
//                                            // store in db
//                                            FirebaseFirestore.getInstance().collection("follow").document(UUID.randomUUID().toString())
//                                                    .set(followMap)
//                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                        @Override
//                                                        public void onSuccess(Void aVoid) {
//                                                            Toast.makeText(FindActivity.this, "Followed " + followUsernameText.getText().toString(), Toast.LENGTH_LONG).show();
//                                                            Log.d("HOME", "DocumentSnapshot successfully written!");
//
//                                                        }
//                                                    })
//                                                    .addOnFailureListener(new OnFailureListener() {
//                                                        @Override
//                                                        public void onFailure(@NonNull Exception e) {
//
//
//                                                            Log.d("HOME", "Error writing document", e);
//                                                        }
//                                                    });
//                                        } else {
////                                            Log.d("HOME", "No such document");
//                                            Toast.makeText(FindActivity.this, "COULDN'T FIND THAT USERNAME", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                } else{
//                                    Log.d("HOME", "get failed with ", task.getException());
//                                }
//                            }
//
//                        });
//            }
//        });



    }
}
