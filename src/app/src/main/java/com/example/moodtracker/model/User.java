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
