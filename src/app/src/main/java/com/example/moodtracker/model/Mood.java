/**
 * Mood
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

package com.example.moodtracker.model;
import com.example.moodtracker.R;
import com.example.moodtracker.constants;

public class Mood {
    // Should not need db logic in mood
    private String color;
    private int icon;
    private String moodName;
    private String moodNum;

    public Mood(String color, int icon, String moodName, String moodNum) {
        this.color = color;
        this.icon = icon;
        this.moodName = moodName;
        this.moodNum = moodNum;
    }

    public String getColor() {
        return color;
    }

    public int getIcon() {
        return icon;
    }

    public String getMoodName() {
        return moodName;
    }

    public String getMoodNum() {
        return moodNum;
    }
}


