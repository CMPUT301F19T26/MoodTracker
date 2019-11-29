/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.moodtracker;

import com.example.moodtracker.model.Mood;
import com.example.moodtracker.model.mood.Neutral;
import org.junit.*;
import static com.example.moodtracker.constants.NEUTRAL;
import static org.junit.Assert.*;
import java.util.HashMap;

/**
 * Testing Constants class
 */
public class ConstantsTest {
    public static final String NEUTRAl = "0";
    public static final String HAPPY = "1";

    public static final int ALONE = 0;
    public static final int ONE_OTHER = 1;

    public static final String date_format = "yyyy-MM-dd'T'HH:mm:sss'Z'";
    public static final String clean_format = "HH:mm • yyyy/MM/dd";

    public static HashMap<String, String> test_mood_name_to_num_mapper;
    public static HashMap<String, Integer> test_mood_num_to_index_mapper;
    public static HashMap<String, Mood> test_mood_num_to_mood_obj_mapper;
    public static HashMap<String, Integer> test_SS_name_to_index_mapper;

    @Before
    public void setUp() throws Exception{
        test_mood_name_to_num_mapper = new HashMap<>();
        test_mood_num_to_index_mapper = new HashMap<>();
        test_mood_num_to_mood_obj_mapper = new HashMap<>();
        test_SS_name_to_index_mapper = new HashMap<>();
    }

    @Test
    public void setTest_mood_name_to_num_mapper(){
        test_mood_name_to_num_mapper.put("Neutral",NEUTRAl);
        assertEquals(1,test_mood_name_to_num_mapper.size());
    }

    @Test
    public void setTest_mood_num_to_index_mapper(){
        test_mood_num_to_index_mapper.put(NEUTRAl,0);
        assertEquals(1,test_mood_num_to_index_mapper.size());
    }

    @Test
    public void setTest_mood_num_to_mood_obj_mapper(){
        test_mood_num_to_mood_obj_mapper.put(NEUTRAl, new Neutral("#f498ad", R.drawable.neutral, "Neutral", NEUTRAL));
        assertEquals(1,test_mood_num_to_mood_obj_mapper.size());
    }

    @Test
    public void setTest_SS_name_to_index_mapper(){
        test_SS_name_to_index_mapper.put("None", 0);
        assertEquals(1,test_SS_name_to_index_mapper.size());
    }








}
