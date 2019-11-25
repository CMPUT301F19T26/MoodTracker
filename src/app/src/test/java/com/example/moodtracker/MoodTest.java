package com.example.moodtracker;

import com.example.moodtracker.model.Mood;
import com.example.moodtracker.model.MoodEvent;

import org.junit.*;

import java.util.Date;

import static org.junit.Assert.*;

public class MoodTest {
    private static Mood TestMood;
    private static MoodEvent TestMoodEvent;
    private static Date MEdate;

    /*
    private int color;
    private int icon;
    private int moodName;
    private String moodNum;
     */

    @BeforeClass
    public static void setUp() throws Exception {
        TestMood = new Mood("1");
        MEdate = new Date();
        TestMoodEvent = new MoodEvent(TestMood,"bob",MEdate);
    }

    @Test
    public void correctMoodNum(){
        assertEquals("1",TestMood.getMoodNum());
    }

    @Test
    public void correctColor(){
        assertEquals(R.color.color_happy,TestMood.getColor());
    }

    @Test
    public void correctIcon(){
        assertEquals(R.drawable.happy_icon,TestMood.getIcon());
    }

    @Test
    public void correctName(){
        //Ask
        assertEquals("Happy",TestMood.getMoodName());
    }

    //test get mood
    //@Test
    //public void MeMood(){
        //assertEquals(TestMood,TestMoodEvent.getMood());
    //}
    //test set mood

}
