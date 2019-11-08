package com.example.moodtracker.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

//import com.example.moodtracker.view.AddMoodEvent;
import com.example.moodtracker.R;
import com.example.moodtracker.helpers.BottomNavigationViewHelper;
import com.example.moodtracker.model.MoodEvent;
import com.example.moodtracker.view.mood.AddMoodEventActivity;
import com.example.moodtracker.view.mood.MoodHistoryActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

/**
 * @auhtor CMPUT301F19T26
 * ProfileFragment extends AppCompactActivity
 * it overwrites onCreateView
 */

public class ProfileFragment extends AppCompatActivity {
    private MaterialButton EditFab;
    private FloatingActionButton AddMoodFab;
    private FirebaseAuth fAuth;
    private Button FollowersButton;
    private Button FollowingButton;
    private Button MoodEventButton;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Gets Current User
        fAuth = FirebaseAuth.getInstance();
//        String username = fAuth.getCurrentUser().getDisplayName();
//        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
//        FirebaseStorage storage = FirebaseStorage.getInstance();

        //Gets the textView fields
        final TextView ProfileName = (TextView) findViewById(R.id.userNameFragmentProfile);
        EditFab = findViewById(R.id.EditFab);
        EditFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editActivity = new Intent(ProfileFragment.this, EditProfile.class);
                editActivity.putExtra("userID", fAuth.getCurrentUser().getUid());
                editActivity.putExtra("username", ProfileName.getText());
                startActivity(editActivity);

            }
        });

//        AddMoodFab = findViewById(R.id.AddMoodFab);
//        AddMoodFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent addActivity = new Intent(ProfileFragment.this, AddMoodEvent.class);
//                startActivity(addActivity);
//            }
//        });

        FollowersButton = findViewById(R.id.FollowersButton);
        FollowersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent followersActivity = new Intent(getActivity(), Followers.class);
//                getActivity().startActivity(followersActivity);
            }
        });

        FollowingButton = findViewById(R.id.FollowingButton);
        FollowingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent followersActivity = new Intent(getActivity(), Following.class);
//                getActivity().startActivity(followersActivity);
            }
        });

        MoodEventButton = findViewById(R.id.MoodEventButton);
        MoodEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent followersActivity = new Intent(ProfileFragment.this, MoodHistoryActivity.class);
                startActivity(followersActivity);
            }
        });

        //add Navigation bar functionality
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.ic_Profile:

                        break;

                    case R.id.ic_Search:
                        Intent intent1 = new Intent(ProfileFragment.this, FindActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_Add:
                        Intent intent2 = new Intent(ProfileFragment.this, AddMoodEventActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_Map:
//                        Intent intent3 = new Intent(ProfileFragment.this, MapActivity.class);
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





        //Sets the name
//        ProfileName.setText(fAuth.getCurrentUser().getDisplayName());

//        //Gets the user's profile picture
//        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Log.e("Tuts+", "uri: " + uri.toString());
//                DownloadLink = uri.toString();
//                CircleImageView iv = (CircleImageView) view.findViewById(R.id.profilePictureEditFragment);
//                Picasso.with(getContext()).load(uri.toString()).placeholder(R.drawable.ic_launcher3slanted).error(R.drawable.ic_launcher3slanted).into(iv);
//                //Handle whatever you're going to do with the URL here
//            }
//        });

//    }


