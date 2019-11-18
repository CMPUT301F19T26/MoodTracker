/**
 * Constants
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

package com.example.moodtracker;

import com.example.moodtracker.model.Mood;
import com.example.moodtracker.model.mood.Angry;
import com.example.moodtracker.model.mood.Happy;
import com.example.moodtracker.model.mood.Neutral;
import com.example.moodtracker.model.mood.Surprised;

import java.util.HashMap;

public class constants {

    // Moods
    public static final String NEUTRAL = "0";
    public static final String HAPPY = "1";
    public static final String SURPRISED = "2";
    public static final String ANGRY = "3";

    // Social Situations
    public static final int ALONE = 0;
    public static final int ONE_OTHER = 1;
    public static final int TWO_OTHER = 2;
    public static final int SEVERAL = 3;
    public static final int CROWD = 4;
    public static final int NONE = 5;

    // Front end mappers
    public static HashMap<String, String> mood_name_to_num_mapper = new HashMap<>();
    static{
            mood_name_to_num_mapper.put("Neutral", NEUTRAL);
            mood_name_to_num_mapper.put("Happy", HAPPY);
            mood_name_to_num_mapper.put("Surprised", SURPRISED);
            mood_name_to_num_mapper.put("Angry", ANGRY);
    }

    // Given a mood event object, we can map the mood num to an object that will be used to clean up the UI
    public static HashMap<String, Mood> mood_num_to_mood_obj_mapper = new HashMap<>();
    static {
        mood_num_to_mood_obj_mapper.put(NEUTRAL, new Neutral("#f498ad", R.drawable.neutral, "Neutral", NEUTRAL));
        mood_num_to_mood_obj_mapper.put(HAPPY, new Happy("#008000" , R.drawable.happy_icon, "Happy", HAPPY));
        mood_num_to_mood_obj_mapper.put(SURPRISED, new Surprised("#ffff00", R.drawable.surprised, "Surprised", SURPRISED));
        mood_num_to_mood_obj_mapper.put(ANGRY, new Angry("#ff2929", R.drawable.angry, "Angry", ANGRY));
    }

//    public static HashMap<String, Integer> mood_to_icon_mapper = new HashMap<>();
//    static {
//        mood_to_icon_mapper.put(HAPPY, R.drawable.happy_icon)
//    }
}
