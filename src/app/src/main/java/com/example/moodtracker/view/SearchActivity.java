package com.example.moodtracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moodtracker.R;
import com.example.moodtracker.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

public class SearchActivity extends AppCompatActivity {
    ListView userListView;
    ArrayAdapter<User> userArrayAdapter;


    EditText searchText;
    Button searchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
                    Toast.makeText(SearchActivity.this,"SEARCHING" , Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
