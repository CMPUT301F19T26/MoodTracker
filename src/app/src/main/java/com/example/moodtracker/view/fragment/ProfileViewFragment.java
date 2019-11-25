/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.moodtracker.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.moodtracker.R;
import com.example.moodtracker.view.EditProfile;
import com.example.moodtracker.view.FollowersActivity;
import com.example.moodtracker.view.FollowingActivity;
import com.example.moodtracker.view.ProfileViewActivity;
import com.example.moodtracker.view.mood.MoodHistoryActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileViewFragment extends Fragment {
    // Front end params
    private FloatingActionButton EditFab;private
    FloatingActionButton AddMoodFab;
    private FirebaseAuth fAuth;
    private Button FollowersButton;
    private Button FollowingButton;
    private Button MoodEventButton;
    private Button MoodHistoryButton;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USER_ID = "param1";
    private static final String USER_NAME = "param2";

    // TODO: Rename and change types of parameters
    private String mUid;
    private String mUsername;

    private OnFragmentInteractionListener mListener;

    public ProfileViewFragment(String uid, String username) {
        // Required empty public constructor
        mUid = uid;
        mUsername = username;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileViewFragment newInstance(String mUid, String mUsername) {
        ProfileViewFragment fragment = new ProfileViewFragment(mUid, mUsername);
        Bundle args = new Bundle();
        args.putString(USER_ID, mUid );
        args.putString(USER_NAME, mUsername);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUid = getArguments().getString(USER_ID);
            mUsername = getArguments().getString(USER_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fAuth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_view, container, false);;
        TextView ProfileName = view.findViewById(R.id.userNameFragmentProfile);
        ProfileName.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        EditFab = view.findViewById(R.id.editFAB);
        EditFab.hide();
        if (fAuth.getCurrentUser().getUid().equals(mUid)) {
            EditFab.show();
            EditFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    Intent editActivity = new Intent(getActivity(), EditProfile.class);
                    startActivity(editActivity);
//                Intent editActivity = new Intent(ProfileViewActivity.this, EditProfile.class);
//                editActivity.putExtra("userID", fAuth.getCurrentUser().getUid());
//                editActivity.putExtra("username", ProfileName.getText());
//                startActivity(editActivity);

                }
            });
            //Todo: Add follow and unfollow logic rn
        }


        FollowersButton = view.findViewById(R.id.FollowersButton);
        FollowersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent followersActivity = new Intent(getActivity(), FollowersActivity.class);
                startActivity(followersActivity);
            }
        });

        FollowingButton = view.findViewById(R.id.FollowingButton);
        FollowingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent followingActivity = new Intent(getActivity(), FollowingActivity.class);
                startActivity(followingActivity);
            }
        });

        MoodHistoryButton = view.findViewById(R.id.MoodHistoryButton);
        MoodHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moodHistoryIntent = new Intent(getActivity(), MoodHistoryActivity.class);
                moodHistoryIntent.putExtra("userID", fAuth.getCurrentUser().getUid());
                startActivity(moodHistoryIntent);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
