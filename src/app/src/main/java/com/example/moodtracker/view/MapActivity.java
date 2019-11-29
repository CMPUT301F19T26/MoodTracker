/*
 * MapActivity
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

package com.example.moodtracker.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.moodtracker.R;
import com.example.moodtracker.constants;
import com.example.moodtracker.helpers.BottomNavigationViewHelper;
import com.example.moodtracker.helpers.FirebaseHelper;
import com.example.moodtracker.model.Location;
import com.example.moodtracker.model.Mood;
import com.example.moodtracker.model.MoodEvent;
import com.example.moodtracker.model.MoodHistory;
import com.example.moodtracker.model.User;
import com.example.moodtracker.view.fragment.MoodEventFragment;
import com.example.moodtracker.view.mood.AddMoodEventActivity;
import com.example.moodtracker.view.mood.MoodHistoryActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Handles activity for maps
 */
public class MapActivity extends FragmentActivity implements OnMapReadyCallback, MoodEventFragment.OnFragmentInteractionListener {

    //declare stuff
    private GoogleMap mMap;
    private ArrayList<Marker> userMarkers = new ArrayList<Marker>();
    private ArrayList<Marker> friendMarkers = new ArrayList<Marker>();
    private HashMap<Integer, Marker> posmarker = new HashMap<>();
    private HashMap<Marker, Integer> markerpos = new HashMap<>();

    //create listener
    private View.OnClickListener userclick = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            for (Marker m : friendMarkers) {
                m.setVisible(false);
            }

            for (Marker m : userMarkers) {
                m.setVisible(true);
            }

        }
    };

    private View.OnClickListener friendclick = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            for (Marker m : userMarkers) {
                m.setVisible(false);
            }

            for (Marker m : friendMarkers) {
                m.setVisible(true);
            }

        }
    };

    private View.OnClickListener allclick = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            for (Marker m : userMarkers) {
                m.setVisible(true);
            }

            for (Marker m : friendMarkers) {
                m.setVisible(true);
            }

        }
    };

    /**
     * Opens fragment for maps
     * @param moodEvent the mood event
     * @param position unique identifier for mood event
     */
    public void openFragment(MoodEvent moodEvent, int position) {

        MoodEventFragment fragment = MoodEventFragment.newInstance(moodEvent, position, false);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction =  fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.addToBackStack(null);
        transaction.add(R.id.mood_event_frag_container, fragment, "MOOD_EVENT_FRAGMENT").commit();

    }


    /**
     * On create for Map activity
     * @param savedInstanceState the saved instance for the map activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //declare vars
        mMap = googleMap;

        if (getIntent().getParcelableExtra("LOC") != null){

            LatLng location = getIntent().getParcelableExtra("LOC");
            String mood = getIntent().getStringExtra("MNAME");

            mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(mood));

            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));

            return;

        }




        ArrayList<Location> userlocations = new ArrayList<Location>();
        ArrayList<Location> friendlocations = new ArrayList<Location>();

        // get mode for friends or user
        int mode = getIntent().getIntExtra("MODE", 0);

        //get user
        User user = getIntent().getParcelableExtra("USER");

        //get locations

        user.getUserLocations(new MoodHistory.FirebaseCallback<ArrayList<MoodEvent>>() {
            @Override
            public void onSuccess(ArrayList<MoodEvent> moodEvents) {
                //append locations to map

                int count = 0;


                userMarkers.clear();

                for(MoodEvent moodEvent: moodEvents) {
                    LatLng loc = new LatLng(moodEvent.getLat(),moodEvent.getLng());
                    Mood m = constants.mood_num_to_mood_obj_mapper.get(moodEvent.getMood());
                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(loc)
                            .title(m.getMoodName()));
                    marker.setTag(moodEvent);
                    posmarker.put(count,marker);
                    markerpos.put(marker,count);
                    userMarkers.add(marker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                    count++;

                }



            }

            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<MoodEvent> friendMoods = new ArrayList<>();
        user.getFriendLocations(db, FirebaseAuth.getInstance().getUid(), friendMoods, new FirebaseHelper.FirebaseCallback() {
            @Override
            public void onSuccess(Object document) {
                friendMarkers.clear();

                System.out.println("HERE WOW" + friendMoods.size());


                for(MoodEvent moodEvent: friendMoods) {




                    LatLng loc = new LatLng(moodEvent.getLat(),moodEvent.getLng());
                    Mood m = constants.mood_num_to_mood_obj_mapper.get(moodEvent.getMood());
                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(loc)
                            .title(m.getMoodName())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                    );
                    marker.setTag(moodEvent);
//                    posmarker.put(count,marker);
//                    markerpos.put(marker,count);
                    friendMarkers.add(marker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
//                    count++;


                }
                System.out.println("Done getting feed");
            }

            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });





//        user.getFriendLocations(new MoodHistory.FirebaseCallback<ArrayList<MoodEvent>>() {
//            @Override
//            public void onSuccess(ArrayList<MoodEvent> moodEvents) {
//                //append locations to map
//                friendMarkers.clear();
//                System.out.println("HERE " + moodEvents.size());
//                for(MoodEvent moodEvent: moodEvents) {
//                    LatLng loc = new LatLng(moodEvent.getLat(),moodEvent.getLng());
//                    Mood m = constants.mood_num_to_mood_obj_mapper.get(moodEvent.getMood());
//                    Marker marker = mMap.addMarker(new MarkerOptions()
//                            .position(loc)
//                            .title(m.getMoodName())
////                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
//                    );
//                    marker.setTag(moodEvent);
//                    friendMarkers.add(marker);
//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
////                    LatLng loc = new LatLng(location.getLatitude(),location.getLongitude());
////                    mMap.addMarker(new MarkerOptions().position(loc).title(location.getMood()));
////                    mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
//
//                }
//
//                System.out.println("LENGTHER " + friendMarkers.size());
//
//
//
//            }
//
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });

        Button USER = findViewById(R.id.user_button);
        Button FRIENDS = findViewById(R.id.friends_button);
        Button ALL = findViewById(R.id.all_button);

        USER.setVisibility(View.VISIBLE);
        FRIENDS.setVisibility(View.VISIBLE);
        ALL.setVisibility(View.VISIBLE);

        USER.setOnClickListener(userclick);
        FRIENDS.setOnClickListener(friendclick);
        ALL.setOnClickListener(allclick);

//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                MoodEvent m = (MoodEvent)marker.getTag();
//                openFragment(m, 5);
//                return false;
//            }
//        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                MoodEvent m = (MoodEvent)marker.getTag();
                openFragment(m, markerpos.get(marker));

            }
        });


    }

    @Override
    public void onDeleteFragmentInteraction(int position) {
        Marker marker = posmarker.get(position);
        MoodEvent e = (MoodEvent) marker.getTag();
        MoodHistory.deleteMoodEventForMarker(e, new MoodHistory.FirebaseCallback<Void>() {
            @Override
            public void onSuccess(Void document) {
                System.out.println("Successfully deleted Mood Event");
                marker.remove();
            }

            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Failed to deleted Mood Event");
            }
        });
    }

    @Override
    public void onUpdateFragmentInteraction(MoodEvent e, int position, Uri photo, MoodHistory.FirebaseCallback cb) {
        Marker marker = posmarker.get(position);
        marker.setTag(e);
        Mood m = constants.mood_num_to_mood_obj_mapper.get(e.getMood());
        marker.setTitle(m.getMoodName());
        MoodHistory.externalUpdateMarkerMoodEvent(e, position, photo, cb);

    }


}
