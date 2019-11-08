package com.example.moodtracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
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
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(userID);

    }

    //constructor used for parcel
    public User(Parcel parcel) {
        userID = parcel.readString();

    }

    //used when un-parceling our parcel (creating the object)
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>(){

        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int size) {
            return new User[0];
        }
    };

    //return hashcode of object
    public int describeContents() {
        return hashCode();
    }

}
