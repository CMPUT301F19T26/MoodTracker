/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
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

public class MoodHistoryHelpers  implements Comparator<MoodEvent>{

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

    @Override
    public Comparator<MoodEvent> reversed() {
        return null;
    }

    public static String formatDate(String date) {
        Date curr_date = MoodHistoryHelpers.convertStringtoDate(date);
        DateFormat df = new SimpleDateFormat(constants.clean_format);
        return df.format(curr_date);
    }

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
