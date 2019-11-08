package com.example.moodtracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class User implements Parcelable {

    public String userID = "ID";

    public User(String id) {
        userID = id;
    }


    public ArrayList<Location> getUserLocations(){

        //query DB instead
        Location sydney = new Location(-34, 51, "Happy");
        Location edmonton = new Location(53, 113, "Sad");
        Location chicago = new Location(41, 87, "Cheeky");
        ArrayList<Location> locations = new ArrayList<Location>();
        locations.add(sydney);
        locations.add(edmonton);
        locations.add(chicago);

        return locations;

    }

    public ArrayList<Location> getFriendsLocations(){

        //query DB instead
        Location sydney = new Location(34, 34, "Please");
        Location edmonton = new Location(76, 76, "Clap");
        Location chicago = new Location(89, 90, "Sir");
        ArrayList<Location> locations = new ArrayList<Location>();
        locations.add(sydney);
        locations.add(edmonton);
        locations.add(chicago);

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
