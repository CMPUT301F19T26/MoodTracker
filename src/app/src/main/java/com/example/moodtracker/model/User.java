/**
 * User
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

package com.example.moodtracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class User implements Parcelable {

    public String userID;
    public ArrayList<String> followingIDs = new ArrayList<String>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public User(String id) {
        userID = id;
    }


//    public ArrayList<String> getFriendIDs() {
//
//        db.collection("follow")
//                .whereEqualTo("follower_id", userID)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        for(QueryDocumentSnapshot doc: task.getResult()) {
//                            if (doc.exists()) {
//                                if (doc.get("following_id") != null) {
//                                    String following_id = doc.get("following_id").toString();
//                                    followingIDs.add(following_id);
//
//                                }
//                            }
//                        }
//                    }
//
//                });
//
//        return followingIDs;
//    }


    public void getUserLocations(final MoodHistory.FirebaseCallback<ArrayList<MoodEvent>> cb) {
        ArrayList<MoodEvent> usermoods = new ArrayList<>();
        db.collection("moodEvents")
                .whereEqualTo("user_id", userID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                            if (doc.exists()) {
                                if (doc.get("lat") != null) {
                                    MoodEvent me  = MoodHistory.buildMoodEventFromDoc(doc, userID);
                                    usermoods.add(me);

                                }
                            }
                        }
                        cb.onSuccess(usermoods);
                    }
                });

    }

    public void getFriendLocations(final MoodHistory.FirebaseCallback<ArrayList<MoodEvent>> cb) {
        ArrayList<MoodEvent> friendmoods = new ArrayList<>();
        System.out.println("inside");
        db.collection("follower")
                .whereEqualTo("follower_id", userID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        System.out.println("inside2");
                        for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                            System.out.println("inside3");
                            if (doc.exists()) {
                                System.out.println("inside4");
                                if (doc.get("following_id") != null) {
                                    System.out.println("inside5");
                                    String id = (String)doc.get("following_id");
                                    db.collection("moodEvents")
                                            .whereEqualTo("user_id", id)
                                            .get()
                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                    System.out.println("inside6");
                                                    for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                                                        System.out.println("inside7");
                                                        if (doc.exists()) {
                                                            System.out.println("inside8");
                                                            if (doc.get("lat") != null) {
                                                                System.out.println("inside9");
                                                                MoodEvent me  = MoodHistory.buildMoodEventFromDoc(doc, userID);
                                                                friendmoods.add(me);

                                                            }
                                                        }
                                                    }

                                                }
                                            });
                                }

                            }
                        }
                        cb.onSuccess(friendmoods);
                    }
                });

    }

//    public ArrayList<Location> getFriendsLocations(){
//
//        getUserLocations();
//
//        ArrayList<Location> locations = new ArrayList<Location>();
//
//        for (String friend_id: followingIDs) {
//            db.collection("moodEvents")
//                    .whereEqualTo("user_id", userID)
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            for(QueryDocumentSnapshot doc: task.getResult()) {
//                                if (doc.exists()) {
//                                    if (doc.get("location") != null) {
//                                        LatLng coords = (LatLng)doc.get("location");
//                                        String moodName = (String)doc.get("moodName");
//                                        Location location = new Location(coords.latitude,coords.longitude,moodName);
//                                        locations.add(location);
//
//                                    }
//                                }
//                            }
//                        }
//                    });
//
//        }


        //query DB instead
//        Location sydney = new Location(34, 34, "Please");
//        Location edmonton = new Location(76, 76, "Clap");
//        Location chicago = new Location(89, 90, "Sir");
//        ArrayList<Location> locations = new ArrayList<Location>();
//        locations.add(sydney);
//        locations.add(edmonton);
//        locations.add(chicago);

//        return locations;

//    }

    //write object values to parcel for storage

    /**
     * write the object value to parcel for storage
     *
     * @param dest the destination
     * @param flags the flags
     */
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(userID);

    }

    //constructor used for parcel

    /**
     * Constructor used for parcel
     *
     * @param parcel the parcel
     */
    public User(Parcel parcel) {
        userID = parcel.readString();

    }

    //used when un-parceling our parcel (creating the object)
    /**
     * used when un-parceling our parcel (creating the object)
     */
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>(){

        /**
         * Create user object from parcel
         *
         * @param parcel the parcel
         * @return user object with parcel argument
         */
        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        /**
         * User array
         *
         * @param size size of user array
         * @return user array
         */
        @Override
        public User[] newArray(int size) {
            return new User[0];
        }
    };

    //return hashcode of object

    /**
     * describe the contents
     *
     * @return the hash code
     */
    public int describeContents() {
        return hashCode();
    }

}
