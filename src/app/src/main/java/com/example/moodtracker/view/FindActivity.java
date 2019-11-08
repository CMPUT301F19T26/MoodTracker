package com.example.moodtracker.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moodtracker.R;
import com.example.moodtracker.helpers.BottomNavigationViewHelper;
import com.example.moodtracker.model.User;
import com.example.moodtracker.view.mood.AddMoodEventActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.moodtracker.model.User;

public class FindActivity extends AppCompatActivity {

    ListView userListView;
    ArrayAdapter<User> userArrayAdapter;


    EditText searchText;
    Button searchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);


        searchText = findViewById(R.id.search_bar);
        searchButton = findViewById(R.id.search_button);
        userListView = findViewById(R.id.result_list);

        searchButton.setVisibility(View.INVISIBLE);

        final String searchInput = searchText.getText().toString();
        if (!searchInput.isEmpty()){
            searchButton.setVisibility(View.VISIBLE);
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(FindActivity.this,"SEARCHING" , Toast.LENGTH_SHORT).show();
                }
            });
        }

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.ic_Profile:
                        Intent intent0 = new Intent(FindActivity.this, ProfileFragment.class);
                        startActivity(intent0);
                        break;

                    case R.id.ic_Search:

                        break;

                    case R.id.ic_Add:
                        Intent intent2 = new Intent(FindActivity.this, AddMoodEventActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_Map:
//                        Intent intent3 = new Intent(FindActivity.this, MapActivity.class);
//                        startActivity(intent3);
                        break;

                    case R.id.ic_Feed:

                        break;

                }

                return false;
            }
        });

    }
}
