package com.example.moodtracker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.moodtracker.model.MoodEvent;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;
import com.google.firebase.storage.FirebaseStorage;

public class ProfileFragment extends Fragment {
    private FloatingActionButton EditFab;
    private FloatingActionButton AddMoodFab;
    private FirebaseAuth fAuth;
    private Button FollowersButton;
    private Button FollowingButton;
    private Button MoodEventButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Gets Current User
        fAuth = FirebaseAuth.getInstance();
        String username = fAuth.getCurrentUser().getDisplayName();
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        FirebaseStorage storage = FirebaseStorage.getInstance();

        //Gets the textView fields
        final TextView ProfileName = (TextView) view.findViewById(R.id.userNameFragmentProfile);
        EditFab = view.findViewById(R.id.EditFab);
        EditFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editActivity = new Intent(getActivity(), EditProfile.class);
                editActivity.putExtra("userID", fAuth.getCurrentUser().getUid());
                editActivity.putExtra("username", ProfileName.getText());
                getActivity().startActivity(editActivity);

            }
        });

        AddMoodFab = view.findViewById(R.id.AddMoodFab);
        AddMoodFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addActivity = new Intent(getActivity(), AddMoodEvent.class);
                getActivity().startActivity(addActivity);
            }
        });

        FollowersButton = view.findViewById(R.id.FollowersButton);
        FollowersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent followersActivity = new Intent(getActivity(), Followers.class);
                getActivity().startActivity(followersActivity);
            }
        });

        FollowingButton = view.findViewById(R.id.FollowingButton);
        FollowingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent followersActivity = new Intent(getActivity(), Following.class);
                getActivity().startActivity(followersActivity);
            }
        });

        MoodEventButton = view.findViewById(R.id.MoodEventButton);
        MoodEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent followersActivity = new Intent(getActivity(), MoodEvent.class);
                getActivity().startActivity(followersActivity);
            }
        });

        //Sets the name
        ProfileName.setText(fAuth.getCurrentUser().getDisplayName());




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

        return view;
//    }

     }
}


