/*
 * User
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

package com.example.moodtracker.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.moodtracker.view.SignupActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 *
 */
public class User implements Parcelable {

    public interface UsernameListener {
        void onRetrieve(String username);
        void onError();
    }

    public interface UsernamesListener {
        void onRetrieve(ArrayList<String> usernames);
        void onError();
    }

    public interface FeedListener {
        void onRetrieve(ArrayList<String> usernames);
        void onError();
    }

    public String userID;
    public ArrayList<String> followingIDs = new ArrayList<String>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * set user id
     * @param id user id to set
     */
    public User(String id) {
        this.userID = id;
    }

    /**
     * gets user id
     * @return user id
     */
    public String getUid()
    {
        return this.userID;
    }

    /**
     * Gets username
     * @param listener listener for button
     */
    public void getUsername(UsernameListener listener)
    {
        db.collection("users").document(this.userID).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            listener.onRetrieve(documentSnapshot.get("username").toString());
                        } else {
                            listener.onError();
                        }
                    }
                });
    }

    /**
     * get following username
     * @param listener listener for button click
     */
    public void getFollowingUsernames(UsernamesListener listener) {
        db.collection("follow")
                .whereEqualTo("follower_id", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String followingId = document.get("following_id").toString();
                                followingIDs.add(followingId);
                            }
                            listener.onRetrieve(followingIDs);
                        } else {
                            listener.onError();
                        }
                    }
                });
    }

    /**
     * Get Feed of followers
     * @param fIds Following ids
     */
    public void getFollowingFeed(ArrayList<String> fIds) {

        ArrayList<String> followingFeed = new ArrayList<String>();
        for (String uid : fIds) {
           db.collection("users").document(uid).collection("moodEvents").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
               @Override
               public void onComplete(@NonNull Task<QuerySnapshot> task) {
                   if (task.isSuccessful()) {
                       for (QueryDocumentSnapshot document : task.getResult()) {
                           String followingId = document.get("mood_id").toString();
                           Log.d("FOLLOWLOOP", followingId);
                       }
                   }
               }
           });
        }



    }

    /**
     * Get Friends ids
     * @return friend ids of user
     */
    public ArrayList<String> getFriendIDs() {

        db.collection("follow")
                .whereEqualTo("follower_id", userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot doc: task.getResult()) {
                            if (doc.exists()) {
                                if (doc.get("following_id") != null) {
                                    String following_id = doc.get("following_id").toString();
                                    followingIDs.add(following_id);

                                }
                            }
                        }
                    }

                });

        return followingIDs;
    }

    /**
     * Get user locations
     */
    public ArrayList<Location> getUserLocations(){
        ArrayList<Location> locations = new ArrayList<Location>();
        db.collection("moodEvents")
                .whereEqualTo("user_id", userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot doc: task.getResult()) {
                            if (doc.exists()) {
                                if (doc.get("location") != null) {
                                    LatLng coords = (LatLng)doc.get("location");
                                    String moodName = (String)doc.get("moodName");
                                    Location location = new Location(coords.latitude,coords.longitude,moodName);
                                    locations.add(location);

                                }
                            }
                        }
                    }
                });


//        //query DB instead
//        Location sydney = new Location(-34, 51, "Happy");
//        Location edmonton = new Location(53, 113, "Sad");
//        Location chicago = new Location(41, 87, "Cheeky");
//        ArrayList<Location> locations = new ArrayList<Location>();
//        locations.add(sydney);
//        locations.add(edmonton);
//        locations.add(chicago);

        return locations;
    }

    /**
     * Get locations of friends mood events
     */
    public ArrayList<Location> getFriendsLocations(){

        getUserLocations();

        ArrayList<Location> locations = new ArrayList<Location>();

        for (String friend_id: followingIDs) {
            db.collection("moodEvents")
                    .whereEqualTo("user_id", userID)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for(QueryDocumentSnapshot doc: task.getResult()) {
                                if (doc.exists()) {
                                    if (doc.get("location") != null) {
                                        LatLng coords = (LatLng)doc.get("location");
                                        String moodName = (String)doc.get("moodName");
                                        Location location = new Location(coords.latitude,coords.longitude,moodName);
                                        locations.add(location);

                                    }
                                }
                            }
                        }
                    });

        }


        //query DB instead
//        Location sydney = new Location(34, 34, "Please");
//        Location edmonton = new Location(76, 76, "Clap");
//        Location chicago = new Location(89, 90, "Sir");
//        ArrayList<Location> locations = new ArrayList<Location>();
//        locations.add(sydney);
//        locations.add(edmonton);
//        locations.add(chicago);

        return locations;

    }

    //write object values to parcel for storage

    /**
     * write the object value to parcel for storage
     * @param dest the destination
     * @param flags the flags
     */
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(userID);

    }

    //constructor used for parcel

    /**
     * Constructor used for parcel
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
         * @param parcel the parcel
         * @return user object with parcel argument
         */
        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        /**
         * User array
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
     * @return the hash code
     */
    public int describeContents() {
        return hashCode();
    }

}
