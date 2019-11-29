package com.example.moodtracker;

import com.example.moodtracker.model.Location;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Testing locations
 */
public class LocationTest {
    private static Location newLoc;
    private static Location newLocL;
    private static Location newLocS;

    @BeforeClass
    public static void setUp() throws Exception {
        newLoc = new Location(2, 2, "Happy");
        newLocL = new Location(2323232323.232323, 999999980696.55464645, "Happy");
        newLocS = new Location(0.00001, 0.00001, "Happy");
    }

    @Test
    public void testGetLatitude() {
        assertEquals(2, newLoc.getLatitude(), 0);
    }

    @Test
    public void testGetLongitude() {
        assertEquals(2, newLoc.getLongitude(), 0);
    }

    @Test
    public void testGetLatitudeL() {
        assertEquals(2323232323.232323, newLocL.getLatitude(), 0);
    }

    @Test
    public void testGetLongitudeL() {
        assertEquals(999999980696.55464645, newLocL.getLongitude(), 0);
    }

    @Test
    public void testGetLatitudeS() {
        assertEquals(0.00001, newLocS.getLatitude(), 0);
    }

    @Test
    public void testGetLongitudeS() {
        assertEquals(0.00001, newLocS.getLongitude(), 0);
    }

    @Test
    public void testGetMood() {
        assertEquals("Happy", newLoc.getMood());
    }

}
