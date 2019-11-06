package com.example.moodtracker.Model;

public class User {

    // db to object logic



    function follower()
    {
         return firestore('followers').where('followee_id == this.id');

    }
}
