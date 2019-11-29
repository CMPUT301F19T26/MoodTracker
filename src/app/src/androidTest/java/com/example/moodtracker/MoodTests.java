package com.example.moodtracker;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.moodtracker.model.Mood;
import com.example.moodtracker.model.MoodEvent;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MoodTests {
    @Test
    public void testMood() throws Exception{
        Mood mood = new Mood("0");

    }

    @Test
    public void testMoodEvent1() {

        Date date = new Date(1999,12,21);

        MoodEvent event = new MoodEvent("happy" , "21", date);

        assertEquals(event.getMood(), "happy");
        assertEquals(event.getUser_id(), "21");
        assertEquals(event.getDate(), date);

    }

    @Test
    public void testMoodEvent2() {

        Date date = new Date(2000,9,4);

        MoodEvent event = new MoodEvent("sad" , "22", date, "I was Bored");

        assertEquals(event.getMood(), "sad");
        assertEquals(event.getUser_id(), "22");
        assertEquals(event.getDate(), date);
        assertEquals(event.getReason(), "I was Bored");

    }

    @Test
    public void testMoodEvent3() {

        Date date = new Date(2020,8,30);

        MoodEvent event = new MoodEvent("surprised" , "20202", date, 21.0,12.0);

        assertEquals(event.getMood(), "surprised");
        assertEquals(event.getUser_id(), "20202");
        assertEquals(event.getDate(), date);
//        assertEquals(java.util.Optional.ofNullable(event.getLat()), 21.0);
//        assertEquals(java.util.Optional.ofNullable(event.getLng()), 12.0);


    }

    @Test
    public void testMoodEvent4() {

        Date date = new Date(1999,12,21);

        MoodEvent event = new MoodEvent("happy" , "21", date, "required by law", "https://www.abc.net.au/news/image/11688156-3x2-940x627.jpg", 21.0,23.0, "crowd");

        assertEquals(event.getMood(), "happy");
        assertEquals(event.getUser_id(), "21");
        assertEquals(event.getDate(), date);
        assertEquals(event.getReason(), "required by law");
        assertEquals(event.getPhoto_url(), "https://www.abc.net.au/news/image/11688156-3x2-940x627.jpg");
//        assertEquals(java.util.Optional.ofNullable(event.getLat()), 21.0);
//        assertEquals(java.util.Optional.ofNullable(event.getLng()), 23.0);
        assertEquals(event.getSocialSituation(), "crowd");


    }
}
