package com.example.moodtracker;

import com.example.moodtracker.model.Mood;

import org.junit.*;
import static org.junit.Assert.*;

public class MoodTest {
    private static Mood TestMood;

    @BeforeClass
    public static void setUp() throws Exception {
        TestMood = new Mood("1",1,"1","1");
    }

    @Test
    public void MoodNumTest(){
        assertEquals("1",TestMood.getMoodNum());
    }

    @Test
    public void ColorTest(){
        assertEquals("1",TestMood.getColor());
    }

    @Test
    public void IconTest(){
        assertEquals(1,TestMood.getIcon());
    }

    @Test
    public void NameTest(){
        //Ask
        assertEquals("1",TestMood.getMoodName());
    }


}
