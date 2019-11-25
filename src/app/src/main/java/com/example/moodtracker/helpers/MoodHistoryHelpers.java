/*
 * MoodHistoryHelper
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
 */

package com.example.moodtracker.helpers;

import com.example.moodtracker.constants;
import com.example.moodtracker.model.MoodEvent;
import com.example.moodtracker.model.MoodHistory;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * Compares, formats and converts dates for mood history sorting
 */
public class MoodHistoryHelpers  implements Comparator<MoodEvent>{

    /**
     * compares mood event dates
     * @param m1 first mood event to compare
     * @param m2 second mood event to compare
     * @return returns value based on which date precedes which
     */
    @Override
    public int compare(MoodEvent m1, MoodEvent m2) {
        Date d1 = convertStringtoDate(m1.getDate());
        Date d2 = convertStringtoDate(m2.getDate());
        int value;
        if (d1.after(d2)) {
            value = -1;
        } else if (d1.before(d2)){
            value = 1;
        } else {
            value = 0;
        }
        return value;
    }

    /**
     * @return NULL
     */
    @Override
    public Comparator<MoodEvent> reversed() {
        return null;
    }

    /**
     * Format date
     * @param date date to format
     * @return formatted date
     */
    public static String formatDate(String date) {
        Date curr_date = MoodHistoryHelpers.convertStringtoDate(date);
        DateFormat df = new SimpleDateFormat(constants.clean_format);
        return df.format(curr_date);
    }

    /**
     * Convert String to date
     * @param date date convert
     * @return converted date or current date
     */
    public static Date convertStringtoDate(String date) {
        DateFormat df = new SimpleDateFormat(constants.date_format);
        Date d;
        try {
            d = df.parse(date);
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public void filterList(MoodHistory h, String mood) {

    }
}
