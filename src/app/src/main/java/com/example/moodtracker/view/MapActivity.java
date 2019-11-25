/**
 * MapActivity
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

import java.util.ArrayList;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, MoodEventFragment.OnFragmentInteractionListener {

    //declare stuff
    private GoogleMap mMap;
    private ArrayList<Marker> userMarkers = new ArrayList<Marker>();
    private ArrayList<Marker> friendMarkers = new ArrayList<Marker>();

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

    public void openFragment(MoodEvent moodEvent, int position) {

        MoodEventFragment fragment = MoodEventFragment.newInstance(moodEvent, position, false);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction =  fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.addToBackStack(null);
        transaction.add(R.id.mood_event_frag_container, fragment, "MOOD_EVENT_FRAGMENT").commit();



//        boolean location_changed = false;
//        if (moodEvent.getLat() == null) {
//            location_changed = true;
//            moodEvent.setLng(0.0);
//            moodEvent.setLat(0.0);
//        }
//        MoodEventFragment fragment = MoodEventFragment.newInstance(moodEvent, position, location_changed);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
//        transaction.addToBackStack(null);
//        transaction.add(R.id.mood_event_frag_container, fragment, "MOOD_EVENT_FRAGMENT").commit();
    }


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

                userMarkers.clear();
                for(MoodEvent moodEvent: moodEvents) {
                    LatLng loc = new LatLng(moodEvent.getLat(),moodEvent.getLng());
                    Mood m = constants.mood_num_to_mood_obj_mapper.get(moodEvent.getMood());
                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(loc)
                            .title(m.getMoodName()));
                    marker.setTag(moodEvent);
                    userMarkers.add(marker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));

                }

            }

            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        user.getFriendLocations(new MoodHistory.FirebaseCallback<ArrayList<MoodEvent>>() {
            @Override
            public void onSuccess(ArrayList<MoodEvent> moodEvents) {
                //append locations to map
                friendMarkers.clear();
                for(MoodEvent moodEvent: moodEvents) {
                    LatLng loc = new LatLng(moodEvent.getLat(),moodEvent.getLng());
                    Mood m = constants.mood_num_to_mood_obj_mapper.get(moodEvent.getMood());
                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(loc)
                            .title(m.getMoodName())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                    );
                    marker.setTag(moodEvent);
                    friendMarkers.add(marker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
//                    LatLng loc = new LatLng(location.getLatitude(),location.getLongitude());
//                    mMap.addMarker(new MarkerOptions().position(loc).title(location.getMood()));
//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));

                }

            }

            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        Button USER = findViewById(R.id.user_button);
        Button FRIENDS = findViewById(R.id.friends_button);
        Button ALL = findViewById(R.id.all_button);

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
                openFragment(m, 5);

            }
        });


    }

    @Override
    public void onDeleteFragmentInteraction(int position) {

    }

    @Override
    public void onUpdateFragmentInteraction(MoodEvent e, int position, Uri photo, MoodHistory.FirebaseCallback cb) {
////        markers.get(position);
//        marker.setTag(e);
//        Mood m = constants.mood_num_to_mood_obj_mapper.get(e.getMood());
//        market.title(m.getMoodName())

    }


}
