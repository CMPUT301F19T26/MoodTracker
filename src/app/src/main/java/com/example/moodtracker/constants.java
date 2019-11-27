/*
 * Constants
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
 *
 */

package com.example.moodtracker;

import com.example.moodtracker.model.Mood;
import com.example.moodtracker.model.mood.Angry;
import com.example.moodtracker.model.mood.Happy;
import com.example.moodtracker.model.mood.Neutral;
import com.example.moodtracker.model.mood.Surprised;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.SimpleFormatter;

/**
 * Creates Hash maps associating social situations and moods to values
 */
public class constants {

    // Moods
    public static final String NEUTRAL = "0";
    public static final String HAPPY = "1";
    public static final String SURPRISED = "2";
    public static final String ANGRY = "3";
    public static final String SAD = "4";
    public static final String HUNGRY = "5";
    public static final String CONFUSED = "6";


    // Social Situations
    public static final int ALONE = 0;
    public static final int ONE_OTHER = 1;
    public static final int TWO_OTHER = 2;
    public static final int SEVERAL = 3;
    public static final int CROWD = 4;
    public static final int NONE = 5;

    public static final String date_format = "yyyy-MM-dd'T'HH:mm:sss'Z'"; // This is used for DB
    public static final String clean_format = "HH:mm • yyyy/MM/dd"; // This is used for front_end


    // Front end mappers
    /**
     * Mood names and num association via hash map
     */
    public static HashMap<String, String> mood_name_to_num_mapper = new HashMap<>();
    static{
            mood_name_to_num_mapper.put("Neutral", NEUTRAL);
            mood_name_to_num_mapper.put("Happy", HAPPY);
            mood_name_to_num_mapper.put("Surprised", SURPRISED);
            mood_name_to_num_mapper.put("Angry", ANGRY);
            mood_name_to_num_mapper.put("Sad", SAD);
            mood_name_to_num_mapper.put("Hungry", HUNGRY);
            mood_name_to_num_mapper.put("Confused", CONFUSED);
    }

    /**
     * Mood num and index association via hash map
     */
    public static HashMap<String, Integer> mood_num_to_index_mapper = new HashMap<>();
    static {
        mood_num_to_index_mapper.put(NEUTRAL, 0);
        mood_num_to_index_mapper.put(HAPPY, 1);
        mood_num_to_index_mapper.put(SURPRISED, 2);
        mood_num_to_index_mapper.put(ANGRY, 3);
        mood_num_to_index_mapper.put(SAD, 4);
        mood_num_to_index_mapper.put(HUNGRY, 5);
        mood_num_to_index_mapper.put(CONFUSED, 6);

    }

    /**
     * Given a mood event object, we can map the mood num to an object that will be used to clean up the UI
     */
    public static HashMap<String, Mood> mood_num_to_mood_obj_mapper = new HashMap<>();
    static {
        mood_num_to_mood_obj_mapper.put(NEUTRAL, new Neutral("#f498ad", R.drawable.neutral, "Neutral", NEUTRAL));
        mood_num_to_mood_obj_mapper.put(HAPPY, new Happy("#7FFF00" , R.drawable.happy_icon, "Happy", HAPPY));
        mood_num_to_mood_obj_mapper.put(SURPRISED, new Surprised("#ffff00", R.drawable.surprised, "Surprised", SURPRISED));
        mood_num_to_mood_obj_mapper.put(ANGRY, new Angry("#ff2929", R.drawable.angry, "Angry", ANGRY));
        mood_num_to_mood_obj_mapper.put(SAD, new Angry("#3232ff", R.drawable.sad, "Sad", SAD));
        mood_num_to_mood_obj_mapper.put(HUNGRY, new Angry("#8B4513", R.drawable.hungry, "Hungry", HUNGRY));
        mood_num_to_mood_obj_mapper.put(CONFUSED, new Angry("#ffdb58", R.drawable.confused, "Confused", CONFUSED));
    }

    /**
     * Social situation and index hash map
     */
    public static HashMap<String, Integer> SS_name_to_index_mapper = new HashMap<>();
    static {
        SS_name_to_index_mapper.put("None", 0);
        SS_name_to_index_mapper.put("Alone", 1);
        SS_name_to_index_mapper.put("With One Other", 2);
        SS_name_to_index_mapper.put("With Two Others", 3);
        SS_name_to_index_mapper.put("With Several", 4);
        SS_name_to_index_mapper.put("With a Crowd", 5);
    }
    public static String[] mood_list = {"Neutral", "Happy", "Surprised", "Angry", "Sad", "Hungry", "Confused"};
    public static String[] mood_spinner_list = {"All", "Neutral", "Happy", "Surprised", "Angry", "Sad", "Hungry", "Confused"};
    public static String[] social_situations_list = {"None","Alone", "With One Other", "With Two Others", "With Several", "With a Crowd"};
}
