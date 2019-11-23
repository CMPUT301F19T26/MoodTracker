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

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.moodtracker.R;
import com.example.moodtracker.helpers.BottomNavigationViewHelper;
import com.example.moodtracker.helpers.FirebaseHelper;
import com.example.moodtracker.model.Location;
import com.example.moodtracker.model.MoodHistory;
import com.example.moodtracker.model.User;
import com.example.moodtracker.view.mood.AddMoodEventActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Marker> userMarkers = new ArrayList<Marker>();
    Button hide = findViewById(R.id.hide_button);
    Button show = findViewById(R.id.show_button);

//
//    private View.OnClickListener hideclick = new View.OnClickListener() {
//        @Override
//        public void onClick(View v){
//            for (Marker m : userMarkers) {
//                m.setVisible(false);
//            }
//
//        }
//    };
//
//    private View.OnClickListener showclick = new View.OnClickListener() {
//        @Override
//        public void onClick(View v){
//            for (Marker m : userMarkers) {
//                m.setVisible(true);
//            }
//
//        }
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



//        hide.setOnClickListener(hideclick);
//        show.setOnClickListener(showclick);

//
//        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
//        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
//        Menu menu = bottomNavigationView.getMenu();
//        MenuItem menuItem = menu.getItem(1);
//        menuItem.setChecked(true);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()){
//                    case R.id.ic_Profile:
//                        Intent intent0 = new Intent(MapActivity.this, ProfileFragment.class);
//                        startActivity(intent0);
//                        break;
//
//                    case R.id.ic_Search:
//                        Intent intent1 = new Intent(MapActivity.this, MapActivity.class);
//                        startActivity(intent1);
//                        break;
//
//                    case R.id.ic_Add:
//                        Intent intent2 = new Intent(MapActivity.this, AddMoodEventActivity.class);
//                        startActivity(intent2);
//                        break;
//
//                    case R.id.ic_Map:
//
//                        break;
//
//                    case R.id.ic_Feed:
//
//                        break;
//
//                }
//
//                return false;
//            }
//        });
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

        user.getUserLocations(new MoodHistory.FirebaseCallback<ArrayList<Location>>() {
            @Override
            public void onSuccess(ArrayList<Location> locations) {
                //append locations to map

                userMarkers.clear();
                for(Location location: locations) {
                    LatLng loc = new LatLng(location.getLatitude(),location.getLongitude());
                    userMarkers.add(mMap.addMarker(new MarkerOptions()
                            .position(loc)
                            .title(location.getMood())));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));

                }

            }

            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });



//        user.getFriendLocations(new MoodHistory.FirebaseCallback<ArrayList<Location>>() {
//            @Override
//            public void onSuccess(ArrayList<Location> locations) {
//                //append locations to map
//                for(Location location: locations) {
//                    LatLng loc = new LatLng(location.getLatitude(),location.getLongitude());
//                    mMap.addMarker(new MarkerOptions().position(loc).title(location.getMood()));
//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });





    }
}
