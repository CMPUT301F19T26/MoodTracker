/*
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
 */

package com.example.moodtracker;

import com.example.moodtracker.model.Mood;
import com.example.moodtracker.model.MoodEvent;

import org.junit.*;
import static org.junit.Assert.*;


public class MoodEventTest {
    private static MoodEvent TestMoodEvent;
    private static MoodEvent TestMoodEvent2;
    private static MoodEvent TestMoodEvent3;
    private static MoodEvent TestMoodEvent4;
    private static double TestLat = 11.23;
    private static double TestLng = 12.23;
    private static double TestLat2 = 11.24;
    private static double TestLng2 = 12.24;
    private static String TestUser_id = "user_id";
    private static String TestMood = "mood";
    private static String TestMood2 = "mood2";
    private static String TestReason = "reason";
    private static String TestReason2 = "Reason2";
    private static String TestPhoto_url = "photo_url";
    private static String TestPhoto_url2 = "photo_url";
    private static String TestDate = "date";
    private static String TestSS = "social_situation";
    private static String TestSS2 = "social_situation2";



    @BeforeClass
    public static void setUp() throws Exception {
        TestMoodEvent = new MoodEvent("mood", "user_id", "date");
        TestMoodEvent2 = new MoodEvent("mood", "user_id", "date", "reason");
        TestMoodEvent3 = new MoodEvent("mood", "user_id", "date", 11.23, 12.23);
        TestMoodEvent4 = new MoodEvent("mood", "user_id", "date", "reason", "photo_url", 11.23, 12.23, "social_situation");


    }

    @Test
    public void TestGetLat3() {
        assertEquals(TestLat, TestMoodEvent3.getLat(), 0);
    }

    @Test
    public void TestGetLat4() {
        assertEquals(TestLat, TestMoodEvent4.getLat(), 0);
    }

    @Test
    public void TestSetLat3() {
        TestMoodEvent3.setLat(TestLat2);
        assertEquals(TestLat2, TestMoodEvent3.getLat(), 0);
        TestMoodEvent3.setLat(TestLat);
    }

    @Test
    public void TestSetLat4() {
        TestMoodEvent4.setLat(TestLat2);
        assertEquals(TestLat2, TestMoodEvent4.getLat(), 0);
        TestMoodEvent4.setLat(TestLat);
    }

    @Test
    public void TestGetLng3() {
        assertEquals(TestLng, TestMoodEvent3.getLng(), 0);
    }

    @Test
    public void TestGetLng4() {
        assertEquals(TestLng, TestMoodEvent4.getLng(), 0);
    }

    @Test
    public void TestSetLng3() {
        TestMoodEvent3.setLng(TestLng2);
        assertEquals(TestLng2, TestMoodEvent3.getLng(), 0);
        TestMoodEvent3.setLng(TestLng);
    }

    @Test
    public void TestSetLng4() {
        TestMoodEvent4.setLng(TestLng2);
        assertEquals(TestLng2, TestMoodEvent4.getLng(), 0);
        TestMoodEvent4.setLng(TestLng);

    }

    @Test
    public void TestGetUser() {
        assertEquals(TestUser_id, TestMoodEvent.getUser_id());
    }

    @Test
    public void TestGetUser2() {
        assertEquals(TestUser_id, TestMoodEvent2.getUser_id());
    }

    @Test
    public void TestGetUser3() {
        assertEquals(TestUser_id, TestMoodEvent3.getUser_id());
    }

    @Test
    public void TestGetUser4() {
        assertEquals(TestUser_id, TestMoodEvent4.getUser_id());
    }

    @Test
    public void TestGetMood() {
        assertEquals(TestMood, TestMoodEvent.getMood());
    }

    @Test
    public void TestGetMood2() {
        assertEquals(TestMood, TestMoodEvent2.getMood());
    }

    @Test
    public void TestGetMood3() {
        assertEquals(TestMood, TestMoodEvent3.getMood());
    }

    @Test
    public void TestGetMood4() {
        assertEquals(TestMood, TestMoodEvent4.getMood());
    }

    @Test
    public void TestSetMood() {
        TestMoodEvent.setMood(TestMood2);
        assertEquals(TestMood2, TestMoodEvent.getMood());
        TestMoodEvent.setMood(TestMood);
    }

    @Test
    public void TestSetMood2() {
        TestMoodEvent2.setMood(TestMood2);
        assertEquals(TestMood2, TestMoodEvent2.getMood());
        TestMoodEvent2.setMood(TestMood);
    }

    @Test
    public void TestSetMood3() {
        TestMoodEvent3.setMood(TestMood2);
        assertEquals(TestMood2, TestMoodEvent3.getMood());
        TestMoodEvent3.setMood(TestMood);
    }

    @Test
    public void TestSetMood4() {
        TestMoodEvent4.setMood(TestMood2);
        assertEquals(TestMood2, TestMoodEvent4.getMood());
        TestMoodEvent4.setMood(TestMood);
    }

    @Test
    public void TestSetReason() {
        TestMoodEvent.setReason(TestReason2);
        assertEquals(TestReason2, TestMoodEvent.getReason());
        TestMoodEvent.setReason(TestReason);
    }

    @Test
    public void TestSetReason2() {
        TestMoodEvent2.setReason(TestReason2);
        assertEquals(TestReason2, TestMoodEvent2.getReason());
        TestMoodEvent2.setReason(TestReason);
    }

    @Test
    public void TestSetReason3() {
        TestMoodEvent3.setReason(TestReason2);
        assertEquals(TestReason2, TestMoodEvent3.getReason());
        TestMoodEvent3.setReason(TestReason);
    }

    @Test
    public void TestSetReason4() {
        TestMoodEvent.setReason(TestReason2);
        assertEquals(TestReason2, TestMoodEvent.getReason());
        TestMoodEvent.setReason(TestReason);
    }

    @Test
    public void TestGetReason2() {
        assertEquals(TestReason, TestMoodEvent2.getReason());
    }

    @Test
    public void TestGetReason4() {
        assertEquals(TestReason, TestMoodEvent4.getReason());
    }

    @Test
    public void TestSetPhoto_url4() {
        TestMoodEvent.setPhoto_url(TestPhoto_url2);
        assertEquals(TestPhoto_url2, TestMoodEvent4.getPhoto_url());
        TestMoodEvent.setPhoto_url(TestPhoto_url);
    }

    @Test
    public void TestGetPhoto_url4() {
        assertEquals(TestPhoto_url, TestMoodEvent4.getPhoto_url());
    }

    @Test
    public void TestDate() {
        assertEquals(TestDate, TestMoodEvent.getDate());
    }

    @Test
    public void TestDate2() {
        assertEquals(TestDate, TestMoodEvent2.getDate());
    }

    @Test
    public void TestDate3() {
        assertEquals(TestDate, TestMoodEvent3.getDate());
    }

    @Test
    public void TestDate4() {
        assertEquals(TestDate, TestMoodEvent4.getDate());
    }

    @Test
    public void TestGetSocialSit4(){
        assertEquals(TestSS,TestMoodEvent4.getSocialSituation());
    }

    @Test
    public void TestSetSS4() {
        TestMoodEvent4.setSocial_situation(TestSS2);
        assertEquals(TestSS2, TestMoodEvent4.getSocialSituation());
        TestMoodEvent4.setSocial_situation(TestSS);
    }
}
