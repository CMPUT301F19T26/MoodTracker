package com.example.moodtracker.view.mood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.moodtracker.R;
import com.example.moodtracker.constants;
import com.example.moodtracker.model.MoodEvent;
import com.example.moodtracker.model.MoodHistory;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import com.example.moodtracker.helpers.BottomNavigationViewHelper;
import com.example.moodtracker.model.User;
import com.example.moodtracker.view.FindActivity;
import com.example.moodtracker.view.ProfileViewActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;

public class AddMoodEventActivity extends AppCompatActivity {
    private String user_id = FirebaseAuth.getInstance().getUid();

    // Process Manager to ensure we dont have multiple request go out per click. Wait till first is done
    boolean processing = false;

    //Image uploading
    Button choose, cancel;
    ImageView img;
    StorageReference mStorageRef;
    public Uri imguri = null;

    // Logic Mappers
    HashMap<String, String> mood_name_to_num_mapper = constants.mood_name_to_num_mapper;

    // Front end Views
    ArrayAdapter<String> adapt;
    ArrayAdapter<String> social_adapt;
    Spinner mood_dropdown;
    Spinner social_situation_dropdown;
    Button submit_btn;
    Switch location_switch;

    // Location provider
    private FusedLocationProviderClient fusedLocationClient;
    boolean needs_location = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mood_event);
        // Image Selectors
        choose = findViewById(R.id.upload_choose);
        cancel = findViewById(R.id.upload_cancel);
        img = findViewById(R.id.me_image);
        location_switch = findViewById(R.id.location_switch);

        // Location handling
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mood_dropdown = findViewById(R.id.mood_type_selector);
        // Dynamically create the moods list
        String[] mood_items = Arrays.copyOf(mood_name_to_num_mapper.keySet().toArray(), mood_name_to_num_mapper.keySet().toArray().length, String[].class);
        adapt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mood_items);
        mood_dropdown.setAdapter(adapt);

        social_situation_dropdown = findViewById(R.id.social_sitation_selector);
        social_adapt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, constants.social_situations_list);
        social_situation_dropdown.setAdapter(social_adapt);

        // Submission handling
        submit_btn = findViewById(R.id.add_mood_event_submit);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!processing) {
                    EditText reason_view = findViewById(R.id.reason_edit);
                    SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat(constants.date_format);
                    String now = ISO_8601_FORMAT.format(new Date());
                    String mood = mood_name_to_num_mapper.get(mood_dropdown.getSelectedItem().toString());
                    String reason = reason_view.getText().toString();
                    String situation = social_situation_dropdown.getSelectedItem().toString();
                    // Build the mood event item
                    MoodEvent new_item = buildMoodEventfromUserInput(mood, user_id, now, reason, situation);
                    if (location_switch.isChecked()) {
                        needs_location = true;
                    }
                    processing = true;
                    if (needs_location) {
                        fusedLocationClient.getLastLocation()
                                .addOnSuccessListener(AddMoodEventActivity.this, new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location) {
                                        User.getUsernameExternal(new_item.getUser_id(), new User.UsernameListener() {
                                            @Override
                                            public void onRetrieve(String username) {
                                                new_item.setUser_name(username);
                                                if (location != null) {
                                                    new_item.setLng(location.getLongitude());
                                                    new_item.setLat(location.getLatitude());
                                                    MoodHistory.externalAddMoodEvent(new_item, imguri, new MoodHistory.FirebaseCallback<Void>() {
                                                        @Override
                                                        public void onSuccess(Void document) {
                                                            finish();
                                                        }

                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            processing = false;
                                                        }
                                                    });
                                                }
                                            }

                                            @Override
                                            public void onError() {

                                            }
                                        });
                                    }
                                })
                                .addOnFailureListener(AddMoodEventActivity.this, new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        processing = false;
                                        Toast.makeText(AddMoodEventActivity.this, "Failed to get the users location, ensure permissions are given.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else { // Basic addition of an event with no location
                        User.getUsernameExternal(new_item.getUser_id(), new User.UsernameListener() {
                            @Override
                            public void onRetrieve(String username) {
                                new_item.setUser_name(username);
                                MoodHistory.externalAddMoodEvent(new_item, imguri, new MoodHistory.FirebaseCallback<Void>() {
                                    @Override
                                    public void onSuccess(Void document) {
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        processing = false;
                                    }
                                });
                            }

                            @Override
                            public void onError() {

                            }
                        });
                    }
                } else {
                    Toast.makeText(AddMoodEventActivity.this, "Currently Adding Mood Event...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Location toggler
        location_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (location_switch.isChecked()) {
                    checkPermission();
                    System.out.println("permission checked");
                }
            }
        });

        // Image manipulation
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileChooser();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img.setImageResource(0);
                imguri = null;
                cancel.setVisibility(View.GONE);
                choose.setVisibility(View.VISIBLE);
            }
        });

        //add Navigation bar functionality
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        BottomNavigationViewHelper.enableNavigation(AddMoodEventActivity.this, bottomNavigationView);

    }



    @Override
    public void onBackPressed() {
        if (processing) {
            Toast.makeText(this, "Adding mood event...", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }
    private static  MoodEvent buildMoodEventfromUserInput(String mood, String user_id, String now, String reason, String social_situation) {
        // TODO fix this logic, needs reason rn
        MoodEvent new_item = new MoodEvent(mood, user_id, now);
        if (!(reason.equals(""))) {
            new_item.setReason(reason);
        }
        if (!(social_situation.equals("None"))) {
            new_item.setSocial_situation(social_situation);
        }
        return new_item;
    }

    // Opens activity to have access to gallery and select and image
    private void FileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    // Checks the user permission to have access to current user location
    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode==RESULT_OK && data != null && data.getData()!= null) {
            imguri = data.getData();
            img.setImageURI(imguri);
            choose.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
        }
    }
}
