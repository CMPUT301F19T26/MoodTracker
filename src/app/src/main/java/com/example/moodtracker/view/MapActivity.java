package com.example.moodtracker.view;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.moodtracker.R;
import com.example.moodtracker.model.Location;
import com.example.moodtracker.model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

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
        ArrayList<Location> locations;

        // get mode for friends or user
        int mode = getIntent().getIntExtra("MODE", 0);

        //get user
        User user = getIntent().getParcelableExtra("USER");

        //get locations
        if (mode == 0) {

            locations = user.getUserLocations();
        }

        else {
            locations = user.getFriendsLocations();
        }

        //append locations to map
        for(Location location: locations) {
            LatLng loc = new LatLng(location.getLatitude(),location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(loc).title(location.getMood()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));

        }

    }
}
