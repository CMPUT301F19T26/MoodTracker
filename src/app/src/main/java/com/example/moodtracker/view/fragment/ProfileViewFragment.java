/*
 * ProfileViewFragment
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

package com.example.moodtracker.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moodtracker.R;
import com.example.moodtracker.model.User;
import com.example.moodtracker.view.EditProfile;
import com.example.moodtracker.view.FindActivity;
import com.example.moodtracker.view.FollowersActivity;
import com.example.moodtracker.view.FollowingActivity;
import com.example.moodtracker.view.ProfileViewActivity;
import com.example.moodtracker.view.RequestsActivity;
import com.example.moodtracker.view.mood.MoodHistoryActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    private Button EditFab;
    private FloatingActionButton AddMoodFab;
    private FirebaseAuth fAuth;
    private Button FollowersButton;
    private Button FollowButton;
    private Button UnfollowButton;
    private Button FollowingButton;
    private Button MoodEventButton;
    private Button MoodHistoryButton;
    private User displayUser;
    private Button RequestsButton;



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
        displayUser = new User(uid);
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
        args.putString(USER_ID, mUid);
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
        displayUser = new User(mUid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fAuth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_view, container, false);;
        TextView ProfileName = view.findViewById(R.id.userNameFragmentProfile);
        Boolean itsMe = (mUid.equals(fAuth.getUid()));
        ProfileName.setText(mUsername);

        FollowButton = view.findViewById(R.id.FollowButton);
        UnfollowButton = view.findViewById(R.id.UnfollowButton);

        RequestsButton = view.findViewById(R.id.RequestedButton);

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

//        RequestsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent requestsActivity = new Intent(getActivity(), RequestsActivity.class);
//                startActivity(requestsActivity);
//            }
//        });

//        MoodHistoryButton = view.findViewById(R.id.MoodHistoryButton);
//        MoodHistoryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent moodHistoryIntent = new Intent(getActivity(), MoodHistoryActivity.class);
//                moodHistoryIntent.putExtra("userID",mUid);
//                startActivity(moodHistoryIntent);
//            }
//        });


        EditFab = view.findViewById(R.id.editFAB);
        EditFab.setVisibility(View.INVISIBLE);
        if (itsMe) {
            EditFab.setVisibility(View.VISIBLE);
            EditFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent editActivity = new Intent(getActivity(), EditProfile.class);
                    startActivity(editActivity);
//                Intent editActivity = new Intent(ProfileViewActivity.this, EditProfile.class);
//                editActivity.putExtra("userID", fAuth.getCurrentUser().getUid());
//                editActivity.putExtra("username", ProfileName.getText());
//                startActivity(editActivity);

                }
            });

        } else {

            User myUser = new User(fAuth.getUid());
            myUser.getFollowingUsernames(new User.UsernamesListener() {
                @Override
                public void onRetrieve(ArrayList<String> usernames) {
                    if(usernames.contains(mUid)) {
                        UnfollowButton.setVisibility(View.VISIBLE);
                    } else {

                        myUser.getRequestedUsernames(new User.UsernamesListener() {
                            @Override
                            public void onRetrieve(ArrayList<String> username) {
                                if(username.contains(mUid)) {
                                    RequestsButton.setVisibility(View.VISIBLE);
                                    UnfollowButton.setVisibility(View.INVISIBLE);
                                    FollowButton.setVisibility(View.INVISIBLE);
                                } else {
                                    Log.d("HOME", "Getting here");
                                    FollowButton.setVisibility(View.VISIBLE);
                                    RequestsButton.setVisibility(View.INVISIBLE);
                                    UnfollowButton.setVisibility(View.INVISIBLE);
                                }
                            }

                            @Override
                            public void onError() {

                            }
                        });
                    }
                }
                @Override
                public void onError() {

                }
            });
        }

        UnfollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance().collection("follow")
                        .whereEqualTo("follower_id", fAuth.getUid())
                        .whereEqualTo("following_id", mUid)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for(DocumentSnapshot doc : task.getResult()) {
                                    FirebaseFirestore.getInstance().collection("follow").document(doc.getId()).delete();
                                }
                                UnfollowButton.setVisibility(View.INVISIBLE);
                                FollowButton.setVisibility(View.VISIBLE);

                            }
                        });
            }
        });

        FollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> followMap = new HashMap<>();
                followMap.put("follower_id", fAuth.getUid());
                followMap.put("following_id", displayUser.getUid());
                followMap.put("timestamp", "time");

                // store a request for that person in db from authenticated person
                FirebaseFirestore.getInstance().collection("requests").document(UUID.randomUUID().toString())
                        .set(followMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("HOME", "DocumentSnapshot successfully written! T1");
                                RequestsButton.setVisibility(View.VISIBLE);
                                UnfollowButton.setVisibility(View.INVISIBLE);
                                FollowButton.setVisibility(View.INVISIBLE);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("HOME", "Error writing document", e);
                            }
                        });

                // store in db
//                FirebaseFirestore.getInstance().collection("follow").document(UUID.randomUUID().toString())
//                        .set(followMap)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Log.d("HOME", "DocumentSnapshot successfully written!");
//                                UnfollowButton.setVisibility(View.VISIBLE);
//                                FollowButton.setVisibility(View.INVISIBLE);
//
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d("HOME", "Error writing document", e);
//                            }
//                        });




//                // get username of myself
//                String myId = FirebaseAuth.getInstance().getUid();
//                // get username of the person trying to follow
//                FirebaseFirestore.getInstance().collection("users").whereEqualTo("username", followUsernameText.getText().toString()).get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (QueryDocumentSnapshot doc : task.getResult()) {
//                                        if (doc.exists()) {
//                                            Log.d("HOME", "DocumentSnapshot data: " + doc.getId());
//                                            // put in a hashmap
//                                            Map<String, Object> followMap = new HashMap<>();
//                                            followMap.put("follower_id", myId);
//                                            followMap.put("following_id", doc.getId());
//
//                                            // store in db
//                                            FirebaseFirestore.getInstance().collection("follow").document(UUID.randomUUID().toString())
//                                                    .set(followMap)
//                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                        @Override
//                                                        public void onSuccess(Void aVoid) {
//                                                            Toast.makeText(FindActivity.this, "Followed " + followUsernameText.getText().toString(), Toast.LENGTH_LONG).show();
//                                                            Log.d("HOME", "DocumentSnapshot successfully written!");
//
//                                                        }
//                                                    })
//                                                    .addOnFailureListener(new OnFailureListener() {
//                                                        @Override
//                                                        public void onFailure(@NonNull Exception e) {
//
//
//                                                            Log.d("HOME", "Error writing document", e);
//                                                        }
//                                                    });
//                                        } else {
////                                            Log.d("HOME", "No such document");
//                                            Toast.makeText(FindActivity.this, "COULDN'T FIND THAT USERNAME", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                } else{
//                                    Log.d("HOME", "get failed with ", task.getException());
//                                }
//                            }
//
//                        });
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
